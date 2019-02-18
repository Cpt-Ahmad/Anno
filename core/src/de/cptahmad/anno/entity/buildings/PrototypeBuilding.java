package de.cptahmad.anno.entity.buildings;

import de.cptahmad.anno.entity.Prototype;
import org.jetbrains.annotations.NotNull;

public abstract class PrototypeBuilding extends Prototype
{
    public enum Type
    {
        ROAD,
        HOUSE,
        RESOURCE,
        ;
    }

    public final Type type;

    public PrototypeBuilding(@NotNull String name, Type type)
    {
        super(name);
        this.type = type;
    }
}
