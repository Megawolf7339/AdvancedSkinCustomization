package com.mercy194.main.gui.category.accessory;

import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.category.AdvCategory;
import com.mercy194.main.gui.elements.SteinComboButton;
import com.mercy194.main.gui.elements.SteinComboButton.Type;
import com.mercy194.main.gui.screen.MercyCustomizationScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class CategoryAccShoulder extends AdvCategory {

	public CategoryAccShoulder(MercyCustomizationScreen parent) {
		super(parent, "Body");
    	Minecraft m = Minecraft.getInstance();
    	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
        
        int j = parent.height / 2;

        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47, 168, 3, "Backpack: " + (plr.getAccessoryInt("backpack")+1) + "/2", plr.getAccessoryInt("backpack"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessoryInt("backpack", val.getValue());
	        	val.setMessage("Backpack: " + (plr.getAccessoryInt("backpack")+1) + "/2");
        	}
        	if(type == Type.VARIATION) {
	        	plr.updateAccessoryInt("backpack_var", val.getVariation());
        	}
        }).setVariant(plr.getAccessoryInt("backpack_var")));

        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47+(15*1), 168, 2, "Pouch: " + (plr.getAccessoryInt("pouch")+1) + "/2", plr.getAccessoryInt("pouch"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessoryInt("pouch", val.getValue());
	        	val.setMessage("Pouch: " + (plr.getAccessoryInt("pouch")+1) + "/2");
        	}
        	if(type == Type.VARIATION) {
	        	plr.updateAccessoryInt("pouch_var", val.getVariation());
        	}
        }).setVariant(plr.getAccessoryInt("pouch_var")));
        
        
	}

	public int getIndex() {
		return 2;
	}

}
