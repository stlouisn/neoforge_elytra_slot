

package com.illusivesoulworks.elytraslot;

import com.illusivesoulworks.caelus.api.CaelusApi;
import com.illusivesoulworks.elytraslot.common.CurioElytra;
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
public class ElytraSlot {

  public ElytraSlot(IEventBus eventBus) {
    ElytraSlotCommon.init();
    eventBus.addListener(FMLClientSetupEvent.class, (evt) -> this.clientSetup(evt, eventBus));
    eventBus.addListener(this::setup);
    eventBus.addListener(this::registerCapabilities);
  }

  private void setup(final FMLCommonSetupEvent evt) {
    NeoForge.EVENT_BUS.addListener(this::playerTick);
  }

  private void clientSetup(final FMLClientSetupEvent evt, final IEventBus eventBus) {
    ElytraSlotClient.setup(eventBus);
  }

  private void playerTick(final PlayerTickEvent.Post evt) {
    Player player = evt.getEntity();
    AttributeInstance attributeInstance =
        player.getAttribute(CaelusApi.getInstance().getFallFlyingAttribute());

    if (attributeInstance != null) {
      attributeInstance.removeModifier(CurioElytra.ELYTRA_CURIO_MODIFIER.id());

      if (!attributeInstance.hasModifier(CurioElytra.ELYTRA_CURIO_MODIFIER.id()) &&
          ElytraSlotCommon.canFly(player)) {
        attributeInstance.addTransientModifier(CurioElytra.ELYTRA_CURIO_MODIFIER);
      }
    }
  }

  private void registerCapabilities(final RegisterCapabilitiesEvent evt) {

    for (Item item : BuiltInRegistries.ITEM) {

      if (ElytraSlotCommon.IS_ELYTRA.test(item.getDefaultInstance())) {
        evt.registerItem(CuriosCapability.ITEM, (stack, context) -> new CurioElytra(stack), item);
      }
    }
  }
}