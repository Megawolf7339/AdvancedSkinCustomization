package com.mercy194.main;

import com.mercy194.main.gui.experimental.GUISnap;

public class CFG {

	
	public static Configuration generic, gui;
	
	public static Configuration guiPaperDoll, guiTime, guiBiome, guiFPS, guiCoordinates;
	public CFG() {
 
        //Rendering Stuff
        createGeneric();
        
        //GUI Stuff
        createGUI();
        
        
	}
	

	public static void createGUI() {
        gui = new Configuration("AdvancedSkinCustomization", "gui");

        guiPaperDoll = new Configuration("AdvancedSkinCustomization\\GUI", "paperDoll");
        guiPaperDoll.setDefaultParameter("visible", true);
        guiPaperDoll.setDefaultParameter("scale", 0.5f);
        guiPaperDoll.setDefaultParameter("background", true);
        guiPaperDoll.setDefaultParameter("xPos", 1);
        guiPaperDoll.setDefaultParameter("yPos", 13);
        guiPaperDoll.setDefaultParameter("snap", GUISnap.TOPLEFT.toString());
        guiPaperDoll.finish();

        guiTime = new Configuration("AdvancedSkinCustomization\\GUI", "lblTime");
        guiTime.setDefaultParameter("visible", true);
        guiTime.setDefaultParameter("background", true);
        guiTime.setDefaultParameter("xPos", 0);
        guiTime.setDefaultParameter("yPos", 1);
        guiTime.setDefaultParameter("snap", GUISnap.TOP.toString());
        guiTime.finish();
        
        guiBiome = new Configuration("AdvancedSkinCustomization\\GUI", "lblBiome");
        guiBiome.setDefaultParameter("visible", true);
        guiBiome.setDefaultParameter("background", true);
        guiBiome.setDefaultParameter("xPos", 1);
        guiBiome.setDefaultParameter("yPos", 1);
        guiBiome.setDefaultParameter("snap", GUISnap.TOPLEFT.toString());
        guiBiome.finish();

        guiFPS = new Configuration("AdvancedSkinCustomization\\GUI", "lblFPS");
        guiFPS.setDefaultParameter("visible", true);
        guiFPS.setDefaultParameter("background", true);
        guiFPS.setDefaultParameter("xPos", 1);
        guiFPS.setDefaultParameter("yPos", 0);
        guiFPS.setDefaultParameter("snap", GUISnap.TOPLEFT.toString());
        guiFPS.finish();
        
        guiCoordinates = new Configuration("AdvancedSkinCustomization\\GUI", "lblCoords");
        guiCoordinates.setDefaultParameter("visible", true);
        guiCoordinates.setDefaultParameter("background", true);
        guiCoordinates.setDefaultParameter("xPos", 1);
        guiCoordinates.setDefaultParameter("yPos", 82);
        guiCoordinates.setDefaultParameter("snap", GUISnap.TOPLEFT.toString());
        guiCoordinates.finish();
        
        gui.setDefaultParameter("showPUM", false);
        gui.setDefaultParameter("showPUML", false);
        gui.setDefaultParameter("showPUMSelf", false);
        gui.finish();
	}
	
	public static Configuration getConfigByName(String name) {
		switch(name) {
			case "paperDoll":
				return guiPaperDoll;
			case "lblTime":
				return guiTime;
			case "lblBiome":
				return guiBiome;
			case "lblCoords":
				return guiCoordinates;
			case "lblFPS":
				return guiFPS;
		}
		
		return null;
	}
	public static void createGeneric() {
		generic = new Configuration("AdvancedSkinCustomization", "config");
        generic.setDefaultParameter("showArmor", true);
        
        generic.setDefaultParameter("showUpdateChecker", true);
        
        generic.setDefaultParameter("showOffHand", true);
        generic.setDefaultParameter("showLayer", true);
        generic.setDefaultParameter("fp_enabled", false);
        generic.setDefaultParameter("fp_fov", 0.5f);
        generic.setDefaultParameter("fp_natural", true);
        generic.finish();
	}
}
