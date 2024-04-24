package com.fincode.cavechimes.init;

import com.fincode.cavechimes.CaveChimesMod;
import com.fincode.cavechimes.Config;
import com.fincode.cavechimes.common.world.feature.FeatureCaveChimes;
import com.fincode.cavechimes.misc.ChimesDropHandler;
import com.fincode.cavechimes.util.Logic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.CompositeFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
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
                if (Logic.xor(Config.worldgen.biomes.get().contains(name.getPath()), Config.worldgen.biomeBlacklist.get())) {
                    CaveChimesMod.getLogger().info("Cave Chimes will spawn in " + name.getPath() + " biomes!");
                    biome.biome.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, FeatureCaveChimes.COMPOSITE_CAVE_CHIMES);
                }
            }
        }
    }
}
