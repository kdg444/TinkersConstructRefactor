package slimeknights.tconstruct.smeltery.block.entity.tank;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import slimeknights.mantle.lib.transfer.fluid.FluidStack;
import slimeknights.tconstruct.library.fluid.FillOnlyFluidHandler;
import slimeknights.tconstruct.smeltery.block.entity.ChannelBlockEntity;

/** Tank for each side connection, for the sake of rendering */
public class ChannelSideTank extends FillOnlyFluidHandler {
	private final ChannelBlockEntity channel;
	private final Direction side;

	public ChannelSideTank(ChannelBlockEntity channel, ChannelTank tank, Direction side) {
		super(tank);
		// only horizontals
		assert side.getAxis() != Axis.Y;
		this.channel = channel;
		this.side = side;
	}

	@Override
	public int fill(FluidStack resource, boolean sim) {
		int filled = super.fill(resource, action);
		if (action.execute() && filled > 0) {
			channel.setFlow(side, true);
		}
		return filled;
	}
}
