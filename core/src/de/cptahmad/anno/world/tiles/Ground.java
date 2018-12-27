package de.cptahmad.anno.world.tiles;

import com.badlogic.gdx.graphics.Texture;
import org.jetbrains.annotations.NotNull;

public abstract class Ground extends AbstractTile
{
    public Ground(int id, @NotNull String name,
                  @NotNull Texture texture)
    {
        super(id, name, texture);
    }
}
