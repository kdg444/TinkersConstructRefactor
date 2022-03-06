package slimeknights.tconstruct.library.events.teleport;

import io.github.fabricators_of_create.porting_lib.event.EntityTeleportCallback;
import io.github.fabricators_of_create.porting_lib.event.EntityTeleportCallback.EntityTeleportEvent;
import net.minecraft.world.entity.Entity;

/** Event fired when an entity teleports via the spilling effect */
public class SpillingTeleportEvent extends EntityTeleportEvent {
  public SpillingTeleportEvent(Entity entity, double targetX, double targetY, double targetZ) {
    super(entity, targetX, targetY, targetZ);
  }

  @Override
  public void sendEvent() {
    EntityTeleportCallback.EVENT.invoker().onTeleport(this);
  }
}
