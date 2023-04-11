package slimeknights.tconstruct.shared;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import slimeknights.tconstruct.TConstruct;

public class TinkerDamageTypes {
  public static final ResourceKey<DamageType> SELF_DESTRUCT = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("self_destruct"));
  public static final ResourceKey<DamageType> BLEEDING = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("bleed"));

  public static void bootstrap(BootstapContext<DamageType> context) {
    context.register(SELF_DESTRUCT, new DamageType(TConstruct.prefix("self_destruct"), 0.1F));
    context.register(BLEEDING, new DamageType(TConstruct.prefix("bleed"), 0.1F));
  }
}
