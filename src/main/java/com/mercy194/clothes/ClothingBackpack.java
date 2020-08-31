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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ClothingBackpack extends AdvClothing {
	
	public SteinModelRenderer.SkinnedModelBox b1, b2, b3;
	
	public ClothingBackpack() {
		super("backpack");
		b1 = new SteinModelRenderer.SkinnedModelBox(32, 32, 0, 0, -4.0F, 0.0F, -2.0F, 8, 10, 3, 0.0F, false);
    	b2 = new SteinModelRenderer.SkinnedModelBox(32, 32, 0, 13, -3.0F, 1.0F, 1.0F, 6, 9, 1, 0.0F, false);
    	b3 = new SteinModelRenderer.SkinnedModelBox(32, 32, 14, 14, -3.0F, 10.0F, -2.0F, 6, 1, 3, 0.0F, false);
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

    	
        if(plr.getAccessoryInt("backpack") == 1) { //TODO: Move somewhere outside this
        	//if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
        	pushMatrix(matrixStack, model.bipedBody, 0);
	
			matrixStack.translate(0, 0, 0.0625f*4f); //shift down to correct position
			
			RenderType type = RenderType.entityTranslucent(ClothingTextures.BACKPACK[plr.getAccessoryInt("backpack_var")]);
			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(type);
			
			int combineTex = 15728640;
			renderSkinnedBox(b1, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
			renderSkinnedBox(b2, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
			renderSkinnedBox(b3, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
			matrixStack.pop();
			if(ent.isInvisible()) {
				//GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
        }
	    RenderSystem.color3f(1f, 1f, 1f);
	}
}
