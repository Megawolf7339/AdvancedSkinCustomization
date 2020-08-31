package com.mercy194.render;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SteinArmorLayer<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends ArmorLayer<T, M, A> {
   public SteinArmorLayer(IEntityRenderer<T, M> p_i50936_1_, A p_i50936_2_, A p_i50936_3_) {
      super(p_i50936_1_, p_i50936_2_, p_i50936_3_);
   }

   protected void setModelSlotVisible(A model, EquipmentSlotType slotIn) {
      this.setModelVisible(model);
      switch(slotIn) {
      case HEAD:
    	 model.bipedHead.showModel = false;
    	 model.bipedHeadwear.showModel = false;
      break;
      case CHEST:
    	 model.bipedBody.showModel = true;
    	 model.bipedRightArm.showModel = true;
    	 model.bipedLeftArm.showModel = true;
      break;
      case LEGS:
    	 model.bipedBody.showModel = true;
    	 model.bipedRightLeg.showModel = true;
    	 model.bipedLeftLeg.showModel = true;
      break;
      case FEET:
    	 model.bipedRightLeg.showModel = true;
    	 model.bipedLeftLeg.showModel = true;
      default: break;
      }

   }

   protected void setModelVisible(A model) {
      model.setVisible(false);
   }
}