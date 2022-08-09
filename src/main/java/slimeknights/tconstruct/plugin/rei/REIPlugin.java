package slimeknights.tconstruct.plugin.rei;

import io.github.fabricators_of_create.porting_lib.mixin.common.accessor.RecipeManagerAccessor;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.minecraft.client.Minecraft;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import slimeknights.mantle.recipe.helper.RecipeHelper;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.recipe.TinkerRecipeTypes;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipe;
import slimeknights.tconstruct.library.recipe.casting.IDisplayableCastingRecipe;
import slimeknights.tconstruct.library.recipe.fuel.MeltingFuel;
import slimeknights.tconstruct.library.recipe.material.MaterialRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IDisplayModifierRecipe;
import slimeknights.tconstruct.library.recipe.molding.MoldingRecipe;
import slimeknights.tconstruct.library.recipe.partbuilder.IDisplayPartBuilderRecipe;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.plugin.jei.melting.MeltingFuelHandler;
import slimeknights.tconstruct.plugin.jei.partbuilder.MaterialItemList;
import slimeknights.tconstruct.plugin.rei.casting.CastingBasinCategory;
import slimeknights.tconstruct.plugin.rei.casting.CastingDisplay;
import slimeknights.tconstruct.plugin.rei.casting.CastingTableCategory;
import slimeknights.tconstruct.plugin.rei.melting.FoundryCategory;
import slimeknights.tconstruct.plugin.rei.melting.MeltingCategory;
import slimeknights.tconstruct.plugin.rei.melting.MeltingDisplay;
import slimeknights.tconstruct.plugin.rei.modifiers.ModifierEntryDefinition;
import slimeknights.tconstruct.plugin.rei.modifiers.ModifierRecipeCategory;
import slimeknights.tconstruct.plugin.rei.modifiers.ModifierRecipeDisplay;
import slimeknights.tconstruct.plugin.rei.partbuilder.PartBuilderCategory;
import slimeknights.tconstruct.plugin.rei.partbuilder.PartBuilderDisplay;
import slimeknights.tconstruct.plugin.rei.partbuilder.PatternEntryDefinition;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tables.TinkerTables;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class REIPlugin implements REIClientPlugin {

  @Override
  public void registerCategories(CategoryRegistry registry) {
    // casting
    registry.add(new CastingBasinCategory());
    registry.add(new CastingTableCategory());
    // melting and casting
    registry.add(new MeltingCategory());
    registry.add(new AlloyRecipeCategory());
//    registry.add(new EntityMeltingRecipeCategory());
    registry.add(new FoundryCategory());
    // tinker station
    registry.add(new ModifierRecipeCategory());
//    registry.add(new SeveringCategory());
    // part builder
    registry.add(new PartBuilderCategory());

    // tables
    registry.addWorkstations(TConstructREIConstants.PART_BUILDER, EntryStacks.of(TinkerTables.partBuilder));
    registry.addWorkstations(TConstructREIConstants.MODIFIERS, EntryStacks.of(TinkerTables.tinkerStation));
    registry.addWorkstations(TConstructREIConstants.MODIFIERS, EntryStacks.of(TinkerTables.tinkersAnvil));
    registry.addWorkstations(TConstructREIConstants.MODIFIERS, EntryStacks.of(TinkerTables.scorchedAnvil));

    // smeltery
    registry.addWorkstations(TConstructREIConstants.MELTING, EntryStacks.of(TinkerSmeltery.searedMelter));
    registry.addWorkstations(BuiltinPlugin.FUEL, EntryStacks.of(TinkerSmeltery.searedHeater));
    addCastingCatalyst(registry, TinkerSmeltery.searedTable, TConstructREIConstants.CASTING_TABLE, TinkerRecipeTypes.MOLDING_TABLE.get());
    addCastingCatalyst(registry, TinkerSmeltery.searedBasin, TConstructREIConstants.CASTING_BASIN, TinkerRecipeTypes.MOLDING_BASIN.get());
    addCatalysts(registry, EntryStacks.of(TinkerSmeltery.smelteryController), TConstructREIConstants.MELTING, TConstructREIConstants.ALLOY, TConstructREIConstants.ENTITY_MELTING);

    // foundry
    registry.addWorkstations(TConstructREIConstants.ALLOY, EntryStacks.of(TinkerSmeltery.scorchedAlloyer));
    addCastingCatalyst(registry, TinkerSmeltery.scorchedTable, TConstructREIConstants.CASTING_TABLE, TinkerRecipeTypes.MOLDING_TABLE.get());
    addCastingCatalyst(registry, TinkerSmeltery.scorchedBasin, TConstructREIConstants.CASTING_BASIN, TinkerRecipeTypes.MOLDING_BASIN.get());
    registry.addWorkstations(TConstructREIConstants.FOUNDRY, EntryStacks.of(TinkerSmeltery.foundryController));

    // modifiers
//    for (Holder<Item> item : Objects.requireNonNull(Registry.ITEM.getTagOrEmpty(TinkerTags.Items.MELEE))) {
//      registry.addWorkstations(TConstructREIConstants.SEVERING, EntryStacks.of(IModifiableDisplay.getDisplayStack(item.value())));
//    }
  }

  @Override
  public void registerEntryTypes(EntryTypeRegistry registry) {
    registry.register(TConstructREIConstants.MODIFIER_TYPE, new ModifierEntryDefinition());
    registry.register(TConstructREIConstants.PATTERN_TYPE, new PatternEntryDefinition());
  }

  @Override
  public void registerEntries(EntryRegistry registry) {
    RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
    List<ModifierEntry> modifiers = Collections.emptyList();
    if (Config.CLIENT.showModifiersInJEI.get()) {
      modifiers = RecipeHelper.getJEIRecipes(manager, TinkerRecipeTypes.TINKER_STATION.get(), IDisplayModifierRecipe.class)
        .stream()
        .map(recipe -> recipe.getDisplayResult().getModifier())
        .distinct()
        .sorted(Comparator.comparing(Modifier::getId))
        .map(mod -> new ModifierEntry(mod, 1))
        .collect(Collectors.toList());
    }

    modifiers.forEach(entry -> registry.addEntry(EntryStack.of(TConstructREIConstants.MODIFIER_TYPE, entry)));
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
    // melting
    List<MeltingRecipe> meltingRecipes = RecipeHelper.getJEIRecipes(manager, TinkerRecipeTypes.MELTING.get(), MeltingRecipe.class);
    meltingRecipes.forEach(meltingRecipe -> registry.add(new MeltingDisplay(meltingRecipe, TConstructREIConstants.MELTING), meltingRecipes));
    meltingRecipes.forEach(meltingRecipe -> registry.add(new MeltingDisplay(meltingRecipe, TConstructREIConstants.FOUNDRY), meltingRecipes));
    MeltingFuelHandler.setMeltngFuels(RecipeHelper.getRecipes(manager, TinkerRecipeTypes.FUEL.get(), MeltingFuel.class));

    // alloying
    List<AlloyRecipe> alloyRecipes = RecipeHelper.getJEIRecipes(manager, TinkerRecipeTypes.ALLOYING.get(), AlloyRecipe.class);
    alloyRecipes.forEach(alloyRecipe -> registry.add(new AlloyDisplay(alloyRecipe)));

    // modifiers
    List<IDisplayModifierRecipe> modifierRecipes = RecipeHelper.getJEIRecipes(manager, TinkerRecipeTypes.TINKER_STATION.get(), IDisplayModifierRecipe.class)
      .stream()
      .sorted((r1, r2) -> {
        SlotType t1 = r1.getSlotType();
        SlotType t2 = r2.getSlotType();
        String n1 = t1 == null ? "zzzzzzzzzz" : t1.getName();
        String n2 = t2 == null ? "zzzzzzzzzz" : t2.getName();
        return n1.compareTo(n2);
      }).collect(Collectors.toList());
    modifierRecipes.forEach(modifierRecipe -> registry.add(new ModifierRecipeDisplay(modifierRecipe)));

    // part builder
    List<MaterialRecipe> materialRecipes = RecipeHelper.getRecipes(manager, TinkerRecipeTypes.MATERIAL.get(), MaterialRecipe.class);
    MaterialItemList.setRecipes(materialRecipes);
    List<IDisplayPartBuilderRecipe> partRecipes = RecipeHelper.getJEIRecipes(manager, TinkerRecipeTypes.PART_BUILDER.get(), IDisplayPartBuilderRecipe.class);
    partRecipes.forEach(partRecipe -> registry.add(new PartBuilderDisplay(partRecipe)));
  }

  /**
   * Adds an item as a casting catalyst, and as a molding catalyst if it has molding recipes
   * @param registry     Catalyst regisry
   * @param item         Item to add
   * @param ownCategory  Category to always add
   * @param type         Molding recipe type
   */
  private static <T extends Recipe<C>, C extends Container> void addCastingCatalyst(CategoryRegistry registry, ItemLike item, CategoryIdentifier<?> ownCategory, RecipeType<MoldingRecipe> type) {
    EntryStack<ItemStack> stack = EntryStacks.of(item);
    registry.addWorkstations(ownCategory, stack);
    assert Minecraft.getInstance().level != null;
    if (!((RecipeManagerAccessor)Minecraft.getInstance().level.getRecipeManager()).port_lib$byType(type).isEmpty()) {
//      registry.addWorkstations(TConstructREIConstants.MOLDING, stack);
    }
  }

  private static void addCatalysts(CategoryRegistry registry, EntryStack<ItemStack> entryStack, CategoryIdentifier<?> ...categoryIdentifiers) {
    for (CategoryIdentifier<?> categoryIdentifier : categoryIdentifiers)
      registry.addWorkstations(categoryIdentifier, entryStack);
  }
}
