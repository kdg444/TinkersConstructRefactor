package slimeknights.tconstruct.plugin;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public class TinkersDisplay<R extends Recipe<?>> implements Display {

  private final R recipe;
  private final CategoryIdentifier<TinkersDisplay<R>> uid;

  public TinkersDisplay(R recipe, CategoryIdentifier<TinkersDisplay<R>> id) {
    this.recipe = recipe;
    this.uid = id;
  }

  public R getRecipe() {
    return recipe;
  }

  @Override
  public List<EntryIngredient> getInputEntries() {
    return null;
  }

  @Override
  public List<EntryIngredient> getOutputEntries() {
    return null;
  }

  @Override
  public CategoryIdentifier<?> getCategoryIdentifier() {
    return uid;
  }
}
