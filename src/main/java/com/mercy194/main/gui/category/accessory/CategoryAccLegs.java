package com.mercy194.main.gui.category.accessory;

import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.category.AdvCategory;
import com.mercy194.main.gui.elements.SteinComboButton;
import com.mercy194.main.gui.elements.SteinComboButton.Type;
import com.mercy194.main.gui.screen.MercyCustomizationScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class CategoryAccLegs extends AdvCategory {

	public CategoryAccLegs(MercyCustomizationScreen parent) {
		super(parent, "Legs");
    	Minecraft m = Minecraft.getInstance();
    	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
        
        int j = parent.height / 2;

        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47, 168, 4, "Legs: " + (plr.getAccessoryInt("legs")+1) + "/2", plr.getAccessoryInt("legs"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessoryInt("legs", val.getValue());
	        	val.setMessage("Legs: " + (plr.getAccessoryInt("legs")+1) + "/2");
        	}
        	if(type == Type.VARIATION) {
        		plr.updateAccessoryInt("legs_var", val.getVariation());
        	}
        }).setVariant(plr.getAccessoryInt("legs_var")));
	}

	public int getIndex() {
		return 2;
	}

}
