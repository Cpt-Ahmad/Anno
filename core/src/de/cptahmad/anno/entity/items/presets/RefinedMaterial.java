package de.cptahmad.anno.entity.items.presets;

import de.cptahmad.anno.entity.EntityProperties;
import de.cptahmad.anno.entity.items.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RefinedMaterial extends Item
{
    public RefinedMaterial(@NotNull String name, Map<String, Object> properties)
    {
        super(name);
        List<EntityProperties> mandatoryProperties =
                Arrays.asList(EntityProperties.TEXTURE, EntityProperties.RECIPE);
        addComponents(mandatoryProperties, properties);
    }
}
