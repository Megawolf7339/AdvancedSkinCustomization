package com.mercy194.main.gui.screen;

import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mercy194.SteinCompat;
import com.mercy194.main.AdvSkinHelper;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.CFG;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.experimental.GUIComponent;
import com.mercy194.main.gui.experimental.GUIDoll;
import com.mercy194.main.gui.experimental.GUILabel;
import com.mercy194.main.gui.experimental.GUISnap;
import com.mercy194.main.proxy.AdvSkinClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.ClientBossInfo;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.overlay.BossOverlayGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class AdvSkinScreen {


	//public static AdvGuiLabel lblBiome, lblFPS;
	//public static AdvGuiDoll paperDoll;
	
	public ArrayList<GUIComponent> ELEMENTS = new ArrayList<GUIComponent>();

	public GUILabel lblBiome, lblFPS, lblTime, lblCoords;
	public GUIDoll paperDoll;
	
	public static AdvContextMenu ctxMenu = null;
	
	private GUIComponent dragElement = null;
	
	private boolean alreadyDragging = false;
	//public static ArrayList<AdvGuiElement> ELEMENTS = new ArrayList<AdvGuiElement>();
	public AdvSkinScreen() {
		
		lblBiome = new GUILabel(CFG.guiBiome);
		ELEMENTS.add(lblBiome);

		lblFPS = new GUILabel(CFG.guiFPS);
		lblFPS.setAnchor(lblBiome);
		lblFPS.setAnchorSnap(GUISnap.TOPLEFT);
		ELEMENTS.add(lblFPS);

		lblTime = new GUILabel(CFG.guiTime);
		ELEMENTS.add(lblTime);
		
		lblCoords = new GUILabel(CFG.guiCoordinates);
		ELEMENTS.add(lblCoords);
		
		paperDoll = new GUIDoll(CFG.guiPaperDoll);
		ELEMENTS.add(paperDoll);
		
		System.out.println("Created AdvSkinScreen");
	}
	
	private boolean wasDragging = false;
	public void render(float partialTicks) {
		FontRenderer fR = Minecraft.getInstance().fontRenderer;
		
		if(!Minecraft.getInstance().gameSettings.showDebugInfo && Minecraft.getInstance().currentScreen == null || (Minecraft.getInstance().currentScreen instanceof ChatScreen) || (Minecraft.getInstance().currentScreen instanceof SteinGUISettingsScreen)) {
			MouseHelper h = Minecraft.getInstance().mouseHelper;
			double mX = h.getMouseX() / SteinCompat.getMainWindow().getGuiScaleFactor();
			double mY = h.getMouseY() / SteinCompat.getMainWindow().getGuiScaleFactor();	
	    	
			if(!(Minecraft.getInstance().currentScreen instanceof SteinGUISettingsScreen)) {
				ctxMenu = null;
			} else {
	    		for(int i = 0; i < ELEMENTS.size(); i++) {
	    			GUIComponent c = ELEMENTS.get(i);
	    			if(!c.isVisible) {
	    				AbstractGui.fill(c.getX(), (int) c.getY(), (int)  (c.getX() + c.w), (int) (c.getY() + c.h), 0x22000000);
	    			}
	    		}
			}
			if(!mouseInCtxMenu(mX, mY) || ctxMenu == null) {
				if(Minecraft.getInstance().currentScreen instanceof SteinGUISettingsScreen) {
		    		for(int i = 0; i < ELEMENTS.size(); i++) {
		    			GUIComponent c = ELEMENTS.get(i);
		    			if(!alreadyDragging || (c.isDragging && alreadyDragging)) {
			    			if((c.isDragging && alreadyDragging) || (!alreadyDragging && mX > c.getX() && mY > c.getY() && mX < c.getX() + c.w && mY < c.getY() + c.h)) {
			    				AbstractGui.fill(c.getX(), (int) c.getY(), (int)  (c.getX() + c.w), (int) (c.getY() + c.h), 0x44225588);
			    				/*
			    				AbstractGui.fill(c.getX()+2, (int) (c.getY() + c.h-2), (int)  (c.getX() + c.w-2), (int) (c.getY()+c.h), 0x66000000);
				    			AbstractGui.fill(c.getX(), (int) c.getY(), (int)  (c.getX() + c.w), (int) (c.getY()+2), 0x66000000);
			    				AbstractGui.fill(c.getX(), (int) c.getY()+2, (int)  (c.getX() + 2), (int) (c.getY()+c.h), 0x66000000);
			    				AbstractGui.fill((int) (c.getX()+c.w-2), (int) c.getY()+2, (int)  (c.getX() + c.w), (int) (c.getY()+c.h), 0x66000000);
			    				*/
			    			}
		    			}
		    		}
				}
			}
			
			
    		String biome = "Biome: " + Minecraft.getInstance().world.getBiome(Minecraft.getInstance().player.getPosition()).getDisplayName().getFormattedText();
			lblBiome.setText(biome);
			lblBiome.hardRender();
    		

			
			String[] tFps = Minecraft.getInstance().debug.split(" ");
    		String fpsStr = tFps[0] + "" + tFps[1];
    		lblFPS.setText(fpsStr);
			lblFPS.hardRender();
			
			
        	BossOverlayGui bGui = Minecraft.getInstance().ingameGUI.getBossOverlay();
        	Map<UUID, ClientBossInfo> mapBossInfos = ObfuscationReflectionHelper.getPrivateValue(BossOverlayGui.class, bGui, AdvSkinHelper.Obfuscation.BOSS_BAR_OVERLAY);

    		long time = Minecraft.getInstance().world.getDayTime() % 24000;
    		int hours = (int) (time / 1000 + 6);
    		String minutes = "" + ((time % 1000) * 60 / 1000);
    		if(((time % 1000) * 60 / 1000) < 10) {
    			minutes = "0" + ((time % 1000) * 60 / 1000);
    		}
    		String ampm = "AM";
    		if(hours >= 12) { hours-=12; ampm = "PM"; }
    		if(hours >= 12) { hours-=12; ampm = "AM"; }
            if (hours == 0) hours = 12;
            //lblTime.setY(1 + (19*mapBossInfos.size()));
            lblTime.setText("" + hours + ":" + minutes + " " + ampm);
            lblTime.hardRender();
			
            
    		BlockPos pos = Minecraft.getInstance().player.getPosition();
    		String posStr = pos.getX() + ", " + pos.getY() + ", " + pos.getZ();

			if(CFG.gui.getBool("showPaperDoll")) {
				//lblCoords.setY((int) (paperDoll.getY() + paperDoll.h) + 1);
			} else {
				//lblCoords.setY(13);
			}
    		lblCoords.setText(posStr);
    		lblCoords.hardRender();
	    	
    		paperDoll.render(partialTicks);
    		
    		if(CFG.gui.getBool("showPUM")) {
	    		List<ClothingPlayer> l = AdvSkinMod.getPlayersUsingMod();
	    		for(int i = 0; i < l.size(); i++) {
		    		if(!CFG.gui.getBool("showPUMSelf") && l.get(i).username.contains(Minecraft.getInstance().player.getUniqueID().toString())) {
		    			l.remove(i);
		    		}
	    		}
	    		String t = "Players Using ASC: " + l.size();
	    		//fR.drawString(t, mc.mainWindow.getScaledWidth() - 2 - fR.getStringWidth(t), 3, 0xFFFFFF);
	    		AdvSkinClient.drawRightTextLabel(t, SteinCompat.getMainWindow().getScaledWidth() - 1, 1);
	    		if(CFG.gui.getBool("showPUML")) {
		    		if(Minecraft.getInstance().world != null) {
			    		for(int i = 0; i < l.size(); i++) {
			    			try {
				    			String txt = Minecraft.getInstance().world.getPlayerByUuid(UUID.fromString(l.get(i).username)).getDisplayName().getFormattedText();
				    			//fR.drawString(txt, mc.mainWindow.getScaledWidth() - 2 - fR.getStringWidth(txt), 14 + (i*12), 0xFFFFFF);	
				    			AdvSkinClient.drawRightTextLabel(txt, SteinCompat.getMainWindow().getScaledWidth() - 1, 12 + (i*11));
					    	} catch(Exception e) {}
			    		}
		    		}
	    		}
    		}
		}
		
		
		try {
			for(int i = 0; i < ELEMENTS.size(); i++) {
				GUIComponent c = ELEMENTS.get(i);
				
    			if(!c.isVisible && Minecraft.getInstance().currentScreen instanceof SteinGUISettingsScreen) {
    				if(c instanceof GUILabel) {
    					GUILabel a = (GUILabel) c;
						fR.drawStringWithShadow(a.txt[0], (int) ((c.getX() + c.w / 2) - Minecraft.getInstance().fontRenderer.getStringWidth(a.txt[0]) / 2), (int) (c.getY() + c.h / 2) - 3, 0xAAFFFFFF);
	    			} else {
						fR.drawStringWithShadow("Paper Doll", (int) ((c.getX() + c.w / 2) - Minecraft.getInstance().fontRenderer.getStringWidth("Paper Doll") / 2), (int) (c.getY() + c.h / 2) - 3, 0xAAFFFFFF);
	    			}
    			}
    			
				if(c.isDragging) {
					int width = Minecraft.getInstance().currentScreen.width;
					int height = Minecraft.getInstance().currentScreen.height;

					int textX = (int) (c.getX() + c.w) + 4;
					int textY = (int) (c.getY() + c.h) - 3;
					int textW = Minecraft.getInstance().fontRenderer.getStringWidth((int) c.x + ", " + (int) c.y)+3;
					int textH = textY + 10;
					if(textX + textW > width) {
						textX -= (c.w)+textW + 6;
					}
					
	    			
					AbstractGui.fill(textX-2, textY-2, textX + textW, textH, 0xAA000000);
					fR.drawStringWithShadow((int) c.x + ", " + (int) c.y, textX, textY, 0xFFFFFF);
					
				}
			}
		} catch(Exception e) {}
		if(!AdvSkinMod.MOUSE_PRESSED) {
			alreadyDragging = false;
			for(int i = 0; i < ELEMENTS.size(); i++) {
				GUIComponent c = ELEMENTS.get(i);
				if(wasDragging) {
					/*if(this.dragElement != null) {
						//Do the anchoring if any elements are near to the edges
						if(this.dragElement.isDragging && c != this.dragElement) {
							System.out.println("Drop " + i);
							
						}
					}*/
					if(Math.round(Float.valueOf(c.cfg.getParameter("xPos").toString())) != (int) c.x || Math.round(Float.valueOf(c.cfg.getParameter("yPos").toString())) != (int) c.y) {
						c.cfg.setParameter("xPos", (int) c.x);
						c.cfg.setParameter("yPos", (int) c.y);
						c.cfg.save();
						wasDragging = false;
					}
				}
				c.isDragging = false;
				this.dragElement = null;
			}
		}
	}

	public boolean mouseInCtxMenu(double mX, double mY) {
		if(ctxMenu != null) {
	    	if(mX > ctxMenu.x - 1 && mY > ctxMenu.y - 1 && mX < ctxMenu.x + ctxMenu.WIDTH && mY < ctxMenu.y + ctxMenu.h+1) {
	    		return true;
	    	}
		}
		
		return false;
	}
    	
    public void mouseClicked(double mX, double mY, int btn) {
    	if(Minecraft.getInstance().currentScreen instanceof SteinGUISettingsScreen) {
			if(ctxMenu != null) ctxMenu.mouseClicked(mX, mY, btn);
	    	if(ctxMenu != null) return;
			for(int i = 0; i < ELEMENTS.size(); i++) {
				GUIComponent c = ELEMENTS.get(i);
				if(mX > c.getX() && mY > c.getY() && mX < c.getX() + c.w && mY < c.getY() + c.h) {
					if(btn == 1) {
						int ctY = (int) mY + 5;
						int ctX = (int) mX + 5;
						if(ctX+80 > Minecraft.getInstance().getMainWindow().getScaledWidth()) ctX = Minecraft.getInstance().getMainWindow().getScaledWidth()-82;
						ctxMenu = new AdvContextMenu(ctX, ctY, c);
	
						if(c.getContextButtons() != null) {
							ctxMenu.ELEMENTS = c.getContextButtons();
						}
					}
				}
			}
    	}
    	
    }
	public void mouseDragged(double mouseX, double mouseY, double dragX, double dragY, int btn) {
		if(ctxMenu != null) ctxMenu.mouseDragged(mouseX, mouseY, btn, dragX, dragY);
		if(ctxMenu != null) return;
		if(mouseInCtxMenu(mouseX, mouseY) || ctxMenu == null) {
			if(Minecraft.getInstance().currentScreen instanceof SteinGUISettingsScreen) {
				if(btn == 0) {
					for(int i = 0; i < ELEMENTS.size(); i++) {
						GUIComponent c = ELEMENTS.get(i);
						if(!alreadyDragging && mouseX > c.getX() && mouseY > c.getY() && mouseX < c.getX() + c.w && mouseY < c.getY() + c.h) {
							c.isDragging = true;
							this.dragElement = c;
							alreadyDragging = true;
							wasDragging = true;
						}
						if(c.isDragging) {
							if(c.snap == GUISnap.LEFT || c.snap == GUISnap.TOPLEFT || c.snap == GUISnap.BOTTOMLEFT) {
								c.x += dragX;
							} else {
								c.x -= dragX;
							}
							c.y += dragY;
							
						}
					}
				}
	    	}
		}
	}
	public void mousePressed(double mouseX, double mouseY, int button) {
		if(ctxMenu != null) ctxMenu.mousePressed(mouseX, mouseY, button);
	}
	public void mouseReleased(double mouseX, double mouseY, int button) {
		if(ctxMenu != null) ctxMenu.mouseReleased(mouseX, mouseY, button);
	}
}
