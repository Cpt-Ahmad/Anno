package de.cptahmad.anno.world.buildings;

import com.badlogic.gdx.graphics.Texture;
import de.cptahmad.anno.util.Assets;
import de.cptahmad.anno.util.RectangleInt;
import de.cptahmad.anno.world.tiles.Tile;

public abstract class Building
{
    private final RectangleInt m_hitbox;

    protected Texture m_texture;

    public final BuildingType type;

    public Building(int x, int y, BuildingType type, Texture texture)
    {
        if (texture == null) throw new IllegalArgumentException("the texture cannot be null");

        m_hitbox = new RectangleInt(x, y, type.width, type.height);

        m_texture = texture;
        this.type = type;
    }

    public void render()
    {
        Assets.getSpriteBatch().draw(m_texture, m_hitbox.x * Tile.TILE_SIZE, m_hitbox.y * Tile.TILE_SIZE);
    }

    public boolean isAreaOccupied(RectangleInt hitbox)
    {
        return m_hitbox.intersects(hitbox);
    }
}
