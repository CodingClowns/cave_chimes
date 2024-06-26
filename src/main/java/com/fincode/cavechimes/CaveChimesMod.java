package com.fincode.cavechimes;

import com.fincode.cavechimes.init.CaveChimesBlocks;
import net.minecraftforge.fml.relauncher.Side;
import com.fincode.cavechimes.misc.ChimesDropHandler;
import com.fincode.cavechimes.common.world.feature.WorldGenCaveChimes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = CaveChimesMod.MODID, name = CaveChimesMod.NAME, version = CaveChimesMod.VERSION)
public class CaveChimesMod
{
    public static final String MODID = "cavechimes";
    public static final String NAME = "Cave Chimes";
    public static final String VERSION = "1.1";

    private static Logger logger;
    private static boolean simplifiedModel;

    public static Logger getLogger() {
        return logger;
    }
    public static boolean isModelSimplified() { return simplifiedModel; }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        simplifiedModel = Config.client.simplifiedModel;

        CaveChimesBlocks.registerTileEntities();

        if (event.getSide() == Side.CLIENT) {
            //logger.info("Registering block renderer! I hope...");
            com.fincode.cavechimes.init.CaveChimesClient.registerRenderers();
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        GameRegistry.registerWorldGenerator(new WorldGenCaveChimes(), 50);

        ChimesDropHandler.init();
    }
}
