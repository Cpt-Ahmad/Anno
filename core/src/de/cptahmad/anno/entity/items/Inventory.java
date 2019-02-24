package de.cptahmad.anno.entity.items;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import de.cptahmad.anno.entity.components.Recipe;
import de.cptahmad.anno.main.Assets;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Inventory
{
    private final Array<ItemStack> m_inv = new Array<>();
    private final List<ItemStack>  m_invAsList;

    public Inventory()
    {
        m_invAsList = new List<>(Assets.getSkin());
        m_invAsList.setItems(m_inv);
    }

    public void add(@NotNull Item item, int amount)
    {
        for (ItemStack stack : m_inv)
        {
            if (stack.is(item))
            {
                stack.add(amount);
                m_invAsList.setItems(m_inv);
                return;
            }
        }

        m_inv.add(new ItemStack(item, amount));
        m_invAsList.setItems(m_inv);
    }

    public void remove(@NotNull ItemStack stack)
    {
        for(ItemStack invStack : m_inv)
        {
            if(invStack.is(stack.item))
            {
                invStack.remove(stack.getAmount());
                return;
            }
        }
    }

    public boolean canBuild(Recipe recipe)
    {
        if(recipe == null) return true;

        for (ItemStack needed : recipe)
        {
            if (!hasEnough(needed)) return false;
        }

        return true;
    }

    public boolean hasEnough(@NotNull ItemStack stack)
    {
        return hasEnough(stack.item, stack.getAmount());
    }

    public boolean hasEnough(@NotNull Item item, int amount)
    {
        if (amount < 0) throw new IllegalArgumentException("the amount cannot be negative");
        if (amount == 0) return true; // you always have at least 0 of any item

        for (ItemStack stack : m_inv)
        {
            if (stack.is(item)) return stack.hasEnough(amount);
        }

        return false;
    }

    public void useRecipe(Recipe recipe)
    {
        if(recipe == null) return;
        for(ItemStack stack : recipe) remove(stack);
    }

    public int getAmount(@NotNull Item item)
    {
        for (ItemStack stack : m_inv)
        {
            if (stack.is(item)) return stack.getAmount();
        }

        return 0;
    }

    public List<ItemStack> getList()
    {
        return m_invAsList;
    }

    public Map<String, Integer> getInventorySaveData()
    {
        Map<String, Integer> data  = new HashMap<>();
        for(ItemStack stack : m_inv)
        {
            data.put(stack.item.name, stack.getAmount());
        }
        return data;
    }

    public void clear()
    {
        m_inv.clear();
        m_invAsList.setItems(m_inv);
    }
}
