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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ClothingTFEngine extends AdvClothing {
	SteinModelRenderer.SkinnedModelBox Front1;
	SteinModelRenderer.SkinnedModelBox Front2;
	SteinModelRenderer.SkinnedModelBox Front3;
	SteinModelRenderer.SkinnedModelBox Back1;
	SteinModelRenderer.SkinnedModelBox Back2;

	SteinModelRenderer.SkinnedModelBox Shape1;
	SteinModelRenderer.SkinnedModelBox Shape2;
	SteinModelRenderer.SkinnedModelBox Shape3;
	SteinModelRenderer.SkinnedModelBox Shape4;
	SteinModelRenderer.SkinnedModelBox Shape5;
	SteinModelRenderer.SkinnedModelBox Shape6;
	SteinModelRenderer.SkinnedModelBox Shape7;
	SteinModelRenderer.SkinnedModelBox Shape8;
	SteinModelRenderer.SkinnedModelBox Shape9;
	SteinModelRenderer.SkinnedModelBox Shape10;
	SteinModelRenderer.SkinnedModelBox Shape11;
	SteinModelRenderer.SkinnedModelBox Shape12;
	SteinModelRenderer.SkinnedModelBox Shape13;
	
	public ClothingTFEngine() {
		super(new int[] {1, 2, 3});
	}
	
	public void initialize() {

		Front1 = new SteinModelRenderer.SkinnedModelBox(64, 32, 3, 17, -1F, 0F, -4F, 2, 7, 1, 0F, true);
		Front2 = new SteinModelRenderer.SkinnedModelBox(64, 32, 3, 26, -1F, 6F, -3F, 2, 4, 1, 0F, true);
		Front3 = new SteinModelRenderer.SkinnedModelBox(64, 32, 1, 9, -2F, 0F, -3F, 4, 6, 1, 0F, true);

		Back1 = new SteinModelRenderer.SkinnedModelBox(64, 32, 12, 17, -2F, 0F, 2F, 4, 6, 2, 0F, true);
		Back2 = new SteinModelRenderer.SkinnedModelBox(64, 32, 15, 26, -1F, 6F, 2F, 2, 4, 1, 0F, true);

		Shape1 = new SteinModelRenderer.SkinnedModelBox(64, 32, 12, 15, -7F, -1F, 2F, 3, 7, 2, 0F, true);
		Shape2 = new SteinModelRenderer.SkinnedModelBox(64, 32, 44, 15, 4F, -1F, 2F, 3, 7, 2, 0F, true);
		Shape3 = new SteinModelRenderer.SkinnedModelBox(64, 32, 45, 2, 4F, -10F, 1F, 3, 1, 1, 0F, true);
		Shape4 = new SteinModelRenderer.SkinnedModelBox(64, 32, 13, 4, -7F, -10F, 2F, 3, 10, 1, 0F, true);
		Shape5 = new SteinModelRenderer.SkinnedModelBox(64, 32, 21, 4, -5F, -10F, 1F, 1, 10, 1, 0F, true);
		Shape6 = new SteinModelRenderer.SkinnedModelBox(64, 32, 13, 2, -7F, -10F, 1F, 3, 1, 1, 0F, true);
		Shape7 = new SteinModelRenderer.SkinnedModelBox(64, 32, 14, 24, -6F, 6F, 2F, 2, 2, 1, 0F, true);
		Shape8 = new SteinModelRenderer.SkinnedModelBox(64, 32, 45, 4, 4F, -10F, 2F, 3, 10, 1, 0F, true);
		Shape9 = new SteinModelRenderer.SkinnedModelBox(64, 32, 46, 24, 4F, 6F, 2F, 2, 2, 1, 0F, true);
		Shape10 = new SteinModelRenderer.SkinnedModelBox(64, 32, 22, 18, -4F, 2F, 0F, 8, 2, 3, 0F, true);
		Shape11 = new SteinModelRenderer.SkinnedModelBox(64, 32, 41, 4, 4F, -10F, 1F, 1, 10, 1, 0F, true);
		Shape12 = new SteinModelRenderer.SkinnedModelBox(64, 32, 53, 4, 6F, -10F, 1F, 1, 10, 1, 0F, true);
		Shape13 = new SteinModelRenderer.SkinnedModelBox(64, 32, 9, 4,-7F, -10F, 1F, 1, 10, 1, 0F, true);
		
		super.initialize();
	}

	public void render(MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity ent, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		
		ItemStack itemstack = ent.inventory.armorItemInSlot(2);
		if(itemstack.getItem() == Items.ELYTRA) return;
		//boolean isFirstPerson = (this.getRenderer() instanceof SteinPlayerRenderer);
		String playerName = PlayerEntity.getUUID(ent.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
    	PlayerRenderer rend = (PlayerRenderer) Minecraft.getInstance().getRenderManager().getRenderer(ent);
    	PlayerModel<AbstractClientPlayerEntity> model = rend.getEntityModel(); 
    	if(isFirstPerson) model = this.getEntityModel();
    	if(!shouldRender(plr) && plr.getAccessoryInt("torso") != 1) return;

    	pushMatrix(matrixStack, model.bipedBody, 0);

		int combineTex = 15728640;
		matrixStack.push();
    		matrixStack.translate(0, -0.01f, 0);
			RenderType type = RenderType.entityTranslucent(ClothingTextures.WINGS[plr.getAccessoryInt("torso_var")]);
			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(type);
	    	renderSkinnedBox(Front1, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Front2, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Front3, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));

	    	renderSkinnedBox(Back1, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Back2, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));

			RenderType type2 = RenderType.entityTranslucent(ClothingTextures.ENGINES[plr.getAccessoryInt("torso_var")]);
			IVertexBuilder ivertexbuilder2 = bufferIn.getBuffer(type2);
	    	//renderSkinnedBox(Shape1, matrixStack, ivertexbuilder2, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	//renderSkinnedBox(Shape2, matrixStack, ivertexbuilder2, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape3, matrixStack, ivertexbuilder2, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape4, matrixStack, ivertexbuilder2, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape5, matrixStack, ivertexbuilder2, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape6, matrixStack, ivertexbuilder2, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape7, matrixStack, ivertexbuilder2, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape8, matrixStack, ivertexbuilder2, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape9, matrixStack, ivertexbuilder2, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape10, matrixStack, ivertexbuilder2, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape11, matrixStack, ivertexbuilder2, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape12, matrixStack, ivertexbuilder2, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape13, matrixStack, ivertexbuilder2, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
		matrixStack.pop();
			
		if(ent.isInvisible()) {
			//GlStateManager.unsetProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
		}
	matrixStack.pop();
    RenderSystem.color3f(1f, 1f, 1f);

	}
}
