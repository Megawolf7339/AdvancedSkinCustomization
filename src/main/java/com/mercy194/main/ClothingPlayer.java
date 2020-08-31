package com.mercy194.main;

import org.json.simple.JSONObject;

import net.minecraft.util.ResourceLocation;

public class ClothingPlayer {

	public String username;
	public boolean gender;
	public int skinPack;
	
	public int partSize;
	public float pHeight = 1f;
	public float pBustSize = 0.5f;
	
	public JSONObject accessories = new JSONObject();
	public String capeURL;
	public String preCapeURL;
	public boolean usingMod = false;
	
	public ResourceLocation RES_CAPE, RES_SKIN;
	
	public ClothingPlayer(String username) {
		this(username, true);
	}
	public ClothingPlayer(String username, boolean gender) {
		this.username = username;
		this.gender = gender;
		this.partSize = 1;
		this.skinPack = 0;
		this.capeURL = "";
	}

	public int getAccessoryInt(String k) {
		if(this.accessories.get(k) != null) {
			return Integer.valueOf(this.accessories.get(k).toString());
		}
		return 0;
	}
	public float getAccessoryFloat(String k, float defVal) {
		if(this.accessories.get(k) != null) {
			return Float.valueOf(this.accessories.get(k).toString());
		}
		return defVal;
	}
	
	@SuppressWarnings("unchecked")
	public void updateAccessoryInt(String k, int v) {
		this.accessories.put(k, v);
		AdvSkinMod.updateAccessory(k, v, this); //update on database for other players
	}

	@SuppressWarnings("unchecked")
	public void setAccessoryFloat(String k, float v) { //this sets the value without updating it on the database (for sliders)
		this.accessories.put(k, v);
	}
	public void updateAccessoryFloat(String k, float v) {
		this.setAccessoryFloat(k, v);
		AdvSkinMod.updateAccessory(k, v, this); //update on database for other players
	}

	public void resetAccessories() {
		this.accessories.clear();
		AdvSkinMod.clearAccessories(this); //update on database for other players
	}
	public void reload() {
		AdvSkinMod.loadSQLForPlayer(this.username);
	}
}
