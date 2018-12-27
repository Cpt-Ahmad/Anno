package de.cptahmad.anno.world.tiles;

import com.badlogic.gdx.graphics.Texture;
import de.cptahmad.anno.entity.Entity;
import de.cptahmad.anno.util.Assets;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTile extends Entity
{
    public final static int TILE_SIZE = 32;

    public AbstractTile(int id, @NotNull String name, @NotNull Texture texture)
    {
        super(id, name, texture);
    }

    public abstract void update(float delta);
}
