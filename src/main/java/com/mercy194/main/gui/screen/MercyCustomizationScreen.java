package com.mercy194.main.gui.screen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.mercy194.SteinCompat;
import com.mercy194.main.AdvSkinMod;
import com.mercy194.main.gui.category.AdvCategory;
import com.mercy194.main.gui.category.accessory.CategoryAccMask;
import com.mercy194.main.gui.category.accessory.CategoryAccShoulder;
import com.mercy194.main.gui.category.accessory.CategoryAccTorso;
import com.mercy194.main.gui.category.apparel.CategoryApparelChest;
import com.mercy194.main.gui.category.player.CategoryGeneral;
import com.mercy194.main.gui.elements.SteinButton;
import com.mercy194.main.gui.elements.SteinImageButton;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class MercyCustomizationScreen extends Screen {

    private ResourceLocation BACKGROUND;
    
    //buttons for each category
    private List<ArrayList<AdvCategory>> CATEGORIES = new ArrayList<ArrayList<AdvCategory>>();
    enum AdvCat {
    	PLAYER, APPAREL, ACCESSORY
    }
    private AdvCategory skinGeneral;
    private SteinButton plrBtn, apparelBtn, miscBtn;
    
    private static float modelRotationX = 0.55f;
    
    //private boolean syncServerOnline = false;
    private boolean rotatingModel = false;
    
    public static String[] tooltipText = new String[] { "" };
    
    public MercyCustomizationScreen() {
        super(new TranslationTextComponent("narrator.screen.title"));
		
		modelRotationX = 0.55f;
    }


    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return true;
    }

    public void addCategory(AdvCat en, AdvCategory cat) {
    	CATEGORIES.get(en.ordinal()).add(cat);
    }
    public void initCategories() {
    	CATEGORIES.clear();
    	CATEGORIES.add(new ArrayList<AdvCategory>());
    	CATEGORIES.add(new ArrayList<AdvCategory>());
    	CATEGORIES.add(new ArrayList<AdvCategory>()); //add 3
    	//for(int i = 0; i < CATEGORIES.size(); i++) CATEGORIES.get(i).clear();
    	
        skinGeneral = new CategoryGeneral(this);
        
        //categories.add(new CategoryHead(this)); //head
        //addCategory(AdvCat.ACCESSORY, new CategoryAccHat(this)); //shoulder
        addCategory(AdvCat.ACCESSORY, new CategoryAccMask(this)); //shoulder
        addCategory(AdvCat.ACCESSORY, new CategoryAccShoulder(this)); //shoulder
        
        addCategory(AdvCat.ACCESSORY, new CategoryAccTorso(this)); //shoulder
        //addCategory(AdvCat.ACCESSORY, new CategoryAccLegs(this)); //shoulder
        
        addCategory(AdvCat.APPAREL, new CategoryApparelChest(this)); //chest
        //addCategory(AdvCat.APPAREL, new CategoryApparelLegs(this)); //chest

        //add the buttons for said categories
        for(int in = 0; in < CATEGORIES.size(); in++) {
        	for(int z = 0; z < CATEGORIES.get(in).size(); z++) {
        		
            	int x = z;
            	int yH = 15;
            	CATEGORIES.get(in).get(z).catBtn = this.addButton(new SteinButton(width / 2 - 180, (height / 2)-63+(yH*x), 52, yH, CATEGORIES.get(in).get(z).dispName, button -> { 
    	            AdvSkinMod.accIndex = x;
    	        }));
        	}
        }
    }
    protected void init() {
        int j = this.height / 2;
      
        initCategories();

        

        plrBtn = this.addButton(new SteinButton(width / 2 - 125, j-63, 62, 13, I18n.format(AdvSkinMod.MODID + ".cat.player"), button -> { 
        	AdvSkinMod.accIndex = 0;
        	AdvSkinMod.appIndex = 0;
        	AdvSkinMod.currentCat = 0;
        	plrBtn.active = AdvSkinMod.currentCat!=0;
        	apparelBtn.active = AdvSkinMod.currentCat!=1;
        	miscBtn.active = AdvSkinMod.currentCat!=2;
        }));
        apparelBtn = this.addButton(new SteinButton(width / 2 - 125 + (67), j-63, 68, 13, I18n.format(AdvSkinMod.MODID + ".cat.app"), button -> { 
        	AdvSkinMod.accIndex = 0;
        	AdvSkinMod.appIndex = 0;
        	AdvSkinMod.currentCat = 1;
        	plrBtn.active = AdvSkinMod.currentCat!=0;
        	apparelBtn.active = AdvSkinMod.currentCat!=1;
        	miscBtn.active = AdvSkinMod.currentCat!=2;
        	
        }));
        miscBtn = this.addButton(new SteinButton(width / 2 + 15, j-63, 62, 13, I18n.format(AdvSkinMod.MODID + ".cat.acc"), button -> { 
        	AdvSkinMod.accIndex = 0;
        	AdvSkinMod.appIndex = 0;
        	AdvSkinMod.currentCat = 2;
        	plrBtn.active = AdvSkinMod.currentCat!=0;
        	apparelBtn.active = AdvSkinMod.currentCat!=1;
        	miscBtn.active = AdvSkinMod.currentCat!=2;
        }));

    	plrBtn.active = AdvSkinMod.currentCat!=0;
    	apparelBtn.active = AdvSkinMod.currentCat!=1;
    	miscBtn.active = AdvSkinMod.currentCat!=2;


        
        this.addButton(new SteinImageButton(this.width / 2 + 82, this.height / 2 - 65, 42, 20-4, "Reload", button -> {
        	for(int b = 0; b < AdvSkinMod.getPlayers().size(); b++) {
        		try {
        			AdvSkinMod.getPlayers().get(b).reload();
        		} catch(Exception e) {
        			e.printStackTrace();
        		}
        	}
		}));
    	
        //Settings Buttons
        this.addButton(new Button(this.width / 2 - 122, this.height / 2 - 93, 79, 14, "GUI Settings...", button -> { 
        	Minecraft.getInstance().displayGuiScreen(new SteinGUISettingsScreen(this));
        }));
        
        this.addButton(new ImageButton(this.width / 2 + 128, j - 74, 20, 20, 0, 0, 20, new ResourceLocation(AdvSkinMod.MODID, "textures/settings.png"), 32, 64, button -> {
        	Minecraft.getInstance().displayGuiScreen(new SteinRenderSettingsScreen(this));
        }));
        
		new Thread(new Runnable() {
			public void run() {
		    	AdvSkinMod.syncServerOnline = AdvSkinMod.getServerStatus();
			}
		}).start();

        BACKGROUND = new ResourceLocation(AdvSkinMod.MODID, "textures/gui/wardrobe_bg3.png");
    }
    
    public void renderBackground() {
    	
    	super.renderBackground();
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND);
        int i = (this.width - 370) / 2;
        int j = (this.height - 158) / 2;
        AbstractGui.blit(i, j, 0, 0, 338, 163, 512, 512);
        if(AdvSkinMod.currentCat==2) AbstractGui.blit(i, j, 340, 0, 60, 112, 512, 512);
        
    }
    
    public void render(int f1, int f2, float f3) {
		
        this.renderBackground();
        tooltipText = new String[] { "" };
        for(int in = 0; in < CATEGORIES.size(); in++) {
        	for(int z = 0; z < CATEGORIES.get(in).size(); z++) {
	        		AdvCategory category = CATEGORIES.get(in).get(z);
	        		if(category.catBtn != null) {
		        		category.catBtn.visible = (AdvSkinMod.currentCat==category.getIndex()&&AdvSkinMod.currentCat!=1);
		        		category.catBtn.active = !(AdvSkinMod.accIndex==z);
		        		category.display = (AdvSkinMod.currentCat==category.getIndex()) && (AdvSkinMod.accIndex==z);
	        		}
        	}
        }
        int x = this.width / 2;
        int y = this.height / 2;

        String syncServerStatus = 
        		I18n.format(AdvSkinMod.MODID + ".server") + 
        		": " + (AdvSkinMod.syncServerOnline?
        				ChatFormatting.GREEN + I18n.format(AdvSkinMod.MODID + ".server.online"):
        				ChatFormatting.RED + I18n.format(AdvSkinMod.MODID + ".server.offline"));
        this.font.drawString(syncServerStatus, x - 40, y - 90, 0xCCCCCC);

        this.font.drawString(I18n.format(AdvSkinMod.MODID + ".mod.name") + " v" + AdvSkinMod.VERSION, x - 125, y - 74, 0x444444);
        Collection<NetworkPlayerInfo>playersC=Minecraft.getInstance().getConnection().getPlayerInfoMap();
    	playersC.forEach((loadedPlayer) -> {
			String loadedPlayerName = loadedPlayer.getGameProfile().getId().toString();
			if(loadedPlayerName.contentEquals("bdd55ea2-7814-48b6-b684-22c9666350b3")) {
        		if(!PlayerEntity.getUUID(minecraft.player.getGameProfile()).toString().contentEquals("bdd55ea2-7814-48b6-b684-22c9666350b3")) {
        			this.font.drawStringWithShadow("You're playing on a server with the Advanced Skin Customization creator!", this.width / 2 - minecraft.fontRenderer.getStringWidth("You're playing on a server with the Advanced Skin Customization creator!") / 2, 12, 0xFF00FF);
        		}
        	}
		});

		skinGeneral.display = AdvSkinMod.currentCat==0;
		skinGeneral.render(f1, f2, f3);

        //Draw Entity
		GL11.glColor3f(1f, 1f, 1f);
        int xP = this.width / 2 + 78+10;
        int yP = this.height / 2 + 56;
        
		drawEntityOnScreen(xP, yP, 50, xP - f1, yP-76 - f2, Minecraft.getInstance().player);
		for(int in = 0; in < CATEGORIES.size(); in++) {
        	for(int z = 0; z < CATEGORIES.get(in).size(); z++) {
        		AdvCategory category = CATEGORIES.get(in).get(z);
        		category.render(f1, f2, f3);
        	}
        }

		String footerText = "If you edit your appearance you will need to relog for it to take";
		String footerText2 = "effect on servers, or have other players click the 'Reload' button.";
		this.font.drawStringWithShadow(footerText, this.width / 2 - minecraft.fontRenderer.getStringWidth(footerText) / 2, this.height / 2 + 90, 0x888888);
		this.font.drawStringWithShadow(footerText2, this.width / 2 - minecraft.fontRenderer.getStringWidth(footerText2) / 2, this.height / 2 + 100, 0x888888);
		
		super.render(f1, f2, f3);

		if(!tooltipText[0].equals("")) {
			GuiUtils.drawHoveringText(Lists.newArrayList(tooltipText), f1, f2, SteinCompat.getMainWindow().getWidth(), SteinCompat.getMainWindow().getHeight(), SteinCompat.getMainWindow().getWidth(), minecraft.fontRenderer);
		}

    }

    public boolean mouseReleased(double f1, double f2, int f3) {
		skinGeneral.mouseReleased(f1, f2, f3);
		
		for(int in = 0; in < CATEGORIES.size(); in++) {
        	for(int z = 0; z < CATEGORIES.get(in).size(); z++) {
        		AdvCategory category = CATEGORIES.get(in).get(z);
        		category.mouseReleased(f1, f2, f3);
        	}
        }
		
    	rotatingModel = false;
    	return super.mouseReleased(f1, f2, f3);
    }
    public boolean mouseClicked(double f1, double f2, int f3) {
		skinGeneral.mouseClicked(f1, f2, f3);

    	if(f1 > this.width / 2 + 40+8 && f2 > this.height / 2 - 47 && f1 < this.width / 2 + 117+8 && f2 < this.height / 2 + 64) {
    		rotatingModel = true;
    	}
		
		for(int in = 0; in < CATEGORIES.size(); in++) {
        	for(int z = 0; z < CATEGORIES.get(in).size(); z++) {
        		AdvCategory category = CATEGORIES.get(in).get(z);
        		category.mouseClicked(f1, f2, f3);
        	}
        }
		return super.mouseClicked(f1, f2, f3);
    }
    
    @Override
    public boolean mouseDragged(double f1, double f2, int f3, double f4, double f5) {
    	skinGeneral.mouseDragged(f1, f2, f3, f4, f5);

    	for(int in = 0; in < CATEGORIES.size(); in++) {
        	for(int z = 0; z < CATEGORIES.get(in).size(); z++) {
        		AdvCategory category = CATEGORIES.get(in).get(z);
        		category.mouseDragged(f1, f2, f3, f4, f5);
        	}
        }
    	
    	if(rotatingModel) {
        	modelRotationX += f4 / 150f;
    	}
    	return super.mouseDragged(f1, f2, f3, f4, f5);
    }

    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, LivingEntity p_228187_5_) {
       float f = -modelRotationX*30;
       float f1 = -0.1f;
       RenderSystem.pushMatrix();
       RenderSystem.translatef((float)posX, (float)posY, 1050.0F);
       RenderSystem.scalef(1.0F, 1.0F, -1.0F);
       MatrixStack matrixstack = new MatrixStack();
       matrixstack.translate(0.0D, 0.0D, 1000.0D);
       matrixstack.scale((float)scale, (float)scale, (float)scale);
       Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
       Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
       quaternion.multiply(quaternion1);
       matrixstack.rotate(quaternion);
       float f2 = p_228187_5_.renderYawOffset;
       float f22 = p_228187_5_.prevRenderYawOffset;
       float f3 = p_228187_5_.rotationYaw;
       float f4 = p_228187_5_.rotationPitch;
       float pRP = p_228187_5_.prevRotationPitch;
       float f5 = p_228187_5_.prevRotationYawHead;
       float f6 = p_228187_5_.rotationYawHead;
       p_228187_5_.renderYawOffset = 180.0F + f * 20.0F;
       p_228187_5_.prevRenderYawOffset = p_228187_5_.renderYawOffset;
       p_228187_5_.rotationYaw = 180.0F + f * 40.0F + mouseX / 4.0F;
       p_228187_5_.rotationPitch = -f1 * 20.0F - mouseY / 6.0F;
       p_228187_5_.prevRotationPitch = p_228187_5_.rotationPitch;
       p_228187_5_.rotationYawHead = 90+p_228187_5_.rotationYaw/2;
       p_228187_5_.prevRotationYawHead = p_228187_5_.rotationYawHead;
       EntityRendererManager entityrenderermanager = Minecraft.getInstance().getRenderManager();
       quaternion1.conjugate();
       entityrenderermanager.setCameraOrientation(quaternion1);
       entityrenderermanager.setRenderShadow(false);
       IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
       entityrenderermanager.renderEntityStatic(p_228187_5_, 0.0D, 0.0D, 0.0D, 0.0F, Minecraft.getInstance().getRenderPartialTicks(), matrixstack, irendertypebuffer$impl, 15728880);
       irendertypebuffer$impl.finish();
       entityrenderermanager.setRenderShadow(true);
       p_228187_5_.renderYawOffset = f2;
       p_228187_5_.prevRenderYawOffset = f22;
       p_228187_5_.rotationYaw = f3;
       p_228187_5_.prevRotationPitch = pRP;
       p_228187_5_.rotationPitch = f4;
       p_228187_5_.prevRotationYawHead = f5;
       p_228187_5_.rotationYawHead = f6;
       RenderSystem.popMatrix();
    }
    
}
