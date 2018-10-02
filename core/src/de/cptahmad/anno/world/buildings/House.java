package de.cptahmad.anno.world.buildings;

import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.util.Assets;

public class House extends Building
{
    public House(int x, int y)
    {
        super(x, y, BuildingType.HOUSE, Assets.getTexture(Asset.BUILDING_HOUSE));
    }
}
