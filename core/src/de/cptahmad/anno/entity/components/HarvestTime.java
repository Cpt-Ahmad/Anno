package de.cptahmad.anno.entity.components;

import com.badlogic.ashley.core.Component;

public class HarvestTime implements Component
{
    public final long time;

    public HarvestTime(long time)
    {
        this.time = time;
    }
}
