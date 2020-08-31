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
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ClothingPouch extends AdvClothing {
	
	public SteinModelRenderer.SkinnedModelBox b1, b2, b3, b4;
	
	public ClothingPouch() {
		super("pouch");
		b1 = new SteinModelRenderer.SkinnedModelBox(32, 32, 0, 0, 3.0F, 8.0F, -3.0F, 2, 4, 6, 0.0F, false);
		b2 = new SteinModelRenderer.SkinnedModelBox(32, 32, 4, 10, 3.0F, -4.0F, 2.0F, 1, 12, 1, 0.0F, false);
		b3 = new SteinModelRenderer.SkinnedModelBox(32, 32, 8, 10, 3.0F, -4.0F, -2.0F, 1, 1, 4, 0.0F, false);
		b4 = new SteinModelRenderer.SkinnedModelBox(32, 32, 0, 10, 3.0F, -4.0F, -3.0F, 1, 12, 1, 0.0F, false);
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
    	if(!shouldRender(plr)) return;

    	
        if(plr.getAccessoryInt("pouch") == 1) { //TODO: Move somewhere outside this
        	//if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
        	pushMatrix(matrixStack, model.bipedBody, 0);
		
				
				RenderType type = RenderType.entityTranslucent(ClothingTextures.POUCH[plr.getAccessoryInt("pouch_var")]);
				IVertexBuilder ivertexbuilder = bufferIn.getBuffer(type);

				
				int combineTex = 15728640;
				renderSkinnedBox(b1, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));

				matrixStack.push();
					matrixStack.translate(0.0825f*-3f, 0.061f*5f, 0);
					matrixStack.rotate(new Quaternion(0, 0, -43, true));
					renderSkinnedBox(b2, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
					renderSkinnedBox(b3, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));

					SteinModelRenderer.SkinnedModelBox b6 = new SteinModelRenderer.SkinnedModelBox(32, 32, 0, 10, 3.0F, -4.0F, -3.0F, 1, 8, 1, 0.0F, false);
					SteinModelRenderer.SkinnedModelBox b5 = new SteinModelRenderer.SkinnedModelBox(32, 32, 0, 10, 3.0F, -4.0F, -3.0F, 1, 7, 1, 0.0F, false);
					
					if(plr.gender == true) {
						renderSkinnedBox(b4, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
					} else {
						matrixStack.push();
							matrixStack.rotate(new Quaternion(32, 0, 0, true));
							matrixStack.translate(0.073f*-1f, 0.042f*5f, 0.05f*-4.47f);
							renderSkinnedBox(b5, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
						matrixStack.pop();
						matrixStack.push();
							matrixStack.rotate(new Quaternion(-30, 0, 10, true));
							matrixStack.translate(0.023f*-1f, 0.03f*2f, 0.05f*-1f);
							renderSkinnedBox(b6, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
						matrixStack.pop();
					}
				matrixStack.pop();
				
			matrixStack.pop();
			if(ent.isInvisible()) {
				//GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
        }
	    RenderSystem.color3f(1f, 1f, 1f);
	}
}
