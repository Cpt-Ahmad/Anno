package de.cptahmad.anno.entity.buildings;

import java.util.Comparator;

/**
 * Sorts the given buildings by their y coordinate. They are sorted from lowest to highest.
 */
public class BuildingComparatorForRendering implements Comparator<Building>
{
    @Override
    public int compare(Building o1, Building o2)
    {
        return Integer.compare(o2.hitbox.y, o1.hitbox.y);
    }
}
