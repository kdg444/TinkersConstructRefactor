package slimeknights.tconstruct.fluids.util;

import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import lombok.Getter;
import net.minecraft.world.item.ItemStack;
import slimeknights.mantle.transfer.fluid.IFluidHandlerItem;

import javax.annotation.Nonnull;

/** Represents a capability handler for a container with a constant fluid */
public class ConstantFluidContainerWrapper implements IFluidHandlerItem/*, ICapabilityProvider*/ {
  private final LazyOptional<IFluidHandlerItem> holder = LazyOptional.of(() -> this);

  /** Contained fluid */
  private final FluidStack fluid;
  /** If true, the container is now empty */
  private boolean empty = false;
  /** Item stack representing the current state */
  @Getter
  @Nonnull
  protected ItemStack container;

  public ConstantFluidContainerWrapper(FluidStack fluid, ItemStack container) {
    this.fluid = fluid;
    this.container = container;
  }

  @Override
  public int getTanks() {
    return 1;
  }

  @Override
  public long getTankCapacity(int tank) {
    return fluid.getAmount();
  }

  @Override
  public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
    return stack.isEmpty() || stack.getFluid() == fluid.getFluid();
  }

  @Nonnull
  @Override
  public FluidStack getFluidInTank(int tank) {
    return empty ? FluidStack.EMPTY : fluid;
  }

  @Override
  public long fill(FluidStack resource, boolean sim) {
    return 0;
  }

  @Nonnull
  @Override
  public FluidStack drain(FluidStack resource, boolean sim) {
    // cannot drain if: already drained, requested the wrong type, or requested too little
    if (empty || resource.getFluid() != fluid.getFluid() || resource.getAmount() < fluid.getAmount()) {
      return FluidStack.EMPTY;
    }
    if (!sim) {
      container = container.getRecipeRemainder();
      empty = true;
    }
    return fluid.copy();
  }

  @Nonnull
  @Override
  public FluidStack drain(long maxDrain, boolean sim) {
    // cannot drain if: already drained, requested the wrong type, or requested too little
    if (empty || maxDrain < fluid.getAmount()) {
      return FluidStack.EMPTY;
    }
    if (!sim) {
      container = container.getRecipeRemainder();
      empty = true;
    }
    return fluid.copy();
  }

//  @Nonnull
//  @Override
//  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
//    return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.orEmpty(capability, holder);
//  }
}
