package de.cptahmad.anno.entity.items.presets;

import de.cptahmad.anno.entity.items.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RefinedMaterial extends Item
{
    public RefinedMaterial(@NotNull String name, Map<String, Object> properties)
    {
        super(name);
        addComponents(new String[]{ "texture", "recipe" }, properties);
    }
}
