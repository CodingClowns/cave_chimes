package com.fincode.cavechimes;

import com.fincode.cavechimes.init.CaveChimesFeatures;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("cavechimes")
@Mod.EventBusSubscriber(modid = CaveChimesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CaveChimesMod
{
    public static final String MODID = "cavechimes";
    public static final String NAME = "Cave Chimes";
    public static final String VERSION = "1.0";

    private static final Logger logger = LogManager.getLogger();

    public static Logger getLogger() {
        return logger;
    }

    public CaveChimesMod() {
        logger.info("WAEH");
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigCommon.spec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigClient.spec);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    @SubscribeEvent
    public void setupClient(final FMLClientSetupEvent event) {
        com.fincode.cavechimes.init.CaveChimesClient.initClient();
    }

    @SubscribeEvent
    public void setupCommon(final FMLCommonSetupEvent event) {
        CaveChimesFeatures.init();
    }
}
