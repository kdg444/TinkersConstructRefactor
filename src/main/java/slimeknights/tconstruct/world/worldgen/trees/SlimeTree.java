package slimeknights.tconstruct.world.worldgen.trees;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import slimeknights.tconstruct.shared.block.SlimeType;
import slimeknights.tconstruct.world.TinkerStructures;

import javax.annotation.Nullable;

public class SlimeTree extends AbstractTreeGrower {

  private final SlimeType foliageType;

  public SlimeTree(SlimeType foliageType) {
    this.foliageType = foliageType;
  }

  @Deprecated
  @Nullable
  @Override
  protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomIn, boolean largeHive) {
    return null;
  }

  /**
   * Get a {@link ConfiguredFeature} of tree
   */
  @Nullable
  private ConfiguredFeature<?, ?> getSlimeTreeFeature() {
    switch (this.foliageType) {
      case EARTH:
        return TinkerStructures.earthSlimeTree.get();
      case SKY:
        return TinkerStructures.skySlimeTree.get();
      case ENDER:
        return TinkerStructures.enderSlimeTree.get();
      case BLOOD:
        return TinkerStructures.bloodSlimeFungus.get();
      case ICHOR:
        return TinkerStructures.ichorSlimeFungus.get();
    }

    return null;
  }

  @Override
  public boolean growTree(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomSource rand) {
    ConfiguredFeature<?, ?> configuredFeature = this.getSlimeTreeFeature();
    if (configuredFeature == null) {
      return false;
    }
    world.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);
    if (configuredFeature.place(world, chunkGenerator, rand, pos)) {
      return true;
    }
    else {
      world.setBlock(pos, state, 4);
      return false;
    }
  }
}
