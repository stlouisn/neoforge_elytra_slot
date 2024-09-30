package com.illusivesoulworks.elytraslot.common.integration.deeperdarker;

import com.illusivesoulworks.elytraslot.platform.Services;
import com.kyanite.deeperdarker.DeeperDarker;
import com.kyanite.deeperdarker.content.DDItems;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class DeeperDarkerPlugin {

  public static void setup() {
    registerPayloads();
  }

  public static void registerPayloads() {
    PayloadTypeRegistry.playC2S()
        .register(SoulElytraBoostPayload.TYPE, SoulElytraBoostPayload.CODEC);
    ServerPlayNetworking.registerGlobalReceiver(SoulElytraBoostPayload.TYPE, (payload, ctx) -> {
      ServerPlayer player = ctx.player();
      Level level = player.level();

      if (DeeperDarker.CONFIG.server.soulElytraCooldown() == -1) {
        player.displayClientMessage(
            Component.translatable(DDItems.SOUL_ELYTRA.getDescriptionId() + ".boost_disabled")
                .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), true);
        return;
      }

      if (player.isFallFlying() && Services.ELYTRA.getEquipped(player).is(DDItems.SOUL_ELYTRA) &&
          !player.getCooldowns().isOnCooldown(DDItems.SOUL_ELYTRA) &&
          DeeperDarker.CONFIG.server.soulElytraCooldown() != -1) {
        FireworkRocketEntity rocket =
            new FireworkRocketEntity(level, new ItemStack(Items.FIREWORK_ROCKET), player);
        level.addFreshEntity(rocket);
        player.getCooldowns()
            .addCooldown(DDItems.SOUL_ELYTRA, DeeperDarker.CONFIG.server.soulElytraCooldown());
      }
    });
  }
}
