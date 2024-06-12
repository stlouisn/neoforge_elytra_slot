package com.illusivesoulworks.elytraslot.platform;

import com.illusivesoulworks.elytraslot.platform.services.ILoadingPlatform;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgeLoadingPlatform implements ILoadingPlatform {

  @Override
  public boolean isModLoaded(String id) {
    return FMLLoader.getLoadingModList().getModFileById(id) != null;
  }
}
