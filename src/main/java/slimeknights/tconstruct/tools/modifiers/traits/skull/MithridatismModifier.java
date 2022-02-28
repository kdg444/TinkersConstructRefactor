package slimeknights.tconstruct.tools.modifiers.traits.skull;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.mantle.lib.event.PotionEvents;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability.TinkerDataKey;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;

public class MithridatismModifier extends TotalArmorLevelModifier {
  private static final TinkerDataKey<Integer> MITHRIDATISM = TConstruct.createKey("mithridatism");
  public MithridatismModifier() {
    super(MITHRIDATISM, true);
    PotionEvents.POTION_APPLICABLE.register(MithridatismModifier::isPotionApplicable);
  }

  /** Prevents poison on the entity */
  private static void isPotionApplicable(PotionEvents.PotionApplicableEvent event) {
    if (event.getPotionEffect().getEffect() == MobEffects.POISON && ModifierUtil.getTotalModifierLevel((LivingEntity) event.getEntity(), MITHRIDATISM) > 0) {
      event.setResult(InteractionResult.FAIL);
    }
  }
}
