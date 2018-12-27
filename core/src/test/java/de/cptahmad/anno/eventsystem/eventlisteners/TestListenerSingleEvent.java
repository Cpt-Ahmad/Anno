package de.cptahmad.anno.eventsystem.eventlisteners;

import de.cptahmad.anno.eventsystem.Event;
import de.cptahmad.anno.eventsystem.EventListener;
import de.cptahmad.anno.eventsystem.events.TestEvent1;

public class TestListenerSingleEvent extends EventListener
{
    public String test;

    public TestListenerSingleEvent()
    {
        super(TestEvent1.class);
    }

    @Override
    public void processEvent(Event event)
    {
        test = ((TestEvent1) event).name;
    }
}
