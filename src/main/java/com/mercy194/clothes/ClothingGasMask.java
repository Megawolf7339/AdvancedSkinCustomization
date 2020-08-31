package com.mercy194.clothes;

import com.mercy194.gfx.SteinModelRenderer;
import com.mercy194.main.AdvClothing;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class ClothingGasMask extends AdvClothing {
	
	private ResourceLocation TEXTURE = new ResourceLocation(AdvSkinMod.MODID, "clothes/gas_mask/variant0.png");
	
	private SteinModelRenderer.SkinnedModelBox cBreather, lVent, rVent;
	public ClothingGasMask() {
		super("gas_mask");
		cBreather = new SteinModelRenderer.SkinnedModelBox(16, 16, 0, 0, -1.0F, -2.75F, -5.2F, 2, 3, 1, 0.13F, false);
		lVent = new SteinModelRenderer.SkinnedModelBox(16, 16, 5, 6, -3.0F, -2.0F, -5.0F, 2, 2, 1, 0.13F, false);
		rVent = new SteinModelRenderer.SkinnedModelBox(16, 16, 0, 4, 1.0F, -2.0F, -5.0F, 2, 2, 1, 0.13F, false);
	}

	public void initialize() {

		super.initialize();
	}
	public void render(MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn,
			AbstractClientPlayerEntity ent, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch) {
		
		String playerName = PlayerEntity.getUUID(ent.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
    	PlayerRenderer rend = (PlayerRenderer) Minecraft.getInstance().getRenderManager().getRenderer(ent);
    	PlayerModel<AbstractClientPlayerEntity> model = rend.getEntityModel(); 
    	if(isFirstPerson) return;

    	if(!shouldRender(plr)) return;
        
        if(plr.getAccessoryInt("mask") == 1 /*&& ent.isWearing(PlayerModelPart.HAT)*/) { //TODO: Move somewhere outside this
			
        	pushMatrix(matrixStack, model.bipedHead, 0);
        	//if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            

			RenderType type = RenderType.entityTranslucent(TEXTURE);
			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(type);

			
			renderSkinnedBox(cBreather, matrixStack, ivertexbuilder, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			
			matrixStack.push();
				matrixStack.translate(0.12f, 0, -0.08f);
				matrixStack.rotate(new Quaternion(0, 24, 0, true));
	            renderSkinnedBox(lVent, matrixStack, ivertexbuilder, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
	            matrixStack.pop();
			
			matrixStack.push();
				matrixStack.translate(-0.12f, 0, -0.08f);
				matrixStack.rotate(new Quaternion(0, -24, 0, true));
	            renderSkinnedBox(rVent, matrixStack, ivertexbuilder, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
	            matrixStack.pop();
			
			matrixStack.pop();

			if(ent.isInvisible()) {
				//GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
        }
	    RenderSystem.color3f(1f, 1f, 1f);

	}
}
