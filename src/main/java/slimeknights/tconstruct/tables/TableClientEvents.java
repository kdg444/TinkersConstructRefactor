package slimeknights.tconstruct.tables;

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import slimeknights.mantle.lib.event.ColorHandlersCallback;
import slimeknights.tconstruct.common.ClientEventBase;
import slimeknights.tconstruct.tables.block.entity.chest.TinkersChestBlockEntity;
import slimeknights.tconstruct.tables.client.TableTileEntityRenderer;
import slimeknights.tconstruct.tables.client.inventory.CraftingStationScreen;
import slimeknights.tconstruct.tables.client.inventory.PartBuilderScreen;
import slimeknights.tconstruct.tables.client.inventory.TinkerChestScreen;
import slimeknights.tconstruct.tables.client.inventory.TinkerStationScreen;

@SuppressWarnings("unused")
public class TableClientEvents extends ClientEventBase {

  public static void init() {
    registerModelLoader();
    ColorHandlersCallback.BLOCK.register(TableClientEvents::registerBlockColors);
    ColorHandlersCallback.ITEM.register(TableClientEvents::registerItemColors);
  }

  static void registerModelLoader() {
//    ModelLoaderRegistry.registerLoader(TConstruct.getResource("table"), TableModel.LOADER);
  }

  static void registerRenderers() {
    BlockEntityRendererRegistry.register(TinkerTables.craftingStationTile.get(), TableTileEntityRenderer::new);
    BlockEntityRendererRegistry.register(TinkerTables.tinkerStationTile.get(), TableTileEntityRenderer::new);
    BlockEntityRendererRegistry.register(TinkerTables.partBuilderTile.get(), TableTileEntityRenderer::new);
  }

  static void setupClient() {
    ScreenRegistry.register(TinkerTables.craftingStationContainer.get(), CraftingStationScreen::new);
    ScreenRegistry.register(TinkerTables.tinkerStationContainer.get(), TinkerStationScreen::new);
    ScreenRegistry.register(TinkerTables.partBuilderContainer.get(), PartBuilderScreen::new);
    ScreenRegistry.register(TinkerTables.tinkerChestContainer.get(), TinkerChestScreen::new);
  }

  static void registerBlockColors(BlockColors blockColors) {
    blockColors.register((state, world, pos, index) -> {
      if (world != null && pos != null) {
        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof TinkersChestBlockEntity) {
          return ((TinkersChestBlockEntity)te).getColor();
        }
      }
      return -1;
    }, TinkerTables.tinkersChest.get());
  }

  static void registerItemColors(ItemColors itemColors, BlockColors blockColors) {
    itemColors.register((stack, index) -> ((DyeableLeatherItem)stack.getItem()).getColor(stack), TinkerTables.tinkersChest.asItem());
  }
}
