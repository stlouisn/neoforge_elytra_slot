package com.illusivesoulworks.elytraslot.platform;

import com.illusivesoulworks.elytraslot.platform.services.ILoadingPlatform;
import org.quiltmc.loader.api.QuiltLoader;

public class QuiltLoadingPlatform implements ILoadingPlatform {

  @Override
  public boolean isModLoaded(String id) {
    return QuiltLoader.isModLoaded(id);
  }
}
