package com.fincode.cavechimes;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Config.*;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

@net.minecraftforge.common.config.Config(modid="cavechimes", category = "")
@Mod.EventBusSubscriber(modid="cavechimes")
public final class Config {
    @Ignore
    public static final String CATEGORY_WORLD = "world";
    @Ignore
    public static final String CATEGORY_OBTAINING = "obtaining";
    @Ignore
    public static final String CATEGORY_CLIENT = "client";

    @Name("Worldgen")
    @Comment("Controls options regarding naturally generated Cave Chimes.")
    @LangKey("config.cavechimes.world")
    public static WorldgenOptions worldgen = new WorldgenOptions();

    @Name("Obtaining")
    @Comment("Controls the the various ways of obtaining Cave Chimes as an item.")
    @LangKey("config.cavechimes.obtaining")
    public static ObtainingOptions obtaining = new ObtainingOptions();

    @Name("Client")
    @Comment("Client-side options.")
    @LangKey("config.cavechimes.client")
    public static ClientOptions client = new ClientOptions();

    public static class WorldgenOptions {
        @Name("Generate Chimes")
        @Comment("If false, Cave Chimes will not generate naturally.")
        public boolean generateChimes = true;
        @Name("Minimum height")
        @Comment("The minimum y-value at which Cave Chimes can generate.")
        public int minChimeHeight = 16;
        @Name("Maximum height")
        @Comment("The maximum y-value at which Cave Chimes can generate.")
        public int maxChimeHeight = 35;
        @Name("Generation frequency")
        @Comment("The chance for Cave Chimes to generate (per chunk).")
        @RangeDouble(min=0, max=1)
        public double chimeFrequency = 0.3f;

        @Name("Dimension list")
        @Comment("A whitelist/blacklist for which dimensions Cave Chimes generate in.")
        public int[] dimensions = { DimensionType.OVERWORLD.getId() };

        @Name("Dimension blacklist")
        @Comment("If enabled, the Dimension list will function as a blacklist instead of a whitelist.")
        public boolean dimBlacklist = false;

        @Name("Biome list")
        @Comment("A whitelist/blacklist for which biomes Cave Chimes generate in.")
        public String[] biomes = {};

        @Name("Biome blacklist")
        @Comment("If enabled, the Biome list will function as a blacklist instead of a whitelist.")
        public boolean biomeBlacklist = true;
    }

    public static class ObtainingOptions {
        @Name("Block drop")
        @Comment("Toggles Cave Chimes dropping themselves when broken.")
        public boolean canBlockDrop = true;
        @Name("Silk touch required")
        @Comment("If true, Cave Chimes will only drop themselves if mined with Silk Touch.")
        public boolean silkTouchRequired = false;
        @Name("Crafting")
        @Comment("Toggles the Cave Chimes crafting recipe.")
        public boolean canCraft = true;
        @Name("Creeper drops")
        @Comment("Toggles Creepers being able to drop Cave Chimes when killed by a Witch.")
        public boolean canCreepersDrop = true;
    }

    public static class ClientOptions {
        @Name("Volume modifier")
        @Comment("A multiplier for Cave Chimes volume.")
        @RangeDouble(min=0, max=1.5)
        public double chimeVolume = 1d;

        @Name("Swing speed")
        @Comment("A multiplier for Cave Chimes animation speed.")
        @RangeDouble(min=0, max=5)
        public double chimeSwing = 1;

        @Name("Simplified model")
        @Comment("Replaces the Cave Chimes' custom model with a simplified cross model that lacks animations.")
        @RequiresMcRestart
        public boolean simplifiedModel = false;

        @Name("Priority options")
        @Comment("Due to the way Minecraft's sound system works, a limit of 8 Cave Chimes can be producing sound at once. The priority system ensures that the 8 Chimes that are playing are the ones closest to the player.")
        public PriorityOptions priority = new PriorityOptions();
    }

    public static class PriorityOptions {
        @Name("Priority enabled")
        @Comment("Be warned, disabling this option may result in Cave Chimes not playing at times, as chimes nowhere near the player get priority over the ones the player may actually be able to hear.")
        public boolean enabled = true;

        @Name("Chimes limit")
        @Comment("The amount of Cave Chimes the system will keep track of. If your render distance is above 16, I'd recommend increasing this.")
        @RangeInt(min=16, max=1024)
        public int limit = 300;

        @Name("Refresh interval")
        @Comment("Time between Priority refreshes (in seconds). Values 1-10 recommended for aesthetic, values 5-20 recommended for performance.")
        public int refreshInterval = 3;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        CaveChimesMod.getLogger().info("Config changed!");
        if (event.getModID().equals("cavechimes"))
            ConfigManager.sync("cavechimes", net.minecraftforge.common.config.Config.Type.INSTANCE);
    }
}
