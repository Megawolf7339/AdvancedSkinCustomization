package com.mercy194.main.gui.screen;

import com.mercy194.main.CFG;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;

public class SteinRenderSettingsScreen extends Screen {

	private AbstractSlider fovSlider;
    private Screen lastScreen;
    public SteinRenderSettingsScreen(Screen inst) {
        super(new TranslationTextComponent("narrator.screen.title"));
        this.lastScreen = inst;
    }


    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return true;
    }

    protected void init() {
        int j = this.height / 2 - 48;
        
        this.addButton(new Button(this.width / 2 - 150, j-44, 98, 20, "Armor: " + (CFG.generic.getBool("showArmor")?"ON":"OFF"), button -> { 
        	CFG.generic.setParameter("showArmor", !CFG.generic.getBool("showArmor"));
        	CFG.generic.save();
        	button.setMessage("Armor: " + (CFG.generic.getBool("showArmor")?"ON":"OFF"));
        }));
        this.addButton(new Button(this.width / 2 - 150, j-22, 98, 20, "Off-Hand: " + (CFG.generic.getBool("showOffHand")?"ON":"OFF"), button -> {
        	CFG.generic.setParameter("showOffHand", !CFG.generic.getBool("showOffHand"));
        	CFG.generic.save();
        	button.setMessage("Off-Hand: " + (CFG.generic.getBool("showOffHand")?"ON":"OFF"));
        }));
        /*
        this.addButton(new Button(this.width / 2 - 150, j, 98, 20, "Enabled: " + (CFG.generic.getBool("showLayer")?"ON":"OFF"), button -> {
        	CFG.generic.setParameter("showLayer", !CFG.generic.getBool("showLayer"));
        	CFG.generic.save();
        	button.setMessage("Enabled: " + (CFG.generic.getBool("showLayer")?"ON":"OFF"));
        }));
        */

        this.addButton(new Button(this.width / 2 - 150, j + 44, 98, 20, "First Person: " + (CFG.generic.getBool("fp_enabled")?"ON":"OFF"), button -> {
        	CFG.generic.setParameter("fp_enabled", !CFG.generic.getBool("fp_enabled"));
        	CFG.generic.save();
        	button.setMessage("First Person: " + (CFG.generic.getBool("fp_enabled")?"ON":"OFF"));
        }));

		float fov = Float.valueOf(CFG.generic.getParameter("fp_fov").toString());
        fovSlider = new AbstractSlider(this.width / 2 - 150, j + 66, 98, 20, fov) {
			
			@Override
			protected void updateMessage() {
				float fov = Float.valueOf(CFG.generic.getParameter("fp_fov").toString());
				fovSlider.setMessage("FOV: " + Math.floor(-20+fov*40));
			}
			
			@Override
			protected void applyValue() {
				CFG.generic.setParameter("fp_fov", (float) this.value);
				CFG.generic.save();
				//modelRotation = (float) this.value;
			}
		};
		fovSlider.setMessage("FOV: " + Math.floor(-20+fov*40));
		addButton(fovSlider);
		
        this.addButton(new Button(this.width / 2 - 150, j + 88, 98, 20, "Natural: " + (CFG.generic.getBool("fp_natural")?"ON":"OFF"), button -> {
        	CFG.generic.setParameter("fp_natural", !CFG.generic.getBool("fp_natural"));
        	CFG.generic.save();
        	button.setMessage("Natural: " + (CFG.generic.getBool("fp_natural")?"ON":"OFF"));
        }));

		
        Button t2 = this.addButton(new Button(this.width / 2 + 40, j + 114, 20, 20, ">", button -> {
        	//m.displayGuiScreen(new SteinSurvivalScreen(this.lastScreen));
        }));
        t2.active = false;
        
        Button t = this.addButton(new Button(this.width / 2 - 60, j + 114, 20, 20, "<", button -> {}));
        t.active = false;
        
        this.addButton(new Button(this.width / 2 - 98/2, j + 140, 98, 20, "Done", button -> {
            Minecraft.getInstance().displayGuiScreen(this.lastScreen);
        }));
        
        
    }
    public void render(int f1, int f2, float f3) {
        this.renderBackground();

        this.drawCenteredString(this.font, "Advanced Skin Customization - Configuration Settings", this.width / 2, this.height / 2 - 110, 0xFFFFFF);
        
        //this.drawCenteredString(this.font, "Render Settings", this.width / 2, this.height / 2 - 85, 0xFFFFFF);
        this.drawString(this.font, "Toggle rendering through armor (females)", this.width / 2 - 48, this.height / 2 - 86, 0xAAAAAA);
        this.drawString(this.font, "Toggle rendering shield in off-hand", this.width / 2 - 48, this.height / 2 - 64, 0xAAAAAA);
        //this.drawString(this.font, "Toggle rendering extra layers on player", this.width / 2 - 48, this.height / 2 - 42, 0xAAAAAA);
        
        this.drawCenteredString(this.font, "First Person Settings", this.width / 2, this.height / 2 - 20, 0xFFFFFF);
        this.drawString(this.font, "Toggle realistic first person mode", this.width / 2 - 48, this.height / 2 + 2, 0xAAAAAA);
        this.drawString(this.font, "FOV Offset Slider", this.width / 2 - 48, this.height / 2 + 24, 0xAAAAAA);
        this.drawString(this.font, "Body rotates true to your actual player.", this.width / 2 - 48, this.height / 2 + 46, 0xAAAAAA);

        this.drawCenteredString(this.font, "Page 1/1", this.width / 2, this.height / 2 + 72, 0xFFFFFF);
		//drawEntityOnScreen(xP, yP, 60, xP - f1, yP-100 - f2, Minecraft.getInstance().player);
        
        super.render(f1, f2, f3);
    }

    public boolean mouseClicked(double f1, double f2, int f3) {
        return super.mouseClicked(f1, f2, f3);
    }
}
