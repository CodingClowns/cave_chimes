package com.fincode.cavechimes.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder("cavechimes")
@Mod.EventBusSubscriber
public class CaveChimesItems {
    public static Item CAVE_CHIMES = null;

    private static ItemBlock create(IForgeRegistry<Item> itemsRegistry, String name, Block block, ItemBlock itemBlock) {
        itemBlock.setRegistryName(name);
        itemsRegistry.register(itemBlock);
        return itemBlock;
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        CAVE_CHIMES = create(event.getRegistry(), "cave_chimes", CaveChimesBlocks.CAVE_CHIMES, new ItemBlock(CaveChimesBlocks.CAVE_CHIMES));
    }
}
