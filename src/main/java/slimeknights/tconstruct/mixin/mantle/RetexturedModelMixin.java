package slimeknights.tconstruct.mixin.mantle;

import net.fabricmc.fabric.api.renderer.v1.model.WrapperBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import slimeknights.mantle.client.model.RetexturedModel;
import slimeknights.mantle.client.model.data.IModelData;
import slimeknights.mantle.client.model.util.DynamicBakedWrapper;
import slimeknights.mantle.util.RetexturedHelper;
import slimeknights.tconstruct.library.client.model.ModelHelper;

import java.util.function.Supplier;

@Mixin(RetexturedModel.Baked.class)
public abstract class RetexturedModelMixin extends DynamicBakedWrapper<BakedModel> {

  @Shadow
  protected abstract BakedModel getCachedModel(Block block);

  protected RetexturedModelMixin(BakedModel originalModel) {
    super(originalModel);
  }

  /**
   * @author
   * @reason
   */
  @Overwrite
  public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
    if(blockView.getBlockEntityRenderData(pos) instanceof IModelData data) {
      Block block = data.getData(RetexturedHelper.BLOCK_PROPERTY);
      if (block == null) {
        super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        return;
      }
      getCachedModel(block).emitBlockQuads(blockView, state, pos, randomSupplier, context);
    } else {
      super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
    }
  }

  @Mixin(targets = {"slimeknights.mantle.client.model.RetexturedModel$RetexturedOverride"})
  public static class RetexturedModelRetexturedOverrideMixin {
    @Redirect(method = "resolve", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/renderer/v1/model/WrapperBakedModel;getWrappedModel()Lnet/minecraft/client/resources/model/BakedModel;"))
    private BakedModel unwrap(WrapperBakedModel instance) {
      return ModelHelper.unwrap((BakedModel) instance, RetexturedModel.Baked.class);
    }
  }

}
