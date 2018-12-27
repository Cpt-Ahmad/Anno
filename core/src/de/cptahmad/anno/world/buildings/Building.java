package de.cptahmad.anno.world.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.cptahmad.anno.recipes.Recipe;
import de.cptahmad.anno.util.RectangleInt;

public final class Building
{
    private final AbstractBuilding m_building;
    private final RectangleInt     m_hitbox;
    private final int              m_xCoord, m_yCoord;

    public Building(AbstractBuilding building, int x, int y)
    {
        this.m_building = building;
        this.m_xCoord = x;
        this.m_yCoord = y;
        this.m_hitbox = new RectangleInt(x, y, building.width, building.height);
    }

    public void render(SpriteBatch batch)
    {
        m_building.render(batch, m_xCoord, m_yCoord);
    }

    public boolean isAreaOccupied(RectangleInt other)
    {
        return m_hitbox.intersects(other);
    }
}
