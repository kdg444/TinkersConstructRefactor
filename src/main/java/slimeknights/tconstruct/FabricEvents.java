package slimeknights.tconstruct;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import slimeknights.tconstruct.fluids.FluidEvents;
import slimeknights.tconstruct.shared.AchievementEvents;
import slimeknights.tconstruct.shared.CommonsEvents;
import slimeknights.tconstruct.tools.logic.ToolEvents;
import slimeknights.tconstruct.world.WorldClientEvents;

public class FabricEvents {
  public static void init() {
    FluidEvents.onFurnaceFuel();
    ToolEvents.init();
    CommonsEvents.init();
    AchievementEvents.init();
    
    if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
      Client.init();
    }
  }
  
  private static class Client {
    @Environment(EnvType.CLIENT)
    private static void init() {
      WorldClientEvents.clientSetup();
    }
  }
}
