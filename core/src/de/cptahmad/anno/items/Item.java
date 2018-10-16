package de.cptahmad.anno.items;

public abstract class Item
{
    public final int id, value;

    public final String name;

    Item(int id, String name, int value)
    {
        this.id = id;
        this.value = value;
        this.name = name;

        Items.add(this);
    }

    @Override
    public String toString()
    {
        return "Item{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
