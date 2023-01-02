package slimeknights.tconstruct.tools.modifiers.traits.harvest;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import io.github.fabricators_of_create.porting_lib.event.common.PlayerBreakSpeedCallback.BreakSpeed;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.TinkerHooks;
import slimeknights.tconstruct.library.modifiers.hook.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierHookMap.Builder;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class AirborneModifier extends NoLevelsModifier implements ConditionalStatModifierHook {
  @Override
  public int getPriority() {
    return 75; // runs after other modifiers
  }

  @Override
  protected void registerHooks(Builder hookBuilder) {
    hookBuilder.addHook(this, TinkerHooks.CONDITIONAL_STAT);
  }

  @Override
  public void onBreakSpeed(IToolStackView tool, int level, BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
    // the speed is reduced when not on the ground, cancel out
    if (!event.player.isOnGround()) { // TODO: PORT? getPlayer used to be getEntity idk if they are the same
      event.newSpeed = event.newSpeed * 5;
    }
  }

  @Override
  public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
    if (stat == ToolStats.ACCURACY && !living.isOnGround() && !living.onClimbable()) {
      return baseValue + 0.5f;
    }
    return baseValue;
  }
}
