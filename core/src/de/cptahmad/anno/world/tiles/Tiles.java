package de.cptahmad.anno.world.tiles;

import com.badlogic.gdx.utils.Array;
import org.jetbrains.annotations.NotNull;

public final class Tiles
{
    private static final Array<AbstractTile> s_tiles = new Array<>();

    public static AbstractTile stone;

    private Tiles()
    {
    }

    public static void init()
    {
        stone = new Stone(0);
    }

    public static AbstractTile get(int id)
    {
        if (id < 0) throw new IllegalArgumentException("id of a tile cannot be negative");
        else if (id > s_tiles.size) throw new IllegalArgumentException("there is not tile with the id " + id);

        return s_tiles.get(id);
    }

    static void add(@NotNull AbstractTile tile)
    {
        if (tile.id != s_tiles.size) throw new IllegalArgumentException("the id of the building is wrong");

        s_tiles.add(tile);
    }
}
