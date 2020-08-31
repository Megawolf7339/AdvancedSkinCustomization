package com.mercy194.main.proxy;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.mercy194.clothes.ClothingBackpack;
import com.mercy194.clothes.ClothingFaceMask;
import com.mercy194.clothes.ClothingFemale;
import com.mercy194.clothes.ClothingGasMask;
import com.mercy194.clothes.ClothingHat;
import com.mercy194.clothes.ClothingPouch;
import com.mercy194.clothes.transformer.ClothingTFBoosters;
import com.mercy194.clothes.transformer.ClothingTFEngine;
import com.mercy194.clothes.transformer.ClothingTFWings;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.AdvSkinSounds;
import com.mercy194.main.CFG;
import com.mercy194.main.ClothingPlayer;
import com.mercy194.main.SteinEventHandler;
import com.mercy194.render.LayerClothing;
import com.mercy194.render.LayerSteinCape;
import com.mercy194.render.SteinFirstPersonRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class AdvSkinClient extends AdvSkinServer {

	public static final KeyBinding toggleFirstPerson = new KeyBinding(AdvSkinMod.MODID + ".key.firstperson", GLFW.GLFW_KEY_V, "key.categories." + AdvSkinMod.MODID);
	public static final KeyBinding toggleEditGUI = new KeyBinding(AdvSkinMod.MODID + ".key.gui", GLFW.GLFW_KEY_G, "key.categories." + AdvSkinMod.MODID);
	public static SteinFirstPersonRenderer fpsRenderer;
    public static CFG cfg;
	public void register() {
        cfg = new CFG();
		AdvSkinSounds.init();
    	ClientRegistry.registerKeyBinding(toggleEditGUI);
    	ClientRegistry.registerKeyBinding(toggleFirstPerson);
    	
    	AdvSkinMod.syncServerOnline = AdvSkinMod.getServerStatus();
    	
    	//update version on database
    	try {
	    	String uuid = PlayerEntity.getUUID(Minecraft.getInstance().getSession().getProfile()).toString();
	    	ClothingPlayer PLAYER = AdvSkinMod.loadSQLForPlayerSync(uuid);
	    	if(PLAYER != null) {
	    		AdvSkinMod.updateStatus(PLAYER);
	    	}
    	} catch(Exception e) {}
    	AdvSkinMod.checkForUpdates();
    	
    	
        MinecraftForge.EVENT_BUS.register(new SteinEventHandler());
        
        
        AdvSkinMod.addClothingToMod(new ClothingFemale());
        AdvSkinMod.addClothingToMod(new ClothingBackpack());
        AdvSkinMod.addClothingToMod(new ClothingGasMask());
        AdvSkinMod.addClothingToMod(new ClothingFaceMask());
        AdvSkinMod.addClothingToMod(new ClothingHat());
        AdvSkinMod.addClothingToMod(new ClothingPouch());
        
        //Transformers Models
        AdvSkinMod.addClothingToMod(new ClothingTFWings());
        AdvSkinMod.addClothingToMod(new ClothingTFEngine());
        AdvSkinMod.addClothingToMod(new ClothingTFBoosters());


        for(PlayerRenderer r : Minecraft.getInstance().getRenderManager().getSkinMap().values()) {
        	LayerClothing clothingLayer = new LayerClothing(false, r);
        	//LayerApparel apparelLayer = new LayerApparel(false, r);
            LayerSteinCape capeLayer = new LayerSteinCape(r);
        	r.addLayer(clothingLayer);
        	r.addLayer(capeLayer);
        	//r.addLayer(apparelLayer);
        }
        
        //EntityRendererManager rm = Minecraft.getInstance().getRenderManager();
        //rm.renderers.put(EntityType.VILLAGER, new CustomVillagerRenderer(rm, (IReloadableResourceManager) Minecraft.getInstance().getResourceManager()));
        
        //EntityRendererManager rm = Minecraft.getInstance().getRenderManager();
        //Map<String, PlayerRenderer> skinMap = ObfuscationReflectionHelper.getPrivateValue(EntityRendererManager.class, rm, "field_178636_l"); // field_178636_l -> skinMap
	}
	


	public static void onChangeCustomization() {
		
	}
	
    private static float rotationYaw = 0f;

    
	public static void drawPaperDoll(int p_228187_0_, int p_228187_1_, int p_228187_2_, float partialTicks, LivingEntity ent, boolean notMirrored) {
		//float f = (float)Math.atan((double)(p_228187_3_ / 40.0F));
	    //float f1 = (float)Math.atan((double)(p_228187_4_ / 40.0F));
	    RenderSystem.pushMatrix();
	    RenderSystem.translatef((float)p_228187_0_, (float)p_228187_1_, 1050.0F);
	    
	    if(ent.isCrouching()) {
	    	RenderSystem.translatef(0, -5, 0);
	    }
	    if(ent.getRidingEntity() != null) { 
	    	RenderSystem.translatef(-2, 8, 0);
	    }
	    RenderSystem.translatef(0, -20f*ent.getSwimAnimation(partialTicks), 0);
	    
	    RenderSystem.scalef(1.0F, 1.0F, -1.0F);
	    MatrixStack matrixstack = new MatrixStack();
	    matrixstack.translate(0.0D, 0.0D, 1000.0D);
	    matrixstack.scale((float)p_228187_2_, (float)p_228187_2_, (float)p_228187_2_);
	    Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
	    Quaternion quaternion1 = Vector3f.YP.rotationDegrees(-150.0F);
	    if(!notMirrored) quaternion1 = Vector3f.YP.rotationDegrees(150.0F);
	    quaternion.multiply(quaternion1);
	    matrixstack.rotate(quaternion);
	    


	    float f = ent.renderYawOffset;
	    float f1 = ent.rotationYaw;
	    float f2 = ent.rotationPitch;
	    float f3 = ent.prevRotationYawHead;
	    float f4 = ent.rotationYawHead;
	    float f5 = ent.prevRenderYawOffset;

	    float headYaw = Minecraft.getInstance().player.getYaw(partialTicks);

	    ent.renderYawOffset = 0;
	    ent.prevRenderYawOffset=0;
	      
	    ent.rotationYaw = 0;
	    ent.prevRotationYawHead = Math.max(Math.min(headYaw-rotationYaw, 40), -40);
	    rotationYaw += (headYaw-rotationYaw) / 18f;
	    ent.rotationYawHead = Math.max(Math.min(headYaw-rotationYaw, 40), -40);
	    
	    EntityRendererManager entityrenderermanager = Minecraft.getInstance().getRenderManager();
	    quaternion1.conjugate();
	    entityrenderermanager.setCameraOrientation(quaternion1);
	    entityrenderermanager.setRenderShadow(false);
	    IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
	    entityrenderermanager.renderEntityStatic(ent, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, matrixstack, irendertypebuffer$impl, 15728880);
	    irendertypebuffer$impl.finish();
	    entityrenderermanager.setRenderShadow(true);
	    ent.renderYawOffset = f;
	    ent.rotationYaw = f1;
	    ent.rotationPitch = f2;
	    ent.prevRenderYawOffset = f5;
	    ent.prevRotationYawHead = f3;
	    ent.rotationYawHead = f4;
	    RenderSystem.popMatrix();
	}

	
	//This is still used for the main menu.

    public static void drawTextLabel(String txt, int x, int y) {
        GL11.glDisable(GL11.GL_BLEND);
    	AbstractGui.fill(x, y, x + Minecraft.getInstance().fontRenderer.getStringWidth(txt) + 3, y + 11, 0x000000 + (96 << 24));
		Minecraft.getInstance().fontRenderer.drawString(txt, x+2, y+2, 0xFFFFFF);
    }
    public static void drawRightTextLabel(String txt, int x, int y) {
        GL11.glDisable(GL11.GL_BLEND);
    	int w = Minecraft.getInstance().fontRenderer.getStringWidth(txt) + 3;
    	AbstractGui.fill(x - w, y, x, y + 11, 0x000000 + (96 << 24));
		Minecraft.getInstance().fontRenderer.drawString(txt, x-w+2, y+2, 0xFFFFFF);
    }
    public static void drawCenterTextLabel(String txt, int x, int y) {
        GL11.glDisable(GL11.GL_BLEND);
    	int w = Minecraft.getInstance().fontRenderer.getStringWidth(txt) + 3;
    	AbstractGui.fill(x - w / 2, y, x + w / 2+1, y + 11, 0x000000 + (96 << 24));
		Minecraft.getInstance().fontRenderer.drawString(txt, x- w / 2+2, y+2, 0xFFFFFF);
    }


    
}
