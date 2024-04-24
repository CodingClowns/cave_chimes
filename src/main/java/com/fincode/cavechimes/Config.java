package com.fincode.cavechimes;

import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid="cavechimes")
public final class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static WorldgenOptions worldgen = new WorldgenOptions(BUILDER);

    public static ObtainingOptions obtaining = new ObtainingOptions(BUILDER);

    public static ClientOptions client = new ClientOptions(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();

    public static class WorldgenOptions {
        public WorldgenOptions(ForgeConfigSpec.Builder builder) {
            builder.push("Worldgen");
            generateChimes = builder
                    .translation("config.cavechimes.worldgen.enabled")
                    .comment("If false, Cave Chimes will not generate naturally.")
                    .define("generateChimes", true);
            minChimeHeight = builder
                    .translation("config.cavechimes.worldgen.height_min")
                    .comment("The minimum y-value at which Cave Chimes can generate.")
                    .define("minChimeHeight", 16);
            maxChimeHeight = builder.
                    translation("config.cavechimes.worldgen.height_max")
                    .comment("The maximum y-value at which Cave Chimes can generate.")
                    .define("maxChimeHeight", 35);
            chimeFrequency = builder
                    .translation("config.cavechimes.worldgen.chance")
                    .comment("The chance for Cave Chimes to generate (per chunk).")
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
                    .comment("A whitelist/blacklist for which biomes Cave Chimes generate in.")
                    .define("biomes", new ArrayList<>());
            biomeBlacklist = builder
                    .translation("config.cavechimes.worldgen.biomes_blacklist")
                    .comment("If enabled, the Biome list will function as a blacklist instead of a whitelist.")
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

    public static class ClientOptions {
        public ClientOptions(ForgeConfigSpec.Builder builder) {
            builder.push("Client");
            chimeVolume = builder
                    .translation("config.cavechimes.client.volume")
                    .comment("A multiplier for Cave Chimes volume.")
                    .defineInRange("chimeVolume", 1d, 0d, 1.5d);
            chimeSwing = builder
                    .translation("config.cavechimes.client.swing")
                    .comment("A multiplier for Cave Chimes animation speed.")
                    .defineInRange("chimeSwing", 1d, 0d, 5d);
            simplifiedModel = builder
                    .translation("config.cavechimes.client.simplified")
                    .comment("Replaces the Cave Chimes' custom model with a simplified cross model that lacks animations.")
                    .worldRestart()
                    .define("simplified", false);
            priority = new PriorityOptions(builder);
            builder.pop();
        }

        public DoubleValue chimeVolume;
        public DoubleValue chimeSwing;
        public BooleanValue simplifiedModel;

        public PriorityOptions priority;
    }

    public static class PriorityOptions {
        public PriorityOptions(ForgeConfigSpec.Builder builder) {
            builder.push("Priority");
            enabled = builder
                    .translation("config.cavechimes.priority.enabled")
                    .comment("Be warned, disabling this option may result in Cave Chimes not playing at times, as chimes nowhere near the player get priority over the ones the player may actually be able to hear.")
                    .define("priorityEnabled", true);
            limit = builder
                    .translation("config.cavechimes.priority.limit")
                    .comment("The amount of Cave Chimes the system will keep track of. If your render distance is above 16, I'd recommend increasing this.")
                    .defineInRange("chimesLimit", 300, 16, 1024);
            refreshInterval = builder
                    .translation("config.cavechimes.priority.refresh_rate")
                    .comment("Time between Priority refreshes (in seconds). Values 1-10 recommended for aesthetic, values 5-20 recommended for performance.")
                    .define("refreshInterval", 3);
            builder.pop();
        }

        public BooleanValue enabled;
        public ForgeConfigSpec.ConfigValue<Integer> limit;
        public ForgeConfigSpec.ConfigValue<Integer> refreshInterval;
    }
}
