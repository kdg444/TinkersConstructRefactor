package slimeknights.tconstruct.common;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import slimeknights.tconstruct.TConstruct;

public class TinkerTags {
  /** Called on mod construct to set up tags */
  public static void init() {
    Blocks.init();
    Items.init();
    Fluids.init();
    EntityTypes.init();
    TileEntityTypes.init();
  }

  public static class Blocks {
    private static void init() {}
    public static final Tag.Named<Block> WORKBENCHES = forgeTag("workbenches");
    public static final Tag.Named<Block> TABLES = tag("tables");

    /** Materials that can be used to craft wooden tool tables */
    public static final Tag.Named<Block> PLANKLIKE = tag("planklike");
    /** Metals that can be used to craft the anvil */
    public static final Tag.Named<Block> ANVIL_METAL = tag("anvil_metal");

    /** Things the platform connects to */
    public static final Tag.Named<Block> PLATFORM_CONNECTIONS = tag("platform_connections");
    /** Copper platform variants */
    public static final Tag.Named<Block> COPPER_PLATFORMS = tag("copper_platforms");

    // Slime spawn
    /** Blocks skyslimes can spawn on in the overworld */
    public static final Tag.Named<Block> SKY_SLIME_SPAWN = tag("slime_spawn/sky");
    /** Blocks earthslimes can spawn on in the overworld */
    public static final Tag.Named<Block> EARTH_SLIME_SPAWN = tag("slime_spawn/earth");
    /** Blocks enderslimes can spawn on in the end */
    public static final Tag.Named<Block> ENDER_SLIME_SPAWN = tag("slime_spawn/ender");

    public static final Tag.Named<Block> SLIME_BLOCK = tag("slime_block");
    public static final Tag.Named<Block> CONGEALED_SLIME = tag("congealed_slime");
    public static final Tag.Named<Block> SLIMY_LOGS = tag("slimy_logs");
    public static final Tag.Named<Block> SLIMY_PLANKS = tag("slimy_planks");
    public static final Tag.Named<Block> SLIMY_LEAVES = tag("slimy_leaves");
    public static final Tag.Named<Block> SLIMY_SAPLINGS = tag("slimy_saplings");
    /** Blocks that are slime gr on top of dirt */
    public static final Tag.Named<Block> SLIMY_GRASS = tag("slimy_grass");
    /** Blocks that are slime fungus on top of dirt */
    public static final Tag.Named<Block> SLIMY_NYLIUM = tag("slimy_nylium");
    /** Slime grass, slime fungus, or slime dirt */
    public static final Tag.Named<Block> SLIMY_SOIL = tag("slimy_soil");

    public static final Tag.Named<Block> ORES_COBALT = forgeTag("ores/cobalt");
    public static final Tag.Named<Block> RAW_BLOCK_COBALT = forgeTag("storage_blocks/raw_cobalt");

    public static final Tag.Named<Block> SEARED_BLOCKS = tag("seared_blocks");
    public static final Tag.Named<Block> SEARED_BRICKS = tag("seared_bricks");
    public static final Tag.Named<Block> SEARED_TANKS = tag("seared_tanks");

    public static final Tag.Named<Block> SCORCHED_BLOCKS = tag("scorched_blocks");
    public static final Tag.Named<Block> SCORCHED_TANKS = tag("scorched_tanks");


    /** Blocks which make a heater part of a structure, when placed above a heater */
    public static final Tag.Named<Block> HEATER_CONTROLLERS = tag("heater_controllers");
    /** Blocks valid as a fuel tank for the melter or alloyer, should be item handlers with 1 slot or fluid handlers with 1 fluid */
    public static final Tag.Named<Block> FUEL_TANKS = tag("fuel_tanks");
    /** Tanks that serve as a valid input for the alloyer, should be fluid handlers with 1 fluid */
    public static final Tag.Named<Block> ALLOYER_TANKS = tag("alloyer_tanks");

    /** Blocks that make up the smeltery structure */
    public static final Tag.Named<Block> SMELTERY = tag("smeltery");
    /** Blocks valid as a smeltery tank, required for fuel */
    public static final Tag.Named<Block> SMELTERY_TANKS = tag("smeltery/tanks");
    /** Blocks valid as a smeltery floor */
    public static final Tag.Named<Block> SMELTERY_FLOOR = tag("smeltery/floor");
    /** Blocks valid in the smeltery wall */
    public static final Tag.Named<Block> SMELTERY_WALL = tag("smeltery/wall");

