package de.cptahmad.anno.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.cptahmad.anno.world.tiles.AbstractTile;
import de.cptahmad.anno.world.tiles.Tile;

public class Chunk
{
    public final static int CHUNK_SIZE = 32;

    private final int x, y;

    private Tile[][] m_tiles;

    public Chunk(Tile[][] tiles, int x, int y)
    {
        if (tiles == null) throw new IllegalArgumentException("The tiles array cannot be null");
        if (tiles.length != CHUNK_SIZE) throw new IllegalArgumentException("The chunk size has to be 32x32 tiles");
        for (int i = 0; i < CHUNK_SIZE; i++)
        {
            if (tiles[i].length != CHUNK_SIZE)
                throw new IllegalArgumentException("The chunk size has to be 32x32 tiles");
        }

        m_tiles = tiles;
        this.x = x;
        this.y = y;
    }

    public void update(float delta)
    {
        // TODO
    }

    public void render(SpriteBatch batch, float xOffset, float yOffset)
    {
        // TODO don't render chunks that are outside of the screen

        for (int yCoord = 0; yCoord < CHUNK_SIZE; yCoord++)
        {
            for (int xCoord = 0; xCoord < CHUNK_SIZE; xCoord++)
            {
                m_tiles[xCoord][yCoord].render(batch, x * CHUNK_SIZE * AbstractTile.TILE_SIZE,
                                               y * CHUNK_SIZE * AbstractTile.TILE_SIZE);
            }
        }
    }
}
