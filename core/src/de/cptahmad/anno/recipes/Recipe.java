package de.cptahmad.anno.recipes;

import de.cptahmad.anno.items.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Recipe implements Iterable<ItemStack>
{
    private final ArrayList<ItemStack> m_requiredItems;
    public final  long                 buildingTime;

    public Recipe(List<ItemStack> items, long buildingTime)
    {
        for (ItemStack stack : items)
        {
            if (!stack.isImmutable)
            {
                throw new IllegalArgumentException("a recipe can only consist of immutable item stacks");
            }
        }

        m_requiredItems = new ArrayList<>(items);
        this.buildingTime = buildingTime;
    }

    @Override
    public Iterator<ItemStack> iterator()
    {
        return m_requiredItems.iterator();
    }
}