    /** Blocks that make up the foundry structure */
    public static final Tag.Named<Block> FOUNDRY = tag("foundry");
    /** Blocks valid as a foundry tank, required for fuel */
    public static final Tag.Named<Block> FOUNDRY_TANKS = tag("foundry/tanks");
    /** Blocks valid as a foundry floor */
    public static final Tag.Named<Block> FOUNDRY_FLOOR = tag("foundry/floor");
    /** Blocks valid in the foundry wall */
    public static final Tag.Named<Block> FOUNDRY_WALL = tag("foundry/wall");

    /** Blocks that the mattock is effective on */
    public static final Tag.Named<Block> MINABLE_WITH_MATTOCK = tag("mineable/mattock");
    /** Blocks that the mattock is effective on */
    public static final Tag.Named<Block> MINABLE_WITH_PICKADZE = tag("mineable/pickadze");
    /** Blocks that the hand axe is effective on */
    public static final Tag.Named<Block> MINABLE_WITH_HAND_AXE = tag("mineable/hand_axe");
    /** Blocks that the scythe or kama are effective on */
    public static final Tag.Named<Block> MINABLE_WITH_SCYTHE = tag("mineable/scythe");
    /** Blocks that the vanilla sword is effective on */
    public static final Tag.Named<Block> MINABLE_WITH_SWORD = forgeTag("mineable/sword");
    /** Blocks that the vanilla shears are effective on */
    public static final Tag.Named<Block> MINABLE_WITH_SHEARS = forgeTag("mineable/shears");
    /** Blocks that the dagger is effective on */
    public static final Tag.Named<Block> MINABLE_WITH_DAGGER = tag("mineable/dagger");

    /** Any block that can be harvested using a kama or scythe */
    public static final Tag.Named<Block> HARVESTABLE = tag("harvestable");
    /** Plants that are broken to drop produce and seeds */
    public static final Tag.Named<Block> HARVESTABLE_CROPS = tag("harvestable/crops");
    /** Plants that drop fruit on interaction */
    public static final Tag.Named<Block> HARVESTABLE_INTERACT = tag("harvestable/interact");
    /** Plants that grow by placing a copy on top */
    public static final Tag.Named<Block> HARVESTABLE_STACKABLE = tag("harvestable/stackable");
    /** Any block that counts as a tree trunk for the lumber axe. Note it must also be harvestable by axes to be effective */
    public static final Tag.Named<Block> TREE_LOGS = tag("tree_log");
    /** List of blocks that should produce bonus gold nugget drops from the chrysophilite modifier. Will only drop bonus if the block does not drop itself */
    public static final Tag.Named<Block> CHRYSOPHILITE_ORES = tag("chrysophilite_ores");

    // ceramics compat
    public static final Tag.Named<Block> CISTERN_CONNECTIONS = TagFactory.BLOCK.create(new ResourceLocation("ceramics", "cistern_connections"));

    /** Makes a tag in the tinkers domain */
    public static Tag.Named<Block> tag(String name) {
      return TagFactory.BLOCK.create(TConstruct.getResource(name));
    }

    private static Tag.Named<Block> forgeTag(String name) {
      return TagFactory.BLOCK.create(new ResourceLocation("c", name));
    }
  }

  public static class Items {
    private static void init() {}
    public static final Tag.Named<Item> WORKBENCHES = forgeTag("workbenches");
    public static final Tag.Named<Item> TABLES = tag("tables");

    /** Materials that can be used to craft wooden tool tables */
    public static final Tag.Named<Item> PLANKLIKE = tag("planklike");
    /** Metals that can be used to craft the anvil */
    public static final Tag.Named<Item> ANVIL_METAL = tag("anvil_metal");
    /** Copper platform variants */
    public static final Tag.Named<Item> COPPER_PLATFORMS = tag("copper_platforms");

    /** Planks in this tag are skipped in the default wood crafting recipe as they have their own variant. Tagging your planks here will allow you to add another wood variant */
    public static final Tag.Named<Item> VARIANT_PLANKS = tag("wood_variants/planks");
    /** Logs in this tag are skipped in the default wood crafting recipe as they have their own variant. Tagging your planks here will allow you to add another wood variant */
    public static final Tag.Named<Item> VARIANT_LOGS = tag("wood_variants/logs");

