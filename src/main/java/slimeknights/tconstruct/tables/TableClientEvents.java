package slimeknights.tconstruct.tables;

import io.github.fabricators_of_create.porting_lib.event.ModelLoadCallback;
import io.github.fabricators_of_create.porting_lib.model.ModelLoaderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.ClientEventBase;
import slimeknights.tconstruct.library.client.model.block.TableModel;
import slimeknights.tconstruct.tables.block.entity.chest.TinkersChestBlockEntity;
import slimeknights.tconstruct.tables.client.TableTileEntityRenderer;
import slimeknights.tconstruct.tables.client.inventory.CraftingStationScreen;
import slimeknights.tconstruct.tables.client.inventory.PartBuilderScreen;
import slimeknights.tconstruct.tables.client.inventory.TinkerChestScreen;
import slimeknights.tconstruct.tables.client.inventory.TinkerStationScreen;

@SuppressWarnings("unused")
public class TableClientEvents extends ClientEventBase {

  public static void init() {
    ModelLoadCallback.EVENT.register(TableClientEvents::registerModelLoader);
    registerRenderers();
    setupClient();
    TableClientEvents.registerBlockColors();
    TableClientEvents.registerItemColors();
  }

  static void registerModelLoader(ResourceManager manager, BlockColors colors, ProfilerFiller profiler, int mipLevel) {
    ModelLoaderRegistry.registerLoader(TConstruct.getResource("table"), TableModel.LOADER);
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

  static void registerBlockColors() {
    ColorProviderRegistry.BLOCK.register((state, world, pos, index) -> {
      if (world != null && pos != null) {
        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof TinkersChestBlockEntity) {
          return ((TinkersChestBlockEntity)te).getColor();
        }
      }
      return -1;
    }, TinkerTables.tinkersChest.get());
  }

  static void registerItemColors() {
    ColorProviderRegistry.ITEM.register((stack, index) -> ((DyeableLeatherItem)stack.getItem()).getColor(stack), TinkerTables.tinkersChest.asItem());
  }
}
