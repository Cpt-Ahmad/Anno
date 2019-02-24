package de.cptahmad.anno.entity.buildings.presets;

import de.cptahmad.anno.entity.EntityProperties;
import de.cptahmad.anno.entity.buildings.PrototypeBuilding;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PHouse extends PrototypeBuilding
{
    public PHouse(@NotNull String name, Map<String, Object> properties)
    {
        super(name, Type.HOUSE);
        List<EntityProperties> mandatoryProperties =
                Arrays.asList(EntityProperties.DIMENSION_WORLD, EntityProperties.TEXTURE, EntityProperties.RECIPE,
                              EntityProperties.ROAD_CONNECTION);
        addComponents(mandatoryProperties, properties);
    }
}
