package de.cptahmad.anno.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.cptahmad.anno.main.Assets;
import de.cptahmad.anno.main.Data;

public class MainMenu implements State
{
    private final Data         m_data;
    private final StateStacker m_states;

    private final Stage m_stage;

    public MainMenu(Data data, StateStacker states)
    {
        m_data = data;
        m_states = states;

        m_stage = new Stage(new ScreenViewport(), Assets.getSpriteBatch());
    }

    @Override
    public void update(float delta)
    {
        m_stage.act(delta);
    }

    @Override
    public void render()
    {
        m_stage.draw();
    }

    @Override
    public void onEnter()
    {
        Gdx.input.setInputProcessor(m_stage);

        Assets.getEventManager().clear();



        Table table = new Table(Assets.getSkin());
        table.setDebug(true);
        table.setFillParent(true);

        final List<String> profileList = new List<>(Assets.getSkin());
        profileList.setItems(m_data.getAvailableProfiles());

        final ScrollPane profileScrollPane = new ScrollPane(profileList);

        final TextField textField = new TextField("", Assets.getSkin());
        textField.setVisible(false);
        textField.setCursorPosition(Integer.MAX_VALUE);

        final TextButton startButton = new TextButton("START", Assets.getSkin());
        startButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                String profile;

                if (textField.isVisible())
                {
                    profile = textField.getText();
                } else
                {
                    profile = profileList.getSelected();
                }

                m_states.push(new Ingame(m_data, m_states, profile));
            }
        });

        final TextButton newProfileButton = new TextButton("NEW GAME", Assets.getSkin());
        newProfileButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                textField.setVisible(true);
                m_stage.setKeyboardFocus(textField);
            }
        });

        table.add(profileScrollPane).left().top();
        table.add(startButton).right().top();
        table.row();
        table.add(textField).left().bottom();
        table.add(newProfileButton).right().bottom();

        m_stage.addActor(table);
    }

    @Override
    public void onExit()
    {
        m_stage.dispose();
    }
}
