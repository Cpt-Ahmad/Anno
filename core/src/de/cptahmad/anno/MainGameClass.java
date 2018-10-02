package de.cptahmad.anno;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.states.Ingame;
import de.cptahmad.anno.states.StateStacker;
import de.cptahmad.anno.util.Assets;

public class MainGameClass extends ApplicationAdapter
{
    private final StateStacker m_states = new StateStacker();

    @Override
    public void create()
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        Assets.init();
        m_states.push(new Ingame());
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        m_states.update(Gdx.graphics.getDeltaTime());

        m_states.render();
    }

    @Override
    public void dispose()
    {
        Assets.dispose();
    }
}
