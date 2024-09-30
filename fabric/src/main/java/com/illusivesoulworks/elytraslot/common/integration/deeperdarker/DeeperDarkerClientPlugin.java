package com.illusivesoulworks.elytraslot.common.integration.deeperdarker;

import com.illusivesoulworks.elytraslot.platform.Services;
import com.kyanite.deeperdarker.DeeperDarker;
import com.kyanite.deeperdarker.client.Keybinds;
import com.kyanite.deeperdarker.content.DDItems;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class DeeperDarkerClientPlugin {

  public static void setup() {
    ClientTickEvents.START_WORLD_TICK.register(world -> {
      Minecraft client = Minecraft.getInstance();

      if (client.player == null) {
        return;
      }
      ItemStack itemStack = Services.ELYTRA.getEquipped(client.player);

      if (itemStack.is(DDItems.SOUL_ELYTRA) && client.player.getCooldowns()
          .getCooldownPercent(DDItems.SOUL_ELYTRA,
              (float) (Minecraft.getInstance().getFrameTimeNs() / 1000000000.0)) == 0 &&
          client.player.isFallFlying() && Keybinds.BOOST.isDown()) {
        ClientPlayNetworking.send(SoulElytraBoostPayload.INSTANCE);
      }
    });
    HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
      ResourceLocation texture = DeeperDarker.rl("textures/gui/soul_elytra_overlay_large.png");
      Minecraft client = Minecraft.getInstance();

      if (client.player == null || DeeperDarker.CONFIG.server.soulElytraCooldown() == -1) {
        return;
      }
      ItemStack itemStack = Services.ELYTRA.getEquipped(client.player);

      if (itemStack.is(DDItems.SOUL_ELYTRA)) {
        float f = client.player.getCooldowns().getCooldownPercent(DDItems.SOUL_ELYTRA,
            (float) (Minecraft.getInstance().getFrameTimeNs() / 1000000000.0));
        drawContext.blit(texture, 5, client.getWindow().getGuiScaledHeight() - 37, 0, 0, 0, 12,
            Mth.floor(32 * f), 32, 32);
        drawContext.blit(texture, 5,
            client.getWindow().getGuiScaledHeight() - 37 + Mth.floor(32 * f), 0, 12,
            Mth.floor(32 * f), 12, Mth.ceil(32 * (1.0f - f)), 32, 32);

        if (f == 0.0f && client.player.isFallFlying()) {

          for (BlockPos blockPos : BlockPos.betweenClosed(client.player.getOnPos(),
              client.player.getOnPos().below(5))) {

            if (client.player.level().getBlockState(blockPos).isAir()) {
              continue;
            }
            drawContext.drawString(client.font,
                Component.translatable(DDItems.SOUL_ELYTRA.getDescriptionId() + ".boost",
                        Keybinds.BOOST.getTranslatedKeyMessage())
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)), 20,
                client.getWindow().getGuiScaledHeight() - 37, 0);
          }
        }
      }
    });
  }
}
