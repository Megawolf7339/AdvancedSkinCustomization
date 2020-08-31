package com.mercy194.main;

import net.minecraft.util.ResourceLocation;

public class ClothingVariation {


	private String name;
	private ResourceLocation texture;
	
	public ClothingVariation(String name, ResourceLocation texture) {
		this.name = name;
		this.texture = texture;
	}

	public String getName() {
		return this.name;
	}
	public ResourceLocation getTexutre() {
		return this.texture;
	}
}
