package com.mercy194.main.gui.category.player;

import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.category.AdvCategory;
import com.mercy194.main.gui.elements.MercyButton;
import com.mercy194.main.gui.elements.SteinComboButton;
import com.mercy194.main.gui.elements.SteinComboButton.Type;
import com.mercy194.main.gui.screen.MercyCapeScreen;
import com.mercy194.main.gui.screen.MercyCustomizationScreen;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class CategoryGeneral extends AdvCategory {

	public CategoryGeneral(MercyCustomizationScreen parent) {
		super(parent, "General");
    	Minecraft m = Minecraft.getInstance();
    	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
        
        int j = parent.height / 2;

        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47, 168, "Gender: " + (plr.gender?ChatFormatting.AQUA + "Male":ChatFormatting.LIGHT_PURPLE + "Female"), plr.gender?0:1, (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.gender = !plr.gender;
	            val.setMessage("Gender: " + (plr.gender?ChatFormatting.AQUA + "Male":ChatFormatting.LIGHT_PURPLE + "Female"));
	            AdvSkinMod.updateStatus(plr);
	            parent.initCategories();
        	}
        }));
        
        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47+(15*7), 168, "Custom Cape", plr.getAccessoryInt("showCape"), (val, type) -> {
        	if(type == Type.MODEL) {
        		plr.updateAccessoryInt("showCape", val.getValue());
        	}
        }));
        
        this.addButton(new MercyButton(parent.width / 2 - 26, parent.height / 2 + 60, 36-4, 20-4, "EDIT", button -> {
        	Minecraft.getInstance().displayGuiScreen(new MercyCapeScreen());
		}));
	}

	public int getIndex() {
		return 0;
	}

}
