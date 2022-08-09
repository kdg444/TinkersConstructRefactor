package slimeknights.tconstruct.plugin.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.recipe.partbuilder.Pattern;
import slimeknights.tconstruct.plugin.rei.casting.CastingDisplay;
import slimeknights.tconstruct.plugin.rei.melting.MeltingDisplay;
import slimeknights.tconstruct.plugin.rei.modifiers.ModifierRecipeDisplay;
import slimeknights.tconstruct.plugin.rei.partbuilder.PartBuilderDisplay;

public class TConstructREIConstants {
  // ingredient types
  @SuppressWarnings("rawtypes")
//  public static final IIngredientType<EntityType> ENTITY_TYPE = () -> EntityType.class;
  public static final EntryType<ModifierEntry> MODIFIER_TYPE = EntryType.deferred(TConstruct.getResource("modifier_entry"));
  public static final EntryType<Pattern> PATTERN_TYPE = EntryType.deferred(TConstruct.getResource("pattern"));

  // casting
  public static final CategoryIdentifier<CastingDisplay> CASTING_BASIN = type("casting_basin");
  public static final CategoryIdentifier<CastingDisplay> CASTING_TABLE = type("casting_table");
//  public static final CategoryIdentifier<MoldingRecipe> MOLDING = type("molding");

  // melting
  public static final CategoryIdentifier<MeltingDisplay> MELTING = type("melting");
  public static final CategoryIdentifier<MeltingDisplay> ENTITY_MELTING = type("entity_melting"/*, EntityMeltingRecipe.class*/);
  public static final CategoryIdentifier<AlloyDisplay> ALLOY = type("alloy");
  public static final CategoryIdentifier<MeltingDisplay> FOUNDRY = type("foundry");

  // tinker station
  public static final CategoryIdentifier<ModifierRecipeDisplay> MODIFIERS = type("modifiers");

  // part builder
  public static final CategoryIdentifier<PartBuilderDisplay> PART_BUILDER = type("part_builder");

  private static <D extends Display> CategoryIdentifier<D> type(String name) {
    return CategoryIdentifier.of(TConstruct.MOD_ID, name);
  }
}
