package slimeknights.tconstruct.tools.modifiers.traits.harvest;

import io.github.fabricators_of_create.porting_lib.event.PlayerBreakSpeedCallback;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.TooltipKey;

import javax.annotation.Nullable;
import java.util.List;

public class CrumblingModifier extends Modifier {
  @Override
  public void onBreakSpeed(IToolStackView tool, int level, PlayerBreakSpeedCallback.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
    if (!event.state.requiresCorrectToolForDrops()) {
      event.newSpeed = event.newSpeed + (level * 0.25f * tool.getMultiplier(ToolStats.MINING_SPEED) * miningSpeedModifier);
    }
  }

  @Override
  public void addInformation(IToolStackView tool, int level, @Nullable Player player, List<Component> tooltip, TooltipKey key, TooltipFlag flag) {
    addStatTooltip(tool, ToolStats.MINING_SPEED, TinkerTags.Items.HARVEST, level * 0.25f, tooltip);
  }
}
