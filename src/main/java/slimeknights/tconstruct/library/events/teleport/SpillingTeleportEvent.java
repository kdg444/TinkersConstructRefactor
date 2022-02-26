package slimeknights.tconstruct.library.events.teleport;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import slimeknights.mantle.lib.util.MantleEvent;

/** Event fired when an entity teleports via the spilling effect */
public class SpillingTeleportEvent extends MantleEvent.EntityTeleportEvent {
  public static Event<TeleportSpilling> EVENT = EventFactory.createArrayBacked(TeleportSpilling.class, callbacks -> event -> {
    for(TeleportSpilling e : callbacks)
      e.onTeleport(event);
  });

  public SpillingTeleportEvent(Entity entity, double targetX, double targetY, double targetZ) {
    super(entity, targetX, targetY, targetZ);
  }

  @Override
  public void sendEvent() {
    EVENT.invoker().onTeleport(this);
  }

  @FunctionalInterface
  public interface TeleportSpilling {
    void onTeleport(SpillingTeleportEvent event);
  }
}
