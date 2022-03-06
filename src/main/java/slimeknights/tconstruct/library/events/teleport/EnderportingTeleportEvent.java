package slimeknights.tconstruct.library.events.teleport;

import io.github.fabricators_of_create.porting_lib.event.EntityTeleportCallback;
import io.github.fabricators_of_create.porting_lib.event.EntityTeleportCallback.EntityTeleportEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;

/** Event fired when an entity teleports using the enderporting modifier */
public class EnderportingTeleportEvent extends EntityTeleportEvent {
  public EnderportingTeleportEvent(LivingEntity entity, double targetX, double targetY, double targetZ) {
    super(entity, targetX, targetY, targetZ);
  }

  @Override
  public void sendEvent() {
    EntityTeleportCallback.EVENT.invoker().onTeleport(this);
  }
}
