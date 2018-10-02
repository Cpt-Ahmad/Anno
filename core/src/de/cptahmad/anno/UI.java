package de.cptahmad.anno;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.cptahmad.anno.util.Assets;

public class UI implements Disposable
{
    public final Stage m_stage;

    public UI()
    {
        m_stage = new Stage(new ScreenViewport(), Assets.getSpriteBatch());
    }

    public void update(float delta)
    {
        m_stage.act(delta);
    }

    public void render()
    {
        m_stage.draw();
    }

    public void add(Actor actor)
    {
        m_stage.addActor(actor);
    }

    @Override
    public void dispose()
    {
        m_stage.dispose();
    }
}
