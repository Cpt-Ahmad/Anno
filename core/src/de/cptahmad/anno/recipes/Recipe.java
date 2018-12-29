package de.cptahmad.anno.recipes;

import de.cptahmad.anno.items.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

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

    @NotNull
    @Override
    public Iterator<ItemStack> iterator()
    {
        return Collections.unmodifiableList(m_requiredItems).iterator();
    }
}
