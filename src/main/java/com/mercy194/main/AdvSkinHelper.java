package com.mercy194.main;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DownloadingTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;

public class AdvSkinHelper {


	public class Obfuscation {
		//BossOverlayGui.java
		public static final String BOSS_BAR_OVERLAY = "field_184060_g";
		
		//NetworkPlayerInfo.java
		public static final String NETWORK_PLAYER_INFO = "field_175157_a";
		public static final String PLAYER_TEXTURES = "field_187107_a";
	}
	
	
	//Skin Stuff
 	public static ResourceLocation loadCape(String uuid, String link) {
 		ResourceLocation resourcelocation = null;
 		try {
 			
 			resourcelocation = new ResourceLocation(AdvSkinMod.MODID, uuid + "_" + URLEncoder.encode(link.replaceAll(":", "").replaceAll(".",""), "UTF-8"));
     		File f = new File("cache/capes/" + uuid);
			if(f.exists()) f.delete();
			DownloadingTexture downloadingtexture = new DownloadingTexture(f, link, ClothingTextures.EMPTY_CAPE, false, null); 
 			Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, downloadingtexture);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}

 		return resourcelocation;
 	}

  	public static ResourceLocation loadSkin(String uuid, String link) {
		ResourceLocation resourcelocation = null;
  		try {
  			resourcelocation = new ResourceLocation(AdvSkinMod.MODID, uuid + "_" + URLEncoder.encode(link.replaceAll(":", "").replaceAll(".",""), "UTF-8"));
     		File f = new File("cache/skins/" + uuid);
			if(f.exists()) f.delete();
			DownloadingTexture downloadingtexture = new DownloadingTexture(f, link, DefaultPlayerSkin.getDefaultSkinLegacy(), false, null); 
  			Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, downloadingtexture);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}

  		return resourcelocation;
  	}

   	public static ResourceLocation useCape(String uuid, String link) {
   		ResourceLocation resourcelocation = null;
		try {
			resourcelocation = new ResourceLocation(AdvSkinMod.MODID, uuid + "_" + URLEncoder.encode(link.replaceAll(":", "").replaceAll(".",""), "UTF-8"));
		} catch (UnsupportedEncodingException e) {}
   		return resourcelocation;
   	}
   	
   	
   	//longest character width in array if strings
    public static String longest(String[] array) {
		int index = 0; 
		int elementLength = Minecraft.getInstance().fontRenderer.getStringWidth(array[0]);
		for(int i=1; i< array.length; i++) {
		    if(Minecraft.getInstance().fontRenderer.getStringWidth(array[i]) > elementLength) {
		        index = i; elementLength = Minecraft.getInstance().fontRenderer.getStringWidth(array[i]);
		    }
		}
		return array[index];
	}
   	
}
