package de.cptahmad.anno.world.buildings.prototypes;

import com.badlogic.gdx.graphics.Texture;
import de.cptahmad.anno.items.Item;
import de.cptahmad.anno.items.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PMineableResources extends PrototypeBuilding
{
    public final ItemStack result;

    public PMineableResources(String name, int width, int height,
                              @NotNull Texture texture, @NotNull Item item, int amount)
    {
        super(name, width, height, texture);
        result = new ItemStack(item, amount, true);
    }
}
