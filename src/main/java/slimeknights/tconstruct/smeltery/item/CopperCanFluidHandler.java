package slimeknights.tconstruct.smeltery.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidStack;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.IFluidHandlerItem;
import slimeknights.tconstruct.library.recipe.FluidValues;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Capability handler instance for the copper can item */
@AllArgsConstructor
public class CopperCanFluidHandler implements IFluidHandlerItem/*, ICapabilityProvider*/ {
  private final LazyOptional<IFluidHandlerItem> holder = LazyOptional.of(() -> this);

  @Getter
  private final ItemStack container;

//  @Nonnull
//  @Override // TODO transfer WHAT???
//  public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
//    return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.orEmpty(cap, holder);
//  }


  /* Tank properties */

  @Override
  public int getTanks() {
    return 1;
  }

  @Override
  public boolean isFluidValid(int tank, FluidStack stack) {
    return true;
  }

  @Override
  public long getTankCapacity(int tank) {
    return FluidValues.INGOT;
  }

  /** Gets the contained fluid */
  private Fluid getFluid() {
    return CopperCanItem.getFluid(container);
  }

  /** Gets the contained fluid */
  @Nullable
  private CompoundTag getFluidTag() {
    return CopperCanItem.getFluidTag(container);
  }

  @Nonnull
  @Override
  public FluidStack getFluidInTank(int tank) {
    return new FluidStack(getFluid(), FluidValues.INGOT, getFluidTag());
  }


  /* Interaction */

  @Override
  public long fill(FluidStack resource, boolean sim) {
    // must not be filled, must have enough
    if (getFluid() != Fluids.EMPTY || resource.getAmount() < FluidValues.INGOT) {
      return 0;
    }
    // update fluid and return
    if (!sim) {
      CopperCanItem.setFluid(container, resource);
    }
    return FluidValues.INGOT;
  }

  @Nonnull
  @Override
  public FluidStack drain(FluidStack resource, boolean sim) {
    // must be draining at least an ingot
    if (resource.isEmpty() || resource.getAmount() < FluidValues.INGOT) {
      return FluidStack.EMPTY;
    }
    // must have a fluid, must match what they are draining
    Fluid fluid = getFluid();
    if (fluid == Fluids.EMPTY || fluid != resource.getFluid()) {
      return FluidStack.EMPTY;
    }
    // output 1 ingot
    FluidStack output = new FluidStack(fluid, FluidValues.INGOT, getFluidTag());
    if (!sim) {
      CopperCanItem.setFluid(container, FluidStack.EMPTY);
    }
    return output;
  }

  @Nonnull
  @Override
  public FluidStack drain(long maxDrain, boolean sim) {
    // must be draining at least an ingot
    if (maxDrain < FluidValues.INGOT) {
      return FluidStack.EMPTY;
    }
    // must have a fluid
    Fluid fluid = getFluid();
    if (fluid == Fluids.EMPTY) {
      return FluidStack.EMPTY;
    }
    // output 1 ingot
    FluidStack output = new FluidStack(fluid, FluidValues.INGOT, getFluidTag());
    if (!sim) {
      CopperCanItem.setFluid(container, FluidStack.EMPTY);
    }
    return output;
  }
}
