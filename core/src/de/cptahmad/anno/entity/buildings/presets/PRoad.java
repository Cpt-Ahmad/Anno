package de.cptahmad.anno.entity.buildings.presets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.cptahmad.anno.entity.buildings.PrototypeBuilding;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PRoad extends PrototypeBuilding
{
    public PRoad(@NotNull String name, Map<String, Object> properties)
    {
        super(name);
        String[] mandatoryProperties = new String[]{"dimension_world", "texture", "recipe"};
        addComponents(mandatoryProperties, properties);
    }

    public void adjustRoadSprite(Sprite sprite, boolean up, boolean down, boolean left, boolean right)
    {
        if (!up & !down)
        {
            sprite.setRegion(0, 0, 32, 32);
        } else if (!left & !right)
        {
            sprite.setRegion(0, 0, 32, 32);
            sprite.rotate90(true);
        } else if ((up ^ down) & (left ^ right))
        {
            sprite.setRegion(32, 0, 32, 32);
            if (up)
            {
                sprite.rotate90(true);
                if (right)
                {
                    sprite.rotate90(true);
                }
            } else
            {
                if (right)
                {
                    sprite.rotate90(false);
                }
            }
        } else if (up & down & left & right)
        {
            sprite.setRegion(3 * 32, 0, 32, 32);
        } else
        {
            sprite.setRegion(2 * 32, 0, 32, 32);
            if (!down)
            {
                sprite.rotate90(true);
                sprite.rotate90(true);
            } else if (!left)
            {
                sprite.rotate90(false);
            } else if (!right)
            {
                sprite.rotate90(true);
            }
        }
    }
}
