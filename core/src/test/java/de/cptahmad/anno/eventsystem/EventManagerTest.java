package de.cptahmad.anno.eventsystem;

import de.cptahmad.anno.eventsystem.eventlisteners.TestListenerMultiEvents;
import de.cptahmad.anno.eventsystem.eventlisteners.TestListenerSingleEvent;
import de.cptahmad.anno.eventsystem.events.TestEvent1;
import de.cptahmad.anno.eventsystem.events.TestEvent2;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventManagerTest
{
    @Test
    public void testSingleEventListener()
    {
        EventManager manager = new EventManager();

        TestListenerSingleEvent listener1 = new TestListenerSingleEvent();
        TestListenerSingleEvent listener2 = new TestListenerSingleEvent();
        TestEvent1 event1 = new TestEvent1();
        TestEvent2 event2 = new TestEvent2();

        manager.addListener(listener1);
        manager.addListener(listener2);

        assertTrue(listener1.canProcessEvent(event1));
        assertFalse(listener1.canProcessEvent(event2));

        manager.addEvent(new TestEvent1());
        manager.update();

        assertEquals(listener1.test, event1.name);
        assertNull(listener2.test);
    }

    @Test
    public void testMultiEventListener()
    {
        EventManager manager = new EventManager();

        TestListenerSingleEvent listener1 = new TestListenerSingleEvent();
        TestListenerMultiEvents listener2 = new TestListenerMultiEvents();
        TestEvent1 event1 = new TestEvent1();
        TestEvent2 event2 = new TestEvent2();

        manager.addListener(listener2);
        manager.addListener(listener1);

        assertTrue(listener2.canProcessEvent(event1));
        assertTrue(listener2.canProcessEvent(event2));

        manager.addEvent(event1);
        manager.addEvent(event2);
        manager.update();

        assertEquals(listener2.names[0], event1.name);
        assertEquals(listener2.names[1], event2.name);
        assertNull(listener1.test);
    }
}