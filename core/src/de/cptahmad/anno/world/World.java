package de.cptahmad.anno.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.util.Assets;
import de.cptahmad.anno.util.RectangleInt;
import de.cptahmad.anno.world.buildings.Building;
import de.cptahmad.anno.world.buildings.BuildingType;
import de.cptahmad.anno.world.buildings.House;
import de.cptahmad.anno.world.buildings.Trail;
import de.cptahmad.anno.world.tiles.Ground;
import de.cptahmad.anno.world.tiles.Tile;

import java.util.ArrayList;

public class World extends InputAdapter
{

    private enum Direction
    {
        NONE,
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private Chunk[][]           m_chunks    = null;
    private ArrayList<Building> m_buildings = new ArrayList<Building>();

    private Direction m_direction = Direction.NONE;

    private BuildingType m_currentlySelectedBuilding = null;

    /**
     * Mouse position with respect to the world map
     */
    private Vector3 m_mousePos = new Vector3();

    /**
     * Tile position with respect to the world map
     */
    private int m_selectedTileX, m_selectedTileY;

    /**
     * Chunk position with respect to the world map
     */
    private int m_selectedChunkX, m_selectedChunkY;

    private OrthographicCamera m_camera = new OrthographicCamera();

    public World()
    {
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
    public void generateWorldRandomly(Ground.Type type)
    {
        m_chunks = new Chunk[3][3];

        for (int cx = 0; cx < 3; cx++)
        {
            for (int cy = 0; cy < 3; cy++)
            {
                Tile[][] tiles = new Tile[32][32];

                for (int tx = 0; tx < Chunk.CHUNK_SIZE; tx++)
                {
                    for (int ty = 0; ty < Chunk.CHUNK_SIZE; ty++)
                    {
                        if (type == null) tiles[tx][ty] = new Ground(tx, ty, Ground.Type.randomType());
                        else tiles[tx][ty] = new Ground(tx, ty, type);
                    }
                }

                m_chunks[cx][cy] = new Chunk(tiles, cx, cy);
            }
        }
    }

    public void update(float delta)
    {
        float mapMovementSpeed = 500f;

        switch (m_direction)
        {
            case NONE:
                break;
            case UP:
                m_camera.position.y += delta * mapMovementSpeed;
                break;
            case DOWN:
                m_camera.position.y -= delta * mapMovementSpeed;
                break;
            case LEFT:
                m_camera.position.x -= delta * mapMovementSpeed;
                break;
            case RIGHT:
                m_camera.position.x += delta * mapMovementSpeed;
                break;
        }

        m_camera.update();
        Assets.getSpriteBatch().setProjectionMatrix(m_camera.combined);

        m_mousePos.x = Gdx.input.getX();
        m_mousePos.y = Gdx.input.getY();
        m_camera.unproject(m_mousePos);

        m_selectedTileX = MathUtils.floor(m_mousePos.x / Tile.TILE_SIZE);
        m_selectedTileY = MathUtils.floor(m_mousePos.y / Tile.TILE_SIZE);

        m_selectedChunkX = MathUtils.floor((float) m_selectedTileX / Chunk.CHUNK_SIZE);
        m_selectedChunkY =
                MathUtils.floor((float) m_selectedTileY /
                                Chunk.CHUNK_SIZE); // TODO will lead to bugs, one column could be larger than another

        for (Chunk[] chunkColumn : m_chunks)
        {
            for (Chunk chunk : chunkColumn)
            {
                chunk.update(delta);
            }
        }
    }

    public void render()
    {
        SpriteBatch batch = Assets.getSpriteBatch();

        batch.begin();

        // Draw the tile textures of all chunks currently on the screen
        for (Chunk[] chunkColumn : m_chunks)
        {
            for (Chunk chunk : chunkColumn)
            {
                chunk.render(m_camera.position.x, m_camera.position.y);
            }
        }

        // Draw the buildings
        for (Building building : m_buildings)
        {
            building.render();
        }

        // Draw the tile, which is currently hovered by the mouse
        batch.draw(Assets.getTexture(Asset.SELECTED_TILE), m_selectedTileX * Tile.TILE_SIZE,
                   m_selectedTileY * Tile.TILE_SIZE);

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

    private boolean isSuitableForBuilding(int x, int y, BuildingType type)
    {
        return isSuitableForBuilding(x, y, type.width, type.height);
    }

    public void setBuildingSelection(BuildingType selected)
    {
        m_currentlySelectedBuilding = selected;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.W:
                m_direction = Direction.UP;
                return true;
            case Input.Keys.S:
                m_direction = Direction.DOWN;
                return true;
            case Input.Keys.A:
                m_direction = Direction.LEFT;
                return true;
            case Input.Keys.D:
                m_direction = Direction.RIGHT;
                return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        // TODO add diagonal movement

        switch (keycode)
        {
            case Input.Keys.W:
                if (m_direction == Direction.UP) m_direction = Direction.NONE;
                return true;
            case Input.Keys.S:
                if (m_direction == Direction.DOWN) m_direction = Direction.NONE;
                return true;
            case Input.Keys.A:
                if (m_direction == Direction.LEFT) m_direction = Direction.NONE;
                return true;
            case Input.Keys.D:
                if (m_direction == Direction.RIGHT) m_direction = Direction.NONE;
                return true;
        }

        return false;
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
                switch(m_currentlySelectedBuilding)
                {
                    case HOUSE:
                        m_buildings.add(new House(m_selectedTileX, m_selectedTileY));
                        break;
                    case ROAD_TRAIL:
                        m_buildings.add(new Trail(m_selectedTileX, m_selectedTileY));
                        break;
                }
            }
            return true;
        }
        return false;
    }
}
