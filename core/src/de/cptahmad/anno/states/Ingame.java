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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.cptahmad.anno.entity.Entities;
import de.cptahmad.anno.entity.buildings.PrototypeBuilding;
import de.cptahmad.anno.entity.items.Inventory;
import de.cptahmad.anno.eventsystem.EventManager;
import de.cptahmad.anno.main.Assets;
import de.cptahmad.anno.world.World;
import org.jetbrains.annotations.NotNull;

public class Ingame implements State
{
    private final World        m_world;
    private final Stage        m_stage;
    private final EventManager m_eventManager;
    private final Inventory    m_inv;

    private final ScrollPane m_buildingList;
    private final ScrollPane m_itemList;
    private final Label      m_fpsLabel;

    public Ingame(@NotNull EventManager eManager)
    {
        m_inv = new Inventory();
        m_inv.add(Entities.getItem("stone"), 200);

        m_eventManager = eManager;

        Table ui = new Table(Assets.getSkin());
        ui.setDebug(true);
        ui.setFillParent(true);

        PrototypeBuilding[] buildable = new PrototypeBuilding[]{ Entities.getBuilding("house_basic"), Entities.getBuilding("road_trail") };

        final List<PrototypeBuilding> bList = new List<>(Assets.getSkin());
        bList.setItems(new Array<>(buildable));

        m_world = new World(eManager, m_inv);
        m_world.setBuildingSelection(bList.getSelected());

        // scrollable list of the buildings the player can build
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

        // scrollable list of the items the player currently has
        m_itemList = new ScrollPane(m_inv.getList());
        m_itemList.setBounds(0, 0, Gdx.graphics.getWidth() / 8f, Gdx.graphics.getHeight() / 4f);

        m_fpsLabel = new Label(null, Assets.getSkin());

        ui.add(m_fpsLabel).expand().top().left();
        ui.add(m_itemList).top().right();
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
