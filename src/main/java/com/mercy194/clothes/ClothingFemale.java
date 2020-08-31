package com.mercy194.clothes;

import org.lwjgl.opengl.GL11;

import com.mercy194.gfx.SteinModelRenderer;
import com.mercy194.main.AdvClothing;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.CFG;
import com.mercy194.main.ClothingPlayer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ClothingFemale extends AdvClothing {
	//boobs.
	private SteinModelRenderer.ModelBox chest, chestwear, sBox;

	
	public ClothingFemale() {
		super("");

		chest = new SteinModelRenderer.ModelBox(64, 64, 16, 17, -4F, 0.0F, 0F, 8, 5, 4, 0.0F, false);
		chestwear = new SteinModelRenderer.ModelBox(64, 64, 17, 34, -4F, 0.0F, 0F, 8, 5, 3, 0.0F, false);
		sBox = new SteinModelRenderer.ModelBox(64, 32, 17, 19, -4F, 0.0F, 0F, 8, 5, 3, 0.0F, false);
		//sBox = new ModelBox(17, 19, -4F, 0.0F, 0F, 8, 5, 3, 0.0F, 0, 0, false, 0, 0);
		
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
    	if(isFirstPerson) model = this.getEntityModel();
    	
    	if(plr != null) {
        	if(!shouldRender(plr)) return;
			if(plr.gender == false) {
				
				GL11.glColor3f(1f, 1f, 1f);

				boolean isChestplateOccupied = 
					!ent.getItemStackFromSlot(EquipmentSlotType.CHEST).equals(new ItemStack(Items.ELYTRA, 1), true) && 
					!(ent.getItemStackFromSlot(EquipmentSlotType.CHEST).equals(new ItemStack(Items.AIR, 1), true));
				
				boolean teamSeeFriendly = false;
				if(ent.getTeam() != null) teamSeeFriendly = ent.getTeam().getSeeFriendlyInvisiblesEnabled();
				
				boolean breathingAnimation = true;

				pushMatrix(matrixStack, model.bipedBody, 0);
				matrixStack.translate(0, 0.05625f, -0.0625f*2f); //shift down to correct position

				if(isChestplateOccupied && !CFG.generic.getBool("showArmor")) {
					if(isChestplateOccupied) matrixStack.translate(0, 0, 0.01f);
					matrixStack.rotate(new Quaternion(-12f, 0, 0, true));
				} else {
					if(isChestplateOccupied) matrixStack.translate(0, 0, 0.01f);
					matrixStack.rotate(new Quaternion(-35f * plr.getAccessoryFloat("plr_bust", 0.5f), 0, 0, true));
				}
				float f = 1.0F;
			    if (true) {
			    	f = (float)ent.getMotion().lengthSquared();
			    	f = f / 0.2F;
			    	f = f * f * f;
			    }

			    if (f < 1.0F) {
			    	f = 1.0F;
			    }
				//chest = new SteinModelRenderer.ModelBox(64, 64, 17, 18, -4F, 0.0F, 0F, 8, 5, 3, 0.0F, false);
				//chest = new SteinModelRenderer.ModelBox(64, 64, 16, 17, -4F, 0.0F, 0F, 8, 5, 4, 0.0F, false);
				
			    if(!isChestplateOccupied && breathingAnimation) {
				    float f5 = -MathHelper.cos(ageInTicks * 0.09F) * 0.45F + 0.45F;
					matrixStack.rotate(new Quaternion(f5, 0, 0, true));
			    }
	            //if(ent.isInvisible()) GlStateManager.setProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
				int combineTex = LivingRenderer.getPackedOverlay(ent, 0);
				RenderType type = RenderType.entityTranslucent(rend.getEntityTexture(ent));
				IVertexBuilder ivertexbuilder = bufferIn.getBuffer(type);
				
			    //REMOVE THE LINE ABOVE
			    
	            if((teamSeeFriendly && ent.isInvisible()) || !ent.isInvisible()) {
		        	AdvClothing.renderBox(chest, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
					if(ent.isWearing(PlayerModelPart.JACKET) && (!isChestplateOccupied || CFG.generic.getBool("showArmor"))) {
						matrixStack.translate(0, 0, -0.015f * 1f);
						matrixStack.scale(1.05f, 1.05f, 1.05f);
						AdvClothing.renderBox(chestwear, matrixStack, ivertexbuilder, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
					}
					
	            }
	            
	            if(CFG.generic.getBool("showArmor")) {
		            if(!ent.inventory.armorItemInSlot(2).equals(new ItemStack(Items.AIR, 1), true)) {
		            	unsetBrightness();
		            	ItemStack itemstack = ent.inventory.armorItemInSlot(2);
			            ResourceLocation ARMOR_TXTR = getArmorResource(ent, ent.inventory.armorItemInSlot(2),  EquipmentSlotType.CHEST, null);
			            if(ARMOR_TXTR != null) {
				            if (itemstack.getItem() instanceof ArmorItem) {
					            matrixStack.push();

				            	float armorR = 1f;
				            	float armorG = 1f;
				            	float armorB = 1f;
				            	
				                ArmorItem armoritem = (ArmorItem)itemstack.getItem();
					            if (armoritem instanceof net.minecraft.item.IDyeableArmorItem) {
					               int i = ((net.minecraft.item.IDyeableArmorItem)armoritem).getColor(itemstack);
					               armorR = (float)(i >> 16 & 255) / 255.0F;
					               armorG = (float)(i >> 8 & 255) / 255.0F;
					               armorB = (float)(i & 255) / 255.0F;
					               
					            }
								matrixStack.translate(0, 0.015f * 1f, -0.015f * 1f);
								matrixStack.scale(1.05f, 1, 1);
								RenderType type2 = RenderType.entityTranslucent(ARMOR_TXTR);
								IVertexBuilder ivertexbuilder2 = bufferIn.getBuffer(type2);
								AdvClothing.renderBox(sBox, matrixStack, ivertexbuilder2, packedLightIn, combineTex, armorR, armorG, armorB, getTransparency(ent));

					        	if(ent.inventory.armorItemInSlot(2).isEnchanted()) {
									RenderType type3 = RenderType.entityGlint();
									IVertexBuilder ivertexbuilder3 = bufferIn.getBuffer(type3);
									AdvClothing.renderBox(sBox, matrixStack, ivertexbuilder3, packedLightIn, combineTex, 1f, 1f, 1f, getTransparency(ent));
					        	}
					            matrixStack.pop();
				            }
			            }
		            }
	            }
	            
	            
	        	matrixStack.pop(); //popmatrix
	        	
			}
        }
        GL11.glColor3f(1f, 1f, 1f);

	}
	
}
