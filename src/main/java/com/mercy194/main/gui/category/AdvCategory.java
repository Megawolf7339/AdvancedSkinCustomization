package com.mercy194.main.gui.category;

import java.util.ArrayList;
import java.util.List;

import com.mercy194.main.gui.elements.SteinButton;
import com.mercy194.main.gui.elements.SteinComboButton;
import com.mercy194.main.gui.elements.SteinSliderButton;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;

public class AdvCategory {

	public Screen parent;
	public List<Widget> myButtons;
	public List<SteinComboButton> myCombos;
	public List<SteinSliderButton> mySliders;
	public boolean display = false;
	public String dispName = "missingno";
	public SteinButton catBtn;
	
	public AdvCategory(Screen parent, String dispName) {
		this.parent = parent;
		this.dispName = dispName;
		this.display = false;
		this.myButtons = new ArrayList<Widget>();
		this.myCombos = new ArrayList<SteinComboButton>();
		this.mySliders = new ArrayList<SteinSliderButton>();
	}
	public void addButton(Widget w) {
		myButtons.add(w);
	}
	public void addCombo(SteinComboButton w) {
		myCombos.add(w);
	}
	public void addSlider(SteinSliderButton w) {
		mySliders.add(w);
	}
	public Widget getButton(int x) {
		return myButtons.get(x);
	}
	
	public void render(int f1, int f2, float f3) {
    	if(display) {
	    	for(int i = 0; i < myButtons.size(); i++) myButtons.get(i).render(f1, f2, f3);
	    	for(int i = 0; i < myCombos.size(); i++) myCombos.get(i).render(f1, f2, f3);
	    	for(int i = 0; i < mySliders.size(); i++) mySliders.get(i).render(f1, f2, f3);
    	}
	}
    public void mouseReleased(double f1, double f2, int f3) {
    	if(display) {
	    	for(int i = 0; i < myButtons.size(); i++) myButtons.get(i).mouseReleased(f1, f2, f3);
	    	for(int i = 0; i < myCombos.size(); i++) myCombos.get(i).mouseReleased(f1, f2, f3);
	    	for(int i = 0; i < mySliders.size(); i++) mySliders.get(i).mouseReleased(f1, f2, f3);
    	}
    }
    public void mouseClicked(double f1, double f2, int f3) {
    	if(display) {
	    	for(int i = 0; i < myButtons.size(); i++) myButtons.get(i).mouseClicked(f1, f2, f3);
	    	for(int i = 0; i < myCombos.size(); i++) myCombos.get(i).mouseClicked(f1, f2, f3);
	    	for(int i = 0; i < mySliders.size(); i++) mySliders.get(i).mouseClicked(f1, f2, f3);
    	}
    }
    
    public void mouseDragged(double f1, double f2, int f3, double f4, double f5) {
    	if(display) {
	    	for(int i = 0; i < myButtons.size(); i++) myButtons.get(i).mouseDragged(f1, f2, f3, f4, f5);
	    	for(int i = 0; i < myCombos.size(); i++) myCombos.get(i).mouseDragged(f1, f2, f3, f4, f5);
	    	for(int i = 0; i < mySliders.size(); i++) mySliders.get(i).mouseDragged(f1, f2, f3, f4, f5);
    	}
    }
	public int getIndex() {
		return 0;
	}
}
