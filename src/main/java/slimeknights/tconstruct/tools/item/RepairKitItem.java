package slimeknights.tconstruct.tools.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IRepairableMaterialStats;
import slimeknights.tconstruct.library.tools.part.IRepairKitItem;
import slimeknights.tconstruct.library.tools.part.MaterialItem;

public class RepairKitItem extends MaterialItem implements IRepairKitItem {
  public RepairKitItem(Properties properties, ResourceKey<CreativeModeTab> tab) {
    super(properties, tab);
  }

  @Override
  public boolean canUseMaterial(MaterialId material) {
    return MaterialRegistry.getInstance()
                           .getAllStats(material)
                           .stream()
                           .anyMatch(stats -> stats instanceof IRepairableMaterialStats);
  }

  @Override
  public float getRepairAmount() {
    return Config.COMMON.repairKitAmount.get().floatValue();
  }
}
