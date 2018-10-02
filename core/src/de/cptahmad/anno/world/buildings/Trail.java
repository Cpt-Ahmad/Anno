package de.cptahmad.anno.world.buildings;

import com.badlogic.gdx.graphics.Texture;
import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.util.Assets;

public class Trail extends Building
{
    public Trail(int x, int y)
    {
        super(x, y, BuildingType.ROAD_TRAIL, Assets.getTexture(Asset.ROAD_TRAIL));
    }
}
