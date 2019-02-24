package de.cptahmad.anno.eventsystem.eventlisteners;

import com.badlogic.gdx.Gdx;
import de.cptahmad.anno.eventsystem.Event;
import de.cptahmad.anno.eventsystem.EventListener;
import de.cptahmad.anno.eventsystem.events.DisplayMessageEvent;

public class IngameConsoleListener extends EventListener
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void processEvent(Event e)
    {
        if (e instanceof DisplayMessageEvent)
        {
            DisplayMessageEvent event = (DisplayMessageEvent) e;

            Gdx.app.log("INGAME", event.message);

            e.setHandled();
        }
    }
}
