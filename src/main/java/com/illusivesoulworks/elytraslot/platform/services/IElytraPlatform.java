package com.illusivesoulworks.elytraslot.platform.services;

import java.util.function.BiFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IElytraPlatform {

  boolean isEquipped(LivingEntity livingEntity);

  ItemStack getEquipped(LivingEntity livingEntity);

  boolean canFly(ItemStack stack, LivingEntity livingEntity);

  void processSlots(LivingEntity livingEntity, BiFunction<ItemStack, Boolean, Boolean> processor);
}
