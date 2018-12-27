package de.cptahmad.anno.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.cptahmad.anno.eventsystem.EventManager;
import de.cptahmad.anno.util.Assets;
import de.cptahmad.anno.world.World;
import de.cptahmad.anno.world.buildings.AbstractBuilding;
import de.cptahmad.anno.world.buildings.Buildings;
import org.jetbrains.annotations.NotNull;

public class Ingame implements State
{
    private final World        m_world;
    private final Stage        m_stage;
    private final EventManager m_eventManager;

    private final ScrollPane m_buildingList;
    private final Label      m_fpsLabel;

    public Ingame(@NotNull EventManager eManager)
    {
        m_eventManager = eManager;

        Table ui = new Table(Assets.getSkin());
        ui.setDebug(true);
        ui.setFillParent(true);

        final List<AbstractBuilding> bList = new List<>(Assets.getSkin());
        bList.setItems(Buildings.getAllBuildings());

        m_world = new World(eManager);
        m_world.generateWorldRandomly();
        m_world.setBuildingSelection(bList.getSelected());

        m_buildingList = new ScrollPane(bList);
        m_buildingList.setBounds(0, 0, Gdx.graphics.getWidth() / 8f, Gdx.graphics.getHeight() / 4f);

        m_buildingList.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                m_world.setBuildingSelection(bList.getSelected());
            }
        });

        m_fpsLabel = new Label(null, Assets.getSkin());

        ui.add(m_fpsLabel).expand().top().left();
        ui.add(m_buildingList).bottom().right();

        m_stage = new Stage(new ScreenViewport(), Assets.getSpriteBatch());
        m_stage.addActor(ui);
    }

    @Override
    public void update(float delta)
    {
        m_fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());

        m_stage.act(delta);
        m_world.update(delta);
        m_eventManager.update();
    }

    @Override
    public void render()
    {
        m_world.render();
        m_stage.draw();
    }

    @Override
    public void onEnter()
    {
        Gdx.input.setInputProcessor(new InputMultiplexer(m_stage, m_world));

        // add all event listeners
        m_eventManager.clear();
    }

    @Override
    public void onExit()
    {

    }
}
