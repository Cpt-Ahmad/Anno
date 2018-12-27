package de.cptahmad.anno.items;

import com.badlogic.gdx.graphics.Texture;
import de.cptahmad.anno.entity.Entity;
import org.jetbrains.annotations.NotNull;

public abstract class Item extends Entity
{
    public final int value;

    Item(int id, @NotNull String name, int value, @NotNull Texture texture)
    {
        super(id, name, texture);

        this.value = value;

        Items.add(this);
    }
}
