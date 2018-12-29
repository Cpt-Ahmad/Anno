package de.cptahmad.anno.items;

import com.badlogic.gdx.utils.Array;
import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.main.Assets;
import de.cptahmad.anno.main.LoadingException;
import de.cptahmad.anno.recipes.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Items
{
    private static final Array<Item>           s_items       = new Array<>();
    private static final HashMap<Item, Recipe> s_itemRecipes = new HashMap<>();

    public static Item stone, stoneBrick, rawWood;

    private Items()
    {
    }

    public static void init(Map<String, Map<String, Integer>> recipes)
    {
        // load items
        stone = new Resource(0, "stone", 5, Assets.getTexture(Asset.ITEM_STONE));
        stoneBrick = new Resource(1, "stone-brick", 10, Assets.getTexture(Asset.ITEM_STONE));
        rawWood = new Resource(2, "raw-wood", 5, Assets.getTexture(Asset.ITEM_RAW_WOOD));

        // load item recipes
        for (String itemName : recipes.keySet())
        {
            Item building = get(itemName);

            Map<String, Integer> ingredients = recipes.get(itemName);

            long                 constructionTime = -1;
            ArrayList<ItemStack> itemsReq         = new ArrayList<>();

            for (String ingName : ingredients.keySet())
            {
                switch (ingName)
                {
                    case "time":
                        constructionTime = ingredients.get(ingName);
                        if (constructionTime < 0) throw new LoadingException(
                                "construction time of an item cannot be negative: " + itemName);
                        break;
                    default:
                        Item item = Items.get(ingName);
                        int amount = ingredients.get(ingName);
                        if (amount < 1) throw new LoadingException(
                                "the amount of an item needed for a recipe has to be positive: " + itemName);
                        itemsReq.add(new ItemStack(item, amount, true));
                }
            }

            if (constructionTime == -1)
                throw new LoadingException("item recipe does not have a construction time: " + itemName);

            s_itemRecipes.put(building, new Recipe(itemsReq, constructionTime));
        }
    }

    public static Item get(int id)
    {
        if (id < 0) throw new IllegalArgumentException("id of a tile cannot be negative");
        else if (id > s_items.size) throw new IllegalArgumentException("there is not tile with the id " + id);

        return s_items.get(id);
    }

    public static Item get(String name)
    {
        for (Item item : s_items)
        {
            if (item.name.equals(name))
            {
                return item;
            }
        }

        throw new IllegalArgumentException("there is no item called " + name);
    }

    public static Recipe getRecipe(@NotNull Item item)
    {
        return s_itemRecipes.get(item);
    }

    static void add(@NotNull Item item)
    {
        for (Item it : s_items)
        {
            if (it.id == item.id) throw new IllegalArgumentException("there is already an item with the id " + item.id);
        }

        s_items.add(item);
    }

    public static Array<Item> getAllItems()
    {
        return s_items;
    }
}
