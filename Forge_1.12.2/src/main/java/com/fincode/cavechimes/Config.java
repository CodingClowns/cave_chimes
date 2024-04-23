package com.fincode.cavechimes;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.config.Config.*;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@net.minecraftforge.common.config.Config(modid="cavechimes", category = "")
@Mod.EventBusSubscriber(modid="cavechimes")
public final class Config {
    @Name("Worldgen")
    @Comment("Controls options regarding naturally generated Cave Chimes.")
    @LangKey("config.cavechimes.options.worldgen")
    public static WorldgenOptions worldgen = new WorldgenOptions();

    @Name("Obtaining")
    @Comment("Controls the the various ways of obtaining Cave Chimes as an item.")
    @LangKey("config.cavechimes.options.obtaining")
    public static ObtainingOptions obtaining = new ObtainingOptions();

    @Name("Client")
    @Comment("Client-side options.")
    @LangKey("config.cavechimes.options.client")
    public static ClientOptions client = new ClientOptions();

    public static class WorldgenOptions {
        @Name("Generate Chimes")
        @Comment("If false, Cave Chimes will not generate naturally.")
        @LangKey("config.cavechimes.worldgen.enabled")
        public boolean generateChimes = true;
        @Name("Minimum height")
        @Comment("The minimum y-value at which Cave Chimes can generate.")
        @LangKey("config.cavechimes.worldgen.height_min")
        public int minChimeHeight = 16;
        @Name("Maximum height")
        @Comment("The maximum y-value at which Cave Chimes can generate.")
        @LangKey("config.cavechimes.worldgen.height_max")
        public int maxChimeHeight = 35;
        @Name("Generation frequency")
        @Comment("The chance for Cave Chimes to generate (per chunk).")
        @LangKey("config.cavechimes.worldgen.chance")
        @RangeDouble(min=0, max=1)
        public double chimeFrequency = 0.3f;

        @Name("Dimension list")
        @Comment("A whitelist/blacklist for which dimensions Cave Chimes generate in.")
        @LangKey("config.cavechimes.worldgen.dimensions")
        public int[] dimensions = { DimensionType.OVERWORLD.getId() };

        @Name("Dimension blacklist")
        @Comment("If enabled, the Dimension list will function as a blacklist instead of a whitelist.")
        @LangKey("config.cavechimes.worldgen.dimensions_blacklist")
        public boolean dimBlacklist = false;

        @Name("Biome list")
        @Comment("A whitelist/blacklist for which biomes Cave Chimes generate in.")
        @LangKey("config.cavechimes.worldgen.biomes")
        public String[] biomes = {};

        @Name("Biome blacklist")
        @Comment("If enabled, the Biome list will function as a blacklist instead of a whitelist.")
        @LangKey("config.cavechimes.worldgen.biomes_blacklist")
        public boolean biomeBlacklist = true;
    }

    public static class ObtainingOptions {
        @Name("Block drop")
        @Comment("Toggles Cave Chimes dropping themselves when broken.")
        @LangKey("config.cavechimes.obtaining.block_drops")
        public boolean canBlockDrop = true;
        @Name("Silk touch required")
        @Comment("If true, Cave Chimes will only drop themselves if mined with Silk Touch.")
        @LangKey("config.cavechimes.obtaining.silk_touch")
        public boolean silkTouchRequired = false;
        @Name("Crafting")
        @Comment("Toggles the Cave Chimes crafting recipe.")
        @LangKey("config.cavechimes.obtaining.crafting")
        public boolean canCraft = true;
        @Name("Creeper drops")
        @Comment("Toggles Creepers being able to drop Cave Chimes when killed by a Witch.")
        @LangKey("config.cavechimes.obtaining.creeper_drops")
        public boolean canCreepersDrop = true;
    }

    public static class ClientOptions {
        @Name("Volume modifier")
        @Comment("A multiplier for Cave Chimes volume.")
        @LangKey("config.cavechimes.client.volume")
        @RangeDouble(min=0, max=1.5)
        public double chimeVolume = 1d;

        @Name("Swing speed")
        @Comment("A multiplier for Cave Chimes animation speed.")
        @LangKey("config.cavechimes.client.swing")
        @RangeDouble(min=0, max=5)
        public double chimeSwing = 1;

        @Name("Simplified model")
        @Comment("Replaces the Cave Chimes' custom model with a simplified cross model that lacks animations.")
        @LangKey("config.cavechimes.client.simplified")
        @RequiresMcRestart
        public boolean simplifiedModel = false;

        @Name("Priority options")
        @Comment("Due to the way Minecraft's sound system works, a limit of 8 Cave Chimes can be producing sound at once. The priority system ensures that the 8 Chimes that are playing are the ones closest to the player.")
        @LangKey("config.cavechimes.options.priority")
        public PriorityOptions priority = new PriorityOptions();
    }

    public static class PriorityOptions {
        @Name("Priority enabled")
        @Comment("Be warned, disabling this option may result in Cave Chimes not playing at times, as chimes nowhere near the player get priority over the ones the player may actually be able to hear.")
        @LangKey("config.cavechimes.priority.enabled")
        public boolean enabled = true;

        @Name("Chimes limit")
        @Comment("The amount of Cave Chimes the system will keep track of. If your render distance is above 16, I'd recommend increasing this.")
        @LangKey("config.cavechimes.priority.limit")
        @RangeInt(min=16, max=1024)
        public int limit = 300;

        @Name("Refresh interval")
        @Comment("Time between Priority refreshes (in seconds). Values 1-10 recommended for aesthetic, values 5-20 recommended for performance.")
        @LangKey("config.cavechimes.priority.refresh_rate")
        public int refreshInterval = 3;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        //CaveChimesMod.getLogger().info("Config changed!");
        if (event.getModID().equals("cavechimes"))
            ConfigManager.sync("cavechimes", net.minecraftforge.common.config.Config.Type.INSTANCE);
    }
}
