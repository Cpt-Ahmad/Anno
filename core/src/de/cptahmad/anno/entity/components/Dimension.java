package de.cptahmad.anno.entity.components;

import com.badlogic.ashley.core.Component;

public class Dimension implements Component
{
    public final int width, height;

    public Dimension(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
}
