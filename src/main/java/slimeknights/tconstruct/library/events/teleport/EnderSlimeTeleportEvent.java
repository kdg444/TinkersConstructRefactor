package slimeknights.tconstruct.library.events.teleport;

import lombok.Getter;
import lombok.Setter;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;
import io.github.fabricators_of_create.porting_lib.util.MantleEvent;
import slimeknights.tconstruct.world.entity.EnderSlimeEntity;

/* Fired when an ender slime teleport or teleports another entity */
public class EnderSlimeTeleportEvent extends MantleEvent.EntityTeleportEvent {

  public static Event<TeleportEvent> EVENT = EventFactory.createArrayBacked(TeleportEvent.class, callbacks -> event -> {
    for(TeleportEvent e : callbacks)
      e.onTeleport(event);
  });

  /** Gets the slime that caused this teleport. If this is the same as {@link #getEntity()} then the slime is teleporting itself */
  @Getter
  private final EnderSlimeEntity slime;

  public EnderSlimeTeleportEvent(LivingEntity entity, double targetX, double targetY, double targetZ, EnderSlimeEntity slime) {
    super(entity, targetX, targetY, targetZ);
    this.slime = slime;
  }

  /** Checks if the enderslime is teleporting itself */
  public boolean isTeleportingSelf() {
    return getEntity() == slime;
  }

  @Override
  public void sendEvent() {
    EVENT.invoker().onTeleport(this);
  }

  @FunctionalInterface
  public interface TeleportEvent {
    void onTeleport(EnderSlimeTeleportEvent event);
  }
}
