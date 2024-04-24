package com.fincode.cavechimes.init;

import com.fincode.cavechimes.CaveChimesMod;
import com.fincode.cavechimes.common.block.BlockCaveChimes;
import com.fincode.cavechimes.common.tileentity.TileEntityCaveChimes;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("cavechimes")
@Mod.EventBusSubscriber(modid = CaveChimesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CaveChimesBlocks {
    public static Block CAVE_CHIMES = null;
    public static TileEntityType<TileEntityCaveChimes> CAVE_CHIMES_TYPE = null;

    public static Block registerBlock(IForgeRegistry<Block> registry, String name, Block block) {
        block.setRegistryName("cavechimes", name);
        registry.register(block);
        return block;
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        CAVE_CHIMES = registerBlock(registry, "cave_chimes", (new BlockCaveChimes()));

    }

    @SubscribeEvent
    public static void registerTileEntities(RegistryEvent<TileEntityType<?>> event) {
        CAVE_CHIMES_TYPE = TileEntityType.register("cavechimes:cave_chimes", TileEntityType.Builder.create(TileEntityCaveChimes::new));
        ForgeRegistries.TILE_ENTITIES.register(CAVE_CHIMES_TYPE);
    }
}
