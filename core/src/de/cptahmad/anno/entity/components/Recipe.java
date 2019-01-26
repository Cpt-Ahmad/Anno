package de.cptahmad.anno.entity.components;

import com.badlogic.ashley.core.Component;
import de.cptahmad.anno.entity.items.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Recipe implements Iterable<ItemStack>, Component
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
