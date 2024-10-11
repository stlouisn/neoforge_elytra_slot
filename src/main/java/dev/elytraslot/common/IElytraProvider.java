package dev.elytraslot.common;

import dev.elytraslot.client.ElytraColor;
import dev.elytraslot.client.ElytraRenderResult;
import dev.elytraslot.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IElytraProvider {

  ResourceLocation TEXTURE = ResourceLocation.parse("minecraft:textures/entity/elytra.png");
  ElytraColor COLOR = new ElytraColor(1.0F, 1.0F, 1.0F, 1.0F);

  boolean matches(ItemStack stack);

  default ElytraRenderResult getRender(ItemStack stack) {
    return new ElytraRenderResult(COLOR, TEXTURE, stack.isEnchanted(), stack, hasCapeTexture(stack));
  }

  default boolean canFly(ItemStack stack, LivingEntity livingEntity) {
    return Services.ELYTRA.canFly(stack, livingEntity);
  }

  default boolean hasCapeTexture(ItemStack ignoredStack) {
    return true;
  }
}
