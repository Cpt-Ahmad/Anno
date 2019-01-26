package de.cptahmad.anno.entity.items;

import de.cptahmad.anno.entity.Entities;
import de.cptahmad.anno.main.LoadingException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class ItemStack
{
    /**
     * The item this item stack represents
     */
    public final Item    item;
    /**
     * Determines whether or not the amount of items in this stack can be changed
     */
    public final boolean isImmutable;

    /**
     * The amount of the item in this item stack
     */
    private int m_amount;

    /**
     * Creates an empty item stack with the given item
     *
     * @param item the item of the item stack
     */
    public ItemStack(Item item)
    {
        this(item, 0);
    }

    /**
     * Creates an item stack with the given parameters
     *
     * @param item   the item this item stack represents
     * @param amount the initial amount of the item stack
     */
    public ItemStack(Item item, int amount)
    {
        this(item, amount, false);
    }

    /**
     * Creates an item stack with the given parameters and additionally sets the item stack as immutable.
     * Calling any method that would change the amount of the items in this stack will lead to an exception being thrown.
     *
     * @param item        the item this item stack represents
     * @param amount      the final amount of the item stack
     * @param isImmutable true/false
     */
    public ItemStack(Item item, int amount, boolean isImmutable)
    {
        if (item == null) throw new IllegalArgumentException("an item in an item stack cannot be null");
        if (amount < 0) throw new IllegalArgumentException("the amount of an item in an item stack cannot be negative");

        this.item = item;
        this.m_amount = amount;
        this.isImmutable = isImmutable;
    }

    /**
     * @param item the item to check
     * @return true if this is a stack of the given item, false otherwise
     */
    public boolean is(Item item)
    {
        return this.item == item;
    }

    /**
     * @param needed the amount an item that is needed
     * @return true if the item stack is larger or equal to the amount given, false otherwise
     */
    public boolean hasEnough(int needed)
    {
        return m_amount >= needed;
    }

    /**
     * @return true if the item stack is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return m_amount == 0;
    }

    /**
     * Adds the given amount to the current stack
     *
     * @param amount the amount to be added to the existing stack
     * @throws UnsupportedOperationException if the item stack is immutable
     * @throws IllegalArgumentException      if the amount given is not positive
     */
    public void add(int amount)
    {
        if (isImmutable)
            throw new UnsupportedOperationException("the item stack is immutable, you cannot add an amount");
        if (amount < 1)
            throw new IllegalArgumentException("the amount added has to be positive");

        m_amount += amount;
    }

    /**
     * Removes the given amount from the current stack.
     *
     * @param amount the amount to remove from the stack
     * @throws UnsupportedOperationException if the item stack is immutable
     * @throws IllegalStateException         if amount given is higher than the items in the stack
     * @throws IllegalArgumentException      if the amount given is not positive
     */
    public void remove(int amount)
    {
        if (isImmutable)
            throw new UnsupportedOperationException("the item stack is immutable, you cannot remove an amount");
        if (amount < 1)
            throw new IllegalArgumentException("the amount, which should be subtracted, has to be positive");
        if (!hasEnough(amount))
            throw new IllegalStateException("there were not enough items in the item stack");

        m_amount -= amount;
    }

    /**
     * @return the current amount of items in the stack
     */
    public int getAmount()
    {
        return m_amount;
    }

    @Override
    public String toString()
    {
        return item.toString() + ": " + m_amount;
    }

    /**
     * Converts a map (item names -> amount) to an {@link ArrayList} with {@link ItemStack}
     *
     * @param file the map which should be converted to a list
     * @param isImmutable true if all item stacks in the returned list should be immutable
     * @return a list with item stacks corresponding to the given map
     */
    @NotNull
    public static ArrayList<ItemStack> loadItemStackFromMap(@NotNull Map file, boolean isImmutable)
    {
        ArrayList<ItemStack> list = new ArrayList<>();

        for (Object itemKey : file.keySet())
        {
            Item item   = Entities.getItem((String) itemKey);
            int  amount = (int) file.get(itemKey);
            if (amount < 1) throw new LoadingException(
                    "building.yaml: the amount of an item needed for a recipe has to be positive");
            list.add(new ItemStack(item, amount, isImmutable));
        }

        return list;
    }
}
