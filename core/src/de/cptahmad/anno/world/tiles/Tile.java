package de.cptahmad.anno.world.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.jetbrains.annotations.NotNull;

public final class Tile
{
    public final AbstractTile tile;
    public final int          x, y;

    public Tile(@NotNull AbstractTile tile, int x, int y)
    {
        this.tile = tile;
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch, float chunkOffsetX, float chunkOffsetY)
    {
        batch.draw(tile.texture, chunkOffsetX + x * AbstractTile.TILE_SIZE, chunkOffsetY + y * AbstractTile.TILE_SIZE);
    }
}
