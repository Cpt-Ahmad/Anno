package de.cptahmad.anno.entity;

import com.badlogic.ashley.core.ComponentMapper;
import de.cptahmad.anno.entity.components.*;

public class Mapper
{
    public static final ComponentMapper<Dimension>        DIMENSION         = ComponentMapper.getFor(Dimension.class);
    public static final ComponentMapper<DimensionWorld>   DIMENSION_WORLD   = ComponentMapper.getFor(DimensionWorld.class);
    public static final ComponentMapper<DimensionTexture> DIMENSION_TEXTURE = ComponentMapper.getFor(DimensionTexture.class);
    public static final ComponentMapper<TextureContainer> TEXTURE_CONTAINER = ComponentMapper.getFor(TextureContainer.class);
    public static final ComponentMapper<Yield>            YIELD             = ComponentMapper.getFor(Yield.class);
    public static final ComponentMapper<HarvestTime>      HARVEST_TIME      = ComponentMapper.getFor(HarvestTime.class);

    private Mapper(){}
}
