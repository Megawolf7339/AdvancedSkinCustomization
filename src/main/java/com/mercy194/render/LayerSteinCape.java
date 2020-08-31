package com.mercy194.render;

import java.util.Map;

import com.mercy194.main.AdvSkinHelper;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.ClothingTextures;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class LayerSteinCape extends CapeLayer {

	public LayerSteinCape(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> player) {
		super(player);
		
	}

   public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
       	String playerName = PlayerEntity.getUUID(entityIn.getGameProfile()).toString();
       	ClothingPlayer cPlr = AdvSkinMod.getPlayerByName(playerName);
       	
       	if(cPlr != null) {
       		if(cPlr.usingMod) {
		       	if(entityIn != null) {
		       		if(entityIn.hasPlayerInfo()) {
			        	NetworkPlayerInfo networkPlayerInfo = ObfuscationReflectionHelper.getPrivateValue(AbstractClientPlayerEntity.class, entityIn, AdvSkinHelper.Obfuscation.NETWORK_PLAYER_INFO);
				       	Map<MinecraftProfileTexture.Type, ResourceLocation> texture = ObfuscationReflectionHelper.getPrivateValue(NetworkPlayerInfo.class, networkPlayerInfo, AdvSkinHelper.Obfuscation.PLAYER_TEXTURES); // field_187107_a -> playerTextures
				       	if(cPlr.getAccessoryInt("showCape")==1 && !cPlr.capeURL.equalsIgnoreCase("NO_CAPE") && !cPlr.capeURL.equalsIgnoreCase("")) {
			       			if(cPlr.preCapeURL != cPlr.capeURL) {
			       				cPlr.RES_CAPE = AdvSkinHelper.loadCape(cPlr.username, cPlr.capeURL); //load cape once
			       				cPlr.preCapeURL = cPlr.capeURL;
			       			}
				       		ResourceLocation cl = AdvSkinHelper.useCape(cPlr.username, cPlr.capeURL);
				   			if(cl != null) texture.put(MinecraftProfileTexture.Type.CAPE, cl);
				       	} else {
				   			texture.put(MinecraftProfileTexture.Type.CAPE, ClothingTextures.loadCape(entityIn));
				       	}
		       		}
		       	}
       		}
       	}
   }
}
