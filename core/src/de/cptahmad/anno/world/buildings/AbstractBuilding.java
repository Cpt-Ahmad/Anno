package de.cptahmad.anno.world.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.cptahmad.anno.entity.Entity;
import de.cptahmad.anno.recipes.Recipe;
import de.cptahmad.anno.util.Assets;
import de.cptahmad.anno.world.tiles.AbstractTile;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBuilding extends Entity
{
    public final int width, height;

    private static int s_idGenerator = 0;

    public AbstractBuilding(String name, int width, int height, @NotNull Texture texture)
    {
        super(s_idGenerator++, name, texture);

        this.width = width;
        this.height = height;

        Buildings.add(this);
    }

    public void render(SpriteBatch batch, int x, int y)
    {
        batch.draw(texture, x * AbstractTile.TILE_SIZE, y * AbstractTile.TILE_SIZE);
    }
}
