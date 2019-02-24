package de.cptahmad.anno.eventsystem.events;

import de.cptahmad.anno.eventsystem.Event;

public class DisplayMessageEvent extends Event
{
    public final String message;

    public DisplayMessageEvent(String message)
    {
        super(Type.MESSAGE);
        this.message = message;
    }
}
