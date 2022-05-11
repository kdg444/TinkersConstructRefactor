package slimeknights.tconstruct.world;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingVisibilityEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import slimeknights.mantle.loot.function.SetFluidLootFunction;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.json.AddToolDataFunction;
import slimeknights.tconstruct.library.json.RandomMaterial;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.shared.block.SlimeType;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import javax.annotation.Nullable;
import java.util.Arrays;

@SuppressWarnings("unused")
public class WorldEvents {
  /** Checks if the biome matches the given categories */
  private static boolean matches(boolean hasNoTypes, @Nullable ResourceKey<Biome> key, BiomeCategory given, @Nullable BiomeCategory check, Type type) {
    if (hasNoTypes || key == null) {
      // check of null means not none, the nether/end checks were done earlier
      if (check == null) {
        return given != BiomeCategory.NONE;
      }
      return given == check;
    }
    // we have a key, require matching all the given types
    return BiomeDictionary.hasType(key, type);
  }

  public static void init() {
    LootTableLoadingCallback.EVENT.register(WorldEvents::onLootTableLoad);
    ServerLifecycleEvents.SERVER_STARTING.register(WorldEvents::serverStarting);
    ServerWorldEvents.LOAD.register(WorldEvents::onWorldLoad);
    LivingEntityEvents.DROPS.register(WorldEvents::creeperKill);
    onBiomeLoad();
  }

  static void onBiomeLoad() {
//    BiomeGenerationSettingsBuilder generation = event.getGeneration();
//    MobSpawnSettingsBuilder spawns = event.getSpawns();

    // setup for biome checks
//    BiomeCategory category = event.getCategory();
//    ResourceLocation name = event.getName();
//    ResourceKey<Biome> key = name == null ? null : ResourceKey.create(Registry.BIOME_REGISTRY, name);
//    boolean hasNoTypes = key == null || !BiomeDictionary.hasAnyType(key);

    // nether - any biome is fine
//    if (matches(hasNoTypes, key, category, BiomeCategory.NETHER, Type.NETHER)) {
      if (Config.COMMON.generateCobalt.get()) {
        TinkerWorld.placedSmallCobaltOre.getHolder().ifPresent(holder -> generation.addFeature(Decoration.UNDERGROUND_DECORATION, holder));
        TinkerWorld.placedLargeCobaltOre.getHolder().ifPresent(holder -> generation.addFeature(Decoration.UNDERGROUND_DECORATION, holder));
      }
      // ichor can be anywhere
      if (Config.COMMON.ichorGeodes.get()) {
        TinkerWorld.placedIchorGeode.getHolder().ifPresent(holder -> generation.addFeature(Decoration.LOCAL_MODIFICATIONS, holder));
      }
//    }
    // end, mostly do stuff in the outer islands
//    else if (matches(hasNoTypes, key, category, BiomeCategory.THEEND, Type.END)) {
      // slime spawns anywhere, uses the grass
      BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), MobCategory.MONSTER, TinkerWorld.enderSlimeEntity.get(), 10, 2, 4);
      // geodes only on outer islands
      if (Config.COMMON.enderGeodes.get() && key != null && !Biomes.THE_END.equals(key)) {
        TinkerWorld.placedEnderGeode.getHolder().ifPresent(holder -> generation.addFeature(Decoration.LOCAL_MODIFICATIONS, holder));
      }
//    }
    // overworld gets tricky
//    else if (matches(hasNoTypes, key, category, null, Type.OVERWORLD)) {
      // slime spawns anywhere, uses the grass
      BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.MONSTER, TinkerWorld.earthSlimeEntity.get(), 100, 2, 4);
      BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.MONSTER, TinkerWorld.skySlimeEntity.get(), 100, 2, 4);

      // earth spawns anywhere, sky does not spawn in ocean (looks weird)
      if (Config.COMMON.earthGeodes.get()) {
        TinkerWorld.placedEarthGeode.getHolder().ifPresent(holder -> generation.addFeature(Decoration.LOCAL_MODIFICATIONS, holder));
      }
      // sky spawn in non-oceans, they look funny in the ocean as they spawn so high
      if (Config.COMMON.skyGeodes.get()) {
        boolean add;
        if (hasNoTypes) {
          add = category != BiomeCategory.OCEAN && category != BiomeCategory.BEACH && category != BiomeCategory.RIVER;
        } else {
          add = !BiomeDictionary.hasType(key, Type.WATER) && !BiomeDictionary.hasType(key, Type.BEACH);
        }
        if (add) {
          TinkerWorld.placedSkyGeode.getHolder().ifPresent(holder -> generation.addFeature(Decoration.LOCAL_MODIFICATIONS, holder));
        }
      }
