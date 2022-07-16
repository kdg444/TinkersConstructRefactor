package slimeknights.tconstruct.plugin.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.plugin.rei.casting.CastingDisplay;

public class TConstructREIConstants {

  // casting
  public static final CategoryIdentifier<CastingDisplay> CASTING_BASIN = type("casting_basin");
  public static final CategoryIdentifier<CastingDisplay> CASTING_TABLE = type("casting_table");
//  public static final CategoryIdentifier<MoldingRecipe> MOLDING = type("molding");

  private static <D extends Display> CategoryIdentifier<D> type(String name) {
    return CategoryIdentifier.of(TConstruct.MOD_ID, name);
  }
}
