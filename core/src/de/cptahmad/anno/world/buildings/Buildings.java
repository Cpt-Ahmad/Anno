package de.cptahmad.anno.world.buildings;

import com.badlogic.gdx.utils.Array;
import org.jetbrains.annotations.NotNull;

public final class Buildings
{
    private static final Array<AbstractBuilding> s_buildings = new Array<>();

    public static AbstractBuilding houseBasic, roadBasic;

    private Buildings()
    {
    }

    public static void init()
    {
        houseBasic = new House();
        roadBasic = new Road();
    }

    public static AbstractBuilding get(int id)
    {
        if (id >= s_buildings.size) throw new IllegalArgumentException("there is no building with the id " + id);

        return s_buildings.get(id);
    }

    static void add(@NotNull AbstractBuilding building)
    {
        if (s_buildings.size != building.id) throw new IllegalArgumentException(
                String.format("the id of the building is wrong (id: %d, name: %s)", building.id, building.name));

        s_buildings.add(building);
    }

    public static Array<AbstractBuilding> getAllBuildings()
    {
        return s_buildings;
    }
}
