package slimeknights.tconstruct.tools.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

/** Armor model that wraps another armor model */
public class ArmorModelHelper {
  /** Buffer from the render living event, stored as we lose access to it later */
  @Nullable
  static MultiBufferSource buffer;

  /** Iniitalizes the wrapper */
  public static void init() {
    // register listeners to set and clear the buffer
//    MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, false, RenderLivingEvent.Pre.class, event -> buffer = event.getMultiBufferSource()); TODO: PORT
//    MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, false, RenderLivingEvent.Post.class, event -> buffer = null);
  }

  /** Handles the unchecked cast to copy entity model properties */
  @SuppressWarnings("unchecked")
  static <T extends LivingEntity> void copyProperties(EntityModel<T> base, EntityModel<?> other) {
    base.copyPropertiesTo((EntityModel<T>)other);
  }
}
