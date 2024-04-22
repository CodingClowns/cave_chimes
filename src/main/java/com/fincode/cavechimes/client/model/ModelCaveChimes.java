// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

package com.fincode.cavechimes.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCaveChimes extends ModelBase {
	public final ModelRenderer body;
	public final ModelRenderer chime_big;
	public final ModelRenderer chime_med_0;
	public final ModelRenderer chime_med_1;
	public final ModelRenderer chime_small;
	private final ModelRenderer bb_main;
	private final ModelRenderer chain_r1;
	private final ModelRenderer chain_r2;

	public ModelCaveChimes() {
		textureWidth = 32;
		textureHeight = 16;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 9.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 7, -4.0F, 0.0F, -4.0F, 8, 1, 8, 0.0F, false));

		chime_big = new ModelRenderer(this);
		chime_big.setRotationPoint(2.0F, 1.0F, 2.0F);
		body.addChild(chime_big);
		chime_big.cubeList.add(new ModelBox(chime_big, 0, 0, -1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F, false));
		chime_big.cubeList.add(new ModelBox(chime_big, 16, 5, -1.0F, 5.0F, 0.0F, 2, 1, 1, 0.0F, false));
		chime_big.cubeList.add(new ModelBox(chime_big, 0, 10, -1.0F, 6.0F, -1.0F, 2, 3, 2, 0.0F, false));
		chime_big.cubeList.add(new ModelBox(chime_big, 16, 5, -1.0F, 9.0F, 0.0F, 2, 1, 1, 0.0F, false));
		chime_big.cubeList.add(new ModelBox(chime_big, 24, 8, -1.0F, 10.0F, -1.0F, 2, 2, 2, 0.0F, false));

		chime_med_0 = new ModelRenderer(this);
		chime_med_0.setRotationPoint(2.0F, 1.0F, -2.0F);
		body.addChild(chime_med_0);
		chime_med_0.cubeList.add(new ModelBox(chime_med_0, 24, 0, -1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F, false));
		chime_med_0.cubeList.add(new ModelBox(chime_med_0, 16, 2, -1.0F, 6.0F, 0.0F, 2, 2, 1, 0.0F, false));
		chime_med_0.cubeList.add(new ModelBox(chime_med_0, 24, 8, -1.0F, 8.0F, -1.0F, 2, 2, 2, 0.0F, false));

		chime_med_1 = new ModelRenderer(this);
		chime_med_1.setRotationPoint(-2.0F, 1.0F, 2.0F);
		body.addChild(chime_med_1);
		chime_med_1.cubeList.add(new ModelBox(chime_med_1, 24, 0, -1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F, false));
		chime_med_1.cubeList.add(new ModelBox(chime_med_1, 16, 2, -1.0F, 6.0F, 0.0F, 2, 2, 1, 0.0F, false));
		chime_med_1.cubeList.add(new ModelBox(chime_med_1, 24, 8, -1.0F, 8.0F, -1.0F, 2, 2, 2, 0.0F, false));

		chime_small = new ModelRenderer(this);
		chime_small.setRotationPoint(-2.0F, 1.0F, -2.0F);
		body.addChild(chime_small);
		chime_small.cubeList.add(new ModelBox(chime_small, 0, 0, -1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F, false));
		chime_small.cubeList.add(new ModelBox(chime_small, 16, 5, -1.0F, 5.0F, 0.0F, 2, 1, 1, 0.0F, false));
		chime_small.cubeList.add(new ModelBox(chime_small, 24, 8, -1.0F, 6.0F, -1.0F, 2, 2, 2, 0.0F, false));

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		chain_r1 = new ModelRenderer(this);
		chain_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		bb_main.addChild(chain_r1);
		setRotationAngle(chain_r1, 0.0F, 0.7854F, 0.0F);
		chain_r1.cubeList.add(new ModelBox(chain_r1, 12, 3, 0.0F, -17.0F, -1.0F, 0, 2, 2, 0.0F, false));

		chain_r2 = new ModelRenderer(this);
		chain_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
		bb_main.addChild(chain_r2);
		setRotationAngle(chain_r2, 0.0F, -0.7854F, 0.0F);
		chain_r2.cubeList.add(new ModelBox(chain_r2, 12, 3, 0.0F, -17.0F, -1.0F, 0, 2, 2, 0.0F, false));
	}

	public void renderAll() {
		body.render(0.0625F);
		bb_main.render(0.0625F);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}