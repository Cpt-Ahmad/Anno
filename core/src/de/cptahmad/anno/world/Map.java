package de.cptahmad.anno.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public final class Map
{
    private TiledMap                   m_map;
    private OrthogonalTiledMapRenderer m_renderer;

    public Map(String filename, SpriteBatch batch)
    {
        load(filename, batch);
    }

    public void load(String filename, SpriteBatch batch)
    {
        load(filename);
        m_renderer = new OrthogonalTiledMapRenderer(m_map, batch);
    }

    public void load(String filename)
    {
        m_map = new TmxMapLoader().load("maps/" + filename);
    }

    public void update(OrthographicCamera camera)
    {
        m_renderer.setView(camera);
    }

    public void render()
    {
        m_renderer.render();
    }
}
