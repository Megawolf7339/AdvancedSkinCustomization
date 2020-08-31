package com.mercy194.main.gui.elements;

import org.lwjgl.opengl.GL11;

import com.mercy194.main.gui.screen.MercyCustomizationScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SteinComboButton {

	protected SteinImageButton btn1, btn2;

	private String name;
	public int variationAmount = 0;
	private int varIndex = 0;
	public int x, y, w;
	public boolean visible = true;
	private int index = 0;
	private SteinComboButton.IPressable onChange;
	public SteinComboButton(int optionSize, int x, int y, int w, int variationAmount, String text, int defVal, SteinComboButton.IPressable onPress) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.name = text;
		this.variationAmount = variationAmount;
		this.index = defVal;
		this.onChange = onPress;

		btn1 = new SteinImageButton(x+2 + w - 38, y+2, 20-4, 20-4, "<", button -> {
			index--;
			btn1.active = !(index==0);
			btn2.active = !(index==optionSize-1);
			this.onChange.onValueChanged(this, Type.MODEL);
		});
		btn2 = new SteinImageButton(x+2 + w - 20, y+2, 20-4, 20-4, ">", button -> {
			index++;
			btn1.active = !(index==0);
			btn2.active = !(index==optionSize-1);
			this.onChange.onValueChanged(this, Type.MODEL);
		});

		btn1.active = !(index==0);
		btn2.active = !(index==optionSize-1);
	}

	public SteinComboButton(int optionSize, int x, int y, int w, int variationAmount, String text, SteinComboButton.IPressable onPress) {
		this(optionSize, x, y, w, variationAmount, text, 0, onPress);
	}
	public SteinComboButton(int optionSize, int x, int y, int w, String text, int defVal, SteinComboButton.IPressable onPress) {
		this(optionSize, x, y, w, 0, text, defVal, onPress);
	}
	public SteinComboButton(int optionSize, int x, int y, int w, String text, SteinComboButton.IPressable onPress) {
		this(optionSize, x, y, w, 0, text, 0, onPress);
	}
	


	public void render(int f1, int f2, float f3) {
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer font = minecraft.fontRenderer;
		if(this.visible) {
			btn1.render(f1, f2, f3);
			btn2.render(f1, f2, f3);

			int xP = x +4;
			int xP2 = x + 4 + (font.getStringWidth(this.getMessage()));
		    AbstractGui.fill(xP-2, y+4, xP2+2, y + 16, 0x222222 + (128 << 24));
	    
		    @SuppressWarnings("unused")
			String name = "Default";
		    /*for(AdvClothing c : AdvSkinMod.CLOTHING) {
		    	if(c.getVariations().size() > 0 && this.getVariation() < c.getVariations().size()-1) {
		    		name = c.getVariations().get(this.getVariation()).getName();
		    	}
		    }
		    */
		    if(getValue() != 0 && this.variationAmount > 0 && f1 > x+1 && f2 > y+3 && f1 < xP2+2 && f2 < y+16) {
		    	MercyCustomizationScreen.tooltipText = new String[] {
		    			"Click to Change Variation " + (this.getVariation()+1) + "/" + this.variationAmount
		    	};
			    font.drawStringWithShadow(this.getMessage(), xP, y + 20 / 2 - 4, 0xFFFF00);
		    } else {
			    font.drawStringWithShadow(this.getMessage(), xP, y + 20 / 2 - 4, 0xFFFFFF);
		    }
		}
		
		GL11.glColor3f(1f, 1f, 1f);
	}
    public void mouseReleased(double f1, double f2, int f3) {
    	if(this.visible) {
    		btn1.mouseReleased(f1, f2, f3);
    		btn2.mouseReleased(f1, f2, f3);
    	}
    }
    public void mouseClicked(double f1, double f2, int f3) {
    	if(this.visible) {
    		btn1.mouseClicked(f1, f2, f3);
    		btn2.mouseClicked(f1, f2, f3);
			int xP2 = x + 4 + (Minecraft.getInstance().fontRenderer.getStringWidth(this.getMessage()));
		    if(getValue() != 0 && f3 == 0 && this.variationAmount > 0 && f1 > x+1 && f2 > y+3 && f1 < xP2+2 && f2 < y+16) {
		    	this.varIndex++;
		    	if(this.varIndex >= this.variationAmount) this.varIndex = 0;
				this.onChange.onValueChanged(this, Type.VARIATION);
				Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		    }
    	}
    }
    
    public int getVariation() {
    	return this.varIndex;
    }
    public void mouseDragged(double f1, double f2, int f3, double f4, double f5) {
    	if(this.visible) {
    		btn1.mouseDragged(f1, f2, f3, f4, f5);
    		btn2.mouseDragged(f1, f2, f3, f4, f5);
    	}
    }
	
	public int getValue() {
		return index;
	}
	public String getMessage() {
		return name;
	}
	

	@OnlyIn(Dist.CLIENT)
	public interface IPressable {
		void onValueChanged(SteinComboButton steinComboButton, Type type);
	}


	public enum Type {
		MODEL, VARIATION
	}
	public void setMessage(String string) {
		this.name = string;
	}

	public SteinComboButton setVariant(int ind) {
		this.varIndex = ind;
		return this;
	}
	public SteinComboButton setValue(int ind) {
		this.setValue(ind);
		return this;
	}
}