package com.mercy194.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.electronwill.nightconfig.core.Config;
import com.mercy194.main.gui.screen.AdvSkinScreen;
import com.mercy194.main.gui.screen.MercyCustomizationScreen;
import com.mercy194.main.proxy.AdvSkinClient;
import com.mercy194.render.SteinFirstPersonRenderer;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.ISound.AttenuationType;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseClickedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseDragEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputEvent.MouseInputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import net.minecraftforge.fml.network.NetworkRegistry;

@OnlyIn(Dist.CLIENT)
public class SteinEventHandler {

	List<PlayerEntity> prePlayers = new ArrayList<PlayerEntity>();
	
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent evt) {
        if(AdvSkinClient.toggleEditGUI.isPressed()) {
            Minecraft.getInstance().displayGuiScreen(new MercyCustomizationScreen());
        }
        if(AdvSkinClient.toggleFirstPerson.isPressed()) {
        	CFG.generic.setParameter("fp_enabled", !CFG.generic.getBool("fp_enabled"));
        	CFG.generic.save();
        }
    }

    private boolean justShownUpdate = false;
    private int showUpdateTimer = 0;

    
    @SubscribeEvent
    public void preRenderPlayer(RenderPlayerEvent.Pre evt) {
    	ClothingPlayer plr = AdvSkinMod.getPlayerByName(PlayerEntity.getUUID(evt.getPlayer().getGameProfile()).toString());
        if(plr == null) return;
    	if(!(evt.getRenderer() instanceof SteinFirstPersonRenderer)) {
	    	evt.getMatrixStack().push();
	    	float scale = plr.getAccessoryFloat("plr_height", 0.7f);
	    	
	    	float mScale = 0.5f + (0.36f + (scale / 5f));
	    	evt.getMatrixStack().scale(mScale, mScale, mScale);
        }
       	
    }

    @SubscribeEvent
    public void postRenderPlayer(RenderPlayerEvent.Post evt) {
    	ClothingPlayer plr = AdvSkinMod.getPlayerByName(PlayerEntity.getUUID(evt.getPlayer().getGameProfile()).toString());
        if(plr == null) return;
    	evt.getMatrixStack().pop();
    }
    
    private long timer = System.currentTimeMillis();
    
	@SubscribeEvent
	public void clientTickEvent(TickEvent.ClientTickEvent evt) {
		if(justShownUpdate && showUpdateTimer < 400) {
			showUpdateTimer++;
		}

		World w = Minecraft.getInstance().world;
		//System.out.println()
    	if(w != null) {
    		List<PlayerEntity> plrs = new ArrayList<PlayerEntity>(w.getPlayers());
    		if(!prePlayers.equals(plrs)) {
    			if(prePlayers.size() > plrs.size()) {
        			for(int i = 0; i < prePlayers.size(); i++) {
        				if(!plrs.contains(prePlayers.get(i))) {
        	    			if(!prePlayers.get(i).getUniqueID().equals(Minecraft.getInstance().player.getUniqueID())) {
    	    	    			AdvSkinMod.getPlayers().remove(AdvSkinMod.getPlayerByName(PlayerEntity.getUUID(prePlayers.get(i).getGameProfile()).toString()));
    	    				}
        				}
        			}
    			}
    			prePlayers = plrs;
    		}
    	} else {
    		if(AdvSkinMod.getPlayers().size() != 0) {
    			AdvSkinMod.getPlayers().clear(); //clear players in menu
    		}
    	}
    	/*
		if(System.currentTimeMillis() - timer >= 1000) {
			timer += 1000;
			System.out.println("Render Time: " + AdvSkinMod.millisRender + "ms");
			AdvSkinMod.millisRender = 0;
		}*/
	}
	

	@SubscribeEvent
	public void onRenderGUI(GuiScreenEvent.DrawScreenEvent.Post evt) {
		/*
		if(CFG.generic.getBool("showUpdateChecker")) {
			if(AdvSkinMod.UPDATE_OBJ != null) {
				if(AdvSkinMod.UPDATE_OBJ.get("currentVersion15") != null && evt.getGui() instanceof MainMenuScreen) {
					if(!AdvSkinMod.VERSION.equals(AdvSkinMod.UPDATE_OBJ.get("currentVersion15").toString())) {
						if(showUpdateTimer < 400) {
							AdvSkinClient.drawTextLabel("Advanced Skin Customization", 2, 2);
							AdvSkinClient.drawTextLabel("Update Available! (v" + AdvSkinMod.UPDATE_OBJ.get("currentVersion15").toString() + ")", 2, 13);
						}
						if(!justShownUpdate) {
							justShownUpdate = true;
						}
					}
				} else {
					justShownUpdate = false;
					showUpdateTimer = 0;
				}
			}
		}
		*/
	}
    
    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent evt) {
    	if(evt.getEntity() instanceof PlayerEntity) {
    		PlayerEntity plr = (PlayerEntity) evt.getEntity();
    		String playerName = PlayerEntity.getUUID(plr.getGameProfile()).toString();
    		AdvSkinMod.loadSQLForPlayer(playerName);
    		
    	}
    	
    }
    
    public boolean addedLayer = false;
    public ResourceLocation getSkinResourceLocation(GameProfile gProf) {
    	if (gProf != null) {
    		final SkinManager manager = Minecraft.getInstance().getSkinManager();
    		Map<Type, MinecraftProfileTexture> map = manager.loadSkinFromCache(gProf);

    		if (map.containsKey(Type.SKIN)) {
    			final MinecraftProfileTexture skin = map.get(Type.SKIN);
    			return manager.loadSkin(skin, Type.SKIN);
    		} else {
    			UUID uuid = PlayerEntity.getUUID(gProf);
    			return DefaultPlayerSkin.getDefaultSkin(uuid);
    		}
    	}

    	return null;
    }
     
    
    @SubscribeEvent
    public void onPlayerRenderPre(RenderPlayerEvent.Pre evt) {
        for(int adv = 0; adv < AdvSkinMod.CLOTHING.size(); adv++) {
        	AdvSkinMod.CLOTHING.get(adv).setRenderer(evt.getRenderer());
        	if(!AdvSkinMod.CLOTHING.get(adv).isInitialized()) {
        		AdvSkinMod.CLOTHING.get(adv).initialize();
        	}
        }
    }
    
    @SubscribeEvent
    public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(AdvSkinSounds.maleDamage);
        event.getRegistry().register(AdvSkinSounds.femaleDamage);
        event.getRegistry().register(AdvSkinSounds.femaleDamage2);
        
    }
    
    /*
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onHurtEvent(LivingHurtEvent evt) {
        if(evt.getEntity().world.isRemote && Minecraft.getInstance().isSingleplayer()) return;
    	
	    	if(evt.getEntity() instanceof PlayerEntity) {
		    	PlayerEntity pEntity = (PlayerEntity) evt.getEntity();
				ClothingPlayer plr = AdvSkinMod.getPlayerByName(PlayerEntity.getUUID(pEntity.getGameProfile()).toString());
				SoundEvent sEvt = AdvSkinSounds.maleDamage;
				
				if(!plr.gender) {
					Random r = new Random();
					if(r.nextInt(10) < 5) {
						sEvt = AdvSkinSounds.femaleDamage;
					} else {
						sEvt = AdvSkinSounds.femaleDamage2;
					}
					sEvt = AdvSkinSounds.femaleDamage;
				}
				System.out.println("Get Oof'd. HAHAH");
			    Minecraft.getInstance().world.playSound(pEntity.getPosition(), sEvt, pEntity.getSoundCategory(), 1.0F, 1.0F, true);
	    	}
    }
    */
    
    
    private float rotationYawV2 = 0;
    public boolean addAnotherLayer = false;
    private boolean hideFirstPersonBody = false;
	private boolean forceNaturalRotation = false;

    @SubscribeEvent
    public void onPlayerEvent(TickEvent.PlayerTickEvent evt) {
    	
    	
    	if(Minecraft.getInstance().player == evt.player) {
			hideFirstPersonBody = false;
			forceNaturalRotation = false;
	    	if(evt.player.getHeldItemMainhand().getItem() instanceof FilledMapItem) hideFirstPersonBody = true;
	    	if((evt.player.getHeldItemMainhand().getItem() instanceof BowItem) || (evt.player.getHeldItemOffhand().getItem() instanceof BowItem) ||
	    			(evt.player.getHeldItemMainhand().getItem() instanceof CrossbowItem) || (evt.player.getHeldItemOffhand().getItem() instanceof CrossbowItem)) {
	    		hideFirstPersonBody = true;
	    	}
	    	if(evt.player.getRidingEntity() != null) {
	    		hideFirstPersonBody = true;
	    	}
    	}
    	//Iterable<Entity> ENTITIES = Minecraft.getInstance().world.getAllEntities();
    	//RayTraceResult ray = Minecraft.getInstance().objectMouseOver;
    	
    	//Entity ent = Minecraft.getInstance().pointedEntity;
    	//((AbstractClientPlayerEntity) ent).getLocationSkin()
    }
    
    
    
    private int fpsTimer = 0;
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent evt) {
    	/*
	    if(CFG.generic.getBool("fp_enabled") && !hideFirstPersonBody) {
	    	Minecraft m = Minecraft.getInstance();
		    if(Minecraft.getInstance().gameSettings.thirdPersonView == 0) {
		    	PlayerEntity ent = Minecraft.getInstance().player;
		  	   	float f = ent.renderYawOffset;
		  	   	float f1 = ent.rotationYaw;
				float f2 = ent.rotationPitch;
				float f3 = ent.prevRotationYawHead;
				float f4 = ent.rotationYawHead;
				float f5 = ent.prevRenderYawOffset;
				EntityRendererManager rMgr = Minecraft.getInstance().getRenderManager();
				GL11.glPushMatrix();
				
				float fov2 = Float.valueOf(CFG.generic.getParameter("fp_fov").toString());
				float fovOffset = (float)Math.floor(-20+fov2*40);
				
				RenderSystem.matrixMode(5889);
				RenderSystem.loadIdentity();
				RenderSystem.multMatrix(Matrix4f.perspective(Minecraft.getInstance().gameSettings.fov + fovOffset, (float)m.getMainWindow().getFramebufferWidth() / (float)m.getMainWindow().getFramebufferHeight(), 0.05F, 500 * MathHelper.SQRT_2));
				RenderSystem.matrixMode(5888);
				RenderSystem.loadIdentity();
			    
				RenderSystem.rotatef(Minecraft.getInstance().player.getPitch(Minecraft.getInstance().getRenderPartialTicks()), 1, 0, 0);
			    
			    if(CFG.generic.getBool("fp_natural") && !forceNaturalRotation) {
				    float tmpYaw = Minecraft.getInstance().player.getYaw(Minecraft.getInstance().getRenderPartialTicks());
				    float fYaw = (tmpYaw+180) - (ent.prevRenderYawOffset + (ent.renderYawOffset - ent.prevRenderYawOffset) * Minecraft.getInstance().getRenderPartialTicks());
				    RenderSystem.rotatef(fYaw, 0, 1, 0);
			    } else {
			    	RenderSystem.rotatef(180f, 0, 1, 0);
			    }
				
			    RenderSystem.translatef(0, 0, -0.35f);
				float fov = (float) Minecraft.getInstance().gameSettings.fov * -(Minecraft.getInstance().player.getFovModifier()-2);
				//80 = 0.1
				//110 = 0.25
				//30 = -0.55
				float finalFov = (fov / 110) - (0.75f * (fov/110));
				
				RenderSystem.translatef(0, finalFov, 0);
				float headYaw = 0;
				ent.prevRenderYawOffset = Math.max(Math.min(headYaw-rotationYawV2, 40), -40);
				rotationYawV2 += (headYaw-rotationYawV2) / 8f;
				ent.renderYawOffset = Math.max(Math.min(headYaw-rotationYawV2, 40), -40);
				ent.rotationYawHead = 0;
				ent.prevRotationYawHead = ent.rotationYawHead;

				if(Minecraft.getInstance().gameSettings.viewBobbing) {
					applyBobbing(Minecraft.getInstance().getRenderPartialTicks());
				}
				
				String skinType = ((AbstractClientPlayerEntity) ent).getSkinType();
				if(fpsTimer < 20) { //wait 20 frames because it doesn't detect it right away?
					boolean useSmallArms = skinType.contains("default")?false:true;
					AdvSkinClient.fpsRenderer = new SteinPlayerRenderer(rMgr, useSmallArms);
					fpsTimer++;
				}
				if(AdvSkinClient.fpsRenderer != null && 
						ent.getSwimAnimation(Minecraft.getInstance().getRenderPartialTicks()) == 0 && 
						!ent.isElytraFlying()) {
					GL11.glColor3f(1, 1, 1);
					

				    IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();

				    MatrixStack matrixstack = new MatrixStack();
				    
					ClothingPlayer plr = AdvSkinMod.getPlayerByName(PlayerEntity.getUUID(Minecraft.getInstance().player.getGameProfile()).toString());
				    matrixstack.translate(0.0D, (ent.isCrouching()?-1.5f:-1.8f),0.0D);
				    
				    GL11.glColor4f(1F, 1F, 1F, 1F);
				    
				    AdvSkinClient.fpsRenderer.getEntityModel().bipedHead.showModel = false;
				    AdvSkinClient.fpsRenderer.getEntityModel().bipedHeadwear.showModel = false;
				    
				    AdvSkinClient.fpsRenderer.render(Minecraft.getInstance().player, 0, Minecraft.getInstance().getRenderPartialTicks(), matrixstack, irendertypebuffer$impl, AdvSkinClient.fpsRenderer.getPackedLight((AbstractClientPlayerEntity) ent, Minecraft.getInstance().getRenderPartialTicks()));

				    irendertypebuffer$impl.finish();
				    
				}
				  	
				//reset values
				ent.renderYawOffset = f;
				ent.rotationYaw = f1;
				ent.rotationPitch = f2;
				ent.prevRenderYawOffset = f5;
				ent.prevRotationYawHead = f3;
				ent.rotationYawHead = f4;
				GL11.glPopMatrix();
		    }
		    	
	    }*/
    	
    }
    @SubscribeEvent
    public void onRenderSpecificHand(RenderHandEvent evt) {
    	
    	if(evt.getHand() == Hand.OFF_HAND) {
    		if(!CFG.generic.getBool("showOffHand")) {
	    		if(evt.getItemStack().isItemEqual(new ItemStack(Items.SHIELD, 1))) evt.setCanceled(true);
    		}
    	}
    	
    	
	    if(CFG.generic.getBool("fp_enabled") && !hideFirstPersonBody) {
	    	Minecraft m = Minecraft.getInstance();
		    if(Minecraft.getInstance().gameSettings.thirdPersonView == 0) {
		    	PlayerEntity ent = Minecraft.getInstance().player;
		  	   	float f = ent.renderYawOffset;
		  	   	float f1 = ent.rotationYaw;
				float f2 = ent.rotationPitch;
				float f3 = ent.prevRotationYawHead;
				float f4 = ent.rotationYawHead;
				float f5 = ent.prevRenderYawOffset;
				EntityRendererManager rMgr = Minecraft.getInstance().getRenderManager();

				GL11.glPushMatrix();
				
				float fov2 = Float.valueOf(CFG.generic.getParameter("fp_fov").toString());
				float fovOffset = (float)Math.floor(-20+fov2*40);
				
				GL11.glMatrixMode(5889);
				GL11.glLoadIdentity();
				RenderSystem.multMatrix(Matrix4f.perspective(Minecraft.getInstance().gameSettings.fov + fovOffset, (float)m.getMainWindow().getFramebufferWidth() / (float)m.getMainWindow().getFramebufferHeight(), 0.05F, 500 * MathHelper.SQRT_2));
				GL11.glMatrixMode(5888);
				GL11.glLoadIdentity();
			    
				GL11.glRotatef(Minecraft.getInstance().player.getPitch(evt.getPartialTicks()), 1, 0, 0);
			    
			    if(CFG.generic.getBool("fp_natural") && !forceNaturalRotation) {
				    float tmpYaw = Minecraft.getInstance().player.getYaw(evt.getPartialTicks());
				    float fYaw = (tmpYaw+180) - (ent.prevRenderYawOffset + (ent.renderYawOffset - ent.prevRenderYawOffset) * evt.getPartialTicks());
				    GL11.glRotatef(fYaw, 0, 1, 0);
			    } else {
			    	GL11.glRotatef(180f, 0, 1, 0);
			    }
				
			    GL11.glTranslatef(0, 0, -0.35f);
				float fov = (float) Minecraft.getInstance().gameSettings.fov;
				
				GL11.glTranslatef(0, (fov / 110) - (0.75f * (fov/110)), 0);
				float headYaw = 0;
				ent.prevRenderYawOffset = Math.max(Math.min(headYaw-rotationYawV2, 40), -40);
				rotationYawV2 += (headYaw-rotationYawV2) / 8f;
				ent.renderYawOffset = Math.max(Math.min(headYaw-rotationYawV2, 40), -40);
				ent.rotationYawHead = 0;
				ent.prevRotationYawHead = ent.rotationYawHead;

				if(Minecraft.getInstance().gameSettings.viewBobbing) {
					applyBobbing(evt.getPartialTicks());
				}
				
				String skinType = ((AbstractClientPlayerEntity) ent).getSkinType();
				if(fpsTimer < 20) { //wait 20 frames because it doesn't detect it right away?
					boolean useSmallArms = skinType.contains("default")?false:true;
					AdvSkinClient.fpsRenderer = new SteinFirstPersonRenderer(rMgr, useSmallArms);
					fpsTimer++;
				}
				
				if(AdvSkinClient.fpsRenderer != null && 
						ent.getSwimAnimation(evt.getPartialTicks()) == 0 && 
						!ent.isElytraFlying()) {
					GL11.glColor3f(1, 1, 1);
					

				    IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();

				    MatrixStack matrixstack = new MatrixStack();
				    matrixstack.translate(0.0D, (ent.isCrouching()?-1.5f:-1.8f),0.0D);
				    AdvSkinClient.fpsRenderer.render(Minecraft.getInstance().player, 0, evt.getPartialTicks(), matrixstack, irendertypebuffer$impl, AdvSkinClient.fpsRenderer.getPackedLight((AbstractClientPlayerEntity) ent, evt.getPartialTicks()));
				    irendertypebuffer$impl.finish();
				    
				}
				  	
				//reset values
				ent.renderYawOffset = f;
				ent.rotationYaw = f1;
				ent.rotationPitch = f2;
				ent.prevRenderYawOffset = f5;
				ent.prevRotationYawHead = f3;
				ent.rotationYawHead = f4;
				GL11.glPopMatrix();
		    }
		    	
			evt.setCanceled(true);
	    }
		
    }

    private void applyBobbing(float partialTicks) {
    	Minecraft mc = Minecraft.getInstance();
    	if (mc.getRenderViewEntity() instanceof PlayerEntity) {
    		PlayerEntity playerentity = (PlayerEntity)mc.getRenderViewEntity();
    		float f = playerentity.distanceWalkedModified - playerentity.prevDistanceWalkedModified;
    		float f1 = -(playerentity.distanceWalkedModified + f * partialTicks);
    		float f2 = MathHelper.lerp(partialTicks, playerentity.prevCameraYaw, playerentity.cameraYaw);
    		GL11.glTranslatef(MathHelper.sin(f1 * (float)Math.PI) * f2 * 0.1F, -Math.abs(MathHelper.cos(f1 * (float)Math.PI) * f2) / 5, 0.0F);
    		GL11.glRotatef(MathHelper.sin(f1 * (float)Math.PI) * f2 * 3.0F, 0.0F, 0.0F, 1.0F);
    		GL11.glRotatef(Math.abs(MathHelper.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F, 1.0F, 0.0F, 0.0F);
    	}
    }

    @SubscribeEvent
    public void onMouseEvent(MouseInputEvent evt) {
    	if(evt.getAction() == 1) {
    		AdvSkinMod.MOUSE_PRESSED = true;
    	} else if(evt.getAction() == 0) {
    		AdvSkinMod.MOUSE_PRESSED = false;
    	}
    }
    

    public static AdvSkinScreen advScreen = new AdvSkinScreen();


	public static void resetGUI() {
		CFG.gui.delete();
		CFG.guiBiome.delete();
		CFG.guiCoordinates.delete();
		CFG.guiFPS.delete();
		CFG.guiPaperDoll.delete();
		CFG.guiTime.delete();
        CFG.createGUI();
        
		advScreen = new AdvSkinScreen();
	}
	
    @SubscribeEvent
    public void onClickEvent(MouseClickedEvent.Pre evt) {
    	advScreen.mouseClicked(evt.getMouseX(), evt.getMouseY(), evt.getButton());
    }

    @SubscribeEvent
    public void onDragEvent(MouseDragEvent.Pre evt) {
    	advScreen.mouseDragged(evt.getMouseX(), evt.getMouseY(), evt.getDragX(), evt.getDragY(), evt.getMouseButton());
    }
    
    @SubscribeEvent
    public void mouseInputEvent(InputEvent.MouseInputEvent evt) {
    	if(evt.getAction() == GLFW.GLFW_PRESS) {
    		advScreen.mousePressed(Minecraft.getInstance().mouseHelper.getMouseX(), Minecraft.getInstance().mouseHelper.getMouseY(), evt.getButton());
    	} else if(evt.getAction() == GLFW.GLFW_RELEASE) {
    		advScreen.mouseReleased(Minecraft.getInstance().mouseHelper.getMouseX(), Minecraft.getInstance().mouseHelper.getMouseY(), evt.getButton());
    	}
    }
    
    @SubscribeEvent
    public void onRenderGUIPost(RenderGameOverlayEvent.Post evt) {
    	if(evt.getType() == ElementType.CROSSHAIRS) {
    		if(!Minecraft.getInstance().gameSettings.showDebugInfo) {
    			SteinEventHandler.advScreen.render(evt.getPartialTicks());
    		}
		}
    }
}
