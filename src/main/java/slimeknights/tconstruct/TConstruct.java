package slimeknights.tconstruct;

import com.mojang.datafixers.DataFixerBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.mantle.registration.RegistrationHelper;
import slimeknights.tconstruct.common.TinkerModule;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.common.data.AdvancementsProvider;
import slimeknights.tconstruct.common.data.loot.GlobalLootModifiersProvider;
import slimeknights.tconstruct.common.data.loot.TConstructLootTableProvider;
import slimeknights.tconstruct.common.data.tags.BlockTagProvider;
import slimeknights.tconstruct.common.data.tags.EntityTypeTagProvider;
import slimeknights.tconstruct.common.data.tags.FluidTagProvider;
import slimeknights.tconstruct.common.data.tags.ItemTagProvider;
import slimeknights.tconstruct.common.data.tags.TileEntityTypeTagProvider;
import slimeknights.tconstruct.common.network.TinkerNetwork;
import slimeknights.tconstruct.fluids.FluidEvents;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.gadgets.TinkerGadgets;
import slimeknights.tconstruct.library.TinkerBookIDs;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability.ComputableDataKey;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability.TinkerDataKey;
import slimeknights.tconstruct.library.tools.definition.ToolDefinitionLoader;
import slimeknights.tconstruct.library.tools.layout.StationSlotLayoutLoader;
import slimeknights.tconstruct.library.utils.Util;
//import slimeknights.tconstruct.plugin.ImmersiveEngineeringPlugin;
import slimeknights.tconstruct.shared.AchievementEvents;
import slimeknights.tconstruct.shared.CommonsEvents;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.shared.TinkerMaterials;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tables.TinkerTables;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerToolParts;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.logic.ToolEvents;
import slimeknights.tconstruct.world.TinkerStructures;
import slimeknights.tconstruct.world.TinkerWorld;
import slimeknights.tconstruct.world.WorldEvents;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;

/**
 * TConstruct, the tool mod. Craft your tools with style, then modify until the original is gone!
 *
 * @author mDiyo
 */

@SuppressWarnings("unused")
public class TConstruct implements ModInitializer, DataGeneratorEntrypoint {

  public static final String MOD_ID = "tconstruct";
  public static final Logger LOG = LogManager.getLogger(MOD_ID);
  public static final Random RANDOM = new Random();

  /* Instance of this mod, used for grabbing prototype fields */
  public static TConstruct instance;

  @Override
  public void onInitialize() {
    instance = this;

    Config.init();

    // initialize modules, done this way rather than with annotations to give us control over the order
    // base
    new TinkerCommons();
    new TinkerMaterials();
    new TinkerFluids();
    new TinkerGadgets();
    // world
    new TinkerWorld();
    TinkerStructures structures = new TinkerStructures();
    // tools
   new TinkerTables();
   new TinkerModifiers();
   new TinkerToolParts();
   new TinkerTools();
    // smeltery
    new TinkerSmeltery();

    // init deferred registers
    TinkerModule.initRegisters();
    TinkerModule.initDefferedRegisters();
    TinkerGadgets.commonSetup();
    TinkerWorld.init();
    TinkerTags.init();
    WorldEvents.init();
    structures.commonSetup();

    TinkerNetwork.setup();



    // init client logic
    TinkerBookIDs.registerCommandSuggestion();
//    if (ModList.get().isLoaded("crafttweaker")) {
//      MinecraftForge.EVENT_BUS.register(new CRTHelper());
//    }

    // compat
    if (FabricLoader.getInstance().isModLoaded("immersiveengineering")) {
//      new ImmersiveEngineeringPlugin();
    }
    commonSetup();
    FabricEvents.init();
//    RegisterDataFixerCallback.EVENT.register(this::missingBlocks); TODO: PORT
//    RegisterDataFixerCallback.EVENT.register(this::missingItems);
  }

  static void commonSetup() {
    MaterialRegistry.init();
    ToolDefinitionLoader.init();
    StationSlotLayoutLoader.init();
  }

