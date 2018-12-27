package de.cptahmad.anno.world.tiles;

import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.util.Assets;

public class Stone extends Ground
{
    public Stone(int id)
    {
        super(id, "stone", Assets.getTexture(Asset.TILE_STONE));
    }

    @Override
    public void update(float delta)
    {
    }
}
