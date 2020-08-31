package com.mercy194.main.gui.elements;

import org.lwjgl.opengl.GL11;

import com.mercy194.main.proxy.AdvSkinClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SteinImageButton extends AbstractButton {
   protected final SteinImageButton.IPressable onPress;
   
   public SteinImageButton(int widthIn, int heightIn, int p_i51141_3_, int p_i51141_4_, String text, SteinImageButton.IPressable onPress) {
      super(widthIn, heightIn, p_i51141_3_, p_i51141_4_, text);
      this.onPress = onPress;
   }

   @Override
   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
	  Minecraft minecraft = Minecraft.getInstance();
      FontRenderer font = minecraft.fontRenderer;

      if(this.visible) {
	      this.isHovered = p_render_1_ >= this.x && p_render_2_ >= this.y && p_render_1_ < this.x + this.width && p_render_2_ < this.y + this.height;
	      
	      int clr = 0x000000 + (50 << 24);
	      if(this.isHovered()) clr = 0x000000 + (75 << 24);
	      if(!this.active) clr = 0x000000 + (75 << 24);
	      fill(x+1, y+1, x + getWidth()-1, y + getHeight()-1, clr);
	      //fill(x+1, y+1, x + getWidth()-1, y + getHeight()-1, clr);
	      
	      int txtClr = 0x555555;
	      if(isHovered()&&active) {
	    	  txtClr = 0xFFFF66;
	      } else if(active) {
	    	  txtClr = 0xFFFFFF;
	      }
	      font.drawString(this.getMessage(), x + (this.width / 2) - (font.getStringWidth(this.getMessage()) / 2), y + getHeight() / 2 - 4, txtClr);
      }
      GL11.glColor3f(1f, 1f, 1f);
   }
   public void onPress() {
	  this.onPress.onPress(this);
	  AdvSkinClient.onChangeCustomization();
   }

   @OnlyIn(Dist.CLIENT)
   public interface IPressable {
      void onPress(SteinImageButton p_onPress_1_);
   }
}