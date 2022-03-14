package slimeknights.tconstruct.plugin.jei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;
import slimeknights.tconstruct.plugin.TinkersDisplay;

// TODO: constant case
public class TConstructRecipeCategoryUid {
  public static final ResourceLocation pluginUid = TConstruct.getResource("rei_plugin");

  // casting
  public static final CategoryIdentifier<TinkersDisplay<?>> castingBasin = CategoryIdentifier.of(TConstruct.getResource("casting_basin"));
  public static final CategoryIdentifier<TinkersDisplay<?>> castingTable = CategoryIdentifier.of(TConstruct.getResource("casting_table"));
  public static final CategoryIdentifier<TinkersDisplay<?>> molding = CategoryIdentifier.of(TConstruct.getResource("molding"));

  // melting
  public static final CategoryIdentifier<TinkersDisplay<MeltingRecipe>> melting = CategoryIdentifier.of(TConstruct.getResource("melting"));
  public static final CategoryIdentifier<TinkersDisplay<MeltingRecipe>> entityMelting = CategoryIdentifier.of(TConstruct.getResource("entity_melting"));
  public static final CategoryIdentifier<TinkersDisplay<AlloyRecipe>> alloy = CategoryIdentifier.of(TConstruct.getResource("alloy"));
  public static final CategoryIdentifier<TinkersDisplay<MeltingRecipe>> foundry = CategoryIdentifier.of(TConstruct.getResource("foundry"));

  // tinker station
  public static final CategoryIdentifier<TinkersDisplay<?>> modifiers = CategoryIdentifier.of(TConstruct.getResource("modifiers"));
  public static final CategoryIdentifier<TinkersDisplay<?>> severing = CategoryIdentifier.of(TConstruct.getResource("severing"));

  // part builder
  public static final CategoryIdentifier<TinkersDisplay<?>> partBuilder = CategoryIdentifier.of(TConstruct.getResource("part_builder"));
}
