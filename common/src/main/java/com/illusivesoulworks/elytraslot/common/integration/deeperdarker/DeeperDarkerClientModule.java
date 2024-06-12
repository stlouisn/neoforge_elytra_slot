package com.illusivesoulworks.elytraslot.common.integration.deeperdarker;

import com.illusivesoulworks.elytraslot.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DeeperDarkerClientModule {

  private static final String MOD_ID = "deeperdarker";

  public static void registerHudCallback(GuiGraphics drawContext, float tickDelta) {
    ResourceLocation texture =
        new ResourceLocation(MOD_ID, "textures/gui/soul_elytra_overlay_large.png");
    Minecraft client = Minecraft.getInstance();

    if (client.player == null) {
      return;
    }
    ItemStack itemStack = Services.ELYTRA.getEquipped(client.player);
    Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(MOD_ID, "soul_elytra"));

    if (item != Items.AIR && itemStack.is(item)) {
      float f = client.player.getCooldowns()
          .getCooldownPercent(item, Minecraft.getInstance().getFrameTime());
      drawContext.blit(texture, 5, client.getWindow().getGuiScaledHeight() - 37, 0, 0, 0, 12,
          Mth.floor(32 * f), 32, 32);
      drawContext.blit(texture, 5, client.getWindow().getGuiScaledHeight() - 37 + Mth.floor(32 * f),
          0, 12, Mth.floor(32 * f), 12, Mth.ceil(32 * (1.0f - f)), 32, 32);

      if (f == 0.0f && client.player.isFallFlying()) {

        for (BlockPos blockPos : BlockPos.betweenClosed(client.player.getOnPos(),
            client.player.getOnPos().below(5))) {

          if (client.player.level().getBlockState(blockPos).isAir()) {
            continue;
          }
          drawContext.drawString(client.font,
              Component.translatable(item.getDescriptionId() + ".boost",
                      client.options.keyShift.getTranslatedKeyMessage())
                  .setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)), 20,
              client.getWindow().getGuiScaledHeight() - 37, 0);
        }
      }
    }
  }
}
