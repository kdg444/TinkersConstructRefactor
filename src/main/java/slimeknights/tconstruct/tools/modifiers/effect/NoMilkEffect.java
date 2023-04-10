package slimeknights.tconstruct.tools.modifiers.effect;

import io.github.fabricators_of_create.porting_lib.extensions.MobEffectExtensions;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.common.TinkerEffect;

import java.util.ArrayList;
import java.util.List;

/** Effect that cannot be cured with milk */
public class NoMilkEffect extends TinkerEffect implements MobEffectExtensions {
  public NoMilkEffect(MobEffectCategory typeIn, int color, boolean show) {
    super(typeIn, color, show);
  }

  @Override
  public List<ItemStack> getCurativeItems() {
    return new ArrayList<>();
  }
}
