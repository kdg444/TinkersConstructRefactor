package slimeknights.tconstruct.library.fluid;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.mixin.transfer.BucketItemAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import slimeknights.mantle.lib.util.LazyOptional;
import slimeknights.mantle.lib.extensions.FluidExtensions;
import slimeknights.mantle.lib.transfer.TransferUtil;
import slimeknights.mantle.lib.transfer.fluid.FluidAttributes;
import slimeknights.mantle.lib.transfer.fluid.FluidStack;
import slimeknights.mantle.lib.transfer.fluid.IFluidHandler;
import slimeknights.mantle.lib.transfer.fluid.EmptyFluidHandler;
import slimeknights.mantle.lib.transfer.item.ItemHandlerHelper;
import slimeknights.tconstruct.TConstruct;

/**
 * Alternative to {@link net.minecraftforge.fluids.FluidUtil} since no one has time to make the forge util not a buggy mess
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FluidTransferUtil {
  private static final String KEY_FILLED = TConstruct.makeTranslationKey("block", "tank.filled");
  private static final String KEY_DRAINED = TConstruct.makeTranslationKey("block", "tank.drained");

  /**
   * Attempts to transfer fluid
   * @param input    Fluid source
   * @param output   Fluid destination
   * @param maxFill  Maximum to transfer
   * @return  True if transfer succeeded
   */
  public static FluidStack tryTransfer(IFluidHandler input, IFluidHandler output, int maxFill) {
    // first, figure out how much we can drain
    FluidStack simulated = input.drain(maxFill, true);
    if (!simulated.isEmpty()) {
      // next, find out how much we can fill
      long simulatedFill = output.fill(simulated, true);
      if (simulatedFill > 0) {
        // actually drain
        FluidStack drainedFluid = input.drain(simulatedFill, false);
        if (!drainedFluid.isEmpty()) {
          // acutally fill
          long actualFill = output.fill(drainedFluid.copy(), false);
          if (actualFill != drainedFluid.getAmount()) {
            TConstruct.LOG.error("Lost {} fluid during transfer", drainedFluid.getAmount() - actualFill);
          }
        }
        return drainedFluid;
      }
    }
    return FluidStack.EMPTY;
  }

  /**
   * Attempts to interact with a flilled bucket on a fluid tank. This is unique as it handles fish buckets, which don't expose fluid capabilities
   * @param world    World instance
   * @param pos      Block position
   * @param player   Player
   * @param hand     Hand
   * @param hit      Hit side
   * @param offset   Direction to place fish
   * @return True if using a bucket
   */
  public static boolean interactWithBucket(Level world, BlockPos pos, Player player, InteractionHand hand, Direction hit, Direction offset) {
    ItemStack held = player.getItemInHand(hand);
    if (held.getItem() instanceof BucketItem bucket) {
      Fluid fluid = ((BucketItemAccessor)bucket).fabric_getFluid();
      if (fluid != Fluids.EMPTY) {
        if (!world.isClientSide) {
          BlockEntity te = world.getBlockEntity(pos);
          if (te != null) {
            TransferUtil.getFluidHandler(te, hit)
              .ifPresent(handler -> {
                FluidStack fluidStack = new FluidStack(((BucketItemAccessor)bucket).fabric_getFluid(), FluidAttributes.BUCKET_VOLUME);
                // must empty the whole bucket
                if (handler.fill(fluidStack, true) == FluidAttributes.BUCKET_VOLUME) {
                  handler.fill(fluidStack, false);
                  bucket.checkExtraContent(player, world, held, pos.relative(offset));
                  world.playSound(null, pos, ((FluidExtensions)fluid).getAttributes().getEmptySound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                  player.displayClientMessage(new TranslatableComponent(KEY_FILLED, FluidAttributes.BUCKET_VOLUME, fluidStack.getDisplayName()), true);
                  if (!player.isCreative()) {
                    player.setItemInHand(hand, new ItemStack(held.getItem().getCraftingRemainingItem()));
                  }
                }
              });
          }
        }
        return true;
      }
    }
    return false;
  }

  /**
   * Base logic to interact with a tank
   * @param world   World instance
   * @param pos     Tank position
   * @param player  Player instance
   * @param hand    Hand used
   * @param hit     Hit position
   * @return  True if further interactions should be blocked, false otherwise
   */
  public static boolean interactWithFluidItem(Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    // success if the item is a fluid handler, regardless of if fluid moved
    ItemStack stack = player.getItemInHand(hand);
    Direction face = hit.getDirection();
    // fetch capability before copying, bit more work when its a fluid handler, but saves copying time when its not
    if (!stack.isEmpty() && TransferUtil.getFluidHandlerItem(stack).isPresent()) {
      // only server needs to transfer stuff
      if (!world.isClientSide) {
        BlockEntity te = world.getBlockEntity(pos);
        if (te != null) {
          LazyOptional<IFluidHandler> teCapability = TransferUtil.getFluidHandler(te, face);
          if (teCapability.isPresent()) {
            IFluidHandler teHandler = teCapability.orElse(EmptyFluidHandler.INSTANCE);
            ItemStack copy = ItemHandlerHelper.copyStackWithSize(stack, 1);
            TransferUtil.getFluidHandlerItem(copy).ifPresent(itemHandler -> {
              // first, try filling the TE from the item
              FluidStack transferred = tryTransfer(itemHandler, teHandler, Integer.MAX_VALUE);
              if (!transferred.isEmpty()) {
                world.playSound(null, pos, ((FluidExtensions)transferred.getFluid()).getAttributes().getEmptySound(transferred), SoundSource.BLOCKS, 1.0F, 1.0F);
                player.displayClientMessage(new TranslatableComponent(KEY_FILLED, transferred.getAmount(), transferred.getDisplayName()), true);
              } else {
                // if that failed, try filling the item handler from the TE
                transferred = tryTransfer(teHandler, itemHandler, Integer.MAX_VALUE);
                if (!transferred.isEmpty()) {
                  world.playSound(null, pos, ((FluidExtensions)transferred.getFluid()).getAttributes().getFillSound(transferred), SoundSource.BLOCKS, 1.0F, 1.0F);
                  player.displayClientMessage(new TranslatableComponent(KEY_DRAINED, transferred.getAmount(), transferred.getDisplayName()), true);
                }
              }
              // if either worked, update the player's inventory
              if (!transferred.isEmpty()) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, itemHandler.getContainer()));
              }
            });
          }
        }
      }
      return true;
    }
    return false;
  }

  /**
   * Utility to try fluid item then bucket
   * @param world   World instance
   * @param pos     Tank position
   * @param player  Player instance
   * @param hand    Hand used
   * @param hit     Hit position
   * @return  True if interacted
   */
  public static boolean interactWithTank(Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    return interactWithFluidItem(world, pos, player, hand, hit)
           || interactWithBucket(world, pos, player, hand, hit.getDirection(), hit.getDirection());
  }
}
