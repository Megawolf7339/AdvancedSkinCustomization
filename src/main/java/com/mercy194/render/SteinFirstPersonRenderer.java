package com.mercy194.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class SteinFirstPersonRenderer extends PlayerRenderer {

	@SuppressWarnings("rawtypes")
	public SteinFirstPersonRenderer(EntityRendererManager renderManager, boolean useSmallArms) {
		super(renderManager, useSmallArms);
		this.layerRenderers.remove(0); //armor layer
		this.layerRenderers.remove(0); //held item layer
		this.layerRenderers.remove(0); //arrow layer
		this.layerRenderers.remove(2); //head layer
		this.addLayer(new SteinArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
		this.addLayer(new LayerClothing(true, this));
		//this.addLayer(new LayerApparel(true, this));
		this.addLayer(new SteinHeldItemLayer<>(this));
		
	}

	public void render(AbstractClientPlayerEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		  if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderPlayerEvent.Pre(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn))) return;
	      matrixStackIn.push();
	      RenderSystem.disableCull();
	      this.entityModel.swingProgress = this.getSwingProgress(entityIn, partialTicks);
	      boolean shouldSit = entityIn.isPassenger() && (entityIn.getRidingEntity() != null && entityIn.getRidingEntity().shouldRiderSit());
	      this.entityModel.isSitting = shouldSit;
	      this.entityModel.isChild = entityIn.isChild();
	      float f = MathHelper.interpolateAngle(partialTicks, entityIn.prevRenderYawOffset, entityIn.renderYawOffset);
	      float f1 = MathHelper.interpolateAngle(partialTicks, entityIn.prevRotationYawHead, entityIn.rotationYawHead);
	      float f2 = f1 - f;
	      if (shouldSit && entityIn.getRidingEntity() instanceof LivingEntity) {
	         LivingEntity livingentity = (LivingEntity)entityIn.getRidingEntity();
	         f = MathHelper.interpolateAngle(partialTicks, livingentity.prevRenderYawOffset, livingentity.renderYawOffset);
	         f2 = f1 - f;
	         float f3 = MathHelper.wrapDegrees(f2);
	         if (f3 < -85.0F) {
	            f3 = -85.0F;
	         }

	         if (f3 >= 85.0F) {
	            f3 = 85.0F;
	         }

	         f = f1 - f3;
	         if (f3 * f3 > 2500.0F) {
	            f += f3 * 0.2F;
	         }

	         f2 = f1 - f;
	      }

	      float f6 = MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch);
	      if (entityIn.getPose() == Pose.SLEEPING) {
	         Direction direction = entityIn.getBedDirection();
	         if (direction != null) {
	            float f4 = entityIn.getEyeHeight(Pose.STANDING) - 0.1F;
	            matrixStackIn.translate((double)((float)(-direction.getXOffset()) * f4), 0.0D, (double)((float)(-direction.getZOffset()) * f4));
	         }
	      }

	      float f7 = this.handleRotationFloat(entityIn, partialTicks);
	      this.applyRotations(entityIn, matrixStackIn, f7, f, partialTicks);
	      matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
	      this.preRenderCallback(entityIn, matrixStackIn, partialTicks);
	      matrixStackIn.translate(0.0D, (double)-1.501F, 0.0D);
	      float f8 = 0.0F;
	      float f5 = 0.0F;
	      if (!shouldSit && entityIn.isAlive()) {
	         f8 = MathHelper.lerp(partialTicks, entityIn.prevLimbSwingAmount, entityIn.limbSwingAmount);
	         f5 = entityIn.limbSwing - entityIn.limbSwingAmount * (1.0F - partialTicks);
	         if (entityIn.isChild()) {
	            f5 *= 3.0F;
	         }

	         if (f8 > 1.0F) {
	            f8 = 1.0F;
	         }
	      }
	      this.entityModel.setLivingAnimations(entityIn, f5, f8, partialTicks);
	      this.setModelVisibilities(entityIn);
	      this.entityModel.render(entityIn, f5, f8, f7, f2, f6);
	      boolean flag = this.isVisible(entityIn);
	      boolean flag1 = !flag && !entityIn.isInvisibleToPlayer(Minecraft.getInstance().player);
	      RenderType rendertype = this.func_230042_a_(entityIn, flag, flag1);
	      if (rendertype != null) {
	         IVertexBuilder ivertexbuilder = bufferIn.getBuffer(rendertype);
	         int i = getPackedOverlay(entityIn, this.getOverlayProgress(entityIn, partialTicks));
	         this.entityModel.render(matrixStackIn, ivertexbuilder, packedLightIn, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
	      }
	      if (!entityIn.isSpectator()) {
	         for(LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> layerrenderer : this.layerRenderers) {
	        	 //if(layerrenderer instanceof LayerClothing) ((LayerClothing)layerrenderer).isFirstPerson = true;
	        	 layerrenderer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, f5, f8, partialTicks, f7, f2, f6);
	         }
	      }

	      matrixStackIn.pop();
	  }
	
	private void setModelVisibilities(AbstractClientPlayerEntity clientPlayer) {
	      PlayerModel<AbstractClientPlayerEntity> playermodel = this.getEntityModel();
	         
	         
	      if (clientPlayer.isSpectator()) {
	         playermodel.setVisible(false);
	      } else {
	         ItemStack itemstack = clientPlayer.getHeldItemMainhand();
	         ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
	         playermodel.setVisible(true);
	         playermodel.bipedBodyWear.showModel = clientPlayer.isWearing(PlayerModelPart.JACKET);
	         playermodel.bipedLeftLegwear.showModel = clientPlayer.isWearing(PlayerModelPart.LEFT_PANTS_LEG);
	         playermodel.bipedRightLegwear.showModel = clientPlayer.isWearing(PlayerModelPart.RIGHT_PANTS_LEG);
	         playermodel.bipedLeftArmwear.showModel = clientPlayer.isWearing(PlayerModelPart.LEFT_SLEEVE);
	         playermodel.bipedRightArmwear.showModel = clientPlayer.isWearing(PlayerModelPart.RIGHT_SLEEVE);
	         playermodel.isSneak = clientPlayer.isCrouching();
	         BipedModel.ArmPose bipedmodel$armpose = this.getArmPose(clientPlayer, itemstack, itemstack1, Hand.MAIN_HAND);
	         BipedModel.ArmPose bipedmodel$armpose1 = this.getArmPose(clientPlayer, itemstack, itemstack1, Hand.OFF_HAND);
	         if (clientPlayer.getPrimaryHand() == HandSide.RIGHT) {
	            playermodel.rightArmPose = bipedmodel$armpose;
	            playermodel.leftArmPose = bipedmodel$armpose1;
	         } else {
	            playermodel.rightArmPose = bipedmodel$armpose1;
	            playermodel.leftArmPose = bipedmodel$armpose;
	         }
	      }
	      playermodel.bipedHead.showModel = false;
	      playermodel.bipedHeadwear.showModel = false;

	   }
	

	   private BipedModel.ArmPose getArmPose(AbstractClientPlayerEntity playerIn, ItemStack itemStackMain, ItemStack itemStackOff, Hand handIn) {
	      BipedModel.ArmPose bipedmodel$armpose = BipedModel.ArmPose.EMPTY;
	      ItemStack itemstack = handIn == Hand.MAIN_HAND ? itemStackMain : itemStackOff;
	      if (!itemstack.isEmpty()) {
	         bipedmodel$armpose = BipedModel.ArmPose.ITEM;
	         if (playerIn.getItemInUseCount() > 0) {
	            UseAction useaction = itemstack.getUseAction();
	            if (useaction == UseAction.BLOCK) {
	               bipedmodel$armpose = BipedModel.ArmPose.BLOCK;
	            } else if (useaction == UseAction.BOW) {
	               bipedmodel$armpose = BipedModel.ArmPose.BOW_AND_ARROW;
	            } else if (useaction == UseAction.SPEAR) {
	               bipedmodel$armpose = BipedModel.ArmPose.THROW_SPEAR;
	            } else if (useaction == UseAction.CROSSBOW && handIn == playerIn.getActiveHand()) {
	               bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_CHARGE;
	            }
	         } else {
	            boolean flag3 = itemStackMain.getItem() == Items.CROSSBOW;
	            boolean flag = CrossbowItem.isCharged(itemStackMain);
	            boolean flag1 = itemStackOff.getItem() == Items.CROSSBOW;
	            boolean flag2 = CrossbowItem.isCharged(itemStackOff);
	            if (flag3 && flag) {
	               bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
	            }

	            if (flag1 && flag2 && itemStackMain.getItem().getUseAction(itemStackMain) == UseAction.NONE) {
	               bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
	            }
	         }
	      }

	      return bipedmodel$armpose;
	   }
}
