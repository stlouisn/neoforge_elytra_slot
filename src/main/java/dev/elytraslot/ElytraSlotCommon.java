package dev.elytraslot;

import dev.elytraslot.client.ElytraRenderResult;
import dev.elytraslot.common.IElytraProvider;
import dev.elytraslot.common.VanillaElytraProvider;
import dev.elytraslot.platform.Services;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ElytraSlotCommon {

  public static final Predicate<ItemStack> IS_ELYTRA = new Predicate<>() {
    @Override
    public boolean test(ItemStack stack) {
      for (IElytraProvider provider : PROVIDERS) {
        if (provider.matches(stack)) {
          return true;
        }
      }
      return false;
    }
  };
  private static final List<IElytraProvider> PROVIDERS = new LinkedList<>();

  public static void init() {
    PROVIDERS.add(new VanillaElytraProvider());
  }

  public static Optional<ElytraRenderResult> getElytraRender(final LivingEntity livingEntity) {
    AtomicReference<ElytraRenderResult> result = new AtomicReference<>();
    Services.ELYTRA.processSlots(livingEntity, (stack, render) -> {
      if (!stack.isEmpty() && render) {
        for (IElytraProvider provider : PROVIDERS) {
          if (provider.matches(stack)) {
            result.set(provider.getRender(stack));
            return true;
          }
        }
      }
      return false;
    });
    return Optional.ofNullable(result.get());
  }

  public static boolean canFly(final LivingEntity livingEntity) {
    AtomicBoolean result = new AtomicBoolean();
    Services.ELYTRA.processSlots(livingEntity, (stack, render) -> {
      if (!stack.isEmpty()) {
        for (IElytraProvider provider : PROVIDERS) {
          if (provider.matches(stack)) {
            result.set(provider.canFly(stack, livingEntity));
            return true;
          }
        }
      }
      return false;
    });
    return result.get();
  }

  public static boolean isEquipped(final LivingEntity livingEntity) {
    return Services.ELYTRA.isEquipped(livingEntity);
  }

  public static boolean canEquip(final LivingEntity livingEntity) {
    return !IS_ELYTRA.test(livingEntity.getItemBySlot(EquipmentSlot.CHEST));
  }
}