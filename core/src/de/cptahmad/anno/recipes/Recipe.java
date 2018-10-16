package de.cptahmad.anno.recipes;

import de.cptahmad.anno.items.Item;
import de.cptahmad.anno.items.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Recipe implements Iterable<Item>
{
    private final Map<Item, Integer> m_components;

    public Recipe(List<ItemStack> componentList)
    {
        if (componentList == null)
            throw new IllegalArgumentException("the component list for the recipe cannot be null");

        m_components = new HashMap<Item, Integer>();
        int index = 0;

        for (ItemStack stack : componentList)
        {
            if (stack == null) throw new IllegalArgumentException("an item stack of a recipe cannot be null");
            if (m_components.containsKey(stack.item))
                throw new IllegalArgumentException("component list of recipe cannot contain duplicate items");

            m_components.put(stack.item, stack.getAmount());
        }
    }

    public int getAmount(Item item)
    {
        return m_components.get(item);
    }

    @Override
    public Iterator<Item> iterator()
    {
        return m_components.keySet().iterator();
    }
}
