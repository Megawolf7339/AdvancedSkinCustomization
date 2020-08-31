package com.mercy194.main.gui.category.accessory;

import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.category.AdvCategory;
import com.mercy194.main.gui.elements.SteinComboButton;
import com.mercy194.main.gui.elements.SteinComboButton.Type;
import com.mercy194.main.gui.screen.MercyCustomizationScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class CategoryAccTorso extends AdvCategory {

	public CategoryAccTorso(MercyCustomizationScreen parent) {
		super(parent, "SeekerTF");
    	Minecraft m = Minecraft.getInstance();
    	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
        
        int j = parent.height / 2;

        
        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47+(15*0), 168, 4, "Wings: " + (plr.getAccessoryInt("wings")+1) + "/2",plr.getAccessoryInt("wings"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessoryInt("wings", val.getValue());
	        	val.setMessage("Wings: " + (plr.getAccessoryInt("wings")+1) + "/2");
        	}
        	if(type == Type.VARIATION) {
	        	plr.updateAccessoryInt("wings_var", val.getVariation());
        	}
        }).setVariant(plr.getAccessoryInt("wings_var")));
        
        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47+(15*1), 168, 4, "Engines: " + (plr.getAccessoryInt("torso")+1) + "/2", plr.getAccessoryInt("torso"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessoryInt("torso", val.getValue());
	        	val.setMessage("Engines: " + (plr.getAccessoryInt("torso")+1) + "/2");
        	}
        	if(type == Type.VARIATION) {
	        	plr.updateAccessoryInt("torso_var", val.getVariation());
        	}
        }).setVariant(plr.getAccessoryInt("torso_var")));

        this.addCombo(new SteinComboButton(2, parent.width / 2 - 124, j-47+(15*2), 168, 4, "Boots: " + (plr.getAccessoryInt("legs")+1) + "/2", plr.getAccessoryInt("legs"), (val, type) -> {
        	if(type == Type.MODEL) {
	        	plr.updateAccessoryInt("legs", val.getValue());
	        	val.setMessage("Boots: " + (plr.getAccessoryInt("legs")+1) + "/2");
        	}
        	if(type == Type.VARIATION) {
        		plr.updateAccessoryInt("legs_var", val.getVariation());
        	}
        }).setVariant(plr.getAccessoryInt("legs_var")));
	}
	public void render(int f1, int f2, float f3) {
    	super.render(f1, f2, f3);
    	if(this.display) {
    		Minecraft.getInstance().fontRenderer.drawStringWithShadow("Transformers G1 Seeker Skins", parent.width / 2 - 118, parent.height / 2 + 68, 0xFFFFFF);
    	}
	}

	public int getIndex() {
		return 2;
	}

}
