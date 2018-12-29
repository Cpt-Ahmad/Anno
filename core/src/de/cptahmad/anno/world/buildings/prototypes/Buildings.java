package de.cptahmad.anno.world.buildings.prototypes;

import com.badlogic.gdx.utils.Array;
import de.cptahmad.anno.items.Item;
import de.cptahmad.anno.items.ItemStack;
import de.cptahmad.anno.items.Items;
import de.cptahmad.anno.main.LoadingException;
import de.cptahmad.anno.recipes.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Buildings
{
    private static final Array<PrototypeBuilding>           s_buildings       = new Array<>();
    private static final HashMap<PrototypeBuilding, Recipe> s_buildingRecipes = new HashMap<>();

    public static PrototypeBuilding houseBasic, roadTrail;

    private Buildings()
    {
    }

    public static void init(Map<String, Map<String, Integer>> recipes)
    {
        // load buildings
        houseBasic = new PHouse("house-basic");
        roadTrail = new PRoad("road-trail");

        // load building recipes
        for (String buildingName : recipes.keySet())
        {
            PrototypeBuilding building = get(buildingName);

            Map<String, Integer> ingredients = recipes.get(buildingName);

            long                 constructionTime = -1;
            ArrayList<ItemStack> itemsReq         = new ArrayList<>();

            for (String ingName : ingredients.keySet())
            {
                switch (ingName)
                {
                    case "time":
                        constructionTime = ingredients.get(ingName);
                        if (constructionTime < 0) throw new LoadingException(
                                "construction time of a building cannot be negative: " + buildingName);
                        break;
                    default:
                        Item item = Items.get(ingName);
                        int amount = ingredients.get(ingName);
                        if (amount < 1) throw new LoadingException(
                                "the amount of an item needed for a recipe has to be positive: " + buildingName);
                        itemsReq.add(new ItemStack(item, amount, true));
                }
            }

            if (constructionTime == -1)
                throw new LoadingException("building recipe does not have a construction time: " + buildingName);

            s_buildingRecipes.put(building, new Recipe(itemsReq, constructionTime));
        }
    }

    public static PrototypeBuilding get(int id)
    {
        if (id >= s_buildings.size) throw new IllegalArgumentException("there is no building with the id " + id);

        return s_buildings.get(id);
    }

    public static PrototypeBuilding get(String name)
    {
        for (PrototypeBuilding building : s_buildings)
        {
            if (building.name.equals(name))
            {
                return building;
            }
        }

        throw new IllegalArgumentException("there is no building called " + name);
    }

    public static Recipe getRecipe(@NotNull PrototypeBuilding building)
    {
        return s_buildingRecipes.get(building);
    }

    static void add(@NotNull PrototypeBuilding building)
    {
        if (s_buildings.size != building.id) throw new IllegalArgumentException(
                String.format("the id of the building is wrong (id: %d, name: %s)", building.id, building.name));

        s_buildings.add(building);
    }

    public static Array<PrototypeBuilding> getAllBuildings()
    {
        return s_buildings;
    }
}