    public static final Tag.Named<Item> SLIME_BLOCK = tag("slime_block");
    public static final Tag.Named<Item> CONGEALED_SLIME = tag("congealed_slime");
    public static final Tag.Named<Item> SLIMY_LOGS = tag("slimy_logs");
    public static final Tag.Named<Item> SLIMY_PLANKS = tag("slimy_planks");
    public static final Tag.Named<Item> SLIMY_LEAVES = tag("slimy_leaves");
    public static final Tag.Named<Item> SLIMY_SAPLINGS = tag("slimy_saplings");

    public static final Tag.Named<Item> SEARED_BLOCKS = tag("seared_blocks");
    public static final Tag.Named<Item> SEARED_BRICKS = tag("seared_bricks");
    public static final Tag.Named<Item> SCORCHED_BLOCKS = tag("scorched_blocks");
    public static final Tag.Named<Item> SMELTERY = tag("smeltery");
    public static final Tag.Named<Item> FOUNDRY = tag("foundry");

    public static final Tag.Named<Item> ORES_COBALT = forgeTag("ores/cobalt");
    public static final Tag.Named<Item> RAW_BLOCK_COBALT = forgeTag("storage_blocks/raw_cobalt");
    public static final Tag.Named<Item> RAW_COBALT = forgeTag("raw_materials/cobalt");

    public static final Tag.Named<Item> NUGGETS_NETHERITE = forgeTag("nuggets/netherite");
    public static final Tag.Named<Item> INGOTS_NETHERITE_SCRAP = forgeTag("ingots/netherite_scrap");
    public static final Tag.Named<Item> NUGGETS_NETHERITE_SCRAP = forgeTag("nuggets/netherite_scrap");
    public static final Tag.Named<Item> NUGGETS_COPPER = forgeTag("nuggets/copper");

    public static final Tag.Named<Item> CASTS = tag("casts");
    public static final Tag.Named<Item> GOLD_CASTS = tag("casts/gold");
    public static final Tag.Named<Item> SAND_CASTS = tag("casts/sand");
    public static final Tag.Named<Item> RED_SAND_CASTS = tag("casts/red_sand");
    public static final Tag.Named<Item> SINGLE_USE_CASTS = tag("casts/single_use");
    public static final Tag.Named<Item> MULTI_USE_CASTS = tag("casts/multi_use");
    public static final Tag.Named<Item> BLANK_SINGLE_USE_CASTS = tag("casts/single_use/blank");
    /** Items that count as empty in a casting table */
    public static final Tag.Named<Item> TABLE_EMPTY_CASTS = tag("casts/empty/table");
    /** Items that count as empty in a casting basin */
    public static final Tag.Named<Item> BASIN_EMPTY_CASTS = tag("casts/empty/basin");

    /** Items that can be placed in the pattern slot in the part builder */
    public static final Tag.Named<Item> PATTERNS = tag("patterns");
    /** Items that work in all basic part builder recipes, when unspecified this tag is used for the input */
    public static final Tag.Named<Item> DEFAULT_PATTERNS = tag("patterns/default");
    /** Pattern items that are not consumed in the part builder */
    public static final Tag.Named<Item> REUSABLE_PATTERNS = tag("patterns/reusable");

    /** All basic tinkers tanks */
    public static final Tag.Named<Item> SEARED_TANKS = tag("seared_tanks");
    public static final Tag.Named<Item> SCORCHED_TANKS = tag("scorched_tanks");
    public static final Tag.Named<Item> TANKS = tag("tanks");

    public static final Tag.Named<Item> WITHER_BONES = forgeTag("wither_bones");
    public static final Tag.Named<Item> BOOKS = forgeTag("books");
    public static final Tag.Named<Item> GUIDEBOOKS = forgeTag("books/guide");
    public static final Tag.Named<Item> TINKERS_GUIDES = tag("guides");

    /** Any items in this tag will show the erroring block in smelteries and foundries when held or worn */
    public static final Tag.Named<Item> GENERAL_STRUCTURE_DEBUG = tag("structure_debug/general");
    /** Any items in this tag will show the erroring block in smelteries when held or worn */
    public static final Tag.Named<Item> SMELTERY_DEBUG = tag("structure_debug/smeltery");
    /** Any items in this tag will show the erroring block in foundries when held or worn */
    public static final Tag.Named<Item> FOUNDRY_DEBUG = tag("structure_debug/foundry");

    /** Containers that can be used in the duct */
    public static final Tag.Named<Item> DUCT_CONTAINERS = tag("duct_containers");

