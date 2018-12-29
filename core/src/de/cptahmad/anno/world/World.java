package de.cptahmad.anno.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import de.cptahmad.anno.entity.npcs.Npc;
import de.cptahmad.anno.eventsystem.EventManager;
import de.cptahmad.anno.items.Inventory;
import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.main.Assets;
import de.cptahmad.anno.util.Point2DInteger;
import de.cptahmad.anno.util.RectangleInt;
import de.cptahmad.anno.world.buildings.implementation.Building;
import de.cptahmad.anno.world.buildings.prototypes.Buildings;
import de.cptahmad.anno.world.buildings.prototypes.PRoad;
import de.cptahmad.anno.world.buildings.prototypes.PrototypeBuilding;
import de.cptahmad.anno.world.tiles.AbstractTile;
import de.cptahmad.anno.world.tiles.Tile;
import de.cptahmad.anno.world.tiles.Tiles;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;

import java.util.ArrayList;

public class World extends InputAdapter
{

    //private       Chunk[][]                          m_chunks    = null;
    private       TiledMap                           m_map;
    private       OrthogonalTiledMapRenderer         m_mapRenderer;
    private final ArrayList<Building>                m_buildings = new ArrayList<>();
    private final ArrayList<Npc>                     m_npcs      = new ArrayList<>();
    private final Graph<Point2DInteger, DefaultEdge> m_roadGraph =
            new DefaultUndirectedWeightedGraph<>(DefaultEdge.class);

    private PrototypeBuilding m_currentlySelectedBuilding = null;

    private final Inventory m_inv;

    private final EventManager m_eventManager;

    /**
     * Mouse position with respect to the world map
     */
    private final Vector3 m_mousePos = new Vector3();

    /**
     * The velocity of the camera movement. Controlled by W, A, S, D keys.
     */
    private final Vector3 m_cameraMovementVelocity = new Vector3();

    /**
     * AbstractTile position with respect to the world map
     */
    private int m_selectedTileX, m_selectedTileY;

    /**
     * Chunk position with respect to the world map
     */
    private int m_selectedChunkX, m_selectedChunkY;

    private OrthographicCamera m_camera = new OrthographicCamera();

