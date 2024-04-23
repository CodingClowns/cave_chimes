package com.fincode.cavechimes.misc;

import com.fincode.cavechimes.Config;
import com.fincode.cavechimes.init.CaveChimesItems;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

// Uses events to (hopefully) allow Cave Chimes to be dropped by Creepers when killed by Witches.
@GameRegistry.ObjectHolder("cavechimes")
@Mod.EventBusSubscriber
public class ChimesDropHandler {
    public static void init() {

    }

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        //CaveChimesMod.getLogger().info("Something is DYING!!!");
        if (!Config.obtaining.canCreepersDrop) return;
        //CaveChimesMod.getLogger().info("Creeper Drops are enabled!");
        if (!(event.getEntityLiving() instanceof EntityCreeper)) return;
        //CaveChimesMod.getLogger().info("Entity is a Creeper!");
        if (!(event.getSource().getTrueSource() instanceof EntityWitch)) return;
        //CaveChimesMod.getLogger().info("Entity was killed by a Witch!!!!");

        event.getEntity().dropItem(CaveChimesItems.CAVE_CHIMES, 1);
    }
}
