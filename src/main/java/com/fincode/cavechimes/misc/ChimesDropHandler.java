package com.fincode.cavechimes.misc;

import com.fincode.cavechimes.CaveChimesMod;
import com.fincode.cavechimes.Config;
import com.fincode.cavechimes.init.CaveChimesFeatures;
import com.fincode.cavechimes.init.CaveChimesItems;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ObjectHolder;

// Uses events to (hopefully) allow Cave Chimes to be dropped by Creepers when killed by Witches.
@ObjectHolder("cavechimes")
@Mod.EventBusSubscriber(modid = CaveChimesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ChimesDropHandler {
    public static boolean initialized = init();

    public static boolean init() {
        CaveChimesMod.getLogger().info("Stand back! There may be a large Creeper dropping!");

        return true;
    }

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        //CaveChimesMod.getLogger().info("Something is DYING!!!");
        if (!Config.obtaining.canCreepersDrop.get()) return;
        //CaveChimesMod.getLogger().info("Creeper Drops are enabled!");
        if (!(event.getEntityLiving() instanceof EntityCreeper)) return;
        //CaveChimesMod.getLogger().info("Entity is a Creeper!");
        if (!(event.getSource().getTrueSource() instanceof EntityWitch)) return;
        //CaveChimesMod.getLogger().info("Entity was killed by a Witch!!!!");

        event.getEntity().entityDropItem(CaveChimesItems.CAVE_CHIMES, 1);
    }

}
