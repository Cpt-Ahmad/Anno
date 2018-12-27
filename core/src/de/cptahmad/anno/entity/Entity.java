package de.cptahmad.anno.entity;

import com.badlogic.gdx.graphics.Texture;
import org.jetbrains.annotations.NotNull;

public abstract class Entity
{
    public final int id;

    public final String name;

    public final Texture texture;

    protected Entity(int id, @NotNull String name, @NotNull Texture texture)
    {
        this.id = id;
        this.name = name;
        this.texture = texture;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
