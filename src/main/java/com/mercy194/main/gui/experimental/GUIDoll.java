package com.mercy194.main.gui.experimental;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.mercy194.main.CFG;
import com.mercy194.main.Configuration;
import com.mercy194.main.gui.elements.SteinButton;
import com.mercy194.main.gui.elements.SteinGUISlider;
import com.mercy194.main.gui.screen.AdvContextMenu;
import com.mercy194.main.gui.screen.AdvSkinScreen;
import com.mercy194.main.proxy.AdvSkinClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.Widget;

public class GUIDoll extends GUIComponent {

	public float scale = 1;
	public GUIDoll(Configuration cfg) {
		super(cfg);
	}
	
	public void render(float partialTicks) {
		if(this.isVisible) {
			scale = 0.5f + Float.valueOf(CFG.guiPaperDoll.getParameter("scale").toString());
			if(scale < 0.25f) scale = 0.25f;
			if(scale > 1.5f) scale = 1.5f;
			this.w = 70*scale;
			this.h = 68*scale;
			GL11.glColor3f(1, 1, 1);
			if(this.drawBackground) AbstractGui.fill((int) getX(), (int) getY(), (int) (getX() + this.w), (int) (getY() + this.h), 0x000000 + (80 << 24));		
			boolean notMirrored = (snap==GUISnap.LEFT||snap==GUISnap.TOPLEFT||snap==GUISnap.BOTTOMLEFT);
			AdvSkinClient.drawPaperDoll((int) (getX() + this.w / 2), (int) (getY() + this.h-5*scale), (int) (30*scale), partialTicks,  Minecraft.getInstance().player, notMirrored);
		}
		super.render();
	}

	public ArrayList<Widget> getContextButtons() {
		ArrayList<Widget> btns = super.getContextButtons();
		AdvContextMenu ctx = AdvSkinScreen.ctxMenu;
		float scal = Float.valueOf(CFG.guiPaperDoll.getParameter("scale").toString());
		SteinGUISlider scalSlider = new SteinGUISlider(ctx.x, (int) (ctx.y + btns.size()*16), (int) AdvContextMenu.WIDTH, 16, "", 0.5f, scal, null) {
			
			@Override
			protected void updateMessage() {
				float fov = Float.valueOf(CFG.guiPaperDoll.getParameter("scale").toString());
				this.setMessage("Scale: " + (int) (fov * 100) + "%");
			}
			
			@Override
			protected void applyValue() {
				cfg.setParameter("scale", (float) this.value);
				cfg.save();
			}
		};
		scalSlider.setMessage("Scale: " + (int) (scal * 100) + "%");
		btns.add(scalSlider);
		
		btns.add(new SteinButton(ctx.x, (int) (ctx.y + btns.size()*16), (int) AdvContextMenu.WIDTH, 16, "Align: " + snap, button -> {
			if(GUIDoll.this.snap == GUISnap.TOPRIGHT) {
				GUIDoll.this.setSnap(GUISnap.TOPLEFT);
			} else if(GUIDoll.this.snap == GUISnap.TOP) {
				GUIDoll.this.setSnap(GUISnap.TOPRIGHT);
			} else {
				GUIDoll.this.setSnap(GUISnap.TOP);
			}
			button.setMessage("Align: " + snap);
			cfg.setParameter("snap", snap.toString());
			cfg.save();
        }));
		
		return btns;
	}
	

}
