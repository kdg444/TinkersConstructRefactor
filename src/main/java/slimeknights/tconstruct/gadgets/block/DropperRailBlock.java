package slimeknights.tconstruct.gadgets.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import slimeknights.mantle.inventory.EmptyItemHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.IItemHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemHandlerHelper;

public class DropperRailBlock extends RailBlock {

  public DropperRailBlock(Properties properties) {
    super(properties);
  }

//  @Override TODO: PORT
//  public void onMinecartPass(BlockState state, Level world, BlockPos pos, AbstractMinecart cart) {
//    if (!cart.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).isPresent() || !(cart instanceof Hopper)) {
//      return;
//    }
//    BlockEntity tileEntity = world.getBlockEntity(pos.below());
//    if (tileEntity == null || !TransferUtilForge.getItemHandler(tileEntity, Direction.DOWN).isPresent()) {
//      return;
//    }
//
//    // todo: fix this optional usage
//    IItemHandler itemHandlerCart = cart.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).orElse(EmptyItemHandler.INSTANCE);
//    IItemHandler itemHandlerTE = TransferUtilForge.getItemHandler(tileEntity, Direction.UP).orElse(EmptyItemHandler.INSTANCE);
//
//    for (int i = 0; i < itemHandlerCart.getSlots(); i++) {
//      ItemStack itemStack = itemHandlerCart.extractItem(i, 1, true);
//      if (itemStack.isEmpty()) {
//        continue;
//      }
//      if (ItemHandlerHelper.insertItem(itemHandlerTE, itemStack, true).isEmpty()) {
//        itemStack = itemHandlerCart.extractItem(i, 1, false);
//        ItemHandlerHelper.insertItem(itemHandlerTE, itemStack, false);
//        break;
//      }
//    }
//  }

}
