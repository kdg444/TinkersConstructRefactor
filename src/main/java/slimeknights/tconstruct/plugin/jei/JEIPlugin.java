package slimeknights.tconstruct.plugin.jei;

import com.google.common.collect.ImmutableList;
import io.github.fabricators_of_create.porting_lib.mixin.common.accessor.RecipeManagerAccessor;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.client.BuiltinClientPlugin;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidAttributes;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidStack;
import slimeknights.mantle.item.RetexturedBlockItem;
import slimeknights.mantle.recipe.helper.RecipeHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.recipe.RecipeTypes;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipe;
import slimeknights.tconstruct.library.recipe.casting.ICastingRecipe;
import slimeknights.tconstruct.library.recipe.casting.IDisplayableCastingRecipe;
import slimeknights.tconstruct.library.recipe.entitymelting.EntityMeltingRecipe;
import slimeknights.tconstruct.library.recipe.fuel.MeltingFuel;
import slimeknights.tconstruct.library.recipe.material.MaterialRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IDisplayModifierRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.severing.SeveringRecipe;
import slimeknights.tconstruct.library.recipe.molding.MoldingRecipe;
import slimeknights.tconstruct.library.recipe.partbuilder.IDisplayPartBuilderRecipe;
import slimeknights.tconstruct.library.recipe.partbuilder.Pattern;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationRecipe;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.item.IModifiableDisplay;
import slimeknights.tconstruct.library.tools.nbt.MaterialIdNBT;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;
import slimeknights.tconstruct.plugin.TinkersDisplay;
import slimeknights.tconstruct.plugin.jei.entity.DefaultEntityMeltingRecipe;
import slimeknights.tconstruct.plugin.jei.melting.FoundryCategory;
import slimeknights.tconstruct.plugin.jei.melting.MeltingCategory;
import slimeknights.tconstruct.plugin.jei.melting.MeltingFuelHandler;
import slimeknights.tconstruct.shared.TinkerMaterials;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.client.screen.HeatingStructureScreen;
import slimeknights.tconstruct.smeltery.client.screen.IScreenWithFluidTank;
import slimeknights.tconstruct.smeltery.client.screen.MelterScreen;
import slimeknights.tconstruct.smeltery.data.SmelteryCompat;
import slimeknights.tconstruct.smeltery.item.CopperCanItem;
import slimeknights.tconstruct.tables.TinkerTables;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import slimeknights.tconstruct.tools.item.CreativeSlotItem;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class JEIPlugin implements REIClientPlugin {
  @SuppressWarnings("rawtypes")
//  public static final IIngredientType<EntityType> ENTITY_TYPE = () -> EntityType.class;
//  public static final IIngredientType<ModifierEntry> MODIFIER_TYPE = () -> ModifierEntry.class;
//  public static final IIngredientType<Pattern> PATTERN_TYPE = () -> Pattern.class;

//  @Override
  public ResourceLocation getPluginUid() {
    return TConstructRecipeCategoryUid.pluginUid;
  }

  @Override
  public void registerCategories(CategoryRegistry registry) {
//    final IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
    // casting
//    registry.add(new CastingBasinCategory(guiHelper));
//    registry.add(new CastingTableCategory(guiHelper));
    registry.add(new MoldingRecipeCategory());
    registry.removePlusButton(TConstructRecipeCategoryUid.molding);
    // melting and casting
    registry.add(new MeltingCategory());
    registry.add(new AlloyRecipeCategory());
//    registry.add(new EntityMeltingRecipeCategory(guiHelper));
    registry.add(new FoundryCategory());
    // tinker station
//    registry.add(new ModifierRecipeCategory(guiHelper));
//    registry.add(new SeveringCategory(guiHelper));
    // part builder
//    registry.add(new PartBuilderCategory(guiHelper));

    registerRecipeCatalysts(registry);
  }

  //  @Override
//  public void registerIngredients(IModIngredientRegistration registration) {
//    assert Minecraft.getInstance().level != null;
//    RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
//    List<ModifierEntry> modifiers = Collections.emptyList();
//    if (Config.CLIENT.showModifiersInJEI.get()) {
//      modifiers = RecipeHelper.getJEIRecipes(manager, RecipeTypes.TINKER_STATION, IDisplayModifierRecipe.class)
//                              .stream()
//                              .map(recipe -> recipe.getDisplayResult().getModifier())
//                              .distinct()
//                              .sorted(Comparator.comparing(Modifier::getId))
//                              .map(mod -> new ModifierEntry(mod, 1))
//                              .collect(Collectors.toList());
//    }

//    registration.register(ENTITY_TYPE, Collections.emptyList(), new EntityIngredientHelper(), new EntityIngredientRenderer(16));
//    registration.register(MODIFIER_TYPE, modifiers, new ModifierIngredientHelper(), ModifierBookmarkIngredientRenderer.INSTANCE);
//    registration.register(PATTERN_TYPE, Collections.emptyList(), new PatternIngredientHelper(), PatternIngredientRenderer.INSTANCE);
//  }

  @Override
  public void registerDisplays(DisplayRegistry registry) {
    assert Minecraft.getInstance().level != null;
    RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
    // casting
    List<IDisplayableCastingRecipe> castingBasinRecipes = RecipeHelper.getJEIRecipes(manager, RecipeTypes.CASTING_BASIN, IDisplayableCastingRecipe.class);
    for(IDisplayableCastingRecipe castingRecipe : castingBasinRecipes)
      registry.add(new TinkersDisplay<>(castingRecipe, TConstructRecipeCategoryUid.castingBasin));
    List<IDisplayableCastingRecipe> castingTableRecipes = RecipeHelper.getJEIRecipes(manager, RecipeTypes.CASTING_TABLE, IDisplayableCastingRecipe.class);
    for(IDisplayableCastingRecipe castingRecipe : castingTableRecipes)
      registry.add(new TinkersDisplay<>(castingRecipe, TConstructRecipeCategoryUid.castingTable));

    // melting
    List<MeltingRecipe> meltingRecipes = RecipeHelper.getJEIRecipes(manager, RecipeTypes.MELTING, MeltingRecipe.class);
    for(MeltingRecipe meltingRecipe : meltingRecipes) {
      registry.add(new TinkersDisplay<>(meltingRecipe, TConstructRecipeCategoryUid.melting));
      registry.add(new TinkersDisplay<>(meltingRecipe, TConstructRecipeCategoryUid.foundry));
    }
    MeltingFuelHandler.setMeltngFuels(RecipeHelper.getRecipes(manager, RecipeTypes.FUEL, MeltingFuel.class));

    // entity melting
    List<EntityMeltingRecipe> entityMeltingRecipes = RecipeHelper.getJEIRecipes(manager, RecipeTypes.ENTITY_MELTING, EntityMeltingRecipe.class);
    // generate a "default" recipe for all other entity types
    entityMeltingRecipes.add(new DefaultEntityMeltingRecipe(entityMeltingRecipes));
    for(EntityMeltingRecipe entityMeltingRecipe : entityMeltingRecipes)
      registry.add(new TinkersDisplay<>(entityMeltingRecipe, TConstructRecipeCategoryUid.entityMelting));

    // alloying
    List<AlloyRecipe> alloyRecipes = RecipeHelper.getJEIRecipes(manager, RecipeTypes.ALLOYING, AlloyRecipe.class);
    alloyRecipes.forEach(alloyRecipe -> registry.add(new TinkersDisplay<>(alloyRecipe, TConstructRecipeCategoryUid.alloy)));

    // molding
    List<MoldingRecipe> moldingRecipes = ImmutableList.<MoldingRecipe>builder()
      .addAll(RecipeHelper.getJEIRecipes(manager, RecipeTypes.MOLDING_TABLE, MoldingRecipe.class))
      .addAll(RecipeHelper.getJEIRecipes(manager, RecipeTypes.MOLDING_BASIN, MoldingRecipe.class))
      .build();
    moldingRecipes.forEach(moldingRecipe -> registry.add(new TinkersDisplay<>(moldingRecipe, TConstructRecipeCategoryUid.molding)));

    // modifiers
    List<IDisplayModifierRecipe> modifierRecipes = RecipeHelper.getJEIRecipes(manager, RecipeTypes.TINKER_STATION, IDisplayModifierRecipe.class)
                                                               .stream()
                                                               .sorted((r1, r2) -> {
                                                                 SlotType t1 = r1.getSlotType();
                                                                 SlotType t2 = r2.getSlotType();
                                                                 String n1 = t1 == null ? "zzzzzzzzzz" : t1.getName();
                                                                 String n2 = t2 == null ? "zzzzzzzzzz" : t2.getName();
                                                                 return n1.compareTo(n2);
                                                               }).collect(Collectors.toList());
    modifierRecipes.forEach(modifierRecipe -> registry.add(new TinkersDisplay<>(modifierRecipe, TConstructRecipeCategoryUid.modifiers)));

    // beheading
    List<SeveringRecipe> severingRecipes = RecipeHelper.getJEIRecipes(manager, RecipeTypes.SEVERING, SeveringRecipe.class);
    severingRecipes.forEach(severingRecipe -> registry.add(new TinkersDisplay<>(severingRecipe, TConstructRecipeCategoryUid.severing)));

    // part builder
    List<MaterialRecipe> materialRecipes = RecipeHelper.getRecipes(manager, RecipeTypes.MATERIAL, MaterialRecipe.class);
//    MaterialItemList.setRecipes(materialRecipes); TODO: PORT
    List<IDisplayPartBuilderRecipe> partRecipes = RecipeHelper.getJEIRecipes(manager, RecipeTypes.PART_BUILDER, IDisplayPartBuilderRecipe.class);
    partRecipes.forEach(partRecipe -> registry.add(new TinkersDisplay<>(partRecipe, TConstructRecipeCategoryUid.partBuilder)));
  }

  /**
   * Adds an item as a casting catalyst, and as a molding catalyst if it has molding recipes
   * @param registry     Catalyst regisry
   * @param item         Item to add
   * @param ownCategory  Category to always add
   * @param type         Molding recipe type
   */
  private static <T extends Recipe<C>, C extends Container> void addCastingCatalyst(CategoryRegistry registry, ItemLike item, CategoryIdentifier<?> ownCategory, RecipeType<T> type) {
    ItemStack stack = new ItemStack(item);
    registry.addWorkstations(ownCategory, EntryStacks.of(stack));
    assert Minecraft.getInstance().level != null;
    if (!((RecipeManagerAccessor)Minecraft.getInstance().level.getRecipeManager()).port_lib$byType(type).isEmpty()) {
      registry.addWorkstations(TConstructRecipeCategoryUid.molding, EntryStacks.of(stack));
    }
  }

  public void registerRecipeCatalysts(CategoryRegistry registry) {
    // tables
    registry.addWorkstations(TConstructRecipeCategoryUid.partBuilder, EntryStacks.of(new ItemStack(TinkerTables.partBuilder)));
    registry.addWorkstations(TConstructRecipeCategoryUid.modifiers, EntryStacks.of(new ItemStack(TinkerTables.tinkerStation)));
    registry.addWorkstations(TConstructRecipeCategoryUid.modifiers, EntryStacks.of(new ItemStack(TinkerTables.tinkersAnvil)));
    registry.addWorkstations(TConstructRecipeCategoryUid.modifiers, EntryStacks.of(new ItemStack(TinkerTables.scorchedAnvil)));

    // smeltery
    registry.addWorkstations(TConstructRecipeCategoryUid.melting, EntryStacks.of(new ItemStack(TinkerSmeltery.searedMelter)));
    registry.addWorkstations(BuiltinClientPlugin.FUEL, EntryStacks.of(new ItemStack(TinkerSmeltery.searedHeater)));
    addCastingCatalyst(registry, TinkerSmeltery.searedTable, TConstructRecipeCategoryUid.castingTable, RecipeTypes.MOLDING_TABLE);
    addCastingCatalyst(registry, TinkerSmeltery.searedBasin, TConstructRecipeCategoryUid.castingBasin, RecipeTypes.MOLDING_BASIN);
    registry.addWorkstations(TConstructRecipeCategoryUid.melting, EntryStacks.of(new ItemStack(TinkerSmeltery.smelteryController)));
    registry.addWorkstations(TConstructRecipeCategoryUid.alloy, EntryStacks.of(new ItemStack(TinkerSmeltery.smelteryController)));
    registry.addWorkstations(TConstructRecipeCategoryUid.entityMelting, EntryStacks.of(new ItemStack(TinkerSmeltery.smelteryController)));

    // foundry
    registry.addWorkstations(TConstructRecipeCategoryUid.alloy, EntryStacks.of(new ItemStack(TinkerSmeltery.scorchedAlloyer)));
    addCastingCatalyst(registry, TinkerSmeltery.scorchedTable, TConstructRecipeCategoryUid.castingTable, RecipeTypes.MOLDING_TABLE);
    addCastingCatalyst(registry, TinkerSmeltery.scorchedBasin, TConstructRecipeCategoryUid.castingBasin, RecipeTypes.MOLDING_BASIN);
    registry.addWorkstations(TConstructRecipeCategoryUid.foundry, EntryStacks.of(new ItemStack(TinkerSmeltery.foundryController)));

    // modifiers
    for (Item item : TinkerTags.Items.MELEE.getValues()) {
      registry.addWorkstations(TConstructRecipeCategoryUid.severing, EntryStacks.of(IModifiableDisplay.getDisplayStack(item)));
    }
  }

//  @Override
//  public void registerItemSubtypes(ISubtypeRegistration registry) {
//    // retexturable blocks
//    IIngredientSubtypeInterpreter<ItemStack> tables = (stack, context) -> {
//      if (context == UidContext.Ingredient) {
//        return RetexturedBlockItem.getTextureName(stack);
//      }
//      return IIngredientSubtypeInterpreter.NONE;
//    };
//    registry.registerSubtypeInterpreter(TinkerTables.craftingStation.asItem(), tables);
//    registry.registerSubtypeInterpreter(TinkerTables.partBuilder.asItem(), tables);
//    registry.registerSubtypeInterpreter(TinkerTables.tinkerStation.asItem(), tables);
//    registry.registerSubtypeInterpreter(TinkerTables.tinkersAnvil.asItem(), tables);
//    registry.registerSubtypeInterpreter(TinkerTables.scorchedAnvil.asItem(), tables);
//
//    IIngredientSubtypeInterpreter<ItemStack> toolPartInterpreter = (stack, context) -> {
//      MaterialVariantId materialId = IMaterialItem.getMaterialFromStack(stack);
//      if (materialId.equals(IMaterial.UNKNOWN_ID)) {
//        return IIngredientSubtypeInterpreter.NONE;
//      }
//      return materialId.getId().toString();
//    };
//
//    // parts
//    for (Item item : TinkerTags.Items.TOOL_PARTS.getValues()) {
//      registry.registerSubtypeInterpreter(item, toolPartInterpreter);
//    }
//
//    // tools
//    Item slimeskull = TinkerTools.slimesuit.get(ArmorSlotType.HELMET);
//    registry.registerSubtypeInterpreter(slimeskull, ToolSubtypeInterpreter.ALWAYS);
//    for (Item item : TinkerTags.Items.MULTIPART_TOOL.getValues()) {
//      if (item != slimeskull) {
//        registry.registerSubtypeInterpreter(item, ToolSubtypeInterpreter.INGREDIENT);
//      }
//    }
//
//    registry.registerSubtypeInterpreter(TinkerSmeltery.copperCan.get(), (stack, context) -> CopperCanItem.getSubtype(stack));
//    registry.registerSubtypeInterpreter(TinkerModifiers.creativeSlotItem.get(), (stack, context) -> {
//      SlotType slotType = CreativeSlotItem.getSlot(stack);
//      return slotType != null ? slotType.getName() : "";
//    });
//  }

//  @Override
//  public void registerGuiHandlers(IGuiHandlerRegistration registration) {
//    registration.addGenericGuiContainerHandler(MelterScreen.class, new GuiContainerTankHandler<>());
//    registration.addGenericGuiContainerHandler(HeatingStructureScreen.class, new GuiContainerTankHandler<>());
//  }
//
//  @Override
//  public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
//    registration.addRecipeTransferHandler(new CraftingStationTransferInfo());
//    registration.addRecipeTransferHandler(new TinkerStationTransferInfo());
//  }

  /**
   * Removes a fluid from JEI
   * @param manager  Manager
   * @param fluid    Fluid to remove
   * @param bucket   Fluid bucket to remove
   */
//  private static void removeFluid(IIngredientManager manager, Fluid fluid, Item bucket) {
//    manager.removeIngredientsAtRuntime(VanillaTypes.FLUID, Collections.singleton(new FluidStack(fluid, FluidAttributes.BUCKET_VOLUME)));
//    manager.removeIngredientsAtRuntime(VanillaTypes.ITEM, Collections.singleton(new ItemStack(bucket)));
//  }

  /**
   * Hides an item if the related tag is empty
   * @param manager  Ingredient manager
   * @param item     Cast instance
   * @param tagName  Tag to check
   */
//  @SuppressWarnings("SameParameterValue")
//  private static void optionalItem(IIngredientManager manager, ItemLike item, String tagName) {
//    Tag<Item> tag = SerializationTags.getInstance().getOrEmpty(Registry.ITEM_REGISTRY).getTag(new ResourceLocation("forge", tagName));
//    if (tag == null || tag.getValues().isEmpty()) {
//      manager.removeIngredientsAtRuntime(VanillaTypes.ITEM, Collections.singletonList(new ItemStack(item)));
//    }
//  }

  /**
   * Hides casts if the related tag is empty
   * @param manager  Ingredient manager
   * @param cast     Cast instance
   */
//  private static void optionalCast(IIngredientManager manager, CastItemObject cast) {
//    Tag<Item> tag = SerializationTags.getInstance().getOrEmpty(Registry.ITEM_REGISTRY).getTag(new ResourceLocation("forge", cast.getName().getPath() + "s"));
//    if (tag == null || tag.getValues().isEmpty()) {
//      manager.removeIngredientsAtRuntime(VanillaTypes.ITEM, cast.values().stream().map(ItemStack::new).collect(Collectors.toList()));
//    }
//  }

//  @Override
//  public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
//    IIngredientManager manager = jeiRuntime.getIngredientManager();
//
//    // hide knightslime and slimesteel until implemented
//    removeFluid(manager, TinkerFluids.moltenSoulsteel.get(), TinkerFluids.moltenSoulsteel.asItem());
//    removeFluid(manager, TinkerFluids.moltenKnightslime.get(), TinkerFluids.moltenKnightslime.asItem());
//    // hide compat that is not present
//    for (SmelteryCompat compat : SmelteryCompat.values()) {
//      Tag<Item> ingot = SerializationTags.getInstance().getOrEmpty(Registry.ITEM_REGISTRY).getTag(new ResourceLocation("forge", "ingots/" + compat.getName()));
//      if (ingot == null || ingot.getValues().isEmpty()) {
//        removeFluid(manager, compat.getFluid().get(), compat.getBucket());
//      }
//    }
//    if (!FabricLoader.getInstance().isModLoaded("ceramics")) {
//      removeFluid(manager, TinkerFluids.moltenPorcelain.get(), TinkerFluids.moltenPorcelain.asItem());
//    }
//    optionalCast(manager, TinkerSmeltery.plateCast);
//    optionalCast(manager, TinkerSmeltery.gearCast);
//    optionalCast(manager, TinkerSmeltery.coinCast);
//    optionalCast(manager, TinkerSmeltery.wireCast);
//    optionalItem(manager, TinkerMaterials.necroniumBone, "ingots/uranium");
//  }

  /** Class to pass {@link IScreenWithFluidTank} into JEI */
//  public static class GuiContainerTankHandler<C extends AbstractContainerMenu, T extends AbstractContainerScreen<C> & IScreenWithFluidTank> implements IGuiContainerHandler<T> {
//    @Override
//    @Nullable
//    public Object getIngredientUnderMouse(T containerScreen, double mouseX, double mouseY) {
//      return containerScreen.getIngredientUnderMouse(mouseX, mouseY);
//    }
//  }

  /** Subtype interpreter for tools, treats the tool as unique in ingredient list, generic in recipes */
//  public enum ToolSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
//    ALWAYS, INGREDIENT;
//
//    @Override
//    public String apply(ItemStack itemStack, UidContext context) {
//      if (this == ALWAYS || context == UidContext.Ingredient) {
//        StringBuilder builder = new StringBuilder();
//        List<MaterialVariantId> materialList = MaterialIdNBT.from(itemStack).getMaterials();
//        if (!materialList.isEmpty()) {
//          // append first entry without a comma
//          builder.append(materialList.get(0));
//          for (int i = 1; i < materialList.size(); i++) {
//            builder.append(',');
//            builder.append(materialList.get(i).getId());
//          }
//        }
//        return builder.toString();
//      }
//      return NONE;
//    }
//  }

//  public static class RetexturedSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
//    @Override
//    public String apply(ItemStack itemStack, UidContext context) {
//      if (context == UidContext.Ingredient) {
//        return RetexturedBlockItem.getTextureName(itemStack);
//      }
//      return NONE;
//    }
//  }
}
