package de.cptahmad.anno.main;

import com.badlogic.gdx.graphics.Texture;

public enum Asset
{
    BADLOGIC_LOGO(3, "badlogic.jpg", Texture.class),

    // ITEMS
    STONE(0, "stone.png", Texture.class),
    WOOD_RAW(0, "wood.png", Texture.class),
    STONE_BRICK(0, "stone.png", Texture.class),

    // TILES
    //TILE_GRASS(1, "grass.png", Texture.class),

    //TILE_STONE(1, "stone.png", Texture.class),

    // BUILDINGS
    HOUSE_BASIC(2, "house01.png", Texture.class),

    ROAD_TRAIL(2, "road_trail.png", Texture.class),

    TREE_BASIC(2, "tree.png", Texture.class),

    // NPCS
    WOODCUTTER(4, "woodcutter.png", Texture.class),

    // MISC
    SELECTED_TILE(3, "selectedTile.png", Texture.class),

    ;

    public final String   path;
    public final Class<?> assetClass;

    private static final String tilePath     = "textures/tiles/";
    private static final String itemPath     = "textures/items/";
    private static final String buildingPath = "textures/buildings/";

    Asset(int type, String s, Class ac)
    {
        switch (type)
        {
            case 0: // items
                s = "textures/items/" + s;
                break;
            case 1: // tiles
                s = "textures/tiles/" + s;
                break;
            case 2: // buildings
                s = "textures/buildings/" + s;
                break;
            case 3: // misc
                s = "textures/misc/" + s;
                break;
            case 4: // npcs
                s = "textures/npcs/" + s;
                break;
            default:
                throw new IllegalArgumentException("the asset type is invalid: " + type);
        }

        path = s;
        assetClass = ac;
    }
}
