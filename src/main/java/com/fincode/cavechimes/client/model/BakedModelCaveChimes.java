package com.fincode.cavechimes.client.model;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BakedModelCaveChimes implements IBakedModel {
    private final IBakedModel chimes;
    protected final TextureAtlasSprite texture;

    public BakedModelCaveChimes(IBakedModel chimes, TextureAtlasSprite texture) {
        this.texture = texture;
        this.chimes = chimes;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState iBlockState, @Nullable EnumFacing enumFacing, Random random) {
        return chimes.getQuads(iBlockState, enumFacing, random);
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
