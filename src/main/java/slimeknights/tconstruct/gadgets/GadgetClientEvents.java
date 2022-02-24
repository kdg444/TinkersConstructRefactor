package slimeknights.tconstruct.gadgets;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import slimeknights.tconstruct.common.ClientEventBase;
import slimeknights.tconstruct.gadgets.client.FancyItemFrameRenderer;
import slimeknights.tconstruct.gadgets.client.RenderShuriken;

@SuppressWarnings("unused")
public class GadgetClientEvents extends ClientEventBase {
  static void registerModels() {
    FancyItemFrameRenderer.LOCATIONS_MODEL.forEach((type, loc) -> ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(loc)));
    FancyItemFrameRenderer.LOCATIONS_MODEL_MAP.forEach((type, loc) -> ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(loc)));
  }

  static void registerRenderers() {
    EntityRendererRegistry.register(TinkerGadgets.itemFrameEntity.get(), FancyItemFrameRenderer::new);
    EntityRendererRegistry.register(TinkerGadgets.glowBallEntity.get(), ThrownItemRenderer::new);
    EntityRendererRegistry.register(TinkerGadgets.eflnEntity.get(), ThrownItemRenderer::new);
    EntityRendererRegistry.register(TinkerGadgets.quartzShurikenEntity.get(), RenderShuriken::new);
    EntityRendererRegistry.register(TinkerGadgets.flintShurikenEntity.get(), RenderShuriken::new);
  }

  public static void init() {
    registerModels();
    registerRenderers();
  }
}
