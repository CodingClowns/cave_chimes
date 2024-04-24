package com.fincode.cavechimes.init;

import com.fincode.cavechimes.CaveChimesMod;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("cavechimes")
@Mod.EventBusSubscriber(modid = CaveChimesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CaveChimesSounds {
    public static final SoundEvent CAVE_CHIMES = new SoundEvent(new ResourceLocation("cavechimes", "cave_chimes")).setRegistryName("cavechimes:block.cave_chimes");

    @SubscribeEvent
    public static void register(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(CAVE_CHIMES);
    }
}
