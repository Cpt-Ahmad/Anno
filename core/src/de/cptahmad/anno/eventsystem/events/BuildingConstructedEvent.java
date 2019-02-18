package de.cptahmad.anno.eventsystem.events;

import de.cptahmad.anno.entity.buildings.PrototypeBuilding;
import de.cptahmad.anno.eventsystem.Event;

public class BuildingConstructedEvent extends Event
{
    public final PrototypeBuilding building;

    public final int x, y;

    public BuildingConstructedEvent(PrototypeBuilding building, int x, int y)
    {
        super(Type.BUILD);
        this.building = building;
        this.x = x;
        this.y = y;
    }
}
