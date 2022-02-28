package slimeknights.tconstruct.tools.modifiers.traits.harvest;

import net.minecraft.core.Direction;
import slimeknights.mantle.lib.event.PlayerBreakSpeedCallback.BreakSpeed;
import slimeknights.tconstruct.library.modifiers.impl.SingleUseModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class AirborneModifier extends SingleUseModifier {
  @Override
  public int getPriority() {
    return 75; // runs after other modifiers
  }

  @Override
  public void onBreakSpeed(IToolStackView tool, int level, BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
    // the speed is reduced when not on the ground, cancel out
    if (!event.getPlayer().isOnGround()) { // TODO: PORT? getPlayer used to be getEntity idk if they are the same
      event.setNewSpeed(event.getNewSpeed() * 5);
    }
  }
}
