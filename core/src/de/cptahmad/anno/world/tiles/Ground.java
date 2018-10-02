package de.cptahmad.anno.world.tiles;

import com.badlogic.gdx.graphics.Texture;
import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.util.Assets;

import java.util.Random;

public class Ground extends Tile
{
    public enum Type
    {
        GRASS(Assets.getTexture(Asset.TILE_GRASS)),
        STONE(Assets.getTexture(Asset.TILE_STONE));

        private final static Random random = new Random();

        public final Texture texture;

        Type(Texture texture)
        {
            this.texture = texture;
        }

        public static Type randomType()
        {
            return values()[random.nextInt(values().length)];
        }
    }

    private Type m_groundType;

    public Ground(int x, int y, Type groundType)
    {
        super(x, y, TileType.GROUND, groundType.texture);
        m_groundType = groundType;
    }

    @Override
    public void update(float delta)
    {
        // TODO
    }
}
