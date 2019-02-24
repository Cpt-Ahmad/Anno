package de.cptahmad.anno.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import de.cptahmad.anno.entity.components.*;
import de.cptahmad.anno.entity.items.ItemStack;
import de.cptahmad.anno.main.Asset;
import de.cptahmad.anno.main.Assets;
import de.cptahmad.anno.main.LoadingException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Prototype
{
    public final    String name;
    protected final Entity m_entity;

    public Prototype(@NotNull String name)
    {
        this.name = name;
        m_entity = new Entity();
    }

    public <T extends Component> T getComponent(@NotNull Class<T> componentClass)
    {
        return m_entity.getComponent(componentClass);
    }

    public <T extends Component> T getComponent(@NotNull ComponentMapper<T> mapper)
    {
        return mapper.get(m_entity);
    }

    public <T extends Component> boolean hasComponent(@NotNull Class<T> componentClass)
    {
        return m_entity.getComponent(componentClass) != null;
    }

    @Override
    public String toString()
    {
        return name;
    }

    protected void addComponents(List<EntityProperties> mandatoryProperties, Map<String, Object> properties)
    {
        for (EntityProperties entityProperty : mandatoryProperties)
        {
            if (!properties.containsKey(entityProperty.propertyName)) throw new LoadingException(
                    String.format("the entity %s does not have the mandatory property %s", name,
                                  entityProperty.propertyName));

            Object property = properties.get(entityProperty.propertyName);

            switch (entityProperty)
            {
                case DIMENSION_WORLD:
                    ArrayList dim = (ArrayList) property;
                    int width = (int) dim.get(0);
                    int height = (int) dim.get(1);
                    m_entity.add(new DimensionWorld(width, height));
                    break;

                case DIMENSION_TEXTURE:
                    ArrayList dimTex = (ArrayList) property;
                    int widthTex = (int) dimTex.get(0);
                    int heightTex = (int) dimTex.get(1);
                    m_entity.add(new DimensionTexture(widthTex, heightTex));
                    break;

                case TEXTURE:
                    String tex = (String) property;
                    Texture texture;
                    try
                    {
                        texture = Assets.getTexture(Asset.valueOf(tex));
                    } catch (IllegalArgumentException e)
                    {
                        throw new LoadingException(
                                String.format("the texture %s of the entity %s does not exist", tex,
                                              name));
                    }
                    m_entity.add(new TextureContainer(texture));
                    break;

                case RECIPE:
                    Map recipe = (Map) property;

                    long reqTime = (int) recipe.remove("time");
                    if (reqTime < 0) throw new LoadingException(
                            "the recipe of " + name +
                            " has an invalid or non-existing construction time");

                    ArrayList<ItemStack> reqItems = ItemStack.loadItemStackFromMap(recipe, true);

                    m_entity.add(new Recipe(reqItems, reqTime));
                    break;

                case YIELD:
                    ArrayList<ItemStack> yield = ItemStack.loadItemStackFromMap((Map) property, true);
                    m_entity.add(new Yield(yield));
                    break;

                case TIME:
                    HarvestTime harvestTime = new HarvestTime((int) property);
                    m_entity.add(harvestTime);
                    break;

                case ROAD_CONNECTION:
                    ArrayList roadConnectionCoords = (ArrayList) property;
                    int roadConnectionX = (int) roadConnectionCoords.get(0);
                    int roadConnectionY = (int) roadConnectionCoords.get(1);
                    RoadConnection roadConnection = new RoadConnection(roadConnectionX, roadConnectionY);
                    m_entity.add(roadConnection);
                    break;
            }
        }
    }
}
