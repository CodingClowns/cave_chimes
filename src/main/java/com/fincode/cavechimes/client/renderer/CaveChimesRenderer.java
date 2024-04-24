package com.fincode.cavechimes.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CaveChimesRenderer {
    public CaveChimesRenderer() {
    }
    // Adds support for 3D Cave Chimes item rendering. Will I ever actually implement this? Who knows...
    public void renderCaveChimesBrightness(Block block, float brightness) {
        GlStateManager.color4f(brightness, brightness, brightness, 1.0F);
        GlStateManager.rotatef(90.0F, 0.0F, 1.0F, 0.0F);
        ItemStack stack = new ItemStack(block);
        stack.getItem().getTileEntityItemStackRenderer().renderByItem(stack);
    }
}
