package de.cptahmad.anno.eventsystem;

import com.badlogic.gdx.Gdx;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public final class EventManager
{
    private final ArrayDeque<Event>  m_eventQueue = new ArrayDeque<>();
    private final Set<EventListener> m_listeners  = new HashSet<>();

    public EventManager()
    {
    }

    /**
     * Adds the given event to the event queue. The event cannot be null.
     *
     * @param event an event which should be processed by an appropriate listener
     */
    public void addEvent(@NotNull Event event)
    {
        m_eventQueue.add(event);
    }

    /**
     * Adds an event listener to the event manager. The listener cannot be null and no duplicates of the same instance
     * of a listener are allowed.
     *
     * @param listener the listener processing a specific event
     */
    public void addListener(@NotNull EventListener listener)
    {
        m_listeners.add(listener);
    }

    /**
     * The update method processes all events in the event queue. The events are processes in the order they were
     * added to the queue. Additionally the list of the listeners is iterated over in the order they were added, ie.
     * the listener added first, processing a specific event, will be given the event to process.
     */
    public void update()
    {
        while (!m_eventQueue.isEmpty())
        {
            Event event = m_eventQueue.poll();
            for (EventListener listener : m_listeners)
            {
                listener.processEvent(event);
            }

            if (!event.isHandled())
                Gdx.app.debug(this.getClass().getName(), "an event was not handled: " + event.getClass().getName());
        }
    }

    /**
     * Removes all listeners and events from this event manager
     */
    public void clear()
    {
        m_eventQueue.clear();
        m_listeners.clear();
    }
}
