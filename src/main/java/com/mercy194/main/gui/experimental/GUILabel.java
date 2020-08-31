package com.mercy194.main.gui.experimental;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.mercy194.main.CFG;
import com.mercy194.main.Configuration;
import com.mercy194.main.gui.elements.SteinButton;
import com.mercy194.main.gui.screen.AdvContextMenu;
import com.mercy194.main.gui.screen.AdvSkinScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.Widget;

public class GUILabel extends GUIText {

	public GUILabel(Configuration cfg) {
		super(cfg);
	}

	public void render() {
		//drawGUIBox((int) getX(), (int) getY(), (int) w, (txt.length*18)+3);
		if(this.drawBackground) AbstractGui.fill(getX(), getY(), getX() + (int) w, (int) (getY() + h), 0x44000000);
		GL11.glDisable(GL11.GL_BLEND);
		//String lStr = MainGUI.longest(txt);
		for(int i = 0; i < txt.length; i++) {
			if(this.drawBackground) {
				Minecraft.getInstance().fontRenderer.drawString(txt[i], getX()+2, getY() + (i*18)+2, 0xFFFFFF);
			} else {
				Minecraft.getInstance().fontRenderer.drawStringWithShadow(txt[i], getX()+2, getY() + (i*18)+2, 0xFFFFFF);
			}
		}
		GL11.glEnable(GL11.GL_BLEND);
		//drawText(txt[0], (int) getX()+5, (int) getY(), Color.white);
	}
	public ArrayList<Widget> getContextButtons() {
		ArrayList<Widget> btns = super.getContextButtons();
		AdvContextMenu ctx = AdvSkinScreen.ctxMenu;
		btns.add(new SteinButton(ctx.x, (int) (ctx.y + btns.size()*16), (int) AdvContextMenu.WIDTH, 16, "Align: " + snap, button -> {
			if(GUILabel.this.snap == GUISnap.TOPRIGHT) {
				GUILabel.this.setSnap(GUISnap.TOPLEFT);
			} else if(GUILabel.this.snap == GUISnap.TOP) {
				GUILabel.this.setSnap(GUISnap.TOPRIGHT);
			} else {
				GUILabel.this.setSnap(GUISnap.TOP);
			}
			button.setMessage("Align: " + snap);
			cfg.setParameter("snap", snap.toString());
			cfg.save();
        }));
		return btns;
	}
}
