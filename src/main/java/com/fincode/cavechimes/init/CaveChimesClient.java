package com.fincode.cavechimes.init;

import com.fincode.cavechimes.CaveChimesMod;
import com.fincode.cavechimes.Config;
import com.fincode.cavechimes.common.block.entity.TileEntityCaveChimes;
import com.fincode.cavechimes.client.model.BakedModelCaveChimes;
import com.fincode.cavechimes.client.renderer.TileEntityCaveChimesRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@GameRegistry.ObjectHolder("cavechimes")
@Mod.EventBusSubscriber(modid = "cavechimes", value=Side.CLIENT)
public class CaveChimesClient {
    public static void registerRenderers() {
        boolean simplified = CaveChimesMod.isModelSimplified();
        CaveChimesMod.getLogger().info("Registering Cave Chimes renderer. " + (simplified ? "Using simplified model." : "Using normal model."));
        if (!simplified)
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCaveChimes.class, new TileEntityCaveChimesRenderer());
    }

    @SubscribeEvent
    public static void register(ModelRegistryEvent event) {
        registerItemModel(CaveChimesItems.CAVE_CHIMES);
    }

    @SubscribeEvent
    public static void bake(ModelBakeEvent event) { // All this just to add block breaking particles to the Cave Chimes <:l
        if (CaveChimesMod.isModelSimplified()) return;
        //CaveChimesMod.getLogger().info("Baking you a delicious cake!");
        for (ModelResourceLocation location : event.getModelRegistry().getKeys()) {
            if (!location.getResourceDomain().equals("cavechimes")) continue;
            //CaveChimesMod.getLogger().info("Cave chimes domain located!");

            IBakedModel original = event.getModelRegistry().getObject(location);
            //CaveChimesMod.getLogger().info(location.getResourceDomain() + ":" + location.getResourcePath() + " " + location.getVariant());
            if (!location.getResourcePath().contains("cave_chimes") || location.getVariant().contains("inventory") || original == null) continue;

            //CaveChimesMod.getLogger().info("Awwwwwww yeaaaaaahhh!!!!");
            event.getModelRegistry().putObject(location, new BakedModelCaveChimes(original, event.getModelManager().getTextureMap().getAtlasSprite("minecraft:blocks/gold_block")));
        }
    }

    private static void registerItemModel(Item item) {
        ModelBakery.registerItemVariants(item, item.getRegistryName());
        ModelLoader.setCustomMeshDefinition(item, s -> new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
