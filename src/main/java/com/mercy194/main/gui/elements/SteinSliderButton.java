package com.mercy194.main.gui.elements;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SteinSliderButton {

	protected float value;
	protected float defValue;
    public float minValue = 0.0f;
    public float maxValue = 1.0f;
    
    private boolean dragging = false;

	private String name;
	public int x, y, w;
	public boolean visible = true;
    private SteinSliderButton.IPressable onChange;
	
	public SteinSliderButton(int x, int y, int w, String text, float defVal, float iniVal, SteinSliderButton.IPressable onPress) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.name = text;
		this.value = iniVal;
		this.defValue = defVal;
		this.onChange = onPress;
	}
	

	private float prevValue = 0;
	
	public void render(int f1, int f2, float f3) {
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer font = minecraft.fontRenderer;
		if(this.visible) {
			

			int xP = x +4;
			int xP2 = x + w - 4;
		    AbstractGui.fill(xP-2, y+1, xP2+2, y + 15, 0x222222 + (128 << 24));
		    if(this.dragging) {
		    	this.value = (f1 - (this.x + 1)) / (float)(this.w - 4);
	    		if(this.value < 0) this.value = 0;
	    		if(this.value > 1) this.value = 1;
	    		if(this.value != this.prevValue) {
	    			this.onChange.onValueChanged(this, Type.DRAGGING);
	    			this.prevValue = this.value;
	    		}
		    }
		    int xPos = this.x + 4 + (int) (this.value * (float)(this.w - 6));
		    AbstractGui.fill(xP-1, y+2, xPos-1, y + 14, 0x222266 + (100 << 24));
		    
		    int xPos2 = this.x + 2 + (int) (this.defValue * (float)(this.w - 4));
		    AbstractGui.fill(xPos2-2, y, xPos2, y + 16, 0x222222 + (120 << 24));
		    
		    if(f1 >= this.x && f2 >= this.y && f1 < this.x + this.w && f2 < this.y + 16) {
			    font.drawStringWithShadow(this.getMessage(), xP+1, y + 20 / 2 - 6, 0xFFFF00);
		    } else {
			    font.drawStringWithShadow(this.getMessage(), xP+1, y + 20 / 2 - 6, 0xFFFFFF);
		    }
		}
		
		GL11.glColor3f(1f, 1f, 1f);
	}
    public void mouseReleased(double f1, double f2, int f3) {
    	if(this.visible) {
    		if(this.dragging) {
    			this.onChange.onValueChanged(this, Type.RELEASE);
    			this.dragging = false;
    		}
    	}
    }
    public void mouseClicked(double f1, double f2, int f3) {
    	if(this.visible) {
  	      	if(f1 >= this.x && f2 >= this.y && f1 < this.x + this.w && f2 < this.y + 16) {
  	    		this.dragging = true;
		    	this.value = (float) ((f1 - (this.x + 1)) / (float)(this.w - 4));
	    		if(this.value < 0) this.value = 0;
	    		if(this.value > 1) this.value = 1;
	    		if(this.value != this.prevValue) {
	    			this.onChange.onValueChanged(this, Type.DRAGGING);
	    			this.prevValue = this.value;
	    		}
				Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
  	      	}
    		/*btn1.mouseClicked(f1, f2, f3);
    		btn2.mouseClicked(f1, f2, f3);
			int xP2 = x + 4 + (Minecraft.getInstance().fontRenderer.getStringWidth(this.getMessage()));
		    if(getValue() != 0 && f3 == 0 && this.variationAmount > 0 && f1 > x+1 && f2 > y+3 && f1 < xP2+2 && f2 < y+16) {
		    	this.varIndex++;
		    	if(this.varIndex >= this.variationAmount) this.varIndex = 0;
				this.onChange.onValueChanged(this, Type.VARIATION);
				Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		    }*/
    	}
    }
    
    public float getValue() {
    	return this.value;
    }
    public void mouseDragged(double f1, double f2, int f3, double f4, double f5) {
    	if(this.visible) {
    	}
    }
	
	public String getMessage() {
		return name;
	}
	

	@OnlyIn(Dist.CLIENT)
	public interface IPressable {
		void onValueChanged(SteinSliderButton steinComboButton, Type type);
	}


	public enum Type {
		DRAGGING, RELEASE
	}
	public void setMessage(String string) {
		this.name = string;
	}

	public SteinSliderButton setValue(int ind) {
		this.setValue(ind);
		return this;
	}
}