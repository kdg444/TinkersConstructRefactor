package slimeknights.tconstruct.plugin.jei.fabric;

import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.Registry;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("removal")
public class FluidStackIngredientHelper implements IIngredientHelper<FluidStack> {

  @Override
  public IIngredientType<FluidStack> getIngredientType() {
    return JEITypes.FLUID_STACK;
  }

  @Override
  public String getDisplayName(FluidStack ingredient) {
    return ingredient.getDisplayName().getContents();
  }

  @Override
  public String getUniqueId(FluidStack ingredient, UidContext context) {
    return Registry.FLUID.getKey(ingredient.getFluid()).toString();
  }

  @Override
  public String getModId(FluidStack ingredient) {
    return Registry.FLUID.getKey(ingredient.getFluid()).getNamespace();
  }

  @Override
  public String getResourceId(FluidStack ingredient) {
    return Registry.FLUID.getKey(ingredient.getFluid()).getPath();
  }

  @Override
  public FluidStack copyIngredient(FluidStack ingredient) {
    return ingredient.copy();
  }

  @Override
  public String getErrorInfo(@Nullable FluidStack ingredient) {
    if (ingredient == null)
      return "FluidStack is null";
    return ingredient.getDisplayName().getContents();
  }
}
