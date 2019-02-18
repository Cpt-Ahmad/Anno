package de.cptahmad.anno.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import de.cptahmad.anno.entity.buildings.Building;
import de.cptahmad.anno.entity.buildings.BuildingComparatorForRendering;
import de.cptahmad.anno.entity.buildings.PrototypeBuilding;
import de.cptahmad.anno.entity.components.DimensionWorld;
import de.cptahmad.anno.entity.components.Recipe;
import de.cptahmad.anno.entity.items.Inventory;
import de.cptahmad.anno.entity.npcs.Npc;
import de.cptahmad.anno.eventsystem.eventlisteners.BuildingConstructedListener;
import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.main.Assets;
import de.cptahmad.anno.util.Point2DInteger;
import de.cptahmad.anno.util.RectangleInt;
import org.jetbrains.annotations.NotNull;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.ArrayList;
import java.util.List;

public class World extends InputAdapter
{
    public static final int TILE_SIZE = 32;

    private final Map                                m_map;
    private final ArrayList<Building>                m_buildings = new ArrayList<>();
    private final ArrayList<Npc>                     m_npcs      = new ArrayList<>();
    private final Graph<Point2DInteger, DefaultEdge> m_roadGraph =
            new DefaultUndirectedGraph<>(DefaultEdge.class);

    private PrototypeBuilding m_currentlySelectedBuilding = null;

    private BuildingComparatorForRendering buildingComparatorForRendering = new BuildingComparatorForRendering();

    private final Inventory m_inv;

    /**
     * Mouse position with respect to the world map
     */
    private final Vector3 m_mousePos = new Vector3();

    /**
     * The velocity of the camera movement. Controlled by W, A, S, D keys.
     */
    private final Vector3 m_cameraMovementVelocity = new Vector3();

    /**
     * World position with respect to the world map
     */
    private int m_selectedTileX, m_selectedTileY;

    private OrthographicCamera m_camera = new OrthographicCamera();

    public World(Inventory inv)
    {
        m_map = new Map("test01.tmx", Assets.getSpriteBatch());

        m_inv = inv;
        m_camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    public void onEnter()
    {
        Assets.getEventManager().addListener(new BuildingConstructedListener(m_roadGraph, m_buildings));
    }

    public void update(float delta)
    {
        m_camera.position.mulAdd(m_cameraMovementVelocity, delta * 500f);

        m_camera.update();
        Assets.getSpriteBatch().setProjectionMatrix(m_camera.combined);

        m_map.update(m_camera);
        m_npcs.forEach(npc -> npc.update(delta));

        m_mousePos.x = Gdx.input.getX();
        m_mousePos.y = Gdx.input.getY();
        m_camera.unproject(m_mousePos);

        m_selectedTileX = MathUtils.floor(m_mousePos.x / World.TILE_SIZE);
        m_selectedTileY = MathUtils.floor(m_mousePos.y / World.TILE_SIZE);

        m_buildings.sort(buildingComparatorForRendering);
    }

    public void render()
    {
        final SpriteBatch batch = Assets.getSpriteBatch();

        m_map.render();

        batch.begin();

        // Draw the buildings
        m_buildings.forEach(building -> building.render(batch));

        // Draw the NPCs
        m_npcs.forEach(npc -> npc.render(batch));

        // Draw the tile, which is currently hovered by the mouse
        batch.draw(Assets.getTexture(Asset.SELECTED_TILE), m_selectedTileX * World.TILE_SIZE,
                   m_selectedTileY * World.TILE_SIZE);

        batch.end();
    }

    private boolean isSuitableForBuilding(int x, int y)
    {
        return isSuitableForBuilding(x, y, 0, 0);
    }

    private boolean isSuitableForBuilding(int x, int y, @NotNull PrototypeBuilding type)
    {
        DimensionWorld dimWorld = type.getComponent(DimensionWorld.class);
        return isSuitableForBuilding(x, y, dimWorld.width, dimWorld.height);
    }

    private boolean isSuitableForBuilding(int x, int y, int width, int height)
    {
        final RectangleInt hitbox = new RectangleInt(x, y, width, height);
        for (Building building : m_buildings)
        {
            if (building.isAreaOccupied(hitbox))
            {
                return false;
            }
        }
        return true;
    }

    public void setBuildingSelection(PrototypeBuilding selected)
    {
        m_currentlySelectedBuilding = selected;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.W:
                m_cameraMovementVelocity.y += 1f;
                return true;
            case Input.Keys.S:
                m_cameraMovementVelocity.y -= 1f;
                return true;
            case Input.Keys.A:
                m_cameraMovementVelocity.x -= 1f;
                return true;
            case Input.Keys.D:
                m_cameraMovementVelocity.x += 1f;
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean keyUp(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.W:
                m_cameraMovementVelocity.y -= 1f;
                return true;
            case Input.Keys.S:
                m_cameraMovementVelocity.y += 1f;
                return true;
            case Input.Keys.A:
                m_cameraMovementVelocity.x += 1f;
                return true;
            case Input.Keys.D:
                m_cameraMovementVelocity.x -= 1f;
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        if (button == Input.Buttons.LEFT)
        {
            if (isSuitableForBuilding(m_selectedTileX, m_selectedTileY, m_currentlySelectedBuilding) &&
                m_inv.canBuild(m_currentlySelectedBuilding.getComponent(Recipe.class)))
            {
                Building constructedBuilding =
                        new Building(m_currentlySelectedBuilding, m_selectedTileX, m_selectedTileY);
                m_buildings.add(constructedBuilding);
                m_inv.useRecipe(m_currentlySelectedBuilding.getComponent(Recipe.class));
            }
            return true;
        } else if (button == Input.Buttons.RIGHT)
        {
            // TODO only debug -> Boris should be able to travers the given route

            Npc boris = new Npc("Boris", Asset.WOODCUTTER.getTexture());
            boris.setPosition(32, 32);
            m_npcs.add(boris);

            DijkstraShortestPath<Point2DInteger, DefaultEdge> dsp = new DijkstraShortestPath<>(m_roadGraph);
            List<Point2DInteger> path =
                    dsp.getPath(new Point2DInteger(1, 1), new Point2DInteger(5, 5)).getVertexList();
            System.out.println(path.toString());

            boris.move(path);

        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }
}
