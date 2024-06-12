/*
 * Copyright (C) 2019-2022 Illusive Soulworks
 *
 * Elytra Slot is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * Elytra Slot is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Elytra Slot. If not, see <https://www.gnu.org/licenses/>.
 */

package com.illusivesoulworks.elytraslot.common;

import com.illusivesoulworks.elytraslot.ElytraSlotCommonMod;
import java.util.UUID;
import javax.annotation.Nonnull;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CurioElytra implements ICurioItem {
  public static final AttributeModifier ELYTRA_CURIO_MODIFIER =
      new AttributeModifier(UUID.fromString("c754faef-9926-4a77-abbe-e34ef0d735aa"),
          "Elytra curio modifier", 1.0D, AttributeModifier.Operation.ADD_VALUE);

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    LivingEntity livingEntity = slotContext.entity();
    int ticks = livingEntity.getFallFlyingTicks();

    if (ticks > 0 && livingEntity.isFallFlying()) {
      stack.elytraFlightTick(livingEntity, ticks);
    }
  }

  @Override
  public boolean canEquip(SlotContext slotContext, ItemStack stack) {
    return ElytraSlotCommonMod.canEquip(slotContext.entity());
  }

  @Nonnull
  @Override
  public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
    return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
  }

  @Override
  public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
    return true;
  }
}
