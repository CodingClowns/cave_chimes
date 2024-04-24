package com.fincode.cavechimes.init;

import com.fincode.cavechimes.CaveChimesMod;
import com.fincode.cavechimes.Config;
import com.fincode.cavechimes.common.tileentity.TileEntityCaveChimes;
import com.fincode.cavechimes.client.model.BakedModelCaveChimes;
import com.fincode.cavechimes.client.renderer.TileEntityCaveChimesRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = "cavechimes", value=Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CaveChimesClient {
    private static boolean isModelSimplified;

    public static boolean isModelSimplified() {return isModelSimplified;}

    public static void initClient() {
        CaveChimesMod.getLogger().info("Registering Cave Chimes renderer. " + (isModelSimplified ? "Using simplified model." : "Using normal model."));
        isModelSimplified = Config.client.simplifiedModel.get();
        if (!isModelSimplified)
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCaveChimes.class, new TileEntityCaveChimesRenderer());
    }

    @SubscribeEvent
    public static void bake(ModelBakeEvent event) { // All this just to add block breaking particles to the Cave Chimes <:l
        if (isModelSimplified) return;
        //CaveChimesMod.getLogger().info("Baking you a delicious cake!");
        for (ModelResourceLocation location : event.getModelRegistry().keySet()) {
            if (!location.getNamespace().equals("cavechimes")) continue;
            //CaveChimesMod.getLogger().info("Cave chimes domain located!");

            IBakedModel original = event.getModelRegistry().get(location);
            //CaveChimesMod.getLogger().info(location.getResourceDomain() + ":" + location.getResourcePath() + " " + location.getVariant());
            if (!location.getPath().contains("cave_chimes") || location.getVariant().contains("inventory") || original == null) continue;

            //CaveChimesMod.getLogger().info("Awwwwwww yeaaaaaahhh!!!!");
            event.getModelRegistry().put(location, new BakedModelCaveChimes(original, event.getModelManager().getBlockModelShapes().getTexture(Blocks.GOLD_BLOCK.getDefaultState())));
        }
    }
}
