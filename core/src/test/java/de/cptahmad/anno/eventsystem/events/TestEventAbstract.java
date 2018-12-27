package de.cptahmad.anno.eventsystem.events;

import de.cptahmad.anno.eventsystem.Event;

public class TestEventAbstract extends Event
{
    public final String name;

    public TestEventAbstract(String n)
    {
        super(Type.TEST);
        name = n;
    }
}
