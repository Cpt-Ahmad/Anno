package de.cptahmad.anno.entity.buildings.presets;

import de.cptahmad.anno.entity.EntityProperties;
import de.cptahmad.anno.entity.buildings.PrototypeBuilding;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PResourceDeposit extends PrototypeBuilding
{
    public PResourceDeposit(@NotNull String name, Map<String, Object> properties)
    {
        super(name, Type.RESOURCE);
        List<EntityProperties> mandatoryProperties =
                Arrays.asList(EntityProperties.DIMENSION_WORLD, EntityProperties.DIMENSION_TEXTURE,
                              EntityProperties.TEXTURE, EntityProperties.YIELD);
        addComponents(mandatoryProperties, properties);
    }
}
