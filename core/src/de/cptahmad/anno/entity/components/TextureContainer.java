package de.cptahmad.anno.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.jetbrains.annotations.NotNull;

public class TextureContainer implements Component
{
    public final Texture texture;

    public TextureContainer(@NotNull Texture texture)
    {
        this.texture = texture;
    }

    public void render(@NotNull SpriteBatch batch, float x, float y)
    {
        batch.draw(texture, x, y);
    }
}
