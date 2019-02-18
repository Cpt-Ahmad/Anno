package de.cptahmad.anno.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import de.cptahmad.anno.entity.Entities;
import de.cptahmad.anno.states.Ingame;
import de.cptahmad.anno.states.StateStacker;
import org.yaml.snakeyaml.Yaml;

import java.util.Locale;

public class MainGameClass extends ApplicationAdapter
{
    private final StateStacker m_states       = new StateStacker();

    @Override
    public void create()
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        Yaml yaml = new Yaml();

        long assetTime, itemTime, tileTime;
        long beforeTime = TimeUtils.millis();

        Assets.init();
        assetTime = TimeUtils.timeSinceMillis(beforeTime);

        beforeTime = TimeUtils.millis();
        Entities.init(yaml.load(Gdx.files.internal("items.yaml").read()),
                      yaml.load(Gdx.files.internal("buildings.yaml").read()));
        itemTime = TimeUtils.timeSinceMillis(beforeTime);

        String loadingTimes =
                String.format(Locale.GERMAN,
                              "Loading times: [Assets: %dms, Items: %dms, Total: %dms]",
                              assetTime, itemTime,
                              assetTime + itemTime);

        Gdx.app.debug("Main", loadingTimes);

        m_states.push(new Ingame());
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        m_states.update(Gdx.graphics.getDeltaTime());
        Assets.getEventManager().update();

        m_states.render();
    }

    @Override
    public void dispose()
    {
        Assets.dispose();
    }
}
