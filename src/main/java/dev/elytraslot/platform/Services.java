package dev.elytraslot.platform;

import dev.elytraslot.ElytraSlotConstants;
import dev.elytraslot.platform.services.IElytraPlatform;
import java.util.ServiceLoader;

public class Services {

  public static final IElytraPlatform ELYTRA = load(IElytraPlatform.class);

  public static <T> T load(Class<T> clazz) {
    final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    ElytraSlotConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
    return loadedService;
  }
}
