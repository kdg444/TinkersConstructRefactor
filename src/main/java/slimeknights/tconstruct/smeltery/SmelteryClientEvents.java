package slimeknights.tconstruct.smeltery;

import io.github.fabricators_of_create.porting_lib.event.client.ModelLoadCallback;
import io.github.fabricators_of_create.porting_lib.model.ModelLoaderRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import slimeknights.mantle.client.model.FaucetFluidLoader;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.ClientEventBase;
import slimeknights.tconstruct.library.client.model.block.CastingModel;
import slimeknights.tconstruct.library.client.model.block.ChannelModel;
import slimeknights.tconstruct.library.client.model.block.FluidTextureModel;
import slimeknights.tconstruct.library.client.model.block.MelterModel;
import slimeknights.tconstruct.library.client.model.block.TankModel;
import slimeknights.tconstruct.smeltery.client.CopperCanModel;
import slimeknights.tconstruct.smeltery.client.render.CastingBlockEntityRenderer;
import slimeknights.tconstruct.smeltery.client.render.ChannelBlockEntityRenderer;
import slimeknights.tconstruct.smeltery.client.render.FaucetBlockEntityRenderer;
import slimeknights.tconstruct.smeltery.client.render.HeatingStructureBlockEntityRenderer;
import slimeknights.tconstruct.smeltery.client.render.MelterBlockEntityRenderer;
import slimeknights.tconstruct.smeltery.client.render.TankBlockEntityRenderer;
import slimeknights.tconstruct.smeltery.client.screen.AlloyerScreen;
import slimeknights.tconstruct.smeltery.client.screen.HeatingStructureScreen;
import slimeknights.tconstruct.smeltery.client.screen.MelterScreen;
import slimeknights.tconstruct.smeltery.client.screen.SingleItemScreenFactory;

@SuppressWarnings("unused")
public class SmelteryClientEvents extends ClientEventBase {

  public static void init() {
    addResourceListener();
    registerRenderers();
    clientSetup();
    ModelLoadCallback.EVENT.register(SmelteryClientEvents::registerModelLoaders);
  }

  static void addResourceListener() {
    ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
    if (resourceManager instanceof ReloadableResourceManager reloadable)
      FaucetFluidLoader.initialize(reloadable);
  }

  static void registerRenderers() {
    BlockEntityRendererRegistry.register(TinkerSmeltery.tank.get(), TankBlockEntityRenderer::new);
    BlockEntityRendererRegistry.register(TinkerSmeltery.faucet.get(), FaucetBlockEntityRenderer::new);
    BlockEntityRendererRegistry.register(TinkerSmeltery.channel.get(), ChannelBlockEntityRenderer::new);
    BlockEntityRendererRegistry.register(TinkerSmeltery.table.get(), CastingBlockEntityRenderer::new);
    BlockEntityRendererRegistry.register(TinkerSmeltery.basin.get(), CastingBlockEntityRenderer::new);
    BlockEntityRendererRegistry.register(TinkerSmeltery.melter.get(), MelterBlockEntityRenderer::new);
    BlockEntityRendererRegistry.register(TinkerSmeltery.alloyer.get(), TankBlockEntityRenderer::new);
    BlockEntityRenderers.register(TinkerSmeltery.smeltery.get(), HeatingStructureBlockEntityRenderer::new);
    BlockEntityRenderers.register(TinkerSmeltery.foundry.get(), HeatingStructureBlockEntityRenderer::new);
  }

  static void clientSetup() {
    // render layers
    RenderType cutout = RenderType.cutout();
    // seared
    // casting
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.searedFaucet.get(), cutout);
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.searedBasin.get(), cutout);
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.searedTable.get(), cutout);
    // controller
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.searedMelter.get(), cutout);
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.smelteryController.get(), cutout);
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.foundryController.get(), cutout);
    // peripherals
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.searedDrain.get(), cutout);
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.searedDuct.get(), cutout);
    TinkerSmeltery.searedTank.forEach(tank -> BlockRenderLayerMap.INSTANCE.putBlock(tank, cutout));
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.searedLantern.get(), cutout);
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.searedGlass.get(), cutout);
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.searedGlassPane.get(), cutout);
    // scorched
    // casting
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.scorchedFaucet.get(), cutout);
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.scorchedBasin.get(), cutout);
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.scorchedTable.get(), cutout);
    // controller
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.scorchedAlloyer.get(), cutout);
    // peripherals
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.scorchedDrain.get(), cutout);
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.scorchedDuct.get(), cutout);
    TinkerSmeltery.scorchedTank.forEach(tank -> BlockRenderLayerMap.INSTANCE.putBlock(tank, cutout));
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.scorchedLantern.get(), cutout);
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.scorchedGlass.get(), cutout);
    BlockRenderLayerMap.INSTANCE.putBlock(TinkerSmeltery.scorchedGlassPane.get(), cutout);

    // screens
    ScreenRegistry.register(TinkerSmeltery.melterContainer.get(), MelterScreen::new);
    ScreenRegistry.register(TinkerSmeltery.smelteryContainer.get(), HeatingStructureScreen::new);
    ScreenRegistry.register(TinkerSmeltery.singleItemContainer.get(), new SingleItemScreenFactory());
    ScreenRegistry.register(TinkerSmeltery.alloyerContainer.get(), AlloyerScreen::new);
  }

  static void registerModelLoaders(ResourceManager manager, BlockColors colors, ProfilerFiller profiler, int mipLevel) {
    ModelLoaderRegistry.registerLoader(TConstruct.getResource("tank"), TankModel.LOADER);
    ModelLoaderRegistry.registerLoader(TConstruct.getResource("casting"), CastingModel.LOADER);
    ModelLoaderRegistry.registerLoader(TConstruct.getResource("melter"), MelterModel.LOADER);
    ModelLoaderRegistry.registerLoader(TConstruct.getResource("channel"), ChannelModel.LOADER);
    ModelLoaderRegistry.registerLoader(TConstruct.getResource("fluid_texture"), FluidTextureModel.LOADER);
    ModelLoaderRegistry.registerLoader(TConstruct.getResource("copper_can"), CopperCanModel.LOADER);
  }
}
