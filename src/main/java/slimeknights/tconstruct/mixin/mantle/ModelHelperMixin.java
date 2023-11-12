package slimeknights.tconstruct.mixin.mantle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.MultiPartBakedModel;
import net.minecraft.client.resources.model.WeightedBakedModel;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import slimeknights.mantle.client.model.util.ModelHelper;

import javax.annotation.Nullable;

@Mixin(ModelHelper.class)
public class ModelHelperMixin {
  /**
   * @author
   * @reason
   */
  @Nullable
  @Overwrite
  public static <T extends BakedModel> T getBakedModel(BlockState state, Class<T> clazz) {
    Minecraft minecraft = Minecraft.getInstance();
    //noinspection ConstantConditions  null during run data
    if (minecraft == null) {
      return null;
    }
    BakedModel baked = minecraft.getModelManager().getBlockModelShaper().getBlockModel(state);
    baked = slimeknights.tconstruct.library.client.model.ModelHelper.unwrap(baked, clazz);
    // map multipart and weighted random into the first variant
    if (baked instanceof MultiPartBakedModel) {
      baked = ((MultiPartBakedModel)baked).selectors.get(0).getRight();
    }
    if (baked instanceof WeightedBakedModel) {
      baked = ((WeightedBakedModel) baked).wrapped;
    }
    // final model should match the desired type
    if (clazz.isInstance(baked)) {
      return clazz.cast(baked);
    }
    return null;
  }


  /**
   * @author
   * @reason
   */
  @Nullable
  @Overwrite
  public static <T extends BakedModel> T getBakedModel(ItemLike item, Class<T> clazz) {
    Minecraft minecraft = Minecraft.getInstance();
    //noinspection ConstantConditions  null during run data
    if (minecraft == null) {
      return null;
    }
    BakedModel baked = minecraft.getItemRenderer().getItemModelShaper().getItemModel(item.asItem());
    baked = slimeknights.tconstruct.library.client.model.ModelHelper.unwrap(baked, clazz);
    if (clazz.isInstance(baked)) {
      return clazz.cast(baked);
    }
    return null;
  }
}