    /** Items that cannot be autosmelted */
    public static final Tag.Named<Item> AUTOSMELT_BLACKLIST = tag("autosmelt_blacklist");

    /** Items that are seeds for kama harvest */
    public static final Tag.Named<Item> SEEDS = tag("seeds");

    /** Seeds that produce slimy grass */
    public static final Tag.Named<Item> SLIMY_SEEDS = tag("slimy_grass_seeds");
    /** Anything that works as a slimesling, flinging the player or mobs on use */
    public static final Tag.Named<Item> SLIMESLINGS = tag("slimeslings");

    /** Stones that can be used for stoneshield */
    public static final Tag.Named<Item> STONESHIELDS = tag("stoneshields");
    /** Items that can be consumed for a blaze slimeskull to shoot a fireball */
    public static final Tag.Named<Item> FIREBALLS = tag("fireballs");
    /** Items in this tag cannot be placed inside tool inventories */
    public static final Tag.Named<Item> TOOL_INVENTORY_BLACKLIST = tag("inventory_blacklist");
    /** List of blocks that should produce bonus gold nugget drops from the chrysophilite modifier. Will only drop bonus if the block does not drop itself */
    public static final Tag.Named<Item> CHRYSOPHILITE_ORES = tag("chrysophilite_ores");

    /*
     * Tool tags
     */
    /** Anything that can be modified in the tool station */
    public static final Tag.Named<Item> TOOL_PARTS = tag("parts");

    /** Anything that can be modified in the tool station */
    public static final Tag.Named<Item> MODIFIABLE = tag("modifiable");

    /** Modifiable items that contain multiple parts */
    public static final Tag.Named<Item> MULTIPART_TOOL = tag("modifiable/multipart");
    /** Modifiable items that can have range increased */
    public static final Tag.Named<Item> AOE = tag("modifiable/aoe");
    /** Modifiable items that can be held in a single hand */
    public static final Tag.Named<Item> ONE_HANDED = tag("modifiable/one_handed");
    /** Modifiable items that prevent usage of the offhand */
    public static final Tag.Named<Item> TWO_HANDED = tag("modifiable/two_handed");
    /** Tools that use durability and can be repaired */
    public static final Tag.Named<Item> DURABILITY = tag("modifiable/durability");

    /** This is a common combination for modifiers, so figured it is worth a tag. Should not be added to directly typically */
    public static final Tag.Named<Item> MELEE_OR_HARVEST = tag("modifiable/melee_or_harvest");
    /** This is a common combination for modifiers, so figured it is worth a tag. Should not be added to directly typically */
    public static final Tag.Named<Item> MELEE_OR_UNARMED = tag("modifiable/melee_or_unarmed");
    /** Anything that is used in the player's hand */
    public static final Tag.Named<Item> HELD = tag("modifiable/held");
    /** Anything that can use interaction modifiers */
    public static final Tag.Named<Item> INTERACTABLE = tag("modifiable/interactable");

    /** Modifiable items that support melee attacks */
    public static final Tag.Named<Item> MELEE = tag("modifiable/melee");
    /** Modifiable items that specifically are designed for melee, removes melee penalties */
    public static final Tag.Named<Item> MELEE_PRIMARY = tag("modifiable/melee/primary");
    /** Modifiable items that are also swords, typically no use outside of combat */
    public static final Tag.Named<Item> SWORD = tag("modifiable/melee/sword");

    /** Modifiable items that can break blocks */
    public static final Tag.Named<Item> HARVEST = tag("modifiable/harvest");
    /** Modifiable items that are specifically designed for harvest, removes harvest penalties */
    public static final Tag.Named<Item> HARVEST_PRIMARY = tag("modifiable/harvest/primary");
    /** Modifiable items that can break stone blocks */
    public static final Tag.Named<Item> STONE_HARVEST = tag("modifiable/harvest/stone");

    /** Modifiable items that are worn as armor */
    public static final Tag.Named<Item> ARMOR = tag("modifiable/armor");
    /** Modifiable items that are worn as boots */
    public static final Tag.Named<Item> BOOTS = tag("modifiable/armor/boots");
    /** Modifiable items that are worn as leggings */
    public static final Tag.Named<Item> LEGGINGS = tag("modifiable/armor/leggings");
    /** Modifiable items that are worn as chestplates */
    public static final Tag.Named<Item> CHESTPLATES = tag("modifiable/armor/chestplate");
    /** Modifiable items that are worn as helmets */
    public static final Tag.Named<Item> HELMETS = tag("modifiable/armor/helmets");
    // /** Modifiable items that support ranged attacks, such as bows */
    // public static final Tag.Named<Item> RANGED = tag("modifiable/ranged");

