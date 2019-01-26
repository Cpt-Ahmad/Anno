package de.cptahmad.anno.entity.items;

import de.cptahmad.anno.entity.EntityCollection;
import de.cptahmad.anno.entity.items.presets.RawMaterial;
import de.cptahmad.anno.entity.items.presets.RefinedMaterial;
import de.cptahmad.anno.main.LoadingException;

import java.util.Map;

public final class Items extends EntityCollection<Item>
{
    public Items()
    {
    }

    @Override
    protected Item loadPrototypeFromMap(String name, Map<String, Object> properties)
    {
        String type = (String) properties.get("type");
        if (type == null)
            throw new LoadingException("items.yaml: the entry with the key \'" + name + "\' does not have a type");

        switch (type)
        {
            case "raw_material":
                return new RawMaterial(name, properties);
            case "refined_material":
                return new RefinedMaterial(name, properties);
            default:
                throw new LoadingException("the given type does not exist: " + type);
        }
    }
}
