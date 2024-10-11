package com.illusivesoulworks.elytraslot.client;

import com.illusivesoulworks.elytraslot.ElytraSlotCommon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nonnull;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ArmorItem;

public class ElytraSlotLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

  private final ElytraModel<T> elytraModel;

  public ElytraSlotLayer(RenderLayerParent<T, M> layerParent, EntityModelSet modelSet) {
    super(layerParent);
    this.elytraModel = new ElytraModel<>(modelSet.bakeLayer(ModelLayers.ELYTRA));
  }

  public void render(@Nonnull PoseStack poseStack,
                     @Nonnull MultiBufferSource buffer,
                     int light,
                     @Nonnull T livingEntity,
                     float limbSwing,
                     float limbSwingAmount,
                     float partialTicks,
                     float ageInTicks,
                     float netHeadYaw,
                     float headPitch)
  {
    ElytraSlotCommon.getElytraRender(livingEntity).ifPresent(elytra -> {
      ResourceLocation resourcelocation;
      if (elytra.stack().getItem() instanceof ArmorItem) {
        return;
      }
      if (livingEntity instanceof AbstractClientPlayer abstractclientplayer) {
        PlayerSkin playerSkin = abstractclientplayer.getSkin();
        if (playerSkin.elytraTexture() != null) {
          resourcelocation = playerSkin.elytraTexture();
        }
        else if (elytra.useCapeTexture()) {
          if (playerSkin.capeTexture() != null && abstractclientplayer.isModelPartShown(PlayerModelPart.CAPE)) {
            resourcelocation = playerSkin.capeTexture();
          }
          else {
            resourcelocation = elytra.texture();
          }
        }
        else {
          resourcelocation = elytra.texture();
        }
      }
      else {
        resourcelocation = elytra.texture();
      }
      poseStack.pushPose();
      poseStack.translate(0.0D, 0.0D, 0.125D);
      this.getParentModel().copyPropertiesTo(this.elytraModel);
      this.elytraModel.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(resourcelocation), elytra.enchanted());
      ElytraColor color = elytra.color();
      int alpha = (int) (color.alpha() * 255), red = (int) (color.red() * 255), green = (int) (color.green() * 255), blue = (int) (color.blue() * 255), argb = (alpha << 24) | (red << 16) | (green << 8) | blue;
      this.elytraModel.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, argb);
      poseStack.popPose();
    });
  }
}
