package de.cptahmad.anno.entity.buildings.presets;

import de.cptahmad.anno.entity.buildings.PrototypeBuilding;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PHouse extends PrototypeBuilding
{
    public PHouse(@NotNull String name, Map<String, Object> properties)
    {
        super(name, Type.HOUSE);
        String[] mandatoryProperties = new String[]{"dimension_world", "texture", "recipe"};
        addComponents(mandatoryProperties, properties);
    }
}
