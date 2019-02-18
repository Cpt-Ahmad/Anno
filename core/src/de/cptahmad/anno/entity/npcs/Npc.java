package de.cptahmad.anno.entity.npcs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import de.cptahmad.anno.entity.components.Movement;
import de.cptahmad.anno.util.Point2DInteger;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Npc
{
    private Profession     m_profession = Profession.UNEMPLOYED;
    private ProfessionRank m_rank       = ProfessionRank.NOVICE;

    private final Texture m_texture;
    public final String name;

    private final Movement m_movement;
    private final Vector2 m_position = new Vector2();

    public Npc(@NotNull String m_name, @NotNull Texture m_texture)
    {
        this.m_texture = m_texture;
        this.name = m_name;

        m_movement = new Movement(m_position, 50);
    }

    public void setProfession(Profession p)
    {
        m_profession = p;
        m_rank = ProfessionRank.NOVICE;
    }

    public void update(float delta)
    {
        m_movement.update(delta);
    }

    public void move(List<Point2DInteger> vertices)
    {
        m_movement.moveTo(vertices);
    }

    public void setPosition(float x, float y)
    {
        m_position.set(x, y);
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(m_texture, m_position.x, m_position.y);
    }
}
