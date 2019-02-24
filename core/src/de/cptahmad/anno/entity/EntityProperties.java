package de.cptahmad.anno.entity;

public enum EntityProperties
{
    DIMENSION_WORLD("dimension_world"),
    TEXTURE("texture"),
    RECIPE("recipe"),
    ROAD_CONNECTION("road_connection"),
    DIMENSION_TEXTURE("dimension_texture"),
    YIELD("yield"),
    TIME("time"),
    ;

    public final String propertyName;

    EntityProperties(String name)
    {
        propertyName = name;
    }
}
