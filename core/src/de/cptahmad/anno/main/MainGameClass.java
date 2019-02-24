package de.cptahmad.anno.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import de.cptahmad.anno.entity.Entities;
import de.cptahmad.anno.states.MainMenu;
import de.cptahmad.anno.states.StateStacker;
import org.yaml.snakeyaml.Yaml;

import java.util.Locale;

public class MainGameClass extends ApplicationAdapter
{
    private final StateStacker m_states = new StateStacker();
    private       Data         m_data;

    @Override
    public void create()
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        Yaml yaml = new Yaml();

        long assetTime, entityTime;
        long beforeTime = TimeUtils.millis();

        Assets.init();
        assetTime = TimeUtils.timeSinceMillis(beforeTime);

        beforeTime = TimeUtils.millis();
        Entities.init(yaml.load(Gdx.files.internal("items.yaml").read()),
                      yaml.load(Gdx.files.internal("buildings.yaml").read()));
        entityTime = TimeUtils.timeSinceMillis(beforeTime);

        String loadingTimes =
                String.format(Locale.GERMAN,
                              " Resource loading times: [Assets: %dms, Entities: %dms, Total: %dms]",
                              assetTime, entityTime,
                              assetTime + entityTime);

        Gdx.app.debug("Loading", loadingTimes);

        m_data = new Data();

        m_states.push(new MainMenu(m_data, m_states));
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        m_states.update(Gdx.graphics.getDeltaTime());
        Assets.getEventManager().update();

        m_states.render();
    }

    @Override
    public void dispose()
    {
        m_states.closeAll();

        Assets.dispose();
    }
}
