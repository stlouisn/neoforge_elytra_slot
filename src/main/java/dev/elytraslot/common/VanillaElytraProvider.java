package dev.elytraslot.common;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class VanillaElytraProvider implements IElytraProvider {

  @Override
  public boolean matches(ItemStack stack) {
    return stack.getItem() == Items.ELYTRA;
  }
}
