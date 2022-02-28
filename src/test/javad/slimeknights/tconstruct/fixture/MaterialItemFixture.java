package slimeknights.tconstruct.fixture;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

public class MaterialItemFixture {

  public static final ToolPartItem MATERIAL_ITEM = new ToolPartItem(new Item.Properties(), MaterialStatsFixture.STATS_TYPE);
  public static final ToolPartItem MATERIAL_ITEM_2 = new ToolPartItem(new Item.Properties(), MaterialStatsFixture.STATS_TYPE_2);

  public static final ToolPartItem MATERIAL_ITEM_HEAD = new ToolPartItem(new Item.Properties(), HeadMaterialStats.ID);
  public static final ToolPartItem MATERIAL_ITEM_HANDLE = new ToolPartItem(new Item.Properties(), HandleMaterialStats.ID);
  public static final ToolPartItem MATERIAL_ITEM_EXTRA = new ToolPartItem(new Item.Properties(), ExtraMaterialStats.ID);

  private MaterialItemFixture() {
  }

  private static boolean init = false;
  public static void init() {
    if (init) {
      return;
    }
    init = true;
    Registry.register(Registry.ITEM, new ResourceLocation("test", "test_material"), MATERIAL_ITEM);
    Registry.register(Registry.ITEM, new ResourceLocation("test", "test_material_2"), MATERIAL_ITEM_2);
    Registry.register(Registry.ITEM, new ResourceLocation("test", "test_head"), MATERIAL_ITEM_HEAD);
    Registry.register(Registry.ITEM, new ResourceLocation("test", "test_handle"), MATERIAL_ITEM_HANDLE);
    Registry.register(Registry.ITEM, new ResourceLocation("test", "test_extra"), MATERIAL_ITEM_EXTRA);
  }
}
