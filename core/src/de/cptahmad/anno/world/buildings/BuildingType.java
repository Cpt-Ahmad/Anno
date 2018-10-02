package de.cptahmad.anno.world.buildings;

public enum BuildingType
{
    ROAD_TRAIL(0, 0),
    HOUSE(1, 1),

    ;

    public final int width, height;

    BuildingType(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
}