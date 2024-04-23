package com.fincode.cavechimes.init;

import com.fincode.cavechimes.common.block.BlockCaveChimes;
import com.fincode.cavechimes.common.block.entity.TileEntityCaveChimes;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder("cavechimes")
@Mod.EventBusSubscriber
public class CaveChimesBlocks {
    public static Block CAVE_CHIMES = null;

    public static Block registerBlock(IForgeRegistry<Block> registry, String name, Block block) {
        block.setRegistryName("cavechimes", name);
        block.setUnlocalizedName("cavechimes:" + name);
        registry.register(block);
        return block;
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        CAVE_CHIMES = registerBlock(registry, "cave_chimes", (new BlockCaveChimes()));
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityCaveChimes.class, new ResourceLocation("cavechimes", "cave_chimes"));
    }
}
