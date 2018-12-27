package de.cptahmad.anno.items;

public class ItemStack
{
    public final Item    item;
    public final boolean isImmutable;
    private      int     m_amount;

    public ItemStack(Item item)
    {
        this(item, 0);
    }

    public ItemStack(Item item, int amount)
    {
        this(item, amount, false);
    }

    public ItemStack(Item item, int amount, boolean isImmutable)
    {
        if (item == null) throw new IllegalArgumentException("an item in an item stack cannot be null");
        if (amount < 0) throw new IllegalArgumentException("the amount of an item in an item stack cannot be negative");

        this.item = item;
        this.m_amount = amount;
        this.isImmutable = isImmutable;
    }

    public boolean is(Item item)
    {
        return this.item == item;
    }

    public boolean hasEnough(int needed)
    {
        return m_amount >= needed;
    }

    public boolean isEmpty()
    {
        return m_amount == 0;
    }

    public void add(int amount)
    {
        if (!isImmutable) m_amount += amount;
    }

    public void remove(int amount)
    {
        if (!isImmutable)
        {
            m_amount -= amount;
            if (m_amount < 0) m_amount = 0;
        }
    }


    public int getAmount()
    {
        return m_amount;
    }
}
