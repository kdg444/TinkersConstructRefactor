package slimeknights.tconstruct.shared.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import slimeknights.tconstruct.shared.TinkerDamageTypes;

import java.util.concurrent.CompletableFuture;

public class TinkerDamageSourceProvider extends FabricTagProvider<DamageType> {

  public TinkerDamageSourceProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, Registries.DAMAGE_TYPE, registriesFuture);
  }

  @Override
  protected void addTags(HolderLookup.Provider arg) {
    getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR).addOptional(TinkerDamageTypes.SELF_DESTRUCT).addOptional(TinkerDamageTypes.PLAYER_ATTACK_BYPASS_ARMOR).addOptional(TinkerDamageTypes.MOB_ATTACK_BYPASS_ARMOR);
    getOrCreateTagBuilder(DamageTypeTags.IS_EXPLOSION).addOptional(TinkerDamageTypes.SELF_DESTRUCT).addOptional(TinkerDamageTypes.PLAYER_ATTACK_EXPLOSION).addOptional(TinkerDamageTypes.MOB_ATTACK_EXPLOSION);

    getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS).addOptional(TinkerDamageTypes.BLEEDING).addOptional(TinkerDamageTypes.PLAYER_ATTACK_MAGIC).addOptional(TinkerDamageTypes.MOB_ATTACK_MAGIC);

    getOrCreateTagBuilder(DamageTypeTags.IS_FIRE).addOptional(TinkerDamageTypes.SMELTERY_DAMAGE).addOptional(TinkerDamageTypes.PLAYER_ATTACK_FIRE).addOptional(TinkerDamageTypes.MOB_ATTACK_FIRE);
  }
}
