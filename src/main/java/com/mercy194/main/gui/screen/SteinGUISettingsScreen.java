package com.mercy194.main.gui.screen;

import com.mercy194.main.CFG;
import com.mercy194.main.SteinEventHandler;
import com.mercy194.main.proxy.AdvSkinClient;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;

public class SteinGUISettingsScreen extends Screen {

	
    private Screen lastScreen;
    public SteinGUISettingsScreen(Screen inst) {
        super(new TranslationTextComponent("narrator.screen.title"));
        this.lastScreen = inst;
    }

    private void resetSettings() {
		SteinEventHandler.resetGUI();
	}


    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return true;
    }

    protected void init() {
        int j = this.height / 2 - 48;
        
        /*
        this.addButton(new Button(2, 95, 98, 20, "Paper Doll: " + (CFG.gui.getBool("showPaperDoll")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showPaperDoll", !CFG.gui.getBool("showPaperDoll"));
        	CFG.gui.save();
        	button.setMessage("Paper Doll: " + (CFG.gui.getBool("showPaperDoll")?"ON":"OFF"));
        }));
        
        this.addButton(new Button(2, 95 + (22*1), 98, 20, "Biome: " + (CFG.gui.getBool("showBiomeText")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showBiomeText", !CFG.gui.getBool("showBiomeText"));
        	CFG.gui.save();
        	button.setMessage("Biome: " + (CFG.gui.getBool("showBiomeText")?"ON":"OFF"));
        }));
        
        this.addButton(new Button(2, 95 + (22*2), 98, 20, "Coordinates: " + (CFG.gui.getBool("showCoordText")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showCoordText", !CFG.gui.getBool("showCoordText"));
        	CFG.gui.save();
        	button.setMessage("Coordinates: " + (CFG.gui.getBool("showCoordText")?"ON":"OFF"));
        }));

        this.addButton(new Button(2, 95 + (22*3), 98, 20, "Frame Rate: " + (CFG.gui.getBool("showFPSText")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showFPSText", !CFG.gui.getBool("showFPSText"));
        	CFG.gui.save();
        	button.setMessage("Frame Rate: " + (CFG.gui.getBool("showFPSText")?"ON":"OFF"));
        }));

        this.addButton(new Button(2, 95 + (22*4), 98, 20, "Background: " + (CFG.gui.getBool("showBackground")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showBackground", !CFG.gui.getBool("showBackground"));
        	CFG.gui.save();
        	button.setMessage("Background: " + (CFG.gui.getBool("showBackground")?"ON":"OFF"));
        }));

        this.addButton(new Button(this.width / 2 - 98/2, 16, 98, 20, "Time of Day: " + (CFG.gui.getBool("showTimeText")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showTimeText", !CFG.gui.getBool("showTimeText"));
        	CFG.gui.save();
        	button.setMessage("Time of Day: " + (CFG.gui.getBool("showTimeText")?"ON":"OFF"));
        }));


         */
        Button tmp = this.addButton(new Button(this.width - 98 - 2, 32 + (22*1), 98, 20, "Player List: " + (CFG.gui.getBool("showPUML")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showPUML", !CFG.gui.getBool("showPUML"));
        	CFG.gui.save();
        	button.setMessage("Player List: " + (CFG.gui.getBool("showPUML")?"ON":"OFF"));
        }));
    	tmp.visible = CFG.gui.getBool("showPUM");
    	
        Button tmp2 = this.addButton(new Button(this.width - 98 - 2, 32 + (22*2), 98, 20, "Show Self: " + (CFG.gui.getBool("showPUMSelf")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showPUMSelf", !CFG.gui.getBool("showPUMSelf"));
        	CFG.gui.save();
        	button.setMessage("Show Self: " + (CFG.gui.getBool("showPUMSelf")?"ON":"OFF"));
        }));
    	tmp2.visible = CFG.gui.getBool("showPUM");

        this.addButton(new Button(this.width - 98 - 2, 32, 98, 20, "Player Count: " + (CFG.gui.getBool("showPUM")?"ON":"OFF"), button -> {
        	CFG.gui.setParameter("showPUM", !CFG.gui.getBool("showPUM"));
        	CFG.gui.save();
        	tmp.visible = CFG.gui.getBool("showPUM");
        	tmp2.visible = CFG.gui.getBool("showPUM");
        	button.setMessage("Player Count: " + (CFG.gui.getBool("showPUM")?"ON":"OFF"));
        }));
         
		

        this.addButton(new Button(this.width / 2 + 1, j+66, 98, 20, "Done", button -> {
            Minecraft.getInstance().displayGuiScreen(this.lastScreen);
        }));
        this.addButton(new Button(this.width / 2 - 99, j+66, 98, 20, "Reset Default", button -> {
            this.resetSettings();
        }));
        
        
    }

	public void render(int f1, int f2, float f3) {
		int yPos = this.height / 2 - 80;
        this.fillGradient(this.width / 2 - 120, yPos, this.width / 2 + 120, yPos + 42, 0x88000000, 0x88000000);
       
    	this.drawCenteredString(this.font, "Advanced Skin Customization - GUI Settings", this.width / 2, yPos + 5, 0xFFFFFF);
    	this.drawCenteredString(this.font, ChatFormatting.AQUA + "" + ChatFormatting.BOLD + "Drag a GUI element to move it.", this.width / 2, yPos + 16, 0xFFFFFF);
    	this.drawCenteredString(this.font, ChatFormatting.GREEN + "" + ChatFormatting.BOLD + "Right click a GUI element to edit it.", this.width / 2, yPos + 28, 0xFFFFFF);

		//drawEntityOnScreen(xP, yP, 60, xP - f1, yP-100 - f2, Minecraft.getInstance().player);
		if(AdvSkinScreen.ctxMenu != null) AdvSkinScreen.ctxMenu.render(f3);
		
        super.render(f1, f2, f3);
    }
}
