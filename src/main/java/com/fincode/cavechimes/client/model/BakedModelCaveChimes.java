package com.fincode.cavechimes.client.model;

import com.fincode.cavechimes.CaveChimesMod;
import com.fincode.cavechimes.Config;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nullable;
import java.util.List;

public class BakedModelCaveChimes implements IBakedModel {
    private final IBakedModel chimes;
    protected final TextureAtlasSprite texture;

    public BakedModelCaveChimes(IBakedModel chimes, TextureAtlasSprite texture) {
        this.texture = texture;
        this.chimes = chimes;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState iBlockState, @Nullable EnumFacing enumFacing, long l) {
        return chimes.getQuads(iBlockState, enumFacing, l);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return chimes.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return chimes.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return chimes.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.texture;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return chimes.getOverrides();
    }
}
