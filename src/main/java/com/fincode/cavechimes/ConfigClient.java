package com.fincode.cavechimes;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigClient {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ClientOptions client = new ClientOptions(BUILDER);

    public static final ForgeConfigSpec spec = BUILDER.build();


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

        public ForgeConfigSpec.DoubleValue chimeVolume;
        public ForgeConfigSpec.DoubleValue chimeSwing;
        public ForgeConfigSpec.BooleanValue simplifiedModel;

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

        public ForgeConfigSpec.BooleanValue enabled;
        public ForgeConfigSpec.ConfigValue<Integer> limit;
        public ForgeConfigSpec.ConfigValue<Integer> refreshInterval;
    }
}
