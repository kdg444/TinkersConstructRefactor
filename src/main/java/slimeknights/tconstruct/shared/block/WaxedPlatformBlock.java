package slimeknights.tconstruct.shared.block;

import io.github.fabricators_of_create.porting_lib.util.ToolAction;
import io.github.fabricators_of_create.porting_lib.util.ToolActions;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
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

  @Nullable
  @Override
  public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
    if (ToolActions.AXE_WAX_OFF.equals(toolAction)) {
      return TinkerCommons.copperPlatform.get(age).withPropertiesOf(state);
    }
    return null;
  }

  @Deprecated
  @SuppressWarnings("removal")
  @Override
  public BlockState getToolModifiedState(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack, ToolAction toolAction) {
    if (ToolActions.AXE_WAX_OFF.equals(toolAction)) {
      return TinkerCommons.copperPlatform.get(age).withPropertiesOf(state);
    }
    return InteractionResult.PASS;
  }
}
