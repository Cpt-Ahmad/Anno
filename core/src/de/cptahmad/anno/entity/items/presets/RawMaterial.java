package de.cptahmad.anno.entity.items.presets;

import de.cptahmad.anno.entity.items.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RawMaterial extends Item
{
    public RawMaterial(@NotNull String name, Map<String, Object> properties)
    {
        super(name);

        // TODO add recipe instead of yield and replace yield component with "result" in recipe
        addComponents(new String[]{ "texture", "time" }, properties);
    }
}
