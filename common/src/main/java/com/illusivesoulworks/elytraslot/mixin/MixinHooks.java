package com.illusivesoulworks.elytraslot.mixin;

import com.illusivesoulworks.elytraslot.platform.Services;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class MixinHooks {

  public static int getEnchantmentLevel(LivingEntity livingEntity, String key) {
    Holder<Enchantment> enchantment = livingEntity.registryAccess().lookup(Registries.ENCHANTMENT)
        .flatMap(enchantmentRegistryLookup -> enchantmentRegistryLookup.get(
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse(key)))).orElse(null);
    int level = 0;

    if (enchantment != null) {
      level = EnchantmentHelper.getItemEnchantmentLevel(enchantment,
          Services.ELYTRA.getEquipped(livingEntity));
    }
    return level;
  }
}
