package de.cptahmad.anno.world.buildings.prototypes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.main.Assets;

public class PRoad extends PrototypeBuilding
{
    public PRoad(String name)
    {
        super(name, 0, 0, Assets.getTexture(Asset.ROAD_TRAIL));
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
