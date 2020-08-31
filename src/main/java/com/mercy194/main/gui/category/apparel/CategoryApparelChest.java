package com.mercy194.main.gui.category.apparel;

import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.gui.category.AdvCategory;
import com.mercy194.main.gui.elements.SteinSliderButton;
import com.mercy194.main.gui.screen.MercyCustomizationScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class CategoryApparelChest extends AdvCategory {

	public CategoryApparelChest(MercyCustomizationScreen parent) {
		super(parent, "General");
    	Minecraft m = Minecraft.getInstance();
    	String playerName = PlayerEntity.getUUID(m.player.getGameProfile()).toString();
        ClothingPlayer plr = AdvSkinMod.getPlayerByName(playerName);
        
        int j = parent.height / 2;

        
        float tmpVal = plr.getAccessoryFloat("plr_height", 0.7f);
    	float initVal = 0.5f + (0.36f + (tmpVal / 5f));
        this.addSlider(new SteinSliderButton(parent.width / 2 - 124, j-46, 168, "Player Height: " + Math.round(initVal*100) + "%", 0.7f, tmpVal, (val, type) -> {
        	if(type == SteinSliderButton.Type.DRAGGING) {
	        	plr.setAccessoryFloat("plr_height", val.getValue());
	        	
		    	float tVal = 0.5f + (0.36f + (val.getValue() / 5f));
	            val.setMessage("Player Height: " + Math.round(tVal*100) + "%");
	            
	            //ASCPacketHandler.channel.send(PacketDistributor.SERVER.noArg(), new AdvSkinMessagef("HEIGHT", plr.getAccessoryFloat("plr_height", 0.7f)));
        	}
        	if(type == SteinSliderButton.Type.RELEASE) {
	        	plr.updateAccessoryFloat("plr_height", val.getValue());
        	}
        }));
        
        if(plr.gender==false) {
        	float tmpVal2 = plr.getAccessoryFloat("plr_bust", 0.5f);
        	float initVal2 = tmpVal2;
            this.addSlider(new SteinSliderButton(parent.width / 2 - 124, j-46+(16*1), 168, "Breast Size: " + Math.round(initVal2*100) + "%", 0.5f, tmpVal2, (val, type) -> {
            	if(type == SteinSliderButton.Type.DRAGGING) {
    	        	plr.setAccessoryFloat("plr_bust", val.getValue());
    	        	
    		    	float tVal = val.getValue();
    	            val.setMessage("Breast Size: " + Math.round(tVal*100) + "%");
            	}
            	if(type == SteinSliderButton.Type.RELEASE) {
    	        	plr.updateAccessoryFloat("plr_bust", val.getValue());
            	}
            }));
        }
	}
	

	public int getIndex() {
		return 1;
	}


}
