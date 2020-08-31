package com.mercy194.render;

import com.mercy194.main.AdvSkinMod;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;

public class LayerClothing extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

	public boolean isFirstPerson = false;

	
	private long timer = System.currentTimeMillis(), millisRender;
	
	public LayerClothing(boolean isFirstPerson, IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> player) {
		super(player);
		this.isFirstPerson = isFirstPerson;
	}


	public boolean init = false;
	@Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity ent, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		millisRender = System.nanoTime();
		
		if(AdvSkinMod.getPlayerByName(PlayerEntity.getUUID(ent.getGameProfile()).toString()) != null) {
			if(AdvSkinMod.getPlayerByName(PlayerEntity.getUUID(ent.getGameProfile()).toString()).usingMod) {
		        try {
		        	matrixStack.push();
		        	for(int adv = 0; adv < AdvSkinMod.CLOTHING.size(); adv++) {
		        		AdvSkinMod.CLOTHING.get(adv).isFirstPerson = this.isFirstPerson;
	        			AdvSkinMod.CLOTHING.get(adv).render(matrixStack, bufferIn, packedLightIn, ent, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
		        	}
		        	matrixStack.pop();
		        } catch(Exception e) {}
	        }
        }
		
		AdvSkinMod.millisRender += (System.nanoTime() - millisRender) / 1000000f;
		
	}
}
