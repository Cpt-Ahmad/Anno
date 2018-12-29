package de.cptahmad.anno.world.buildings.prototypes;

import com.badlogic.gdx.graphics.Texture;
import de.cptahmad.anno.items.Items;
import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.main.Assets;
import org.jetbrains.annotations.NotNull;

public class PTree extends PMineableResources
{
    public PTree(String name, int width, int height,
                 @NotNull Texture texture)
    {
        super("tree", 0, 1, Assets.getTexture(Asset.TREE), Items.rawWood, 5);
    }
}
