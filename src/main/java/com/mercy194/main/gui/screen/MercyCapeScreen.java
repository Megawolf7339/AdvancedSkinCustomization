package com.mercy194.main.gui.screen;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.mercy194.main.AdvSkinHelper;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.elements.SteinImageButton;
import com.mercy194.main.proxy.AdvSkinClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class MercyCapeScreen extends Screen {

    private ResourceLocation BACKGROUND;
	
    public MercyCapeScreen() {
        super(new TranslationTextComponent("narrator.screen.title"));
    }


    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return true;
    }
    protected void init() {
        int j = this.height / 2;

    	Minecraft m = Minecraft.getInstance();
    	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
       	
        TextFieldWidget nameField = new TextFieldWidget(this.font, this.width / 2 - 91, j - 6, 174, 12, I18n.format("advskinmod.capeurl"));
      	nameField.setCanLoseFocus(true);
      	nameField.changeFocus(false);
      	nameField.setTextColor(-1);
      	nameField.setDisabledTextColour(-1);
      	nameField.setEnableBackgroundDrawing(false);
      	nameField.setMaxStringLength(1000);
      	nameField.setText(plr.capeURL);
      	this.addButton(nameField);
        
      	
      	this.addButton(new SteinImageButton(this.width / 2 + 53, this.height / 2 + 5, 40, 14, "Update", button -> {
      		try {
	      		URL url=new URL(nameField.getText());
	      		if(url.toString().contains("file:/")) {
          			displayError("Cannot Be A Local File!");
	      			return;
	      		}
	      		BufferedImage image = ImageIO.read(url);
	      		int height = image.getHeight();
	      		int width = image.getWidth();
	      		if(width == 64 && height == 32) {
		      		plr.capeURL = nameField.getText();
		      		AdvSkinMod.updateStatus(plr);
		      		plr.RES_CAPE = AdvSkinHelper.loadCape(plr.username, plr.capeURL);
          			displayError(TextFormatting.DARK_GREEN + "Updated Custom Cape!");
	      		} else {
	      			displayError("Texture " + TextFormatting.BOLD + "Must"+TextFormatting.RESET + " Be 64x32");
	      		}
	      		 
      		} catch(Exception e) {
      			if(nameField.getText().equals("")) {
    	      		plr.capeURL = "";
    	      		AdvSkinMod.updateStatus(plr);
    	      		plr.RES_CAPE = null;
          			displayError("Removed Custom Cape");
      			} else {
          			displayError("Invalid URL");
      			}
      		}
  			AdvSkinClient.onChangeCustomization();
      		
      	}));
      	this.addButton(new SteinImageButton(this.width / 2 + 21, this.height / 2 + 5, 32, 14, "Reset", button -> {
  				nameField.setText("");
	      		plr.capeURL = "";
	      		AdvSkinMod.updateStatus(plr);
	      		plr.RES_CAPE = null;
      			displayError("Removed Custom Cape");
      			AdvSkinClient.onChangeCustomization();
      	}));
		
        BACKGROUND = new ResourceLocation(AdvSkinMod.MODID, "textures/gui/cape_bg.png");
    }
    
    private String errorMsg;
    private int errorMsgTimer;
    private void displayError(String string) {
    	errorMsg = string;
	}

	public void tick() {
		errorMsgTimer++;
		if(errorMsgTimer > 100) {
			errorMsg = "";
			errorMsgTimer = 0;
		}
	}
	public void renderBackground() {
    	super.renderBackground();
    	
    	Minecraft m = Minecraft.getInstance();
    	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND);
        int i = (this.width - 194) / 2;
        int j = (this.height - 46) / 2;
        AbstractGui.blit(i, j, 0, 0, 194, 46, 512, 512);

    	AbstractGui.fill(i+32, j+48, i+2+34+128, j+50+66, 0xFF000000);
    	AbstractGui.fill(i+2+32, j+50, i+2+32+128, j+50+64, 0xFFFFFFFF);
       	//ResourceLocation CAPELOC = LayerSteinCape.loadCape(plr.username.toLowerCase()+"6", plr.capeURL);
        if(plr.RES_CAPE != null && plr.RES_CAPE != DefaultPlayerSkin.getDefaultSkinLegacy()) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        	this.minecraft.getTextureManager().bindTexture(plr.RES_CAPE);
        	AbstractGui.blit(i+2+32, j+50, 0, 0, 128, 64, 128, 64);
        }
        
        
    }
    
    public float scrollPos = 0;
    public void render(int f1, int f2, float f3) {
        this.renderBackground();
        
        int x = this.width / 2;
        int y = this.height / 2;

        this.font.drawString("Custom Cape URL", x - 92, y - 18, 0x444444);
        this.font.drawString(errorMsg, x - 92, y + 8, 0xFF5522);
		
		super.render(f1, f2, f3);

    }

    public boolean mouseReleased(double f1, double f2, int f3) {
    	return super.mouseReleased(f1, f2, f3);
    }
    public boolean mouseScrolled(double f1, double f2, double f3) {
    	return super.mouseScrolled(f1, f2, f3);
    }
    
    public boolean mouseClicked(double f1, double f2, int f3) {
		return super.mouseClicked(f1, f2, f3);
    }
    
    @Override
    public boolean mouseDragged(double f1, double f2, int f3, double f4, double f5) {
    	return super.mouseDragged(f1, f2, f3, f4, f5);
    }
    
}
