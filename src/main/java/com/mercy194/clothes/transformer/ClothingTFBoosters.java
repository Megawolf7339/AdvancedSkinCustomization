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

public class ClothingTFBoosters extends AdvClothing {
	
	SteinModelRenderer.SkinnedModelBox shape_0;
	SteinModelRenderer.SkinnedModelBox Shapew;
	SteinModelRenderer.SkinnedModelBox Shape3;
	SteinModelRenderer.SkinnedModelBox Shape6;
	SteinModelRenderer.SkinnedModelBox Shape10;
    SteinModelRenderer.SkinnedModelBox Shape11;
    SteinModelRenderer.SkinnedModelBox Shape12;
	
	public ClothingTFBoosters() {
		super(new int[] {1, 2, 3});
	}
	
	public void initialize() {

		shape_0 = new SteinModelRenderer.SkinnedModelBox(64, 32, 1, 9, -1F, 1F, 2F, 2, 5, 1, 0F, true);
		Shapew = new SteinModelRenderer.SkinnedModelBox(64, 32, 1, 16, -1F, 7F, 2F, 2, 5, 2, 0F, true);
		Shape3 = new SteinModelRenderer.SkinnedModelBox(64, 32, 1, 27, -2F, 10F, -4F, 4, 2, 2, 0F, true);
		Shape6 = new SteinModelRenderer.SkinnedModelBox(64, 32, 1, 24, -1F, 9F, -3F, 2, 1, 1, 0F, true);
		Shape10 = new SteinModelRenderer.SkinnedModelBox(64, 32, 1, 0, -2F, 2F, -3F, 4, 1, 1, 0F, true);
		Shape11 = new SteinModelRenderer.SkinnedModelBox(64, 32, 1, 2, 1F, 3F, -3F, 1, 1, 1, 0F, true);
		Shape12 = new SteinModelRenderer.SkinnedModelBox(64, 32, 7, 2, -2F, 3F, -3F, 1, 1, 1, 0F, true);
		
		super.initialize();
	}
	

	public void render(MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity ent, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		
        if (ent.isCrouching()) matrixStack.translate(0.0F, -0.2F, 0.0F); //undo for proper rotations
        
		ItemStack itemstack = ent.inventory.armorItemInSlot(2);
		if(itemstack.getItem() == Items.ELYTRA) return;
		//boolean isFirstPerson = (this.getRenderer() instanceof SteinPlayerRenderer);
		String playerName = PlayerEntity.getUUID(ent.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
    	PlayerRenderer rend = (PlayerRenderer) Minecraft.getInstance().getRenderManager().getRenderer(ent);
    	PlayerModel<AbstractClientPlayerEntity> model = rend.getEntityModel(); 
    	if(isFirstPerson) model = this.getEntityModel();
    	if(!shouldRender(plr) && plr.getAccessoryInt("legs") != 1) return;

    	pushMatrix(matrixStack, model.bipedLeftLeg, 0);
			int combineTex = 15728640;
			
			RenderType type = RenderType.entityTranslucent(ClothingTextures.BOOSTERS[plr.getAccessoryInt("legs_var")]);
			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(type);
		

	    	renderSkinnedBox(shape_0, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shapew, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape3, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape6, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape10, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape11, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape12, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
		matrixStack.pop();

    	pushMatrix(matrixStack, model.bipedRightLeg, 0);
	    	renderSkinnedBox(shape_0, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shapew, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape3, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape6, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape10, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape11, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
	    	renderSkinnedBox(Shape12, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
		matrixStack.pop();
    	
		RenderSystem.color3f(1f, 1f, 1f);

	}
}
