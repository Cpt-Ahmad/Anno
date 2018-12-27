package de.cptahmad.anno.items;

import com.badlogic.gdx.utils.Array;
import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.util.Assets;

import java.util.ArrayList;

public final class Items
{
    private static final Array<Item> s_items = new Array<>();

    public static Item wood, stone;

    private Items()
    {
    }

    public static void init()
    {
        stone = new Resource(0, "Stone", 5, Assets.getTexture(Asset.ITEM_STONE));
    }

    public static Item get(int id)
    {
        if(id < 0) throw new IllegalArgumentException("id of a tile cannot be negative");
        else if(id > s_items.size) throw new IllegalArgumentException("there is not tile with the id " + id);

        return s_items.get(id);
    }

    static void add(Item item)
    {
        for (Item it : s_items)
        {
            if (it.id == item.id) throw new IllegalArgumentException("there is already an item with the id " + item.id);
        }

        s_items.add(item);
    }
}
