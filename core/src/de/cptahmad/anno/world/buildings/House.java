package de.cptahmad.anno.world.buildings;

import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.recipes.Recipe;
import de.cptahmad.anno.util.Assets;

import java.util.ArrayList;

public class House extends AbstractBuilding
{
    public House()
    {
        super("House", 3, 2, Assets.getTexture(Asset.BUILDING_HOUSE));
    }
}
