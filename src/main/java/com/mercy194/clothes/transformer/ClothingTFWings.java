package com.mercy194.clothes.transformer;

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
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ClothingTFWings extends AdvClothing {
    SteinModelRenderer.SkinnedModelBox LWing1, LWing2, LWing3, LWing4, LWing5, LWing6, LWing7, LWing8, LWing9, LWing10;
    SteinModelRenderer.SkinnedModelBox RWing1, RWing2, RWing3, RWing4, RWing5, RWing6, RWing7, RWing8, RWing9, RWing10;
    
    float wingRotation = 0;
	public ClothingTFWings() {
		super(new int[] {1, 2, 3});
	}
	
	public void initialize() {

		LWing1 = new SteinModelRenderer.SkinnedModelBox(64, 32, 10, 0, 1F, 0F, 0F, 3, 12, 1, 0F, true);
		LWing2 = new SteinModelRenderer.SkinnedModelBox(64, 32, 18, 0, 4F, -2F, 0F, 2, 13, 1, 0F, true);
		LWing3 = new SteinModelRenderer.SkinnedModelBox(64, 32, 24, 0, 6F, -3F, 0F, 1, 13, 1, 0F, true);
		LWing4 = new SteinModelRenderer.SkinnedModelBox(64, 32, 28, 0, 7F, -3F, 0F, 1, 12, 1, 0F, true);
		
		LWing5 = new SteinModelRenderer.SkinnedModelBox(64, 32, 32, 0, 8F, -3F, 0F, 1, 11, 1, 0F, true);
		LWing6 = new SteinModelRenderer.SkinnedModelBox(64, 32, 36, 0, 9F, -3F, 0F, 2, 10, 1, 0F, true);
		LWing7 = new SteinModelRenderer.SkinnedModelBox(64, 32, 42, 0, 11F, -3F, 0F, 2, 9, 1, 0F, true);
		LWing8 = new SteinModelRenderer.SkinnedModelBox(64, 32, 48, 0, 13F, -3F, 0F, 2, 8, 1, 0F, true);
		LWing9 = new SteinModelRenderer.SkinnedModelBox(64, 32, 54, 0, 15F, -4F, 0F, 1, 8, 1, 0F, true);
		LWing10 = new SteinModelRenderer.SkinnedModelBox(64, 32, 58, 0, 16F, -5F, 0F, 1, 8, 1, 0F, true);

		RWing1 = new SteinModelRenderer.SkinnedModelBox(64, 32, 10, 0, -1F, 0F, 0F, 3, 12, 1, 0F, false);
		RWing2 = new SteinModelRenderer.SkinnedModelBox(64, 32, 18, 0, -4F, -2F, 0F, 2, 13, 1, 0F, false);
		RWing3 = new SteinModelRenderer.SkinnedModelBox(64, 32, 24, 0, -6F, -3F, 0F, 1, 13, 1, 0F, false);
		RWing4 = new SteinModelRenderer.SkinnedModelBox(64, 32, 28, 0, -7F, -3F, 0F, 1, 12, 1, 0F, false);
		
		RWing5 = new SteinModelRenderer.SkinnedModelBox(64, 32, 32, 0, -8F, -3F, 0F, 1, 11, 1, 0F, false);
		RWing6 = new SteinModelRenderer.SkinnedModelBox(64, 32, 36, 0, -9F, -3F, 0F, 2, 10, 1, 0F, false);
		RWing7 = new SteinModelRenderer.SkinnedModelBox(64, 32, 42, 0, -11F, -3F, 0F, 2, 9, 1, 0F, false);
		RWing8 = new SteinModelRenderer.SkinnedModelBox(64, 32, 48, 0, -13F, -3F, 0F, 2, 8, 1, 0F, false);
		RWing9 = new SteinModelRenderer.SkinnedModelBox(64, 32, 54, 0, -15F, -4F, 0F, 1, 8, 1, 0F, false);
		RWing10 = new SteinModelRenderer.SkinnedModelBox(64, 32, 58, 0, -16F, -5F, 0F, 1, 8, 1, 0F, false);
	       	
		super.initialize();
	}

	public void render(MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn,
			AbstractClientPlayerEntity ent, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch) {
		
		ItemStack itemstack = ent.inventory.armorItemInSlot(2);
		if(itemstack.getItem() == Items.ELYTRA) return;
		String playerName = PlayerEntity.getUUID(ent.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
    	PlayerRenderer rend = (PlayerRenderer) Minecraft.getInstance().getRenderManager().getRenderer(ent);
    	PlayerModel<AbstractClientPlayerEntity> model = rend.getEntityModel(); 
    	if(isFirstPerson) model = this.getEntityModel();

    	if(!shouldRender(plr) && plr.getAccessoryInt("wings") != 1) return;

        	pushMatrix(matrixStack, model.bipedBody, 0);
        	//if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);


    		RWing1 = new SteinModelRenderer.SkinnedModelBox(64, 32, 10, 0, -4F, 0F, 0F, 3, 12, 1, 0F, false);
    		RWing2 = new SteinModelRenderer.SkinnedModelBox(64, 32, 18, 0, -6F, -2F, 0F, 2, 13, 1, 0F, false);
    		RWing3 = new SteinModelRenderer.SkinnedModelBox(64, 32, 24, 0, -7F, -3F, 0F, 1, 13, 1, 0F, false);
    		RWing4 = new SteinModelRenderer.SkinnedModelBox(64, 32, 28, 0, -8F, -3F, 0F, 1, 12, 1, 0F, false);
    		
    		RWing5 = new SteinModelRenderer.SkinnedModelBox(64, 32, 32, 0, -9F, -3F, 0F, 1, 11, 1, 0F, false);
    		RWing6 = new SteinModelRenderer.SkinnedModelBox(64, 32, 36, 0, -11F, -3F, 0F, 2, 10, 1, 0F, false);
    		RWing7 = new SteinModelRenderer.SkinnedModelBox(64, 32, 42, 0, -13F, -3F, 0F, 2, 9, 1, 0F, false);
    		RWing8 = new SteinModelRenderer.SkinnedModelBox(64, 32, 48, 0, -15F, -3F, 0F, 2, 8, 1, 0F, false);
    		RWing9 = new SteinModelRenderer.SkinnedModelBox(64, 32, 54, 0, -16F, -4F, 0F, 1, 8, 1, 0F, false);
    		RWing10 = new SteinModelRenderer.SkinnedModelBox(64, 32, 58, 0, -17F, -5F, 0F, 1, 8, 1, 0F, false);
        			
			//GlStateManager.translatef(0, 0, 0.0625f*4f); //shift down to correct position
	        
        	wingRotation = 0;
			if(ent.isCrouching()) {
				wingRotation = -30; 
		    	//GL11.glRotatef((float) Math.toDegrees(0.5f), 1.0F, 0.0F, 0.0F);
		        //GL11.glTranslatef(0, 0.15f, -0.1f);
		    }
			try {
				matrixStack.push();
					RenderType type = RenderType.entityTranslucent(ClothingTextures.WINGS[plr.getAccessoryInt("wings_var")]);
					IVertexBuilder ivertexbuilder = bufferIn.getBuffer(type);
			    	matrixStack.translate(-0.02f, 0, 0.12f);
			    	matrixStack.rotate(new Quaternion(0, 0, -wingRotation, true));
			    	matrixStack.rotate(new Quaternion(0, -10, 0, true));
			    	renderSkinnedBox(LWing1, matrixStack, ivertexbuilder, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(LWing2, matrixStack, ivertexbuilder, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(LWing3, matrixStack, ivertexbuilder, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(LWing4, matrixStack, ivertexbuilder, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(LWing5, matrixStack, ivertexbuilder, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(LWing6, matrixStack, ivertexbuilder, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(LWing7, matrixStack, ivertexbuilder, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(LWing8, matrixStack, ivertexbuilder, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(LWing9, matrixStack, ivertexbuilder, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(LWing10, matrixStack, ivertexbuilder, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
				matrixStack.pop();
				
				matrixStack.push();
					RenderType type2 = RenderType.entityTranslucent(ClothingTextures.WINGS[plr.getAccessoryInt("wings_var")]);
					IVertexBuilder ivertexbuilder2 = bufferIn.getBuffer(type2);
					//matrixStack.scale(-1f, 1f, 1f);
			    	matrixStack.translate(0.02f, 0, 0.12f);
			    	matrixStack.rotate(new Quaternion(0, 0, wingRotation, true));
			    	matrixStack.rotate(new Quaternion(0, 10, 0, true));
			    	renderSkinnedBox(RWing1, matrixStack, ivertexbuilder2, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(RWing2, matrixStack, ivertexbuilder2, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(RWing3, matrixStack, ivertexbuilder2, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(RWing4, matrixStack, ivertexbuilder2, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(RWing5, matrixStack, ivertexbuilder2, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(RWing6, matrixStack, ivertexbuilder2, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(RWing7, matrixStack, ivertexbuilder2, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(RWing8, matrixStack, ivertexbuilder2, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(RWing9, matrixStack, ivertexbuilder2, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
			    	renderSkinnedBox(RWing10, matrixStack, ivertexbuilder2, packedLightIn, 15728640, 1f, 1f, 1f, getTransparency(ent));
				matrixStack.pop();
				
			} catch(Exception e) {}
			if(ent.isInvisible()) {
				//GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
		matrixStack.pop();
	    RenderSystem.color3f(1f, 1f, 1f);

	}
}
