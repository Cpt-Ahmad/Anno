package de.cptahmad.anno.items;

import java.util.ArrayList;

public class Items
{
    private static final ArrayList<Item> s_items = new ArrayList<Item>();

    public static Item wood, stone;

    private Items()
    {
    }

    public static void init()
    {
        wood = new Resource(0, "Wood", 5);
        stone = new Resource(1, "Stone", 5);
    }

    public static Item get(int id)
    {
        for (Item item : s_items)
        {
            if (item.id == id) return item;
        }

        throw new IllegalArgumentException("there is no item with the id " + id);
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
