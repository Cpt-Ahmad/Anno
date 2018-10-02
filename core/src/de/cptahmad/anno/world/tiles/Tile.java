package de.cptahmad.anno.world.tiles;

import com.badlogic.gdx.graphics.Texture;
import de.cptahmad.anno.util.Assets;

public abstract class Tile
{
    public final static int TILE_SIZE = 32;

    public final TileType type;

    public final int x, y;

    private Texture texture;

    public Tile(int x, int y, TileType type)
    {
        this(x, y, type, null);
    }

    public Tile(int x, int y, TileType type, Texture texture)
    {
        this.x = x;
        this.y = y;
        this.type = type;
        this.texture = texture;
    }

    public abstract void update(float delta);

    public void render(float chunkOffsetX, float chunkOffsetY)
    {
        if (texture == null) return;

        Assets.getSpriteBatch().draw(texture, chunkOffsetX + x * TILE_SIZE, chunkOffsetY + y * TILE_SIZE);
    }
}