    /** Tag so mods like thermal know our scyhtes can harvest */
    public static final Tag.Named<Item> SCYTHES = forgeTag("tools/scythe");

    /** Makes a tag in the tinkers domain */
    private static Tag.Named<Item> tag(String name) {
      return TagFactory.ITEM.create(TConstruct.getResource(name));
    }

    /** Makes a tag in the forge domain */
    public static Tag.Named<Item> forgeTag(String name) {
      return TagFactory.ITEM.create(new ResourceLocation("c", name));
    }
  }

  public static class Fluids {
    private static void init() {}
    public static final Tag.Named<Fluid> SLIME = tag("slime");
    /** Causes the fluid to be formatted like a metal in tooltips */
    public static final Tag.Named<Fluid> SLIME_TOOLTIPS = tag("tooltips/slime");
    /** Causes the fluid to be formatted like a clay in tooltips */
    public static final Tag.Named<Fluid> CLAY_TOOLTIPS = tag("tooltips/clay");
    /** Causes the fluid to be formatted like a metal in tooltips */
    public static final Tag.Named<Fluid> METAL_TOOLTIPS = tag("tooltips/metal");
    /** Causes the fluid to be formatted like gems, with 3x3 blocks */
    public static final Tag.Named<Fluid> LARGE_GEM_TOOLTIPS = tag("tooltips/gem_large");
    /** Causes the fluid to be formatted like gems, with 2x2 blocks */
    public static final Tag.Named<Fluid> SMALL_GEM_TOOLTIPS = tag("tooltips/gem_small");
    /** Causes the fluid to be formatted like glass in tooltips */
    public static final Tag.Named<Fluid> GLASS_TOOLTIPS = tag("tooltips/glass");

    // spilling tags - used to reduce the number of spilling recipes
    public static final Tag.Named<Fluid> CLAY_SPILLING = tag("spilling/clay");
    public static final Tag.Named<Fluid> GLASS_SPILLING = tag("spilling/glass");
    public static final Tag.Named<Fluid> CHEAP_METAL_SPILLING = tag("spilling/metal/cheap");
    public static final Tag.Named<Fluid> AVERAGE_METAL_SPILLING = tag("spilling/metal/average");
    public static final Tag.Named<Fluid> EXPENSIVE_METAL_SPILLING = tag("spilling/metal/expensive");

    private static Tag.Named<Fluid> tag(String name) {
      return TagFactory.FLUID.create(TConstruct.getResource(name));
    }

    private static Tag.Named<Fluid> forgeTag(String name) {
      return TagFactory.FLUID.create(new ResourceLocation("c", name));
    }
  }

  public static class EntityTypes {
    private static void init() {}
    public static final Tag.Named<EntityType<?>> SLIMES = forgeTag("slimes");
    public static final Tag.Named<EntityType<?>> BACON_PRODUCER = tag("bacon_producer");

    public static final Tag.Named<EntityType<?>> MELTING_SHOW = tag("melting/show_in_default");
    public static final Tag.Named<EntityType<?>> MELTING_HIDE = tag("melting/hide_in_default");
    public static final Tag.Named<EntityType<?>> PIGGYBACKPACK_BLACKLIST = tag("piggybackpack_blacklist");

    public static final Tag.Named<EntityType<?>> CREEPERS = forgeTag("creepers");
    /** Mobs that rarely spawn, boosts drop rate of severing */
    public static final Tag.Named<EntityType<?>> RARE_MOBS = tag("rare_mobs");


    private static Tag.Named<EntityType<?>> tag(String name) {
      return TagFactory.ENTITY_TYPE.create(TConstruct.getResource(name));
    }

    private static Tag.Named<EntityType<?>> forgeTag(String name) {
      return TagFactory.ENTITY_TYPE.create(new ResourceLocation("c", name));
    }
  }

  public static class TileEntityTypes {
    private static void init() {}
    public static final Tag.Named<BlockEntityType<?>> CRAFTING_STATION_BLACKLIST = tag("crafting_station_blacklist");

    private static Tag.Named<BlockEntityType<?>> tag(String name) {
      return TagFactory.of(Registry.BLOCK_ENTITY_TYPE_REGISTRY, "tags/tile_entity_types").create(TConstruct.getResource(name));
    }
  }
}
