package de.cptahmad.anno.eventsystem.events;

import de.cptahmad.anno.eventsystem.Event;

public class RoadGraphEvent extends Event
{
    public final int x, y;

    public RoadGraphEvent(int x, int y)
    {
        super(Type.BUILD);
        this.x = x;
        this.y = y;
    }
}
