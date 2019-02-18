package de.cptahmad.anno.eventsystem.eventlisteners;

import de.cptahmad.anno.eventsystem.Event;
import de.cptahmad.anno.eventsystem.EventListener;
import de.cptahmad.anno.eventsystem.events.TestEventAbstract;

public class TestListenerMultiEvents extends EventListener
{
    public final String[] names = new String[2];
    private int counter = 0;

    public TestListenerMultiEvents()
    {
    }

    @Override
    public void processEvent(Event event)
    {
        names[counter++] = ((TestEventAbstract) event).name;
    }
}
