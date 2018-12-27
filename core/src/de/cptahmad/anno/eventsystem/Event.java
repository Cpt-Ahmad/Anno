package de.cptahmad.anno.eventsystem;

public abstract class Event
{
    public enum Type
    {
        TEST,
        BUILD,
        ;
    }

    public final Type type;

    private boolean isHandled = false;

    public Event(Type type)
    {
        this.type = type;
    }

    public void setHandled()
    {
        isHandled = true;
    }

    public boolean isHandled()
    {
        return isHandled;
    }
}
