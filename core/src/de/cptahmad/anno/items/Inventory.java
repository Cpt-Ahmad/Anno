package de.cptahmad.anno.items;

import de.cptahmad.anno.recipes.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Inventory
{
    private final ArrayList<ItemStack> m_inv = new ArrayList<ItemStack>();

    public Inventory()
    {
    }

    public void add(Item item, int amount)
    {
        for(ItemStack stack : m_inv)
        {
            if(stack.is(item))
            {
                stack.add(amount);
                return;
            }
        }

        m_inv.add(new ItemStack(item, amount));
    }

    public boolean canBuild(Recipe recipe)
    {
        for(ItemStack needed : recipe)
        {
            if(!hasEnough(needed)) return false;
        }

        return true;
    }

    public boolean hasEnough(ItemStack stack)
    {
        return hasEnough(stack.item, stack.getAmount());
    }

    public boolean hasEnough(Item item, int amount)
    {
        if(amount < 0) throw new IllegalArgumentException("the amount cannot be negative");
        if(amount == 0) return true; // you always have at least 0 of any item

        for(ItemStack stack : m_inv)
        {
            if(stack.is(item)) return stack.hasEnough(amount);
        }

        return false;
    }

    public int getAmount(Item item)
    {
        for(ItemStack stack : m_inv)
        {
            if(stack.is(item)) return stack.getAmount();
        }

        return 0;
    }
}
