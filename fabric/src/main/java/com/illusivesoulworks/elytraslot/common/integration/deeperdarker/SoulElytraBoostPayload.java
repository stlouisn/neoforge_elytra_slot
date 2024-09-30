package com.illusivesoulworks.elytraslot.common.integration.deeperdarker;

import com.illusivesoulworks.elytraslot.ElytraSlotConstants;
import javax.annotation.Nonnull;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SoulElytraBoostPayload() implements CustomPacketPayload {

  public static final CustomPacketPayload.Type<SoulElytraBoostPayload> TYPE =
      new CustomPacketPayload.Type<>(
          ResourceLocation.fromNamespaceAndPath(ElytraSlotConstants.MOD_ID, "soul_elytra_boost"));
  public static final SoulElytraBoostPayload INSTANCE = new SoulElytraBoostPayload();
  public static final StreamCodec<RegistryFriendlyByteBuf, SoulElytraBoostPayload> CODEC =
      StreamCodec.unit(INSTANCE);

  @Override
  @Nonnull
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
