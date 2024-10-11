package dev.elytraslot;

import com.illusivesoulworks.caelus.api.RenderCapeEvent;
import dev.elytraslot.client.ElytraSlotLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import net.neoforged.neoforge.common.NeoForge;

public class ElytraSlotClient {

  public static void setup(IEventBus eventBus) {
    eventBus.addListener(ElytraSlotClient::addLayers);
    NeoForge.EVENT_BUS.addListener(ElytraSlotClient::renderCape);
  }

  private static void addLayers(final EntityRenderersEvent.AddLayers evt) {
    addEntityLayer(evt);
    for (PlayerSkin.Model skin : evt.getSkins()) {
      addPlayerLayer(evt, skin);
    }
  }

  private static void addPlayerLayer(EntityRenderersEvent.AddLayers evt, PlayerSkin.Model skin) {
    EntityRenderer<? extends Player> renderer = evt.getSkin(skin);
    if (renderer instanceof LivingEntityRenderer livingRenderer) {
      livingRenderer.addLayer(new ElytraSlotLayer(livingRenderer, evt.getEntityModels()));
    }
  }

  private static <T extends LivingEntity, M extends HumanoidModel<T>, R extends LivingEntityRenderer<T, M>> void addEntityLayer(AddLayers evt) {
    R renderer = evt.getRenderer((EntityType<? extends T>) EntityType.ARMOR_STAND);
    if (renderer != null) {
      renderer.addLayer(new ElytraSlotLayer<>(renderer, evt.getEntityModels()));
    }
  }

  private static void renderCape(final RenderCapeEvent evt) {
    if (ElytraSlotCommon.isEquipped(evt.getEntity())) {
      evt.setCanceled(true);
    }
  }
}
