package de.cptahmad.anno.world.buildings.implementation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.cptahmad.anno.util.RectangleInt;
import de.cptahmad.anno.world.buildings.prototypes.PrototypeBuilding;
import de.cptahmad.anno.world.tiles.AbstractTile;

public class Building
{
    public final PrototypeBuilding building;
    public final RectangleInt      hitbox;

    public final Sprite sprite;

    public Building(PrototypeBuilding building, int x, int y)
    {
        this.building = building;
        hitbox = new RectangleInt(x, y, building.width, building.height);

        sprite = new Sprite(building.texture);
        sprite.setBounds(x * AbstractTile.TILE_SIZE, y * AbstractTile.TILE_SIZE,
                         (building.width + 1) * AbstractTile.TILE_SIZE, (building.height + 1) * AbstractTile.TILE_SIZE);
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
