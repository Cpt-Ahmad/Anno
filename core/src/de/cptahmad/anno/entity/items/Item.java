package de.cptahmad.anno.entity.items;

import de.cptahmad.anno.entity.Prototype;
import org.jetbrains.annotations.NotNull;

public abstract class Item extends Prototype
{
    public Item(@NotNull String name)
    {
        super(name);
    }
}
