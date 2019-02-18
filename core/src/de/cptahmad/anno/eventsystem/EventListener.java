package de.cptahmad.anno.eventsystem;

public abstract class EventListener
{
    /**
     * Processes the event in case it actually can process it.
     *
     * @param event The event that should be processed
     */
    public abstract void processEvent(Event event);
}