//    }
  }


  /* Loot injection */

  /**
   * Injects an entry into a loot pool
   * @param lootTable      Loot table event
   * @param poolName   Pool name
   * @param entries    Entry to inject
   */
  private static void injectInto(LootTable lootTable, String poolName, LootPoolEntryContainer... entries) {
    LootPool pool = getPool(lootTable, poolName);
    //noinspection ConstantConditions method is annotated wrongly
    if (pool != null) {
      int oldLength = pool.entries.length;
      pool.entries = Arrays.copyOf(pool.entries, oldLength + entries.length);
      System.arraycopy(entries, 0, pool.entries, oldLength, entries.length);
    }
  }

  public static LootPool getPool(LootTable table, String name) {
    return Lists.newArrayList(table.pools).stream().filter(e -> name.equals(/*e.name*/"")).findFirst().orElse(null); // TODO: PORT
  }

  /** Makes a seed injection loot entry */
  private static LootPoolEntryContainer makeSeed(SlimeType type, int weight) {
    return LootItem.lootTableItem(TinkerWorld.slimeGrassSeeds.get(type)).setWeight(weight)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))).build();
  }

  /** Makes a sapling injection loot entry */
  private static LootPoolEntryContainer makeSapling(SlimeType type, int weight) {
    return LootItem.lootTableItem(TinkerWorld.slimeSapling.get(type)).setWeight(weight).build();
  }

  static void onLootTableLoad(ResourceManager resourceManager, LootTables manager, ResourceLocation name, FabricLootSupplierBuilder supplier, LootTableLoadingCallback.LootTableSetter setter) {
    if ("minecraft".equals(name.getNamespace())) {
      switch (name.getPath()) {
        // sky
        case "chests/simple_dungeon":
          if (Config.COMMON.slimyLootChests.get()) {
            injectInto(manager.get(name), "pool1", makeSeed(SlimeType.EARTH, 3), makeSeed(SlimeType.SKY, 7));
            injectInto(manager.get(name), "main", makeSapling(SlimeType.EARTH, 3), makeSapling(SlimeType.SKY, 7));
          }
          break;
        // ichor
        case "chests/nether_bridge":
          if (Config.COMMON.slimyLootChests.get()) {
            injectInto(manager.get(name), "main", makeSeed(SlimeType.BLOOD, 5));
          }
          break;
        case "chests/bastion_bridge":
          if (Config.COMMON.slimyLootChests.get()) {
            injectInto(manager.get(name), "pool2", makeSapling(SlimeType.BLOOD, 1));
          }
          break;
        // ender
        case "chests/end_city_treasure":
          if (Config.COMMON.slimyLootChests.get()) {
            injectInto(manager.get(name), "main", makeSeed(SlimeType.ENDER, 5), makeSapling(SlimeType.ENDER, 3));
          }
          break;

        // barter for molten blaze lanterns
        case "gameplay/piglin_bartering": {
          int weight = Config.COMMON.barterBlazingBlood.get();
          if (weight > 0) {
            injectInto(manager.get(name), "main", LootItem.lootTableItem(TinkerSmeltery.scorchedLantern).setWeight(weight)
                                              .apply(SetFluidLootFunction.builder(new FluidStack(TinkerFluids.blazingBlood.get(), FluidValues.LANTERN_CAPACITY)))
                                              .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
                                              .build());
          }
          break;
        }

          // randomly swap vanilla tool for a tinkers tool
        case "chests/spawn_bonus_chest": {
          int weight = Config.COMMON.tinkerToolBonusChest.get();
          if (weight > 0) {
            RandomMaterial randomHead = RandomMaterial.random(HeadMaterialStats.ID).tier(1).build();
            RandomMaterial firstHandle = RandomMaterial.firstWithStat(HandleMaterialStats.ID); // should be wood
            RandomMaterial randomBinding = RandomMaterial.random(ExtraMaterialStats.ID).tier(1).build();
            injectInto(manager.get(name), "main", LootItem.lootTableItem(TinkerTools.handAxe.get())
                                              .setWeight(weight)
                                              .apply(AddToolDataFunction.builder()
                                                               .addMaterial(randomHead)
                                                               .addMaterial(firstHandle)
                                                               .addMaterial(randomBinding))
                                              .build());
            injectInto(manager.get(name), "pool1", LootItem.lootTableItem(TinkerTools.pickaxe.get())
                                               .setWeight(weight)
                                               .apply(AddToolDataFunction.builder()
                                                               .addMaterial(randomHead)
                                                               .addMaterial(firstHandle)
                                                               .addMaterial(randomBinding))
                                               .build());
          }
          break;
        }
      }
    }
  }


  /* Heads */

//  @SubscribeEvent TODO: PORT
//  static void livingVisibility(LivingVisibilityEvent event) {
//    Entity lookingEntity = event.getLookingEntity();
//    if (lookingEntity == null) {
//      return;
//    }
//    LivingEntity entity = event.getEntityLiving();
//    ItemStack helmet = entity.getItemBySlot(EquipmentSlot.HEAD);
//    Item item = helmet.getItem();
//    if (item != Items.AIR && TinkerWorld.headItems.contains(item)) {
//      if (lookingEntity.getType() == ((TinkerHeadType)((SkullBlock)((BlockItem)item).getBlock()).getType()).getType()) {
//        event.modifyVisibility(0.5f);
//      }
//    }
//  }

  static boolean creeperKill(LivingEntity target, DamageSource source, Collection<ItemEntity> drops) {
    if (source != null) {
      Entity entity = source.getEntity();
      if (entity instanceof Creeper creeper) {
        if (creeper.canDropMobsSkull()) {
          LivingEntity dying = target;
          TinkerHeadType headType = TinkerHeadType.fromEntityType(dying.getType());
          if (headType != null && Config.COMMON.headDrops.get(headType).get()) {
            creeper.increaseDroppedSkulls();
            drops.add(dying.spawnAtLocation(TinkerWorld.heads.get(headType)));
          }
        }
      }
    }
    return false;
  }
}
