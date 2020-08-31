package com.mercy194.main.gui.category;

import org.lwjgl.opengl.GL11;

import com.mercy194.main.CFG;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

public class MercyLabel {

	public int x, y;
	public String dispName = "missingno";
	public int align = 0;
	public boolean hovering = false;

	public MercyLabel(String name, int x, int y, int align) {
		this.x = x;
		this.y = y;
		this.dispName = name;
		this.align = 0;
	}
	public MercyLabel(String name, int x, int y) {
		this(name, x, y, 0);
	}
	
	public void render() {
        GL11.glDisable(GL11.GL_BLEND);
        int hClr = (hovering?114:96);
    	int w = Minecraft.getInstance().fontRenderer.getStringWidth(dispName) + 3;
        
		switch(align) {
			case 0:
		    	if(CFG.gui.getBool("showBackground")) AbstractGui.fill(x, y, x + Minecraft.getInstance().fontRenderer.getStringWidth(dispName) + 3, y + 11, 0x000000 + (hClr << 24));
				Minecraft.getInstance().fontRenderer.drawString(dispName, x+2, y+2, 0xFFFFFF);
			break;
			case 1:
		    	if(CFG.gui.getBool("showBackground")) AbstractGui.fill(x - w, y, x, y + 11, 0x000000 + (hClr << 24));
				Minecraft.getInstance().fontRenderer.drawString(dispName, x-w+2, y+2, 0xFFFFFF);
			break;
			case 2:
		    	if(CFG.gui.getBool("showBackground")) AbstractGui.fill(x - w / 2, y, x + w / 2+1, y + 11, 0x000000 + (hClr << 24));
				Minecraft.getInstance().fontRenderer.drawString(dispName, x- w / 2+2, y+2, 0xFFFFFF);
			break;
		}
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void setX(int v) {
		this.x = v;
	}
	public void setY(int v) {
		this.y = v;
	}
}
