package slimeknights.tconstruct.plugin.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.plugin.rei.casting.CastingDisplay;
import slimeknights.tconstruct.plugin.rei.melting.MeltingDisplay;

public class TConstructREIConstants {

  // casting
  public static final CategoryIdentifier<CastingDisplay> CASTING_BASIN = type("casting_basin");
  public static final CategoryIdentifier<CastingDisplay> CASTING_TABLE = type("casting_table");
//  public static final CategoryIdentifier<MoldingRecipe> MOLDING = type("molding");

  // melting
  public static final CategoryIdentifier<MeltingDisplay> MELTING = type("melting"/*, MeltingRecipe.class*/);
  public static final CategoryIdentifier<MeltingDisplay> ENTITY_MELTING = type("entity_melting"/*, EntityMeltingRecipe.class*/);
  public static final CategoryIdentifier<AlloyDisplay> ALLOY = type("alloy");
//  public static final CategoryIdentifier<MeltingRecipe> FOUNDRY = type("foundry", MeltingRecipe.class);

  private static <D extends Display> CategoryIdentifier<D> type(String name) {
    return CategoryIdentifier.of(TConstruct.MOD_ID, name);
  }
}
