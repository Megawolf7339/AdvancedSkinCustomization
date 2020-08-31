package com.mercy194.main.gui.category.accessory;

import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.category.AdvCategory;
import com.mercy194.main.gui.elements.SteinComboButton;
import com.mercy194.main.gui.elements.SteinComboButton.Type;
import com.mercy194.main.gui.screen.MercyCustomizationScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class CategoryAccMask extends AdvCategory {

	public CategoryAccMask(MercyCustomizationScreen parent) {
		super(parent, "Head");
    	Minecraft m = Minecraft.getInstance();
    	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
        int j = parent.height / 2;

        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47+(15*0), 168, 2, "Hat: " + (plr.getAccessoryInt("hat")+1) + "/2", plr.getAccessoryInt("hat"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessoryInt("hat", val.getValue());
	        	val.setMessage("Hat: " + (plr.getAccessoryInt("hat")+1) + "/2");
        	}
        	if(type == Type.VARIATION) {
	        	plr.updateAccessoryInt("hat_var", val.getVariation());
        	}
        }).setVariant(plr.getAccessoryInt("hat_var")));
        
        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47+(15*1), 168, "Gas Mask: " + (plr.getAccessoryInt("mask")+1) + "/2", plr.getAccessoryInt("mask"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessoryInt("mask", val.getValue());
	        	val.setMessage("Gas Mask: " + (plr.getAccessoryInt("mask")+1) + "/2");
        	}
        }));
        
        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47+(15*2), 168, 4, "Mask: " + (plr.getAccessoryInt("facemask")+1) + "/2", plr.getAccessoryInt("facemask"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessoryInt("facemask", val.getValue());
	        	val.setMessage("Mask: " + (plr.getAccessoryInt("facemask")+1) + "/2");
        	}
        	if(type == Type.VARIATION) {
	        	plr.updateAccessoryInt("facemask_var", val.getVariation());
        	}
        }).setVariant(plr.getAccessoryInt("facemask_var")));
        /*
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*1), 120, "Wings: 0/0", 0, val -> {
        }));
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*2), 120, "Jacket: 0/0", 0, val -> {
        }));
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*3), 120, "Another Item", 0, val -> {
        }));
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*4), 120, "Another Item", 0, val -> {
        }));
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*5), 120, "Another Item", 0, val -> {
        }));
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*6), 120, "Another Item", 0, val -> {
        }));
        this.addCombo(new SteinComboButton(1, parent.width / 2 - 84, j-47+(15*7), 120, "Another Item", 0, val -> {
        }));
        */
	}

	public int getIndex() {
		return 2;
	}

}
