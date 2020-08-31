package com.mercy194.main;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mercy194.gfx.SteinModelRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AdvClothing {
	
	
	private PlayerRenderer renderer;
	private int[] skinPack;
	private boolean INITIALIZED = false;
	
	private ArrayList<ClothingVariation> VARIATIONS = new ArrayList<ClothingVariation>();
	public boolean isFirstPerson;
	
	
	@SuppressWarnings("unchecked")
	public AdvClothing(String filename) {
		this.skinPack = new int[] { 0 };
		if(!filename.contentEquals("")) {
			try {
				ResourceLocation l = new ResourceLocation(AdvSkinMod.MODID, "clothes/" + filename + "/info.json");
				InputStream stream = Minecraft.getInstance().getResourceManager().getResource(l).getInputStream();
				InputStreamReader r = new InputStreamReader(stream);
				JSONObject jsonData = (JSONObject) new JSONParser().parse(r);
		
				JSONArray ELEMENTS = (JSONArray) jsonData.get("variations");
				ELEMENTS.forEach(e -> {
					JSONObject obj = (JSONObject) e;
					VARIATIONS.add(new ClothingVariation(obj.get("name").toString(), new ResourceLocation(AdvSkinMod.MODID, obj.get("texture").toString())));
				});
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		} else {
			VARIATIONS.add(new ClothingVariation("missingno", null));
		}
	}
	public AdvClothing(int[] skinPack) {
		this.skinPack = skinPack;
	}

	public void initialize() {
		//AdvSkinMod.log("Initialized Clothing Item");
		this.INITIALIZED = true;
	}
	
	public float getTransparency(AbstractClientPlayerEntity ent) {
	    float alphaChannel = 1f;
	    boolean flag1 = ent.isInvisible() && !ent.isInvisibleToPlayer(Minecraft.getInstance().player);
	    if(flag1) alphaChannel = 0.15f; else if(ent.isInvisible()) alphaChannel = 0;
	    return alphaChannel;
	}
	public void render(MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn,
			AbstractClientPlayerEntity ent, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch) {}
	

	public ArrayList<ClothingVariation> getVariations() {
		return this.VARIATIONS;
		
	}
	

	public PlayerModel<AbstractClientPlayerEntity> getEntityModel() {
		return this.getRenderer().getEntityModel();
	}

	public void setRenderer(PlayerRenderer renderer) {
		this.renderer = renderer;
	}

	public PlayerRenderer getRenderer() {
		return renderer;
	}
	

	public static void renderBox(SteinModelRenderer.ModelBox box, MatrixStack matrixEntryIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		matrixEntryIn.push(); 
		Matrix4f matrix4f = matrixEntryIn.getLast().getPositionMatrix();
	      Matrix3f matrix3f = matrixEntryIn.getLast().getNormalMatrix();

         for(SteinModelRenderer.TexturedQuad modelrenderer$texturedquad : box.quads) {
            Vector3f vector3f = modelrenderer$texturedquad.normal.copy();
            vector3f.transform(matrix3f);
            float f = vector3f.getX();
            float f1 = vector3f.getY();
            float f2 = vector3f.getZ();

            for(int i = 0; i < 4; ++i) {
            	SteinModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex = modelrenderer$texturedquad.vertexPositions[i];
            	float f3 = modelrenderer$positiontexturevertex.vector3D.getX() / 16.0F;
            	float f4 = modelrenderer$positiontexturevertex.vector3D.getY() / 16.0F;
            	float f5 = modelrenderer$positiontexturevertex.vector3D.getZ() / 16.0F;
            	Vector4f vector4f = new Vector4f(f3, f4, f5, 1.0F);
            	vector4f.transform(matrix4f);
            	bufferIn.vertex(vector4f.getX(), vector4f.getY(), vector4f.getZ(), red, green, blue, alpha, modelrenderer$positiontexturevertex.texturePositionX, modelrenderer$positiontexturevertex.texturePositionY, packedOverlayIn, packedLightIn, f, f1, f2);
            }
         }
         matrixEntryIn.pop();
	}
	public static void renderSkinnedPlane(SteinModelRenderer.SkinnedModelPlane box, MatrixStack matrixEntryIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		matrixEntryIn.push(); 
		Matrix4f matrix4f = matrixEntryIn.getLast().getPositionMatrix();
	      Matrix3f matrix3f = matrixEntryIn.getLast().getNormalMatrix();

         for(SteinModelRenderer.TexturedQuad modelrenderer$texturedquad : box.quads) {
            Vector3f vector3f = modelrenderer$texturedquad.normal.copy();
            vector3f.transform(matrix3f);
            float f = vector3f.getX();
            float f1 = vector3f.getY();
            float f2 = vector3f.getZ();

            for(int i = 0; i < 4; ++i) {
            	SteinModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex = modelrenderer$texturedquad.vertexPositions[i];
            	float f3 = modelrenderer$positiontexturevertex.vector3D.getX() / 16.0F;
            	float f4 = modelrenderer$positiontexturevertex.vector3D.getY() / 16.0F;
            	float f5 = modelrenderer$positiontexturevertex.vector3D.getZ() / 16.0F;
            	Vector4f vector4f = new Vector4f(f3, f4, f5, 1.0F);
            	vector4f.transform(matrix4f);
            	bufferIn.vertex(vector4f.getX(), vector4f.getY(), vector4f.getZ(), red, green, blue, alpha, modelrenderer$positiontexturevertex.texturePositionX, modelrenderer$positiontexturevertex.texturePositionY, packedOverlayIn, packedLightIn, f, f1, f2);
            }
         }
         matrixEntryIn.pop();
	}


	public static void renderSkinnedBox(SteinModelRenderer.SkinnedModelBox box, MatrixStack matrixEntryIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		matrixEntryIn.push(); 
		Matrix4f matrix4f = matrixEntryIn.getLast().getPositionMatrix();
	      Matrix3f matrix3f = matrixEntryIn.getLast().getNormalMatrix();

         for(SteinModelRenderer.TexturedQuad modelrenderer$texturedquad : box.quads) {
            Vector3f vector3f = modelrenderer$texturedquad.normal.copy();
            vector3f.transform(matrix3f);
            float f = vector3f.getX();
            float f1 = vector3f.getY();
            float f2 = vector3f.getZ();

            for(int i = 0; i < 4; ++i) {
            	SteinModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex = modelrenderer$texturedquad.vertexPositions[i];
            	float f3 = modelrenderer$positiontexturevertex.vector3D.getX() / 16.0F;
            	float f4 = modelrenderer$positiontexturevertex.vector3D.getY() / 16.0F;
            	float f5 = modelrenderer$positiontexturevertex.vector3D.getZ() / 16.0F;
            	Vector4f vector4f = new Vector4f(f3, f4, f5, 1.0F);
            	vector4f.transform(matrix4f);
            	bufferIn.vertex(vector4f.getX(), vector4f.getY(), vector4f.getZ(), red, green, blue, alpha, modelrenderer$positiontexturevertex.texturePositionX, modelrenderer$positiontexturevertex.texturePositionY, packedOverlayIn, packedLightIn, f, f1, f2);
            }
         }
         matrixEntryIn.pop();
	}

	public ResourceLocation getArmorResource(net.minecraft.entity.Entity entity, ItemStack stack, EquipmentSlotType slot, @javax.annotation.Nullable String type) {
	   if(stack.getItem() instanceof ArmorItem) {
		ArmorItem item = (ArmorItem)stack.getItem();
      	String texture = item.getArmorMaterial().getName();
      	String domain = "minecraft";
      	int idx = texture.indexOf(':');
      	if (idx != -1) {
    	  domain = texture.substring(0, idx);
         	texture = texture.substring(idx + 1);
      	}
      	String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, 1, type == null ? "" : String.format("_%s", type));

      	s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
      	ResourceLocation resourcelocation = null;

      	if (resourcelocation == null) {
    	  resourcelocation = new ResourceLocation(s1);
      	}
      	return resourcelocation;
	   }
	return null;
   	}
	

	public boolean shouldRender(ClothingPlayer plr) {
		for(int i = 0; i < this.skinPack.length; i++) {
			if(this.skinPack[i] == plr.skinPack) return true;
		}
		
		return false;
	}
	
   protected void unsetBrightness() {
	   
   }

   public void pushMatrix(MatrixStack m, ModelRenderer mdl, float f7) {

       float rPointX = mdl.rotationPointX;
       float rPointY = mdl.rotationPointY;
       float rPointZ = mdl.rotationPointZ;
       float rAngleX = mdl.rotateAngleX;
       float rAngleY = mdl.rotateAngleY;
       float rAngleZ = mdl.rotateAngleZ;
       
       m.push();
       
       m.translate(rPointX * 0.0625f, rPointY * 0.0625f, rPointZ * 0.0625f);
       if (rAngleZ != 0.0F) {
    	   m.rotate(new Quaternion(0f, 0f, rAngleZ, false));
       }

       if (rAngleY != 0.0F) {
    	   m.rotate(new Quaternion(0f, rAngleY, 0f, false));
       }

       if (rAngleX != 0.0F) {
    	   m.rotate(new Quaternion(rAngleX, 0f, 0f, false));
       }
   }
   
	public boolean isInitialized() {
		return INITIALIZED;
	}


	
}
