package slimeknights.tconstruct.plugin.jei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipe;
import slimeknights.tconstruct.library.recipe.casting.ICastingRecipe;
import slimeknights.tconstruct.library.recipe.entitymelting.EntityMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.severing.SeveringRecipe;
import slimeknights.tconstruct.library.recipe.molding.MoldingRecipe;
import slimeknights.tconstruct.library.recipe.partbuilder.IDisplayPartBuilderRecipe;
import slimeknights.tconstruct.plugin.TinkersDisplay;

public class TConstructJEIConstants {
  public static final ResourceLocation PLUGIN = TConstruct.getResource("jei_plugin");

  // ingredient types
  @SuppressWarnings("rawtypes")
//  public static final IIngredientType<EntityType> ENTITY_TYPE = () -> EntityType.class;
//  public static final IIngredientType<ModifierEntry> MODIFIER_TYPE = () -> ModifierEntry.class;
//  public static final IIngredientType<Pattern> PATTERN_TYPE = () -> Pattern.class;

  // casting
  public static final CategoryIdentifier<TinkersDisplay<ICastingRecipe>> CASTING_BASIN = CategoryIdentifier.of(TConstruct.getResource("casting_basin"));
  public static final CategoryIdentifier<TinkersDisplay<ICastingRecipe>> CASTING_TABLE = CategoryIdentifier.of(TConstruct.getResource("casting_table"));
  public static final CategoryIdentifier<TinkersDisplay<MoldingRecipe>> MOLDING = CategoryIdentifier.of(TConstruct.getResource("molding"));

  // melting
  public static final CategoryIdentifier<TinkersDisplay<MeltingRecipe>> MELTING = CategoryIdentifier.of(TConstruct.getResource("melting"));
  public static final CategoryIdentifier<TinkersDisplay<EntityMeltingRecipe>> ENTITY_MELTING = CategoryIdentifier.of(TConstruct.getResource("entity_melting"));
  public static final CategoryIdentifier<TinkersDisplay<AlloyRecipe>> ALLOY = CategoryIdentifier.of(TConstruct.getResource("alloy"));
  public static final CategoryIdentifier<TinkersDisplay<MeltingRecipe>> FOUNDRY = CategoryIdentifier.of(TConstruct.getResource("foundry"));

  // tinker station
  public static final CategoryIdentifier<TinkersDisplay<ModifierRecipe>> MODIFIERS = CategoryIdentifier.of(TConstruct.getResource("modifiers"));
  public static final CategoryIdentifier<TinkersDisplay<SeveringRecipe>> SEVERING = CategoryIdentifier.of(TConstruct.getResource("severing"));

  // part builder
  public static final CategoryIdentifier<TinkersDisplay<IDisplayPartBuilderRecipe>> PART_BUILDER = CategoryIdentifier.of(TConstruct.getResource("part_builder"));
}
