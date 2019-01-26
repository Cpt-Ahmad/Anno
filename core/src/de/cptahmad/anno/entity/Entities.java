package de.cptahmad.anno.entity;

import de.cptahmad.anno.entity.buildings.Buildings;
import de.cptahmad.anno.entity.buildings.PrototypeBuilding;
import de.cptahmad.anno.entity.items.Item;
import de.cptahmad.anno.entity.items.Items;

import java.util.Map;

public final class Entities
{
    private final static Items s_items = new Items();
    private final static Buildings s_buildings = new Buildings();

    private Entities()
    {
    }

    public static void init(Map<String, Object> fileItems, Map<String, Object> fileBuildings)
    {
        s_items.init(fileItems);
        s_buildings.init(fileBuildings);
    }

    public static Item getItem(String name)
    {
        return s_items.get(name);
    }

    public static PrototypeBuilding getBuilding(String name)
    {
        return s_buildings.get(name);
    }
}
