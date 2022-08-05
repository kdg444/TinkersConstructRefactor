package slimeknights.tconstruct.plugin.rei;

import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoRegistry;
import me.shedaniel.rei.api.common.transfer.info.simple.RecipeBookGridMenuInfo;
import me.shedaniel.rei.api.common.transfer.info.simple.SimpleMenuInfoProvider;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import slimeknights.tconstruct.tables.menu.TinkerStationContainerMenu;

public class REIPluginServer implements REIServerPlugin {

  @Override
  public void registerMenuInfo(MenuInfoRegistry registry) {
    registry.register(BuiltinPlugin.CRAFTING, TinkerStationContainerMenu.class, SimpleMenuInfoProvider.of(defaultCraftingDisplay -> new RecipeBookGridMenuInfo(defaultCraftingDisplay)));
  }
}
