package de.cptahmad.anno.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import de.cptahmad.anno.eventsystem.EventManager;
import de.cptahmad.anno.items.Items;
import de.cptahmad.anno.states.Ingame;
import de.cptahmad.anno.states.StateStacker;
import de.cptahmad.anno.world.buildings.prototypes.Buildings;
import de.cptahmad.anno.world.tiles.Tiles;
import org.yaml.snakeyaml.Yaml;

import java.util.Locale;

public class MainGameClass extends ApplicationAdapter
{
    private final StateStacker m_states       = new StateStacker();
    private final EventManager m_eventManager = new EventManager();

    @Override
    public void create()
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        Yaml yaml = new Yaml();

        long assetTime, itemTime, buildingTime, tileTime;
        long beforeTime = TimeUtils.millis();

        Assets.init();
        assetTime = TimeUtils.timeSinceMillis(beforeTime);
        beforeTime = TimeUtils.millis();

        Items.init(yaml.load(Gdx.files.internal("recipes-items.yaml").read()));
        itemTime = TimeUtils.timeSinceMillis(beforeTime);
        beforeTime = TimeUtils.millis();

        Buildings.init(yaml.load(Gdx.files.internal("recipes-buildings.yaml").read()));
        buildingTime = TimeUtils.timeSinceMillis(beforeTime);
        beforeTime = TimeUtils.millis();

        Tiles.init();
        tileTime = TimeUtils.timeSinceMillis(beforeTime);

        String loadingTimes =
                String.format(Locale.GERMAN,
                              "Loading times: [Assets: %dms, Items: %dms, Buildings: %dms, Tiles: %dms, Total: %dms]",
                              assetTime, itemTime, buildingTime, tileTime,
                              assetTime + itemTime + buildingTime + tileTime);

        Gdx.app.debug("Main", loadingTimes);

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
