package slimeknights.tconstruct.smeltery.block.entity.inventory;

import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.mantle.inventory.SingleItemHandler;
import slimeknights.mantle.transfer.TransferUtil;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.network.InventorySlotSyncPacket;
import slimeknights.tconstruct.common.network.TinkerNetwork;
import slimeknights.tconstruct.smeltery.block.entity.component.DuctBlockEntity;

/**
 * Item handler for the duct
 */
public class DuctItemHandler extends SingleItemHandler<DuctBlockEntity> {

  public DuctItemHandler(DuctBlockEntity parent) {
    super(parent, 1);
  }

  /**
   * Sets the stack in this duct
   * @param newStack  New stack
   */
  @Override
  public void setStack(ItemStack newStack) {
    Level world = parent.getLevel();
    boolean hasChange = world != null && !ItemStack.matches(getStack(), newStack);
    super.setStack(newStack);
    if (hasChange) {
      if (!world.isClientSide) {
        BlockPos pos = parent.getBlockPos();
        TinkerNetwork.getInstance().sendToClientsAround(new InventorySlotSyncPacket(newStack, 0, pos), world, pos);
      } else {
        parent.updateFluid();
      }
    }
  }

  @Override
  protected boolean isItemValid(ItemVariant variant) {
    // the item or its container must be in the tag
    ItemStack stack = variant.toStack();
    if (!stack.is(TinkerTags.Items.DUCT_CONTAINERS)) {
      ItemStack container = stack.getRecipeRemainder();
      if (container.isEmpty() || !container.is(TinkerTags.Items.DUCT_CONTAINERS)) {
        return false;
      }
    }
    // the item must contain fluid (no empty cans or buckets)
    return TransferUtil.getFluidHandlerItem(stack)
                .filter(cap -> !cap.getFluidInTank(0).isEmpty())
                .isPresent();
  }

  /**
   * Gets the fluid filter for this duct
   * @return  Fluid filter
   */
  public FluidStack getFluid() {
    ItemStack stack = getStack();
    if (stack.isEmpty()) {
      return FluidStack.EMPTY;
    }
    return TransferUtil.getFluidHandlerItem(stack)
                    .map(handler -> handler.getFluidInTank(0))
                    .orElse(FluidStack.EMPTY);
  }
}
