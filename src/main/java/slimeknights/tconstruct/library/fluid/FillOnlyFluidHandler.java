package slimeknights.tconstruct.library.fluid;

import io.github.fabricators_of_create.porting_lib.util.FluidStack;

import javax.annotation.Nonnull;

import io.github.fabricators_of_create.porting_lib.transfer.fluid.IFluidHandler;

/**
 * Fluid handler wrapper that only allows filling
 */
public class FillOnlyFluidHandler implements IFluidHandler {
	private final IFluidHandler parent;
	public FillOnlyFluidHandler(IFluidHandler parent) {
		this.parent = parent;
	}

	@Override
	public int getTanks() {
		return parent.getTanks();
	}

	@Nonnull
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
		return false;
	}

	@Override
	public long fill(FluidStack resource, boolean sim) {
		return parent.fill(resource, sim);
	}

	@Nonnull
	@Override
	public FluidStack drain(FluidStack resource, boolean sim) {
		return FluidStack.EMPTY;
	}

	@Nonnull
	@Override
	public FluidStack drain(long maxDrain, boolean sim) {
		return FluidStack.EMPTY;
	}
}
