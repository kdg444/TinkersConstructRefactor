package slimeknights.tconstruct.smeltery.block.entity.inventory;

import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import lombok.AllArgsConstructor;
import slimeknights.mantle.transfer.fluid.IFluidHandler;

@AllArgsConstructor
public class DuctTankWrapper implements IFluidHandler {
  private final IFluidHandler parent;
  private final DuctItemHandler itemHandler;


  /* Properties */

  @Override
  public int getTanks() {
    return parent.getTanks();
  }

  @Override
  public FluidStack getFluidInTank(int tank) {
    return parent.getFluidInTank(tank);
  }

  @Override
  public long getTankCapacity(int tank) {
    return parent.getTankCapacity(tank);
  }

  @Override
  public boolean isFluidValid(int tank, FluidStack stack) {
    return itemHandler.getFluid().isFluidEqual(stack);
  }


  /* Interactions */

  @Override
  public long fill(FluidStack resource, boolean sim) {
    if (resource.isEmpty() || !itemHandler.getFluid().isFluidEqual(resource)) {
      return 0;
    }
    return parent.fill(resource, sim);
  }

  @Override
  public FluidStack drain(long maxDrain, boolean sim) {
    FluidStack fluid = itemHandler.getFluid();
    if (fluid.isEmpty()) {
      return FluidStack.EMPTY;
    }
    return parent.drain(new FluidStack(fluid, maxDrain), sim);
  }

  @Override
  public FluidStack drain(FluidStack resource, boolean sim) {
    if (resource.isEmpty() || !itemHandler.getFluid().isFluidEqual(resource)) {
      return FluidStack.EMPTY;
    }
    return parent.drain(resource, sim);
  }
}
