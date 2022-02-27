package slimeknights.tconstruct.tools.modifiers.upgrades.armor;

import io.github.fabricators_of_create.porting_lib.event.LivingEntityEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability.TinkerDataKey;

public class RespirationModifier extends TotalArmorLevelModifier {
  private static final TinkerDataKey<Integer> RESPIRATION = TConstruct.createKey("respiration");
  public RespirationModifier() {
    super(RESPIRATION);
    LivingEntityEvents.TICK.register(RespirationModifier::livingTick);
  }

  /** Big mess of conditions from living tick for when air goes down */
  private static boolean isLosingAir(LivingEntity living) {
    return living.isAlive()
           && !living.canBreatheUnderwater()
           && living.isEyeInFluid(FluidTags.WATER)
           && !MobEffectUtil.hasWaterBreathing(living)
           && !(living instanceof Player player && player.getAbilities().invulnerable)
           && !living.level.getBlockState(new BlockPos(living.getX(), living.getEyeY(), living.getZ())).is(Blocks.BUBBLE_COLUMN);
  }

  /** Called before air is lost to add an air buffer */
  private static void livingTick(LivingEntity living) {
    if (living.isSpectator()) {
      return;
    }
    TinkerDataCapability.CAPABILITY.maybeGet(living).ifPresent(data -> {
      int respiration = data.get(RESPIRATION, 0);
      int air = living.getAirSupply();
      // vanilla has a chance of not losing air with the effect, easiest to implement is just giving some air back
      if (respiration > 0 && air < living.getMaxAirSupply() && isLosingAir(living) && RANDOM.nextInt(respiration + 1) > 0) {
        living.setAirSupply(air + 1);
      }
    });
  }
}
