package de.cptahmad.anno.entity.components;

import com.badlogic.ashley.core.Component;
import de.cptahmad.anno.entity.items.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Yield implements Component
{
    private final ArrayList<ItemStack> yield;

    public Yield(ArrayList<ItemStack> yield)
    {
        for (ItemStack stack : yield)
        {
            if (!stack.isImmutable)
                throw new IllegalArgumentException("all item stacks of the Yield-Component have to be immutable");
        }
        this.yield = yield;
    }

    public List<ItemStack> getYield()
    {
        return Collections.unmodifiableList(yield);
    }
}
