package slimeknights.tconstruct.common.data.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import slimeknights.tconstruct.common.TinkerTags;

import static net.minecraft.tags.BiomeTags.IS_BADLANDS;
import static net.minecraft.tags.BiomeTags.IS_BEACH;
import static net.minecraft.tags.BiomeTags.IS_DEEP_OCEAN;
import static net.minecraft.tags.BiomeTags.IS_FOREST;
import static net.minecraft.tags.BiomeTags.IS_HILL;
import static net.minecraft.tags.BiomeTags.IS_MOUNTAIN;
import static net.minecraft.tags.BiomeTags.IS_NETHER;
import static net.minecraft.tags.BiomeTags.IS_OCEAN;
import static net.minecraft.tags.BiomeTags.IS_RIVER;
import static net.minecraft.tags.BiomeTags.IS_TAIGA;
import static net.minecraft.world.level.biome.Biomes.END_BARRENS;
import static net.minecraft.world.level.biome.Biomes.END_HIGHLANDS;
import static net.minecraft.world.level.biome.Biomes.END_MIDLANDS;
import static net.minecraft.world.level.biome.Biomes.SMALL_END_ISLANDS;

@SuppressWarnings("unchecked")
public class BiomeTagProvider extends BiomeTagsProvider {

  public BiomeTagProvider(DataGenerator generatorIn) {
    super(generatorIn);
  }

  @Override
  protected void addTags() {
    this.tag(TinkerTags.Biomes.CLAY_ISLANDS).addTag(IS_DEEP_OCEAN).addTag(IS_OCEAN).addTag(IS_BEACH).addTag(IS_RIVER).addTag(IS_MOUNTAIN).addTag(IS_BADLANDS).addTag(IS_HILL);
    this.tag(TinkerTags.Biomes.EARTHSLIME_ISLANDS).addTag(IS_DEEP_OCEAN).addTag(IS_OCEAN);
    this.tag(TinkerTags.Biomes.SKYSLIME_ISLANDS).addTag(IS_DEEP_OCEAN).addTag(IS_OCEAN).addTag(IS_BEACH).addTag(IS_RIVER).addTag(IS_MOUNTAIN).addTag(IS_BADLANDS).addTag(IS_HILL).addTag(IS_TAIGA).addTag(IS_FOREST);
    this.tag(TinkerTags.Biomes.BLOOD_ISLANDS).addTag(IS_NETHER);
    this.tag(TinkerTags.Biomes.ENDERSLIME_ISLANDS).add(END_HIGHLANDS, END_MIDLANDS, SMALL_END_ISLANDS, END_BARRENS);
  }
}
