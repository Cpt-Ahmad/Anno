package de.cptahmad.anno.entity.npcs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.cptahmad.anno.entity.components.Movement;
import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.main.LoadingException;
import de.cptahmad.anno.util.Point2DInteger;
import de.cptahmad.anno.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Npc
{
    public final UUID id;

    private Profession     m_profession = Profession.UNEMPLOYED;
    private ProfessionRank m_rank       = ProfessionRank.NOVICE;

    private final Asset m_textureAsset;
    private final Texture m_texture;
    public final  String  name;

    private final Movement m_movement;
    private final Vector2  m_position = new Vector2();

    public Npc(UUID id, Map<String, Object> data)
    {
        this.id = id;

        if (!data.containsKey("name")) throw new LoadingException("the npc doesn't have a name");
        if (!data.containsKey("coordinates")) throw new LoadingException("the npc doesn't have coordinates");
        if (!data.containsKey("texture")) throw new LoadingException("the npc doesn't have a texture");

        name = (String) data.get("name");

        List coordinates = (ArrayList) data.get("coordinates");
        m_position.set(Float.parseFloat(coordinates.get(0).toString()), Float.parseFloat(coordinates.get(1).toString()));

        m_textureAsset = Asset.valueOf(data.get("texture").toString());
        m_texture = m_textureAsset.getTexture();

        m_movement = new Movement(m_position, 250);
    }

    public Npc(@NotNull String m_name, @NotNull Asset textureAsset)
    {
        id = UUID.randomUUID();

        this.m_textureAsset = textureAsset;
        this.m_texture = textureAsset.getTexture();
        this.name = m_name;

        m_movement = new Movement(m_position, 250);
    }

    public void setProfession(Profession p)
    {
        m_profession = p;
        m_rank = ProfessionRank.NOVICE;
    }

    public void update(float delta)
    {
        m_movement.update(delta);
    }

    public void move(List<Point2DInteger> vertices)
    {
        m_movement.moveTo(vertices);
    }

    public void setPosition(float x, float y)
    {
        m_position.set(x, y);
    }

    public int getXPositionInMapGrid()
    {
        return (MathUtils.round(m_position.x) % World.TILE_SIZE) + 1;
    }

    public int getYPositionInMapGrid()
    {
        return (MathUtils.round(m_position.y) % World.TILE_SIZE) + 1;
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(m_texture, m_position.x, m_position.y);
    }

    public void getSaveData(Map<String, Object> data)
    {
        data.put("name", name);
        data.put("coordinates", Arrays.asList(m_position.x, m_position.y));
        data.put("texture", m_textureAsset.name());
    }
}
