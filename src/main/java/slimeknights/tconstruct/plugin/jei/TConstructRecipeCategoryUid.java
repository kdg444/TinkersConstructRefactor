package slimeknights.tconstruct.plugin.jei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipe;
import slimeknights.tconstruct.library.recipe.casting.ICastingRecipe;
import slimeknights.tconstruct.library.recipe.entitymelting.EntityMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.severing.SeveringRecipe;
import slimeknights.tconstruct.library.recipe.molding.MoldingRecipe;
import slimeknights.tconstruct.library.recipe.partbuilder.IDisplayPartBuilderRecipe;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationRecipe;
import slimeknights.tconstruct.plugin.TinkersDisplay;

// TODO: constant case
public class TConstructRecipeCategoryUid {
  public static final ResourceLocation pluginUid = TConstruct.getResource("rei_plugin");

  // casting
  public static final CategoryIdentifier<TinkersDisplay<ICastingRecipe>> castingBasin = CategoryIdentifier.of(TConstruct.getResource("casting_basin"));
  public static final CategoryIdentifier<TinkersDisplay<ICastingRecipe>> castingTable = CategoryIdentifier.of(TConstruct.getResource("casting_table"));
  public static final CategoryIdentifier<TinkersDisplay<MoldingRecipe>> molding = CategoryIdentifier.of(TConstruct.getResource("molding"));

  // melting
  public static final CategoryIdentifier<TinkersDisplay<MeltingRecipe>> melting = CategoryIdentifier.of(TConstruct.getResource("melting"));
  public static final CategoryIdentifier<TinkersDisplay<EntityMeltingRecipe>> entityMelting = CategoryIdentifier.of(TConstruct.getResource("entity_melting"));
  public static final CategoryIdentifier<TinkersDisplay<AlloyRecipe>> alloy = CategoryIdentifier.of(TConstruct.getResource("alloy"));
  public static final CategoryIdentifier<TinkersDisplay<MeltingRecipe>> foundry = CategoryIdentifier.of(TConstruct.getResource("foundry"));

  // tinker station
  public static final CategoryIdentifier<TinkersDisplay<ITinkerStationRecipe>> modifiers = CategoryIdentifier.of(TConstruct.getResource("modifiers"));
  public static final CategoryIdentifier<TinkersDisplay<SeveringRecipe>> severing = CategoryIdentifier.of(TConstruct.getResource("severing"));

  // part builder
  public static final CategoryIdentifier<TinkersDisplay<IDisplayPartBuilderRecipe>> partBuilder = CategoryIdentifier.of(TConstruct.getResource("part_builder"));
}
