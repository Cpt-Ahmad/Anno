package de.cptahmad.anno.entity.components;

import com.badlogic.ashley.core.Component;

public class RoadConnection implements Component
{
    public final int dx, dy;

    public RoadConnection(int dx, int dy)
    {
        this.dx = dx;
        this.dy = dy;
    }
}
