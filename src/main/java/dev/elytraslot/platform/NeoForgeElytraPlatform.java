package dev.elytraslot.platform;

import dev.elytraslot.ElytraSlotCommon;
import dev.elytraslot.platform.services.IElytraPlatform;
import java.util.Map;
import java.util.function.BiFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class NeoForgeElytraPlatform implements IElytraPlatform {

  @Override
  public boolean isEquipped(final LivingEntity livingEntity) {
    return !getEquipped(livingEntity).isEmpty();
  }

  @Override
  public ItemStack getEquipped(final LivingEntity livingEntity) {
    return CuriosApi.getCuriosInventory(livingEntity).map(curios -> curios.findFirstCurio(ElytraSlotCommon.IS_ELYTRA).map(SlotResult::stack).orElse(ItemStack.EMPTY)).orElse(ItemStack.EMPTY);
  }

  @Override
  public boolean canFly(ItemStack stack, LivingEntity livingEntity) {
    return stack.canElytraFly(livingEntity);
  }

  @Override
  public void processSlots(LivingEntity livingEntity, BiFunction<ItemStack, Boolean, Boolean> processor) {
    CuriosApi.getCuriosInventory(livingEntity).ifPresent(curios -> {
      for (Map.Entry<String, ICurioStacksHandler> entry : curios.getCurios().entrySet()) {
        IDynamicStackHandler stacks = entry.getValue().getStacks();
        for (int i = 0; i < stacks.getSlots(); i++) {
          if (processor.apply(stacks.getStackInSlot(i), entry.getValue().getRenders().get(i))) {
            return;
          }
        }
      }
    });
  }
}
