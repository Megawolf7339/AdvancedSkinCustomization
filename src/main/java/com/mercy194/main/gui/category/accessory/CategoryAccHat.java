package com.mercy194.main.gui.category.accessory;

import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.category.AdvCategory;
import com.mercy194.main.gui.elements.SteinComboButton;
import com.mercy194.main.gui.elements.SteinComboButton.Type;
import com.mercy194.main.gui.screen.MercyCustomizationScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class CategoryAccHat extends AdvCategory {

	public CategoryAccHat(MercyCustomizationScreen parent) {
		super(parent, "Hat");
    	Minecraft m = Minecraft.getInstance();
    	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
        int j = parent.height / 2;

        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47+(15*0), 168, "Hat: " + (plr.getAccessoryInt("hat")+1) + "/2", plr.getAccessoryInt("hat"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessoryInt("hat", val.getValue());
	        	val.setMessage("Hat: " + (plr.getAccessoryInt("hat")+1) + "/2");
        	}
        	if(type == Type.VARIATION) {
	        	plr.updateAccessoryInt("hat_var", val.getVariation());
        	}
        }).setVariant(plr.getAccessoryInt("hat_var")));
	}

	public int getIndex() {
		return 2;
	}

}
