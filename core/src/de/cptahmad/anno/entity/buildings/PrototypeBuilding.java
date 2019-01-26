package de.cptahmad.anno.entity.buildings;

import de.cptahmad.anno.entity.Prototype;
import org.jetbrains.annotations.NotNull;

public abstract class PrototypeBuilding extends Prototype
{
    public PrototypeBuilding(@NotNull String name)
    {
        super(name);
    }
}
