package slimeknights.tconstruct.plugin.rei.modifiers;

import lombok.Getter;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IDisplayModifierRecipe;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.plugin.rei.TConstructREIConstants;

import java.util.List;

public class ModifierRecipeDisplay implements Display {
  @Getter
  private final IDisplayModifierRecipe recipe;
  @Getter
  private final List<EntryIngredient> inputEntries, outputEntries;

  public ModifierRecipeDisplay(IDisplayModifierRecipe recipe) {
    this.recipe = recipe;
    this.inputEntries = List.of(
      EntryIngredients.ofItemStacks(recipe.getDisplayItems(0)),
      EntryIngredients.ofItemStacks(recipe.getDisplayItems(1)),
      EntryIngredients.ofItemStacks(recipe.getDisplayItems(2)),
      EntryIngredients.ofItemStacks(recipe.getDisplayItems(3)),
      EntryIngredients.ofItemStacks(recipe.getDisplayItems(4))
    );
    this.outputEntries = List.of(EntryIngredient.of(EntryStack.of(TConstructREIConstants.MODIFIER_TYPE, recipe.getDisplayResult())));
  }

  @Override
  public CategoryIdentifier<ModifierRecipeDisplay> getCategoryIdentifier() {
    return TConstructREIConstants.MODIFIERS;
  }

  public boolean hasRequirements() {
    return recipe.hasRequirements();
  }

  public boolean isIncremental() {
    return recipe.isIncremental();
  }

  public int getMaxLevel() {
    return recipe.getMaxLevel();
  }

  public SlotType.SlotCount getSlots() {
    return recipe.getSlots();
  }

  public String getRequirementsError() {
    return recipe.getRequirementsError();
  }
}
