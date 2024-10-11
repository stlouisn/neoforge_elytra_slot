package dev.elytraslot.common;

import dev.elytraslot.ElytraSlotCommon;
import dev.elytraslot.ElytraSlotConstants;
import javax.annotation.Nonnull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CurioElytra implements ICurio {

  public static final AttributeModifier ELYTRA_CURIO_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(ElytraSlotConstants.MOD_ID, "elytra"),
      1.0D,
      AttributeModifier.Operation.ADD_VALUE);
  private final ItemStack stack;

  public CurioElytra(ItemStack stack) {
    this.stack = stack;
  }

  @Override
  public ItemStack getStack() {
    return this.stack;
  }

  @Override
  public void curioTick(SlotContext slotContext) {
    LivingEntity livingEntity = slotContext.entity();
    int ticks = livingEntity.getFallFlyingTicks();
    if (ticks > 0 && livingEntity.isFallFlying()) {
      this.stack.elytraFlightTick(livingEntity, ticks);
    }
  }

  @Override
  public boolean canEquip(SlotContext slotContext) {
    return ElytraSlotCommon.canEquip(slotContext.entity());
  }

  @Nonnull
  @Override
  public SoundInfo getEquipSound(SlotContext slotContext) {
    return new SoundInfo(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
  }

  @Override
  public boolean canEquipFromUse(SlotContext slotContext) {
    return true;
  }
}
