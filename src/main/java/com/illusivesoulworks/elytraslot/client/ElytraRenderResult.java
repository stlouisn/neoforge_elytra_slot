package com.illusivesoulworks.elytraslot.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record ElytraRenderResult(ElytraColor color, ResourceLocation texture, boolean enchanted, ItemStack stack, boolean useCapeTexture) {

}
