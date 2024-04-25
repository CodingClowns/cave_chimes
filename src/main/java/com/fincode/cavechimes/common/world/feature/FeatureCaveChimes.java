package com.fincode.cavechimes.common.world.feature;

import com.fincode.cavechimes.CaveChimesMod;
import com.fincode.cavechimes.ConfigCommon;
import com.fincode.cavechimes.common.block.BlockCaveChimes;
import com.fincode.cavechimes.init.CaveChimesBlocks;
import com.fincode.cavechimes.util.Logic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.CompositeFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.*;

import java.util.Random;

public class FeatureCaveChimes extends Feature<NoFeatureConfig> {
    public static final Feature<NoFeatureConfig> FEATURE_CAVE_CHIMES = new FeatureCaveChimes();

    public static final ChanceRangeConfig PLACEMENT_CONFIG_CAVE_CHIMES = new ChanceRangeConfig(
            ConfigCommon.worldgen.chimeFrequency.get().floatValue(),
            ConfigCommon.worldgen.minChimeHeight.get(),
            ConfigCommon.worldgen.minChimeHeight.get(),
            ConfigCommon.worldgen.maxChimeHeight.get());
    public static final CompositeFeature<?, ?> COMPOSITE_CAVE_CHIMES = new CompositeFeature<>(
            FEATURE_CAVE_CHIMES,
            NoFeatureConfig.NO_FEATURE_CONFIG,
            Biome.CHANCE_RANGE,
            PLACEMENT_CONFIG_CAVE_CHIMES);

    public static final int TRIES_GENERATION = 8;
    public static final int TRIES_HEIGHT = 20;

    public FeatureCaveChimes() {
        CaveChimesMod.getLogger().info("Constructed Cave Chimes feature!");
    }

    @Override
    public boolean func_212245_a(IWorld iWorld, IChunkGenerator<? extends IChunkGenSettings> iChunkGenerator, Random random, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        //CaveChimesMod.getLogger().info("Trying to generate Cave Chimes.");
        if (!checkDimension(iWorld))
            return false;
        //CaveChimesMod.getLogger().info("Passed config, dimension, and biome checks.");

        int maxHeight = ConfigCommon.worldgen.maxChimeHeight.get();
        int minHeight = ConfigCommon.worldgen.minChimeHeight.get();

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(blockPos);
        boolean flag = false;

        for (int t = 0; t < (TRIES_GENERATION - 1); ++t) {
            if (iWorld.isAirBlock(pos)) {
                flag = true;
                break;
            }
            pos.setY(random.nextInt(maxHeight - minHeight) + minHeight);
        }
        if (!flag) return false;
        return generateChimes(iWorld, pos);
    }

    private boolean checkConfig(Random random) {
        return ConfigCommon.worldgen.generateChimes.get() && random.nextFloat() < ConfigCommon.worldgen.chimeFrequency.get();
    }

    private boolean checkDimension(IWorld world) {
        ResourceLocation dimension = DimensionType.func_212678_a(world.getDimension().getType());
        if (dimension == null) return false;
        //CaveChimesMod.getLogger().info(dimension.getPath());
        return Logic.xor(ConfigCommon.worldgen.dimensions.get().contains(dimension.getPath()), ConfigCommon.worldgen.dimBlacklist.get());

    }

    private boolean checkBiome(IWorld world, BlockPos pos) {
        ResourceLocation biome = world.getBiome(pos).getRegistryName();
        CaveChimesMod.getLogger().info(biome.getPath());
        return biome != null && Logic.xor(ConfigCommon.worldgen.biomes.get().contains(biome.getPath()), ConfigCommon.worldgen.biomeBlacklist.get());
    }

    public boolean generateChimes(IWorld world, BlockPos blockPos) {
        //CaveChimesMod.getLogger().info("Chimes found a valid space to generate in!");

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(blockPos);

        if (!canHang(world, pos)) {
            for (int t = 0; t < TRIES_HEIGHT; ++t) {
                pos.move(0, 1, 0);
                if (!world.isAirBlock(pos)) return false;
                if (canHang(world, pos)) break;
            }
        }

        if (canHang(world, pos)) {
            world.setBlockState(pos, CaveChimesBlocks.CAVE_CHIMES.getDefaultState(), 2 | 16); // "https://forums.minecraftforge.net/topic/64271-solvedsetblockstate-flags/" Absolute legend.
            //CaveChimesMod.getLogger().info("THE BITE OF 87???");

            return true;
        }

        return false;
    }

    private static boolean canHang(IWorld world, BlockPos pos) {
        return BlockCaveChimes.canHang(world, pos);
    }
}
