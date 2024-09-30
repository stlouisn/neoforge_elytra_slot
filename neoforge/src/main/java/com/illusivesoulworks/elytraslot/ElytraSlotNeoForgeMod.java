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

package com.illusivesoulworks.elytraslot;

import com.illusivesoulworks.caelus.api.CaelusApi;
import com.illusivesoulworks.elytraslot.common.CurioElytra;
import com.illusivesoulworks.elytraslot.common.integration.deeperdarker.DeeperDarkerPlugin;
import com.illusivesoulworks.elytraslot.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosCapability;

@Mod(ElytraSlotConstants.MOD_ID)
public class ElytraSlotNeoForgeMod {

  public ElytraSlotNeoForgeMod(IEventBus eventBus) {
    ElytraSlotCommonMod.init();
    eventBus.addListener(FMLClientSetupEvent.class, (evt) -> this.clientSetup(evt, eventBus));
    eventBus.addListener(this::setup);
    eventBus.addListener(this::registerCapabilities);

    if (Services.PLATFORM.isModLoaded("deeperdarker")) {
      DeeperDarkerPlugin.setup(eventBus);
    }
  }

  private void setup(final FMLCommonSetupEvent evt) {
    NeoForge.EVENT_BUS.addListener(this::playerTick);
  }

  private void clientSetup(final FMLClientSetupEvent evt, final IEventBus eventBus) {
    ElytraSlotNeoForgeClientMod.setup(eventBus);
  }

  private void playerTick(final PlayerTickEvent.Post evt) {
    Player player = evt.getEntity();
    AttributeInstance attributeInstance =
        player.getAttribute(CaelusApi.getInstance().getFallFlyingAttribute());

    if (attributeInstance != null) {
      attributeInstance.removeModifier(CurioElytra.ELYTRA_CURIO_MODIFIER.id());

      if (!attributeInstance.hasModifier(CurioElytra.ELYTRA_CURIO_MODIFIER.id()) &&
          ElytraSlotCommonMod.canFly(player)) {
        attributeInstance.addTransientModifier(CurioElytra.ELYTRA_CURIO_MODIFIER);
      }
    }
  }

  private void registerCapabilities(final RegisterCapabilitiesEvent evt) {

    for (Item item : BuiltInRegistries.ITEM) {

      if (ElytraSlotCommonMod.IS_ELYTRA.test(item.getDefaultInstance())) {
        evt.registerItem(CuriosCapability.ITEM, (stack, context) -> new CurioElytra(stack), item);
      }
    }
  }
}