package de.cptahmad.anno.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import de.cptahmad.anno.entity.Entities;
import de.cptahmad.anno.entity.buildings.Building;
import de.cptahmad.anno.entity.buildings.PrototypeBuilding;
import de.cptahmad.anno.entity.components.DimensionWorld;
import de.cptahmad.anno.entity.components.Recipe;
import de.cptahmad.anno.entity.items.Inventory;
import de.cptahmad.anno.entity.npcs.Npc;
import de.cptahmad.anno.eventsystem.eventlisteners.BuildingConstructedListener;
import de.cptahmad.anno.eventsystem.events.DisplayMessageEvent;
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
    /**
     * the width and height of one tile given in pixels
     */
    public static final int TILE_SIZE = 32;

    /**
     * Handles the Tiled map object and the renderer
     */
    private final Map m_map;

    /**
     * List with all buildings currently on the map
     */
    private final ArrayList<Building> m_buildings;

    /**
     * List with all NPCs on the map
     */
    private final ArrayList<Npc> m_npcs;

    /**
     * the road graph used by the NPCs to traverse the map
     */
    private final Graph<Point2DInteger, DefaultEdge> m_roadGraph =
            new DefaultUndirectedGraph<>(DefaultEdge.class);

    /**
     * the currently selected building, i.e. if clicked on a free area on the map this building is constructed
     */
    private PrototypeBuilding m_currentlySelectedBuilding = null;

    /**
     * Inventory of the player
     */
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

    /**
     * the orthographic camera used by the map and the window
     */
    private OrthographicCamera m_camera = new OrthographicCamera();

    public World(ArrayList<Building> buildings, ArrayList<Npc> npcs,
                 Inventory inv)
    {
        m_buildings = buildings;
        m_npcs = npcs;
        m_inv = inv;
        m_map = new Map("test01.tmx", Assets.getSpriteBatch());
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
                return true; // TODO cheating purposes
            case Input.Keys.SPACE:
                m_inv.add(Entities.getItem("stone"), 500);
                m_inv.add(Entities.getItem("wood_raw"), 500);
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
            if (isSuitableForBuilding(m_selectedTileX, m_selectedTileY, m_currentlySelectedBuilding))
            {
                if (m_inv.canBuild(m_currentlySelectedBuilding.getComponent(Recipe.class)))
                {
                    Building constructedBuilding =
                            new Building(m_currentlySelectedBuilding, m_selectedTileX, m_selectedTileY);
                    m_buildings.add(constructedBuilding);
                    m_inv.useRecipe(m_currentlySelectedBuilding.getComponent(Recipe.class));
                } else
                {
                    Assets.getEventManager()
                          .addEvent(new DisplayMessageEvent("Not enough resources to construct building."));
                }
            } else
            {
                Assets.getEventManager()
                      .addEvent(new DisplayMessageEvent("Cannot build there. Area already occupied."));
            }
            return true;

        } else if (button == Input.Buttons.RIGHT)
        {
            // TODO only for debugging purposes
            Npc boris = new Npc("Boris", Asset.WOODCUTTER);
            boris.setPosition(32, 32);
            m_npcs.add(boris);

            DijkstraShortestPath<Point2DInteger, DefaultEdge> dsp = new DijkstraShortestPath<>(m_roadGraph);
            List<Point2DInteger> path =
                    dsp.getPath(new Point2DInteger(boris.getXPositionInMapGrid(), boris.getYPositionInMapGrid()),
                                new Point2DInteger(m_selectedTileX, m_selectedTileY)).getVertexList();

            boris.move(path);

        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        m_mousePos.x = screenX;
        m_mousePos.y = screenY;
        m_camera.unproject(m_mousePos);

        m_selectedTileX = MathUtils.floor(m_mousePos.x / World.TILE_SIZE);
        m_selectedTileY = MathUtils.floor(m_mousePos.y / World.TILE_SIZE);

        return true;
    }
}
