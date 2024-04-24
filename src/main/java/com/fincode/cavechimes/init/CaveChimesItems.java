package com.fincode.cavechimes.init;

import com.fincode.cavechimes.CaveChimesMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("cavechimes")
@Mod.EventBusSubscriber(modid = CaveChimesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CaveChimesItems {
    public static Item CAVE_CHIMES = null;

    private static ItemBlock create(IForgeRegistry<Item> itemsRegistry, String name, ItemBlock itemBlock) {
        itemBlock.setRegistryName("cavechimes:" + name);
        itemsRegistry.register(itemBlock);
        return itemBlock;
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        CAVE_CHIMES = create(event.getRegistry(), "cave_chimes", new ItemBlock(CaveChimesBlocks.CAVE_CHIMES, new Item.Properties().group(ItemGroup.DECORATIONS)));
    }
}
