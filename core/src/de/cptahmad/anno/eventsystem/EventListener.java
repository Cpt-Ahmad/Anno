package de.cptahmad.anno.eventsystem;

import org.jetbrains.annotations.NotNull;

public abstract class EventListener
{
    /**
     * The class of the event that is processed in this listener
     */
    private final Class<? extends Event> m_handledEventClass;

    /**
     * The type of events that is processed in this listener
     */
    private final Event.Type m_handledEventType;

    /**
     * True if it handles a single kind of event only
     * False if it handles a group (type) of events
     */
    private final boolean m_isHandlingSingleEvent;

    public EventListener(Class<? extends Event> handledEvents)
    {
        m_handledEventClass = handledEvents;
        m_handledEventType = null;
        m_isHandlingSingleEvent = true;
    }

    public EventListener(Event.Type eventType)
    {
        m_handledEventType = eventType;
        m_handledEventClass = null;
        m_isHandlingSingleEvent = false;
    }

    /**
     * Checks whether the given event is handled by this listener or not.
     *
     * @param event The event to be processed
     * @return true, if the event can be handled by this listener, false otherwise
     */
    public boolean canProcessEvent(@NotNull Event event)
    {
        if (m_isHandlingSingleEvent) return m_handledEventClass == event.getClass();
        else return event.type == m_handledEventType;
    }

    /**
     * Processes the event given. This method assumes the given event can be handled and therefore does not check the
     * runtime class of the object before casting. Call the {@link #canProcessEvent(Event)} before calling this method.
     *
     * @param event The event that should be processed
     */
    public abstract void processEvent(Event event);
}
