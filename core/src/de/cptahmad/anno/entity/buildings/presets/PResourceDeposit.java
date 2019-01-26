package de.cptahmad.anno.entity.buildings.presets;

import de.cptahmad.anno.entity.buildings.PrototypeBuilding;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PResourceDeposit extends PrototypeBuilding
{
    public PResourceDeposit(@NotNull String name, Map<String, Object> properties)
    {
        super(name);
        String[] mandatoryProperties = new String[]{ "dimension_world", "dimension_texture", "texture", "yield" };
        addComponents(mandatoryProperties, properties);
    }
}
