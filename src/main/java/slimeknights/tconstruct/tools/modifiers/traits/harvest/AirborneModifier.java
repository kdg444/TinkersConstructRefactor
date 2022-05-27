package slimeknights.tconstruct.tools.modifiers.traits.harvest;

import net.minecraft.core.Direction;
import io.github.fabricators_of_create.porting_lib.event.common.PlayerBreakSpeedCallback.BreakSpeed;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class AirborneModifier extends NoLevelsModifier {
  @Override
  public int getPriority() {
    return 75; // runs after other modifiers
  }

  @Override
  public void onBreakSpeed(IToolStackView tool, int level, BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
    // the speed is reduced when not on the ground, cancel out
    if (!event.player.isOnGround()) { // TODO: PORT? getPlayer used to be getEntity idk if they are the same
      event.newSpeed = event.newSpeed * 5;
    }
  }
}
