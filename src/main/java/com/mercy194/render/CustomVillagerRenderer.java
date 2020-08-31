package com.mercy194.render;

import com.mercy194.gfx.SteinModelRenderer;
import com.mercy194.main.AdvSkinMod;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class CustomVillagerRenderer extends VillagerRenderer {
	public static BipedModel<VillagerEntity> modelDefault = new PlayerModel<VillagerEntity>(0f, false);

	private SteinModelRenderer.ModelBox chest, chestwear;
	private boolean vGender;

	public CustomVillagerRenderer(EntityRendererManager rm, IReloadableResourceManager mgr) {
		super(rm, mgr);
		modelDefault.isChild = false;
		chest = new SteinModelRenderer.ModelBox(64, 64, 17, 18, -4F, 0.0F, 0F, 8, 5, 3, 0.0F, false);
		chestwear = new SteinModelRenderer.ModelBox(64, 64, 17, 34, -4F, 0.0F, 0F, 8, 5, 3, 0.0F, false);
		
	}

	public void render(VillagerEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		
		boolean shouldSit = entityIn.isPassenger() && (entityIn.getRidingEntity() != null && entityIn.getRidingEntity().shouldRiderSit());
	    this.entityModel.isSitting = shouldSit;
	    this.entityModel.isChild = entityIn.isChild();
	      
		matrixStackIn.push();
		float rot = this.handleRotationFloat(entityIn, partialTicks);
        float f = MathHelper.interpolateAngle(partialTicks, entityIn.prevRenderYawOffset, entityIn.renderYawOffset);
		ResourceLocation SKIN = ((AbstractClientPlayerEntity) Minecraft.getInstance().player).getLocationSkin();
		SKIN = new ResourceLocation(AdvSkinMod.MODID, "villager/girl1.png");
		if(entityIn.getVillagerData().getProfession() == VillagerProfession.FARMER) {
			SKIN = new ResourceLocation(AdvSkinMod.MODID, "villager/farm1.png");
		}
		
		Minecraft.getInstance().getTextureManager().bindTexture(SKIN);
		int combineTex = LivingRenderer.getPackedOverlay(entityIn, 0);
		
		float f8 = 0.0F;
	      float f5 = 0.0F;
	      if (!shouldSit && entityIn.isAlive()) {
	         f8 = MathHelper.interpolateAngle(partialTicks, entityIn.prevLimbSwingAmount, entityIn.limbSwingAmount);
	         f5 = entityIn.limbSwing - entityIn.limbSwingAmount * (1.0F - partialTicks);
	         if (entityIn.isChild()) {
	            f5 *= 3.0F;
	         }

	         if (f8 > 1.0F) {
	            f8 = 1.0F;
	         }
	      }
		
		
	    float f7 = this.handleRotationFloat(entityIn, partialTicks);
	     matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
	    this.preRenderCallback(entityIn, matrixStackIn, partialTicks);
	    matrixStackIn.translate(0.0D, (double)-1.501F, 0.0D);
	    
	    //modelDefault.setLivingAnimations(entityIn, f5, f8, partialTicks);
	    modelDefault.render(entityIn, f5, f8, rot, entityIn.rotationYawHead, entityIn.rotationPitch);
		modelDefault.render(matrixStackIn, bufferIn.getBuffer(RenderType.entityTranslucent(SKIN)), packedLightIn, combineTex, 1f, 1f, 1f, 1f);
		//super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

		matrixStackIn.pop();
	}
	public void doRender(VillagerEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		/*
		boolean flag = this.setBrightness(entity, partialTicks, true);

		GlStateManager.pushMatrix();

        float f = MathHelper.func_219805_h(partialTicks, entity.prevRenderYawOffset, entity.renderYawOffset);
        float f1 = MathHelper.func_219805_h(partialTicks, entity.prevRotationYawHead, entity.rotationYawHead);
        float f2 = f1 - f;
        
		float f7 = MathHelper.lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch);
        this.render(entity, x, y, z);
        float f8 = this.handleRotationFloat(entity, partialTicks);
        this.applyRotations(entity, f8, f, partialTicks);
        float f4 = this.p(entity, partialTicks);
        float f5 = 0.0F;
        float f6 = 0.0F;
        f5 = MathHelper.lerp(partialTicks, entity.prevLimbSwingAmount, entity.limbSwingAmount);
        f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);
		
		modelDefault.setLivingAnimations(entity, f6, f5, partialTicks);
		
		ResourceLocation SKIN = ((AbstractClientPlayerEntity) Minecraft.getInstance().player).getLocationSkin();
		SKIN = new ResourceLocation(AdvSkinMod.MODID, "villager/girl1.png");
		if(entity.getVillagerData().getProfession() == VillagerProfession.FARMER) {
			SKIN = new ResourceLocation(AdvSkinMod.MODID, "villager/farm1.png");
		}
		
		Minecraft.getInstance().getTextureManager().bindTexture(SKIN);
		modelDefault.render(entity, f6, f5, entity.ticksExisted, f2, entity.getPitch(partialTicks), 0.0625f);
		
		if(this.vGender == false) {
			GlStateManager.pushMatrix();
				BufferBuilder renderer = Tessellator.getInstance().getBuffer();
				
		        GlStateManager.color3f(1f, 1f, 1f);
		
				boolean isChestplateOccupied = 
					!entity.getItemStackFromSlot(EquipmentSlotType.CHEST).equals(new ItemStack(Items.ELYTRA, 1), true) && 
					!(entity.getItemStackFromSlot(EquipmentSlotType.CHEST).equals(new ItemStack(Items.AIR, 1), true));
				
				boolean teamSeeFriendly = false;
				if(entity.getTeam() != null) teamSeeFriendly = entity.getTeam().getSeeFriendlyInvisiblesEnabled();
				
		
				GlStateManager.translatef(0, 0.05625f, -0.0625f*2f); //shift down to correct position
		
				Minecraft.getInstance().getTextureManager().bindTexture(SKIN);
		
				if(!entity.isChild()) {
					GlStateManager.rotatef(-25.3f, 1, 0, 0); //size = 10=small,20=big
				} else {
					GlStateManager.rotatef(-10.3f, 1, 0, 0); //size = 10=small,20=big
				}
		        if(entity.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
		        
		        
		        if((teamSeeFriendly && entity.isInvisible()) || !entity.isInvisible()) {
		        	chest.render(renderer, 0.0625f-0.00001f);
					GlStateManager.translatef(0, 0, -0.015f * 1f);
					GlStateManager.scalef(1.05f, 1.05f, 1.05f);
		        	chestwear.render(renderer, 0.0625f);
		        }
		
				GlStateManager.popMatrix();	
			}
		GlStateManager.popMatrix();
        if (flag) {
            this.unsetBrightness();
        }
		this.renderEntityName(entity, x, y-0.25f, z, entity.getVillagerData().getProfession().toString(), 0);
		*/
	}
}
