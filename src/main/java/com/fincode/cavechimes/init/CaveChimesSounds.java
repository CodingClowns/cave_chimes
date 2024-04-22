package com.fincode.cavechimes.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder("cavechimes")
@Mod.EventBusSubscriber
public class CaveChimesSounds {
    public static final SoundEvent CAVE_CHIMES = new SoundEvent(new ResourceLocation("cavechimes", "cave_chimes")).setRegistryName("cavechimes:cave_chimes");

    @SubscribeEvent
    public static void register(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(CAVE_CHIMES);
    }
}
