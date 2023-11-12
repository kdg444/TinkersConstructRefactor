package slimeknights.tconstruct.mixin.mantle;

import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import slimeknights.mantle.block.entity.IRetexturedBlockEntity;
import slimeknights.mantle.client.model.data.IModelData;
import slimeknights.mantle.util.RetexturedHelper;

@Mixin(RetexturedHelper.class)
public class RetexturedHelperMixin {
  @Redirect(method = "onTextureUpdated", at = @At(value = "INVOKE", target = "Lslimeknights/mantle/block/entity/IRetexturedBlockEntity;getRetexturedModelData()Lslimeknights/mantle/client/model/data/IModelData;"))
  private static IModelData useRenderData(IRetexturedBlockEntity instance) {
    return (IModelData) ((BlockEntity) instance).getRenderData();
  }
}
