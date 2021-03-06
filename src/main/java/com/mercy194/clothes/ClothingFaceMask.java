package com.mercy194.clothes;

import com.mercy194.gfx.SteinModelRenderer;
import com.mercy194.main.AdvClothing;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.ClothingTextures;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;

public class ClothingFaceMask extends AdvClothing {
	

	private SteinModelRenderer.SkinnedModelBox faceMask;
	public ClothingFaceMask() {
		super("face_mask");
    	faceMask = new SteinModelRenderer.SkinnedModelBox(32, 16, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.55f, false);
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

    	
        if(plr.getAccessoryInt("facemask") == 1) { //TODO: Move somewhere outside this
        	//if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
        	pushMatrix(matrixStack, model.bipedHead, 0);
			int combineTex = 15728640;
			
			RenderType type = RenderType.entityTranslucent(ClothingTextures.FACE_MASK[plr.getAccessoryInt("facemask_var")]);
			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(type);

			renderSkinnedBox(faceMask, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
			matrixStack.pop();
			if(ent.isInvisible()) {
				//GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
        }
	    RenderSystem.color3f(1f, 1f, 1f);
	}
	
}
