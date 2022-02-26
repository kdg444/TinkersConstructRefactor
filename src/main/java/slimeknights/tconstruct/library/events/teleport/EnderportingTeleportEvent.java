package slimeknights.tconstruct.library.events.teleport;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.mantle.lib.util.MantleEvent;

/** Event fired when an entity teleports using the enderporting modifier */
public class EnderportingTeleportEvent extends MantleEvent.EntityTeleportEvent {
  public static Event<Teleport> EVENT = EventFactory.createArrayBacked(Teleport.class, callbacks -> event -> {
    for(Teleport e : callbacks)
      e.onTeleport(event);
  });

  public EnderportingTeleportEvent(LivingEntity entity, double targetX, double targetY, double targetZ) {
    super(entity, targetX, targetY, targetZ);
  }

  @Override
  public void sendEvent() {
    EVENT.invoker().onTeleport(this);
  }

  @FunctionalInterface
  public interface Teleport {
    void onTeleport(EnderportingTeleportEvent event);
  }
}
