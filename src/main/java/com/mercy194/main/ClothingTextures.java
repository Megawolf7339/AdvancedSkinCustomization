package com.mercy194.main;

import java.util.Map;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.util.ResourceLocation;

public class ClothingTextures {

	public static final ResourceLocation SKYWARP_BOOSTER = new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/skywarp/booster.png");
	public static final ResourceLocation STARSCREAM_BOOSTER = new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/starscream/booster.png");
	public static final ResourceLocation THUNDER_BOOSTER = new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/thundercracker/booster.png");

	public static final ResourceLocation SKYWARP_WINGS = new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/skywarp/wing.png");
	public static final ResourceLocation STARSCREAM_WINGS = new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/starscream/wing.png");
	public static final ResourceLocation THUNDER_WINGS = new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/thundercracker/wing.png");

	public static final ResourceLocation SKYWARP_ENG = new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/skywarp/engine.png");
	public static final ResourceLocation STARSCREAM_ENG = new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/starscream/engine.png");
	public static final ResourceLocation THUNDER_ENG = new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/thundercracker/engine.png");

	public static final ResourceLocation DEFAULT_WINGS = new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/wing.png");


	public static final ResourceLocation[] WINGS = new ResourceLocation[] {
			new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/wing.png"),
			new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/starscream/wing.png"),
			new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/skywarp/wing.png"),
			new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/thundercracker/wing.png")
	};
	public static final ResourceLocation[] BOOSTERS = new ResourceLocation[] {
			new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/booster.png"),
			new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/starscream/booster.png"),
			new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/skywarp/booster.png"),
			new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/thundercracker/booster.png")
	};
	public static final ResourceLocation[] ENGINES = new ResourceLocation[] {
			new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/engine.png"),
			new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/starscream/engine.png"),
			new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/skywarp/engine.png"),
			new ResourceLocation(AdvSkinMod.MODID, "textures/transformers/thundercracker/engine.png")
	};
	

	public static final ResourceLocation PROTON_PACK = new ResourceLocation("clothes/proton_pack/variant0.png");
	public static final ResourceLocation[] BACKPACK = new ResourceLocation[] {
			new ResourceLocation(AdvSkinMod.MODID, "clothes/backpack/variant0.png"), //Black
			new ResourceLocation(AdvSkinMod.MODID, "clothes/backpack/variant1.png"), //Blue
			new ResourceLocation(AdvSkinMod.MODID, "clothes/backpack/variant2.png") //Orange
	};
	
	public static final ResourceLocation[] POUCH = new ResourceLocation[] {
			new ResourceLocation(AdvSkinMod.MODID, "clothes/pouch/variant0.png"),
			new ResourceLocation(AdvSkinMod.MODID, "clothes/pouch/variant1.png")
	};

	public static final ResourceLocation[] FACE_MASK = new ResourceLocation[] {
			new ResourceLocation(AdvSkinMod.MODID, "clothes/face_mask/variant0.png"), //Pumpkin Mask
			new ResourceLocation(AdvSkinMod.MODID, "clothes/face_mask/variant1.png"), //Skeleton Mask
			new ResourceLocation(AdvSkinMod.MODID, "clothes/face_mask/variant2.png"), //Dallas Mask
			new ResourceLocation(AdvSkinMod.MODID, "clothes/face_mask/variant3.png") //Dallas Mask
	};
	public static final ResourceLocation[] HAT = new ResourceLocation[] {
			new ResourceLocation(AdvSkinMod.MODID, "clothes/hat/variant0.png"), //Farmer Hat
			new ResourceLocation(AdvSkinMod.MODID, "clothes/hat/variant1.png"), //Santa Hat
	};
	public static ResourceLocation EMPTY_CAPE = new ResourceLocation(AdvSkinMod.MODID, "textures/empty.png");


    public static ResourceLocation loadPlayerTextures(AbstractClientPlayerEntity plr) {
    	
    	try {
	    	Map<Type, MinecraftProfileTexture> SKINSTUFF = Minecraft.getInstance().getSessionService().getTextures(plr.getGameProfile(), false);
	    	return Minecraft.getInstance().getSkinManager().loadSkin(SKINSTUFF.get(Type.SKIN), Type.SKIN);
    	} catch(Exception e) {}
    	return null;
    }
    public static ResourceLocation loadCape(AbstractClientPlayerEntity plr) {

    	try {
	    	Map<Type, MinecraftProfileTexture> SKINSTUFF = Minecraft.getInstance().getSessionService().getTextures(plr.getGameProfile(), false);
	    	if(SKINSTUFF.get(Type.CAPE) == null) return null;
	    	return Minecraft.getInstance().getSkinManager().loadSkin(SKINSTUFF.get(Type.CAPE), Type.CAPE);
    	} catch(Exception e) {
    	}
    	return null;
    }

    /*
    public static ResourceLocation loadSkin(String locName, String link) {
        final ResourceLocation resourcelocation = new ResourceLocation("skins/" + locName);
        ITextureObject itextureobject = Minecraft.getInstance().getTextureManager().getTexture(resourcelocation);
        if (itextureobject == null) {
           final IImageBuffer iimagebuffer = new DownloadImageBuffer();
           DownloadingTexture downloadingtexture = new DownloadingTexture(new File("skins/" + locName), link, DefaultPlayerSkin.getDefaultSkinLegacy(), iimagebuffer);
           Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, downloadingtexture);
        }

        return resourcelocation;
     }
    public static ResourceLocation getSkinPackSkin(int tfName)
    {
    	
    	String tfUrl = "";
    	switch(tfName) {
			case 1: //starscrem
				tfUrl = "https://www.dropbox.com/s/6wz85swvnfg6bco/Starscream.png?raw=1";
			break;
			case 2:
				//Sykwarp
				tfUrl = "https://www.dropbox.com/s/lj8nkhykpnsdluf/cae041252ce68342.png?raw=1";
			break;
			case 3:
				//tuhndercracker
				tfUrl = "https://www.dropbox.com/s/wmo010xwamk8xxm/eddb98a59fff2f66.png?raw=1";
			break;
			case 4:
				//meta-tron
				tfUrl = "https://www.dropbox.com/s/edz6vzt19vsuydy/5db2d0c195188eef.png?raw=1";
			break;
			case 5:
				tfUrl = "https://www.dropbox.com/s/2by87180q5604qd/a80c4b707d8f767f.png?raw=1";
			break;
			case 6:
				tfUrl = "https://www.dropbox.com/s/ly72753n26qiy4w/Shockwave.png?raw=1";
			break;
    	}

    	return loadSkin(String.valueOf(tfName), tfUrl);
    }
    */
}
