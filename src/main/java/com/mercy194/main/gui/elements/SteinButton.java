package com.mercy194.main.gui.elements;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SteinButton extends AbstractButton {
   protected final SteinButton.IPressable onPress;
   
   public SteinButton(int widthIn, int heightIn, int p_i51141_3_, int p_i51141_4_, String text, SteinButton.IPressable onPress) {
      super(widthIn, heightIn, p_i51141_3_, p_i51141_4_, text);
      this.onPress = onPress;
   }

   @Override
   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
	  Minecraft minecraft = Minecraft.getInstance();
      FontRenderer font = minecraft.fontRenderer;
      if(this.visible) {
	      this.isHovered = p_render_1_ >= this.x && p_render_2_ >= this.y && p_render_1_ < this.x + this.width && p_render_2_ < this.y + this.height;
	      
	      int clr = 0x444444 + (84 << 24);
	      if(this.isHovered()) clr = 0x666666 + (84 << 24);
	      if(!this.active)  clr = 0x222222 + (84 << 24);
	      fill(x, y, x + getWidth(), y + getHeight(), clr);
	      fill(x, y + getHeight()-1, x + getWidth(), y + getHeight(), 0x666666 + (96 << 24));
	      font.drawString(this.getMessage(), x + (this.width / 2) - (font.getStringWidth(this.getMessage()) / 2), y + getHeight() / 2 - 4, active?0xFFFFFF:0x666666);
      }
      GL11.glColor3f(1f, 1f, 1f);
   }
   public void onPress() {
      this.onPress.onPress(this);
   }

   @OnlyIn(Dist.CLIENT)
   public interface IPressable {
      void onPress(SteinButton p_onPress_1_);
   }
}