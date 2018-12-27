package de.cptahmad.anno.eventsystem.eventlisteners;

import de.cptahmad.anno.eventsystem.Event;
import de.cptahmad.anno.eventsystem.EventListener;
import de.cptahmad.anno.eventsystem.events.TestEventAbstract;

import java.util.ArrayList;
import java.util.List;

public class TestListenerMultiEvents extends EventListener
{
    public final String[] names = new String[2];
    private int counter = 0;

    public TestListenerMultiEvents()
    {
        super(Event.Type.TEST);
    }

    @Override
    public void processEvent(Event event)
    {
        names[counter++] = ((TestEventAbstract) event).name;
    }
}
