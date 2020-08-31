package com.mercy194.main.gui.experimental;

import com.mercy194.main.AdvSkinHelper;
import com.mercy194.main.Configuration;

import net.minecraft.client.Minecraft;

public class GUIText extends GUIComponent {

	public String[] txt = new String[] { "" };
	public GUIText(Configuration cfg) {
		super(cfg);
		this.setText("Placeholder");
		this.setHeight(11);
		this.setWidth(Minecraft.getInstance().fontRenderer.getStringWidth(txt[0])+4);
		//GUISnap.values()[Integer.valueOf(cfg.getParameter("snap").toString())],
		//Integer.valueOf(cfg.getParameter("xPos").toString())
		//Integer.valueOf(cfg.getParameter("yPos").toString())
	}

	public void render() {
		//drawText(txt[0], (int) getX()+6, (int) getY()+1, Color.black);
		//drawText(txt[0], (int) getX()+5, (int) getY(), Color.white);
	}

	public void setText(String t) {
		this.txt[0] = t;
		this.setWidth(Minecraft.getInstance().fontRenderer.getStringWidth(txt[0])+4);
	}
	public void setText(String[] t) {
		this.txt = t;
		this.setWidth(Minecraft.getInstance().fontRenderer.getStringWidth(AdvSkinHelper.longest(txt))+4);
	}
	public void setText(int ind, String t) {
		this.txt[ind] = t;
		this.setWidth(Minecraft.getInstance().fontRenderer.getStringWidth(AdvSkinHelper.longest(txt))+4);
	}
	
	
}
