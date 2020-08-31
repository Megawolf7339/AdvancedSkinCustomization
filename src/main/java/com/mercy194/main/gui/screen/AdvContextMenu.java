package com.mercy194.main.gui.screen;

import java.util.ArrayList;

import com.mercy194.SteinCompat;
import com.mercy194.main.gui.elements.SteinGUISlider;
import com.mercy194.main.gui.experimental.GUIComponent;
import com.mercy194.main.gui.experimental.GUIDoll;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.Widget;

public class AdvContextMenu {


	public ArrayList<Widget> ELEMENTS = new ArrayList<Widget>();
	
	public int x, y, h;
	public static int WIDTH = 80;
	
	public AdvContextMenu(int x, int y, GUIComponent comp) {
		this.x = x;
		this.y = y;
	}
	public void render(float partialTicks) {
		this.h = this.ELEMENTS.size() * 16;
		MouseHelper he = Minecraft.getInstance().mouseHelper;
		double mX = he.getMouseX() / SteinCompat.getMainWindow().getGuiScaleFactor();
		double mY = he.getMouseY() / SteinCompat.getMainWindow().getGuiScaleFactor();

		AbstractGui.fill(x-1, y-1, x + WIDTH+1, y + h+1, 0x44000000);
		AbstractGui.fill(x, y, x + WIDTH, y + h, 0x44000000);
		for(int i = 0; i < ELEMENTS.size(); i++) {
    		Widget c = ELEMENTS.get(i);
			c.render((int) mX, (int) mY, 0);
		}
	}

    public void mouseDragged(double mX, double mY, int btn, double f4, double f5) {
    	for(int i = 0; i < ELEMENTS.size(); i++) {
    		Widget c = ELEMENTS.get(i);
    		//if(c.isMouseOver(mX, mY)) {
	    		c.mouseDragged(mX, mY, btn, f4, f5);
			//}
    	}
    }
    
    public void mouseClicked(double mX, double mY, int btn) {
    	for(int i = 0; i < ELEMENTS.size(); i++) {
    		Widget c = ELEMENTS.get(i);
			c.mouseClicked(mX, mY, btn);
		}
    	if(!(mX > x - 1 && mY > y - 1 && mX < x + WIDTH && mY < y + h+1)) {
    		AdvSkinScreen.ctxMenu = null;
    	}
    }
	public void mousePressed(double mouseX, double mouseY, int button) {
		
	}
	public void mouseReleased(double mouseX, double mouseY, int button) {
		for(int i = 0; i < ELEMENTS.size(); i++) {
    		Widget c = ELEMENTS.get(i);
    		c.mouseReleased(mouseX, mouseY, button);
		}
	}
}
