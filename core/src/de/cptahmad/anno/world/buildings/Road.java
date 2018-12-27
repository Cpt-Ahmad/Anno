package de.cptahmad.anno.world.buildings;

import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.util.Assets;

public class Road extends AbstractBuilding
{
    public Road()
    {
        super("Road", 0, 0, Assets.getTexture(Asset.ROAD_TRAIL));
    }
}