  @Override
  public void onInitializeDataGenerator(FabricDataGenerator datagenerator) {
//    if (event.includeServer()) {
      BlockTagProvider blockTags = new BlockTagProvider(datagenerator);
      datagenerator.addProvider(blockTags);
      datagenerator.addProvider(new ItemTagProvider(datagenerator, blockTags));
      datagenerator.addProvider(new FluidTagProvider(datagenerator));
      datagenerator.addProvider(new EntityTypeTagProvider(datagenerator));
      datagenerator.addProvider(new TileEntityTypeTagProvider(datagenerator));
      datagenerator.addProvider(new TConstructLootTableProvider(datagenerator));
      datagenerator.addProvider(new AdvancementsProvider(datagenerator));
      datagenerator.addProvider(new GlobalLootModifiersProvider(datagenerator));
      //datagenerator.addProvider(new StructureUpdater(datagenerator, existingFileHelper, MOD_ID, PackType.SERVER_DATA, "structures"));
//    }
    /*
    if (event.includeClient()) {
      datagenerator.addProvider(new StructureUpdater(datagenerator, existingFileHelper, MOD_ID, PackType.CLIENT_RESOURCES, "book/structures"));
    }
    */
  }

//  @Nullable
//  private static String missingBlock(String name) {
//    return switch (name) {
//      case MOD_ID + ":copper_block" -> Blocks.COPPER_BLOCK.getRegistryName().toString();
//      case  MOD_ID + ":copper_ore" -> Blocks.COPPER_ORE.getRegistryName().toString();
//      // tinker bronze -> amethyst bronze
//      case "tinkers_bronze_block" -> TinkerMaterials.amethystBronze.get();
//      case "molten_tinkers_bronze_fluid" -> TinkerFluids.moltenAmethystBronze.getBlock();
//      default -> null;
//    };
//  }

//  @SubscribeEvent
//  void missingItems(final MissingMappings<Item> event) {
//    RegistrationHelper.handleMissingMappings(event, MOD_ID, name -> {
//      switch(name) {
//        case "copper_ingot": return Items.COPPER_INGOT;
//        case "blank_cast": return Items.GOLD_INGOT;
//        case "pickaxe_head": return TinkerToolParts.pickHead.get();
//        case "pickaxe_head_cast": return TinkerSmeltery.pickHeadCast.get();
//        case "pickaxe_head_sand_cast": return TinkerSmeltery.pickHeadCast.getSand();
//        case "pickaxe_head_red_sand_cast": return TinkerSmeltery.pickHeadCast.getRedSand();
//        // tinker bronze -> amethyst bronze
//        case "tinkers_bronze_ingot": TinkerMaterials.amethystBronze.getIngot();
//        case "tinkers_bronze_nugget": TinkerMaterials.amethystBronze.getNugget();
//        case "molten_tinkers_bronze_bucket": return TinkerFluids.moltenAmethystBronze.asItem();
//        case "flint_and_bronze": TinkerTools.flintAndBrick.get();
//      }
//      ItemLike block = missingBlock(name);
//      return block == null ? null : block.asItem();
//    });
//  }
//
//  @SubscribeEvent
//  void missingBlocks(final MissingMappings<Block> event) {
//    RegistrationHelper.handleMissingMappings(event, MOD_ID, TConstruct::missingBlock);
//  }
//
//  @SubscribeEvent
//  void missingFluid(final MissingMappings<Fluid> event) {
//    RegistrationHelper.handleMissingMappings(event, MOD_ID, name -> switch (name) {
//      // tinker bronze -> amethyst bronze
//      case "molten_tinkers_bronze" -> TinkerFluids.moltenAmethystBronze.get();
//      case "flowing_molten_tinkers_bronze" -> TinkerFluids.moltenAmethystBronze.getFlowing();
//      default -> null;
//    });
//  }

//  @SubscribeEvent
//  void missingModifier(final MissingMappings<Modifier> event) {
//    RegistrationHelper.handleMissingMappings(event, MOD_ID, name -> switch(name) {
//      case "maintained_2" -> TinkerModifiers.maintained.get();
//      case "fractured" -> TinkerModifiers.sharpness.get();
//      default -> null;
  //  });
//  }


  /* Utils */

  /**
   * Gets a resource location for Tinkers
   * @param name  Resource path
   * @return  Location for tinkers
   */
  public static ResourceLocation getResource(String name) {
    return new ResourceLocation(MOD_ID, name);
  }

  /**
   * Gets a data key for the capability, mainly used for modifier markers
   * @param name  Resource path
   * @return  Location for tinkers
   */
  public static <T> TinkerDataKey<T> createKey(String name) {
    return TinkerDataKey.of(getResource(name));
  }

  /**
   * Gets a data key for the capability, mainly used for modifier markers
   * @param name         Resource path
   * @param constructor  Constructor for compute if absent
   * @return  Location for tinkers
   */
  public static <T> ComputableDataKey<T> createKey(String name, Supplier<T> constructor) {
    return ComputableDataKey.of(getResource(name), constructor);
  }

  /**
   * Returns the given Resource prefixed with tinkers resource location. Use this function instead of hardcoding
   * resource locations.
   */
  public static String resourceString(String res) {
    return String.format("%s:%s", MOD_ID, res);
  }

  /**
   * Prefixes the given unlocalized name with tinkers prefix. Use this when passing unlocalized names for a uniform
   * namespace.
   */
  public static String prefix(String name) {
    return String.format("%s.%s", MOD_ID, name.toLowerCase(Locale.US));
  }

  /**
   * Makes a translation key for the given name
   * @param base  Base name, such as "block" or "gui"
   * @param name  Object name
   * @return  Translation key
   */
  public static String makeTranslationKey(String base, String name) {
    return Util.makeTranslationKey(base, getResource(name));
  }

  /**
   * Makes a translation text component for the given name
   * @param base  Base name, such as "block" or "gui"
   * @param name  Object name
   * @return  Translation key
   */
  public static MutableComponent makeTranslation(String base, String name) {
    return new TranslatableComponent(makeTranslationKey(base, name));
  }

  /**
   * Makes a translation text component for the given name
   * @param base       Base name, such as "block" or "gui"
   * @param name       Object name
   * @param arguments  Additional arguments to the translation
   * @return  Translation key
   */
  public static MutableComponent makeTranslation(String base, String name, Object... arguments) {
    return new TranslatableComponent(makeTranslationKey(base, name), arguments);
  }
}
