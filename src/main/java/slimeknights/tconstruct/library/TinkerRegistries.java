package slimeknights.tconstruct.library;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;

/**
 * Class containing any data that does not have a dedicated registry class. Mostly TiC registries backed by {@link IForgeRegistry}.
 */
public class TinkerRegistries {
  /** Resource location for empty objects */
  public static final ResourceLocation EMPTY = TConstruct.getResource("empty");

  /** Register for modifiers */
  public static final Registry<Modifier> MODIFIERS = FabricRegistryBuilder.createDefaulted(Modifier.class, TConstruct.getResource("modifiers"), EMPTY);
}
