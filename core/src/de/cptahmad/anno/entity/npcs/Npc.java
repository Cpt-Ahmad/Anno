package de.cptahmad.anno.entity.npcs;

import com.badlogic.gdx.graphics.Texture;
import de.cptahmad.anno.entity.Entity;
import org.jetbrains.annotations.NotNull;

public abstract class Npc extends Entity
{
    private Profession m_profession = Profession.UNEMPLOYED;
    private ProfessionRank m_rank = ProfessionRank.NOVICE;

    public Npc(int id, @NotNull String name,
                  @NotNull Texture texture)
    {
        super(id, name, texture);
    }
}
