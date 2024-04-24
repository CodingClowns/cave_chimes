package com.fincode.cavechimes.client.renderer;

import com.fincode.cavechimes.Config;
import com.fincode.cavechimes.common.tileentity.TileEntityCaveChimes;
import com.fincode.cavechimes.client.model.ModelCaveChimes;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEntityCaveChimesRenderer extends TileEntityRenderer<TileEntityCaveChimes> {
    private static final ResourceLocation TEXTURE_CHIMES = new ResourceLocation("cavechimes", "textures/entity/cave_chimes.png");
    private final ModelCaveChimes chimesModel = new ModelCaveChimes();

    private static final float SPEED_MULT = 0.08f;
    private static final float SPEED_BASE = 0.06f;
    private static final float SPEED_CHIMES = 0.1f;

    public TileEntityCaveChimesRenderer() {
    }

    @Override
    public void render(TileEntityCaveChimes chimes, double posX, double posY, double posZ, float subTick, int p_192841_9_) {
        GlStateManager.enableDepthTest();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);

        ModelCaveChimes model = this.chimesModel;
        if (p_192841_9_ >= 0) {
            this.bindTexture(DESTROY_STAGES[p_192841_9_]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scalef(4.0F, 4.0F, 1.0F);
            GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
            this.bindTexture(TEXTURE_CHIMES);
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        if (p_192841_9_ < 0) {
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1);
        }

        GlStateManager.translatef((float)posX, (float)posY + 1.0F, (float)posZ);
        GlStateManager.scalef(1.0F, -1.0F, -1.0F);
        //GlStateManager.translate(0.5F, 0.5F, 0.5F);

        GlStateManager.translatef(0.5F, -0.5F, -0.5F);

        float speed = Config.client.chimeSwing.get().floatValue() * SPEED_MULT;
        float swing = ((15 - chimes.getVolume()) / 15f);
        float time = (getWorld().getGameTime() + subTick);

        if (speed != 0) {
            model.body.rotateAngleX = getAnimXBase(chimes, time, swing, speed);
            model.body.rotateAngleZ = getAnimZBase(chimes, time, swing, speed);

            model.chime_big.rotateAngleX = getAnimXChime(chimes, time, swing, speed, chimes.chimeOffsets[0]);
            model.chime_big.rotateAngleZ = getAnimZChime(chimes, time, swing, speed, chimes.chimeOffsets[0]);

            model.chime_med_0.rotateAngleX = getAnimXChime(chimes, time, swing, speed, chimes.chimeOffsets[1]);
            model.chime_med_0.rotateAngleZ = getAnimZChime(chimes, time, swing, speed, chimes.chimeOffsets[1]);

            model.chime_med_1.rotateAngleX = getAnimXChime(chimes, time, swing, speed, chimes.chimeOffsets[2]);
            model.chime_med_1.rotateAngleZ = getAnimZChime(chimes, time, swing, speed, chimes.chimeOffsets[2]);

            model.chime_small.rotateAngleX = getAnimXChime(chimes, time, swing, speed, chimes.chimeOffsets[3]);
            model.chime_small.rotateAngleZ = getAnimZChime(chimes, time, swing, speed, chimes.chimeOffsets[3]);
        }
        else {
            model.body.rotateAngleX = 0;
            model.body.rotateAngleZ = 0;

            model.chime_big.rotateAngleX = 0;
            model.chime_big.rotateAngleZ = 0;

            model.chime_med_0.rotateAngleX = 0;
            model.chime_med_0.rotateAngleZ = 0;

            model.chime_med_1.rotateAngleX = 0;
            model.chime_med_1.rotateAngleZ = 0;

            model.chime_small.rotateAngleX = 0;
            model.chime_small.rotateAngleZ = 0;
        }

        /* // Animations
        float lvt_14_1_ = chimes.prevLidAngle + (chimes.lidAngle - chimes.prevLidAngle) * p_192841_8_;
        float lvt_15_2_;
        if (chimes.adjacentChestZNeg != null) {
            lvt_15_2_ = chimes.adjacentChestZNeg.prevLidAngle + (chimes.adjacentChestZNeg.lidAngle - chimes.adjacentChestZNeg.prevLidAngle) * p_192841_8_;
            if (lvt_15_2_ > lvt_14_1_) {
                lvt_14_1_ = lvt_15_2_;
            }
        }

        if (chimes.adjacentChestXNeg != null) {
            lvt_15_2_ = chimes.adjacentChestXNeg.prevLidAngle + (chimes.adjacentChestXNeg.lidAngle - chimes.adjacentChestXNeg.prevLidAngle) * p_192841_8_;
            if (lvt_15_2_ > lvt_14_1_) {
                lvt_14_1_ = lvt_15_2_;
            }
        }

        lvt_14_1_ = 1.0F - lvt_14_1_;
        lvt_14_1_ = 1.0F - lvt_14_1_ * lvt_14_1_ * lvt_14_1_;
        model.chestLid.rotateAngleX = -(lvt_14_1_ * 1.5707964F);*/

        model.renderAll();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (p_192841_9_ >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

    private float getAnimSin(float partSpeed, float time, float speed) { return MathHelper.sin(partSpeed * time * speed); }
    private float getAnimCos(float partSpeed, float time, float speed) { return MathHelper.cos(partSpeed * time * speed); }

    private float getAnimXBase(TileEntityCaveChimes chimes, float time, float swing, float speed) { return getAnimSin(chimes.discSpeedX, time, speed) * SPEED_BASE * swing; }
    private float getAnimZBase(TileEntityCaveChimes chimes, float time, float swing, float speed) { return getAnimCos(chimes.discSpeedZ, time, speed) * SPEED_BASE * swing; }

    private float getAnimXChime(TileEntityCaveChimes chimes, float time, float swing, float speed, int chimeOffset) { return getAnimSin(chimes.discSpeedX, time + chimeOffset, speed) * SPEED_CHIMES * swing; }
    private float getAnimZChime(TileEntityCaveChimes chimes, float time, float swing, float speed, int chimeOffset) { return getAnimCos(chimes.discSpeedZ, time + chimeOffset, speed) * SPEED_CHIMES * swing; }
}
