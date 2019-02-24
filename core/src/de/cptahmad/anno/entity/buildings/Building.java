package de.cptahmad.anno.entity.buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.cptahmad.anno.entity.components.Dimension;
import de.cptahmad.anno.entity.components.DimensionTexture;
import de.cptahmad.anno.entity.components.DimensionWorld;
import de.cptahmad.anno.entity.components.TextureContainer;
import de.cptahmad.anno.eventsystem.events.BuildingConstructedEvent;
import de.cptahmad.anno.main.Assets;
import de.cptahmad.anno.main.LoadingException;
import de.cptahmad.anno.util.RectangleInt;
import de.cptahmad.anno.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Building
{
    public final PrototypeBuilding building;
    public final RectangleInt      hitbox;

    public final Sprite sprite;

    public Building(PrototypeBuilding building, Map<String, Object> data)
    {
        this.building = building;

        Dimension dimWorld = building.getComponent(DimensionWorld.class);
        hitbox = new RectangleInt(0, 0, dimWorld.width, dimWorld.height);

        loadBuildingFromData(data);

        sprite = new Sprite(building.getComponent(TextureContainer.class).texture);

        Dimension dimTex = building.getComponent(DimensionTexture.class);
        if (dimTex == null) dimTex = dimWorld;
        sprite.setBounds(hitbox.x * World.TILE_SIZE, hitbox.y * World.TILE_SIZE,
                         (dimTex.width + 1) * World.TILE_SIZE,
                         (dimTex.height + 1) * World.TILE_SIZE);

        Assets.getEventManager().addEvent(new BuildingConstructedEvent(building, hitbox.x, hitbox.y));
    }

    public Building(PrototypeBuilding building, int x, int y)
    {
        this.building = building;

        Dimension dimWorld = building.getComponent(DimensionWorld.class);
        hitbox = new RectangleInt(x, y, dimWorld.width, dimWorld.height);

        sprite = new Sprite(building.getComponent(TextureContainer.class).texture);

        Dimension dimTex = building.getComponent(DimensionTexture.class);
        if (dimTex == null) dimTex = dimWorld;
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

    /**
     * Puts all the data that has to be saved from this building into the given Map.
     * If overwritten it must be ensured that the super method is still called.
     *
     * @param data the map to put all data into
     */
    public void getSaveData(Map<String, Object> data)
    {
        data.put("coordinates", Arrays.asList(hitbox.x, hitbox.y));
    }

    /**
     * Loads all neccessary data from the given map and rebuilds the building. If mandatory data is missing or not
     * properly saved this method will throw an {@link LoadingException}. If overwritten always ensure to call the
     * super method.
     *
     * @param data the map to load all data from
     */
    protected void loadBuildingFromData(Map<String, Object> data)
    {
        if (!data.containsKey("coordinates")) throw new LoadingException("the building does not have coordinates");
        if (!(data.get("coordinates") instanceof ArrayList))
            throw new LoadingException("the coordinates are not saved properly");

        List coordinates = (ArrayList) data.remove("coordinates");
        hitbox.x = (int) coordinates.get(0);
        hitbox.y = (int) coordinates.get(1);
    }

    public int getX()
    {
        return hitbox.x;
    }

    public int getY()
    {
        return hitbox.y;
    }
}
