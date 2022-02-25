package slimeknights.tconstruct.common.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.data.recipe.IRecipeHelper;

import java.util.function.Consumer;

/**
 * Shared logic for each module's recipe provider
 */
public abstract class BaseRecipeProvider extends FabricRecipesProvider implements /*IConditionBuilder,*/ IRecipeHelper {
  public BaseRecipeProvider(FabricDataGenerator generator) {
    super(generator);
  }

  @Override
  protected abstract void generateRecipes(Consumer<FinishedRecipe> consumer);

  @Override
  public abstract String getName();

  @Override
  public String getModId() {
    return TConstruct.MOD_ID;
  }
}
