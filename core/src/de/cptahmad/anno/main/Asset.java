package de.cptahmad.anno.main;

import com.badlogic.gdx.graphics.Texture;

public enum Asset
{
    BADLOGIC_LOGO("badlogic.jpg", Texture.class),

    // ITEMS
    ITEM_STONE("textures/items/stone.png", Texture.class),

    // TILES
    TILE_GRASS("textures//grass.png", Texture.class),
    TILE_STONE("textures/stone.png", Texture.class),

    // BUILDINGS
    BUILDING_HOUSE("textures/house01.png", Texture.class),

    ROAD_TRAIL("textures/trail.png", Texture.class),

    // MISC
    SELECTED_TILE("textures/selectedTile.png", Texture.class),

    ;

    public final String   path;
    public final Class<?> assetClass;

    Asset(String s, Class ac)
    {
        path = s;
        assetClass = ac;
    }
}
