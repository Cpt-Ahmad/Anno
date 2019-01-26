package de.cptahmad.anno.entity.buildings;

import de.cptahmad.anno.entity.EntityCollection;
import de.cptahmad.anno.entity.buildings.presets.PHouse;
import de.cptahmad.anno.entity.buildings.presets.PResourceDeposit;
import de.cptahmad.anno.entity.buildings.presets.PRoad;
import de.cptahmad.anno.main.LoadingException;

import java.util.Map;

public final class Buildings extends EntityCollection<PrototypeBuilding>
{
    public Buildings()
    {
    }

    @Override
    protected PrototypeBuilding loadPrototypeFromMap(String name, Map<String, Object> properties)
    {
        String type = (String) properties.get("type");
        if (type == null)
            throw new LoadingException("buildings.yaml: the entry with the key \'" + name + "\' does not have a type");

        switch (type)
        {
            case "house":
                return new PHouse(name, properties);
            case "road":
                return new PRoad(name, properties);
            case "resource":
                return new PResourceDeposit(name, properties);
            default:
                throw new LoadingException("the given type does not exist: " + type);
        }
    }
}
