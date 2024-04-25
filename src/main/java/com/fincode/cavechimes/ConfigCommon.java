package com.fincode.cavechimes;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

import java.util.ArrayList;
import java.util.List;

public final class ConfigCommon {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static WorldgenOptions worldgen = new WorldgenOptions(BUILDER);

    public static ObtainingOptions obtaining = new ObtainingOptions(BUILDER);

    public static final ForgeConfigSpec spec = BUILDER.build();

    public static class WorldgenOptions {
        public WorldgenOptions(ForgeConfigSpec.Builder builder) {
            builder.push("Worldgen");
            generateChimes = builder
                    .translation("config.cavechimes.worldgen.enabled")
                    .comment("If false, Cave Chimes will not generate naturally. Requires an MC restart.")
                    .define("generateChimes", true);
            minChimeHeight = builder
                    .translation("config.cavechimes.worldgen.height_min")
                    .comment("The minimum y-value at which Cave Chimes can generate. Requires an MC restart.")
                    .define("minChimeHeight", 16);
            maxChimeHeight = builder.
                    translation("config.cavechimes.worldgen.height_max")
                    .comment("The maximum y-value at which Cave Chimes can generate. Requires an MC restart.")
                    .define("maxChimeHeight", 35);
            chimeFrequency = builder
                    .translation("config.cavechimes.worldgen.chance")
                    .comment("The chance for Cave Chimes to generate (per chunk). Requires an MC restart.")
                    .defineInRange("generationChance", 0.01d, 0, 1);
            ArrayList<String> dim1 = new ArrayList<>();
            dim1.add("minecraft:overlord");
            dimensions = builder
                    .translation("config.cavechimes.worldgen.dimensions")
                    .comment("A whitelist/blacklist for which dimensions Cave Chimes generate in.")
                    .define("dimensions", dim1);
            dimBlacklist = builder
                    .translation("config.cavechimes.worldgen.dimensions_blacklist")
                    .comment("If enabled, the Dimension list will function as a blacklist instead of a whitelist.")
                    .define("dimensionBlacklist", false);
            biomes = builder
                    .translation("config.cavechimes.worldgen.biomes")
                    .comment("A whitelist/blacklist for which biomes Cave Chimes generate in. Requires an MC restart.")
                    .define("biomes", new ArrayList<>());
            biomeBlacklist = builder
                    .translation("config.cavechimes.worldgen.biomes_blacklist")
                    .comment("If enabled, the Biome list will function as a blacklist instead of a whitelist. Requires an MC restart.")
                    .define("biomeBlacklist", true);
            builder.pop();
        }

        public BooleanValue generateChimes;
        public ForgeConfigSpec.ConfigValue<Integer> minChimeHeight;
        public ForgeConfigSpec.ConfigValue<Integer> maxChimeHeight;
        public DoubleValue chimeFrequency;

        public ForgeConfigSpec.ConfigValue<List<String>> dimensions;
        public BooleanValue dimBlacklist;

        public ForgeConfigSpec.ConfigValue<List<String>> biomes;
        public BooleanValue biomeBlacklist;
    }

    public static class ObtainingOptions {
        public ObtainingOptions(ForgeConfigSpec.Builder builder) {
            builder.push("Obtaining");
            canBlockDrop = builder
                    .translation("config.cavechimes.obtaining.block_drops")
                    .comment("Toggles Cave Chimes dropping themselves when broken.")
                    .define("blockDrops", true);
            silkTouchRequired = builder
                    .translation("config.cavechimes.obtaining.silk_touch")
                    .comment("If true, Cave Chimes will only drop themselves if mined with Silk Touch.")
                    .define("silkTouch", false);
            canCraft = builder
                    .translation("config.cavechimes.obtaining.crafting")
                    .comment("Toggles the Cave Chimes crafting recipe.")
                    .define("crafting", true);
            canCreepersDrop = builder
                    .translation("config.cavechimes.obtaining.creeper_drops")
                    .comment("Toggles Creepers being able to drop Cave Chimes when killed by a Witch.")
                    .define("creeperDrops", true);
            builder.pop();
        }
        public BooleanValue canBlockDrop;
        public BooleanValue silkTouchRequired;
        public BooleanValue canCraft;
        public BooleanValue canCreepersDrop;
    }

}
