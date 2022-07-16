package slimeknights.tconstruct.plugin.rei.casting;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import slimeknights.tconstruct.library.recipe.casting.IDisplayableCastingRecipe;

import java.util.Collections;
import java.util.List;

public class CastingDisplay implements Display {

  private final CategoryIdentifier<CastingDisplay> id;
  private final IDisplayableCastingRecipe recipe;

  public CastingDisplay(CategoryIdentifier<CastingDisplay> id, IDisplayableCastingRecipe recipe) {
    this.id = id;
    this.recipe = recipe;
  }

  @Override
  public List<EntryIngredient> getInputEntries() {
    return Collections.emptyList();
  }

  @Override
  public List<EntryIngredient> getOutputEntries() {
    return Collections.emptyList();
  }

  @Override
  public CategoryIdentifier<?> getCategoryIdentifier() {
    return this.id;
  }

  public IDisplayableCastingRecipe getRecipe() {
    return recipe;
  }
}
