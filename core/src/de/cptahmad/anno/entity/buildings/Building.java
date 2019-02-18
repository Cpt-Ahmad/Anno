package de.cptahmad.anno.entity.buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.cptahmad.anno.entity.components.Dimension;
import de.cptahmad.anno.entity.components.DimensionTexture;
import de.cptahmad.anno.entity.components.DimensionWorld;
import de.cptahmad.anno.entity.components.TextureContainer;
import de.cptahmad.anno.eventsystem.events.BuildingConstructedEvent;
import de.cptahmad.anno.main.Assets;
import de.cptahmad.anno.util.RectangleInt;
import de.cptahmad.anno.world.World;

public class Building
{
    public final PrototypeBuilding building;
    public final RectangleInt      hitbox;

    public final Sprite sprite;

    public Building(PrototypeBuilding building, int x, int y)
    {
        this.building = building;

        Dimension dimWorld = building.getComponent(DimensionWorld.class);
        hitbox = new RectangleInt(x, y, dimWorld.width, dimWorld.height);

        sprite = new Sprite(building.getComponent(TextureContainer.class).texture);

        Dimension dimTex = building.getComponent(DimensionTexture.class);
        if(dimTex == null) dimTex = dimWorld;
        sprite.setBounds(x * World.TILE_SIZE, y * World.TILE_SIZE,
                         (dimTex.width + 1) * World.TILE_SIZE,
                         (dimTex.height + 1) * World.TILE_SIZE);

        Assets.getEventManager().addEvent(new BuildingConstructedEvent(building, x, y));
    }

    public boolean is(PrototypeBuilding building)
    {
        return building.equals(this.building);
    }

    public boolean isAt(int x, int y)
    {
        return hitbox.x == x && hitbox.y == y;
    }

    public void render(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    public boolean isAreaOccupied(RectangleInt other)
    {
        return hitbox.intersects(other);
    }
}
