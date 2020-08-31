package com.mercy194.main.gui.experimental;

import java.util.ArrayList;

import com.mercy194.SteinCompat;
import com.mercy194.main.Configuration;
import com.mercy194.main.gui.elements.SteinButton;
import com.mercy194.main.gui.screen.AdvContextMenu;
import com.mercy194.main.gui.screen.AdvSkinScreen;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.widget.Widget;

public class GUIComponent {

	public GUIComponent parent;
	
	public Configuration cfg;
	
	public GUIComponent anchor;
	
	public ArrayList<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();

	public float x, y, w, h;
	public boolean drawBackground = true;
	public GUISnap snap = GUISnap.TOPLEFT;
	public GUISnap anchorSnap;
	
	public boolean isVisible = true;
	public boolean isDragging = false;
	public GUIComponent(Configuration cfg) {
		this.cfg = cfg;
		try {
		this.setSnap(GUISnap.valueOf(cfg.getParameter("snap").toString()));
		this.setX(Math.round(Float.valueOf(cfg.getParameter("xPos").toString())));
		this.setY(Math.round(Float.valueOf(cfg.getParameter("yPos").toString())));
		this.setBackground(cfg.getBool("background"));
		this.setVisible(cfg.getBool("visible"));
		} catch(Exception e) {
			System.out.println("Failed to load GUIComponent");
		}
	}
	
	public void hardRender() {
		if(isVisible) {
			render();
			for(GUIComponent c : COMPONENTS) {
				c.render();
			}
		}
	}
	
	public void render() {}

	
	public int getX() {
		float tX = 0;
		if(parent == null) {
			float WIDTH = SteinCompat.getMainWindow().getScaledWidth();
			switch(snap) {
				case CENTER:
					tX = (WIDTH / 2) - ((w / 2));
				break;
				case LEFT: case TOPLEFT: case BOTTOMLEFT:
					tX = x;
				break;
				case RIGHT: case TOPRIGHT: case BOTTOMRIGHT:
					tX = WIDTH - x - w;
				break;
				case TOP:
					tX = (WIDTH / 2) - x - ((w / 2));
				break;
				case BOTTOM:
					tX = (WIDTH / 2) - x - ((w / 2));
				break;
			}
		} else {
			switch(snap) {
				case TOP: case CENTER:  case BOTTOM:
					tX = parent.getX() + parent.w / 2 - ((w / 2));
				break;
				case LEFT: case TOPLEFT: case BOTTOMLEFT:
					tX = parent.getX() + (x);
				break;
				case RIGHT: case TOPRIGHT: case BOTTOMRIGHT:
					tX = ((parent.getX() + parent.w) - x) - w;
				break;
			}
		}
		
		if(anchorSnap != null && anchor != null) {
			tX += anchor.getX() + anchor.w;
		}
		return (int) tX;
	}
	public int getY() {
		float tY = 0;
		if(parent == null) {
			float HEIGHT = SteinCompat.getMainWindow().getScaledHeight();
			
			switch(snap) {
				case LEFT: case CENTER: case RIGHT:
					tY = (HEIGHT / 2) - ((h / 2));
				break;
				case BOTTOMLEFT: case BOTTOM: case BOTTOMRIGHT:
					tY = HEIGHT - y - h;
				break;
				case TOPLEFT: case TOP: case TOPRIGHT:
					tY = y;
				break;
			}
		} else {
			switch(snap) {
				case LEFT: case CENTER: case RIGHT:
					tY = parent.getY() + (parent.h / 2) - (h / 2);
				break;
				case BOTTOMLEFT: case BOTTOM: case BOTTOMRIGHT:
					tY = (parent.getY() + parent.h) - y - h;
				break;
				case TOPLEFT: case TOP: case TOPRIGHT:
					tY = parent.getY() + y;
				break;
			}
		}
		if(anchorSnap != null && anchor != null) {
			tY += anchor.getY();
		}
		return (int) tY;
	}

	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setWidth(int w) {
		this.w = w;
	}
	public void setHeight(int h) {
		this.h = h;
	}
	public void setSnap(GUISnap s) {
		try {
			this.snap = s;
		} catch(Exception e) {}
	}
	public void setAnchorSnap(GUISnap s) {
		this.anchorSnap = s;
	}
	
	public void setParent(GUIComponent parent) {
		this.parent = parent;
	}
	public void setAnchor(GUIComponent parent) {
		this.anchor = parent;
	}
	
	public void setVisible(boolean v) {
		this.isVisible = v;
	}
	public void setBackground(boolean v) {
		this.drawBackground = v;
	}
	public void add(GUIComponent c) {
		c.setParent(this);
		COMPONENTS.add(c);
	}
	public ArrayList<Widget> getContextButtons() {
		ArrayList<Widget> btns = new ArrayList<Widget>();
		AdvContextMenu ctx = AdvSkinScreen.ctxMenu;
		btns.add(new SteinButton(ctx.x, (int) (ctx.y + btns.size()*16), (int) AdvContextMenu.WIDTH, 16, this.isVisible?"Visible":"Invisible", button -> {
			GUIComponent.this.setVisible(!GUIComponent.this.isVisible);
			button.setMessage(this.isVisible?"Visible":"Invisible");
			cfg.setParameter("visible", this.isVisible);
			cfg.save();
        }));
		btns.add(new SteinButton(ctx.x, (int) (ctx.y + btns.size()*16), (int) AdvContextMenu.WIDTH, 16, (this.drawBackground?"Background":"No Background"), button -> {
			GUIComponent.this.setBackground(!GUIComponent.this.drawBackground);
			button.setMessage((this.drawBackground?"Background":"No Background"));
			cfg.setParameter("background", this.drawBackground);
			cfg.save();
        }));
		return btns;
	}
}
