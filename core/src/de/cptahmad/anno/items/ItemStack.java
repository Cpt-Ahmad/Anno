package de.cptahmad.anno.items;

public class ItemStack
{
    public final Item item;
    private int       m_amount;

    public ItemStack(Item item)
    {
        this(item, 0);
    }

    public ItemStack(Item item, int amount)
    {
        if(item == null) throw new IllegalArgumentException("an item in an item stack cannot be null");
        if(amount < 0) throw new IllegalArgumentException("the amount of an item in an item stack cannot be negative");

        this.item = item;
        this.m_amount = amount;
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
        m_amount += amount;
    }

    public void remove(int amount)
    {
        m_amount -= amount;
        if(m_amount < 0) m_amount = 0;
    }


    public int getAmount()
    {
        return m_amount;
    }
}
