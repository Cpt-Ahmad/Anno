package de.cptahmad.anno;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.cptahmad.anno.eventsystem.EventManager;
import de.cptahmad.anno.items.Items;
import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.states.Ingame;
import de.cptahmad.anno.states.StateStacker;
import de.cptahmad.anno.util.Assets;
import de.cptahmad.anno.world.buildings.Buildings;
import de.cptahmad.anno.world.tiles.Tiles;

public class MainGameClass extends ApplicationAdapter
{
    private final StateStacker m_states = new StateStacker();
    private final EventManager m_eventManager = new EventManager();

    @Override
    public void create()
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        Assets.init();
        Items.init();
        Buildings.init();
        Tiles.init();

        m_states.push(new Ingame(m_eventManager));
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        m_states.update(Gdx.graphics.getDeltaTime());
        m_eventManager.update();

        m_states.render();
    }

    @Override
    public void dispose()
    {
        Assets.dispose();
    }
}
