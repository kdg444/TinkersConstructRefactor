package slimeknights.tconstruct.plugin.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeManager;
import slimeknights.mantle.recipe.helper.RecipeHelper;
import slimeknights.tconstruct.library.recipe.TinkerRecipeTypes;
import slimeknights.tconstruct.library.recipe.casting.IDisplayableCastingRecipe;
import slimeknights.tconstruct.plugin.rei.casting.CastingBasinCategory;
import slimeknights.tconstruct.plugin.rei.casting.CastingDisplay;
import slimeknights.tconstruct.plugin.rei.casting.CastingTableCategory;

import java.util.List;

public class REIPlugin implements REIClientPlugin {

  @Override
  public void registerCategories(CategoryRegistry registry) {
    // casting
    registry.add(new CastingBasinCategory());
    registry.add(new CastingTableCategory());
  }

  @Override
  public void registerDisplays(DisplayRegistry registry) {
    assert Minecraft.getInstance().level != null;
    RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
    // casting
    List<IDisplayableCastingRecipe> castingBasinRecipes = RecipeHelper.getJEIRecipes(manager, TinkerRecipeTypes.CASTING_BASIN.get(), IDisplayableCastingRecipe.class);
    castingBasinRecipes.forEach(castingBasinRecipe -> registry.add(new CastingDisplay(TConstructREIConstants.CASTING_BASIN, castingBasinRecipe)));
    List<IDisplayableCastingRecipe> castingTableRecipes = RecipeHelper.getJEIRecipes(manager, TinkerRecipeTypes.CASTING_TABLE.get(), IDisplayableCastingRecipe.class);
    castingTableRecipes.forEach(castingTableRecipe -> registry.add(new CastingDisplay(TConstructREIConstants.CASTING_TABLE, castingTableRecipe)));
  }
}
