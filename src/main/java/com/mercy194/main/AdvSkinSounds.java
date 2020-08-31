package com.mercy194.main;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class AdvSkinSounds {

	public static SoundEvent maleDamage;
	public static SoundEvent femaleDamage, femaleDamage2;
	
	public static void init() {
		maleDamage = register("male_damage");
		femaleDamage = register("female_damage");
		femaleDamage2 = register("female_damage2");
	}

	
	public static SoundEvent register(String name) {
		ResourceLocation location = new ResourceLocation(AdvSkinMod.MODID + ":" + name);
		SoundEvent event = new SoundEvent(location);

		return event;
	}

}