    public World(EventManager eManager, Inventory inv)
    {
        m_map = new TmxMapLoader().load("maps/test01.tmx");
        m_mapRenderer = new OrthogonalTiledMapRenderer(m_map, Assets.getSpriteBatch());

        m_inv = inv;
        m_eventManager = eManager;
        m_camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Loads the chunks of the map with the specified filename
     *
     * @param filename the name of the map file
     */
    public void load(String filename)
    {
        // TODO
    }

    /**
     * Only for test purposes
     */
    public void generateWorldRandomly()
    {
        //m_chunks = new Chunk[3][3];

        for (int cx = 0; cx < 3; cx++)
        {
            for (int cy = 0; cy < 3; cy++)
            {
                Tile[][] tiles = new Tile[32][32];

                for (int tx = 0; tx < Chunk.CHUNK_SIZE; tx++)
                {
                    for (int ty = 0; ty < Chunk.CHUNK_SIZE; ty++)
                    {
                        tiles[tx][ty] = new Tile(Tiles.stone, tx, ty);
                    }
                }

                //m_chunks[cx][cy] = new Chunk(tiles, cx, cy);
            }
        }
    }

    public void update(float delta)
    {
        m_camera.position.mulAdd(m_cameraMovementVelocity, delta * 500f);

        m_camera.update();
        Assets.getSpriteBatch().setProjectionMatrix(m_camera.combined);

        m_mapRenderer.setView(m_camera);

        m_mousePos.x = Gdx.input.getX();
        m_mousePos.y = Gdx.input.getY();
        m_camera.unproject(m_mousePos);

        m_selectedTileX = MathUtils.floor(m_mousePos.x / AbstractTile.TILE_SIZE);
        m_selectedTileY = MathUtils.floor(m_mousePos.y / AbstractTile.TILE_SIZE);

        m_selectedChunkX = MathUtils.floor((float) m_selectedTileX / Chunk.CHUNK_SIZE);
        m_selectedChunkY =
                MathUtils.floor((float) m_selectedTileY /
                                Chunk.CHUNK_SIZE); // TODO will lead to bugs, one column could be larger than another

        /*
        for (Chunk[] chunkColumn : m_chunks)
        {
            for (Chunk chunk : chunkColumn)
            {
                chunk.update(delta);
            }
        }
        */
    }

    public void render()
    {
        final SpriteBatch batch = Assets.getSpriteBatch();

        m_mapRenderer.render();

        batch.begin();

        // Draw the tile textures of all chunks currently on the screen
        /*
        for (Chunk[] chunkColumn : m_chunks)
        {
            for (Chunk chunk : chunkColumn)
            {
                chunk.render(batch, m_camera.position.x, m_camera.position.y);
            }
        }
        */

        // Draw the buildings
        m_buildings.forEach(building -> building.render(batch));

        // Draw the tile, which is currently hovered by the mouse
        batch.draw(Assets.getTexture(Asset.SELECTED_TILE), m_selectedTileX * AbstractTile.TILE_SIZE,
                   m_selectedTileY * AbstractTile.TILE_SIZE);

        batch.end();
    }

    private boolean isSuitableForBuilding(int x, int y)
    {
        return isSuitableForBuilding(x, y, 0, 0);
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

    private boolean isSuitableForBuilding(int x, int y, PrototypeBuilding type)
    {
        return isSuitableForBuilding(x, y, type.width, type.height);
    }

    public void setBuildingSelection(PrototypeBuilding selected)
    {
        m_currentlySelectedBuilding = selected;
    }

    private void updateRoadGraph(int x, int y)
    {
        // TODO so much copying ... come on man

        Point2DInteger road = new Point2DInteger(x, y);
        m_roadGraph.addVertex(road);
        Point2DInteger above      = new Point2DInteger(x, y + 1);
        Point2DInteger below      = new Point2DInteger(x, y - 1);
        Point2DInteger leftPoint  = new Point2DInteger(x - 1, y);
        Point2DInteger rightPoint = new Point2DInteger(x + 1, y);

        boolean up    = m_roadGraph.containsVertex(above);
        boolean down  = m_roadGraph.containsVertex(below);
        boolean left  = m_roadGraph.containsVertex(leftPoint);
        boolean right = m_roadGraph.containsVertex(rightPoint);

        if (up)
        {
            m_roadGraph.addEdge(road, above);
            Building building = m_buildings.stream().filter(b -> b.isAt(above.x, above.y)).findFirst().orElse(null);
            if (building == null || !building.is(Buildings.roadTrail))
                throw new IllegalStateException("the road graph and the actual road buildings do not match");

            boolean upup    = m_roadGraph.containsVertex(new Point2DInteger(x, y + 2));
            boolean upleft  = m_roadGraph.containsVertex(new Point2DInteger(x - 1, y + 1));
            boolean upright = m_roadGraph.containsVertex(new Point2DInteger(x + 1, y + 1));

            ((PRoad) building.building).adjustRoadSprite(building.sprite, upup, true, upleft, upright);
        }
        if (down)
        {
            m_roadGraph.addEdge(road, below);
            Building building = m_buildings.stream().filter(b -> b.isAt(below.x, below.y)).findFirst().orElse(null);
            if (building == null || !building.is(Buildings.roadTrail))
                throw new IllegalStateException("the road graph and the actual road buildings do not match");

            boolean downdown  = m_roadGraph.containsVertex(new Point2DInteger(x, y - 2));
            boolean downleft  = m_roadGraph.containsVertex(new Point2DInteger(x - 1, y - 1));
            boolean downright = m_roadGraph.containsVertex(new Point2DInteger(x + 1, y - 1));

            ((PRoad) building.building).adjustRoadSprite(building.sprite, true, downdown, downleft, downright);
        }
        if (left)
        {
            m_roadGraph.addEdge(road, leftPoint);
            Building building =
                    m_buildings.stream().filter(b -> b.isAt(leftPoint.x, leftPoint.y)).findFirst().orElse(null);
            if (building == null || !building.is(Buildings.roadTrail))
                throw new IllegalStateException("the road graph and the actual road buildings do not match");

            boolean leftup   = m_roadGraph.containsVertex(new Point2DInteger(x - 1, y + 1));
            boolean leftdown = m_roadGraph.containsVertex(new Point2DInteger(x - 1, y - 1));
            boolean leftleft = m_roadGraph.containsVertex(new Point2DInteger(x - 2, y));

            ((PRoad) building.building).adjustRoadSprite(building.sprite, leftup, leftdown, leftleft, true);
        }
        if (right)
        {
            m_roadGraph.addEdge(road, rightPoint);
            Building building =
                    m_buildings.stream().filter(b -> b.isAt(rightPoint.x, rightPoint.y)).findFirst().orElse(null);
            if (building == null || !building.is(Buildings.roadTrail))
                throw new IllegalStateException("the road graph and the actual road buildings do not match");

            boolean rightup    = m_roadGraph.containsVertex(new Point2DInteger(x + 1, y + 1));
            boolean rightdown  = m_roadGraph.containsVertex(new Point2DInteger(x + 1, y - 1));
            boolean rightright = m_roadGraph.containsVertex(new Point2DInteger(x + 2, y));

            ((PRoad) building.building).adjustRoadSprite(building.sprite, rightup, rightdown, true, rightright);
        }

        Building building =
                m_buildings.stream().filter(b -> b.isAt(x, y)).findFirst().orElse(null);
        if (building == null || !building.is(Buildings.roadTrail))
            throw new IllegalStateException("the road graph and the actual road buildings do not match");
        ((PRoad) building.building).adjustRoadSprite(building.sprite, up, down, left, right);
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
                m_inv.canBuild(m_currentlySelectedBuilding.getRecipe()))
            {

                m_buildings.add(new Building(m_currentlySelectedBuilding, m_selectedTileX, m_selectedTileY));
                m_inv.useRecipe(m_currentlySelectedBuilding.getRecipe());
                if (m_currentlySelectedBuilding.equals(Buildings.roadTrail))
                {
                    updateRoadGraph(m_selectedTileX, m_selectedTileY);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }
}
