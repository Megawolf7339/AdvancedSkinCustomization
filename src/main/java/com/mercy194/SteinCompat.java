package com.mercy194;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class SteinCompat {

		
	public static MainWindow getMainWindow() {
		return Minecraft.getInstance().getMainWindow();
	}
	
	public static void bindTexture(ResourceLocation resLoc) {
		Minecraft.getInstance().getTextureManager().bindTexture(resLoc);
	}
}
