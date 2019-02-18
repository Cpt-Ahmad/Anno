package de.cptahmad.anno.entity.components;

import com.badlogic.gdx.math.Vector2;
import de.cptahmad.anno.util.Point2DInteger;
import de.cptahmad.anno.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.List;

public class Movement
{
    private ArrayDeque<Point2DInteger> m_pathToDestination     = new ArrayDeque<>();
    private Vector2                    m_distanceToDestination = new Vector2();

    private final Vector2 m_position;

    private float m_movementSpeed;

    public Movement(@NotNull Vector2 position, float movementSpeed)
    {
        this.m_position = position;
        this.m_movementSpeed = movementSpeed;
    }

    public void moveTo(@NotNull List<Point2DInteger> vertices)
    {
        m_pathToDestination.clear();
        m_pathToDestination.addAll(vertices);
    }

    public void update(float delta)
    {
        if (m_distanceToDestination.epsilonEquals(0f, 0f))
        {
            if (!m_pathToDestination.isEmpty())
            {
                Point2DInteger point = m_pathToDestination.poll();
                if (point == null) throw new NullPointerException("path to destination cannot have null elements");
                m_distanceToDestination.set(point.x * World.TILE_SIZE, point.y * World.TILE_SIZE);
                m_distanceToDestination.sub(m_position);
                if(m_distanceToDestination.x < m_distanceToDestination.y) m_distanceToDestination.x = 0f;
                else m_distanceToDestination.y = 0;
            }
        } else
        {
            float vel = delta * m_movementSpeed;

            if (m_distanceToDestination.len() > vel)
            {
                m_position.add(vel * Math.signum(m_distanceToDestination.x),
                               vel * Math.signum(m_distanceToDestination.y));

                m_distanceToDestination.setLength(m_distanceToDestination.len() - vel);
            } else
            {
                m_position.add(m_distanceToDestination);
                m_distanceToDestination.set(0f, 0f);
            }
        }
    }
}
