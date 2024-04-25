package com.fincode.cavechimes.init;

import com.fincode.cavechimes.CaveChimesMod;
import com.fincode.cavechimes.ConfigCommon;
import com.fincode.cavechimes.common.world.feature.FeatureCaveChimes;
import com.fincode.cavechimes.util.Logic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Objects;

@ObjectHolder("cavechimes")
public final class CaveChimesFeatures {
    public static void init()
    {
        for (BiomeManager.BiomeType type : BiomeManager.BiomeType.values()) {
            for (BiomeManager.BiomeEntry biome : Objects.requireNonNull(BiomeManager.getBiomes(type))) {
                ResourceLocation name = biome.biome.getRegistryName();
                if (name == null) continue;
                if (Logic.xor(ConfigCommon.worldgen.biomes.get().contains(name.getPath()), ConfigCommon.worldgen.biomeBlacklist.get())) {
                    CaveChimesMod.getLogger().info("Cave Chimes will spawn in " + name.getPath() + " biomes!");
                    biome.biome.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, FeatureCaveChimes.COMPOSITE_CAVE_CHIMES);
                }
            }
        }
    }
}
