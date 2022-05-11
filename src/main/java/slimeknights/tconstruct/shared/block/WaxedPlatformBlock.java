package slimeknights.tconstruct.shared.block;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import io.github.fabricators_of_create.porting_lib.util.ToolAction;
import io.github.fabricators_of_create.porting_lib.util.ToolActions;
import net.minecraft.world.phys.BlockHitResult;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.shared.TinkerCommons;

public class WaxedPlatformBlock extends PlatformBlock {
  private final WeatherState age;
  public WaxedPlatformBlock(WeatherState age, Properties prop) {
    super(prop);
    this.age = age;
    UseBlockCallback.EVENT.register(this::getToolModifiedState); // TODO: Move this to a static init method
  }

  @Override
  protected boolean verticalConnect(BlockState state) {
    return state.is(TinkerTags.Blocks.COPPER_PLATFORMS);
  }

  public InteractionResult getToolModifiedState(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
    if (player.getItemInHand(hand).is(FabricToolTags.AXES) && world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof WaxedPlatformBlock) {
      world.setBlock(hitResult.getBlockPos(), TinkerCommons.copperPlatform.get(age).withPropertiesOf(world.getBlockState(hitResult.getBlockPos())), Block.UPDATE_ALL);
      return InteractionResult.SUCCESS;
    }
    return InteractionResult.PASS;
  }
}
