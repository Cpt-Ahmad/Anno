package de.cptahmad.anno.world.buildings.prototypes;

import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.main.Assets;

public class PHouse extends PrototypeBuilding
{
    public PHouse(String name)
    {
        super(name, 3, 2, Assets.getTexture(Asset.BUILDING_HOUSE));
    }
}
