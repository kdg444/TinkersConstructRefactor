package slimeknights.tconstruct.shared;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.mantle.lib.event.LivingEntityEvents;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.world.TinkerWorld;

@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonsEvents {

  public static void init() {
    LivingEntityEvents.JUMP.register(CommonsEvents::onLivingJump);
  }

  // Slimy block jump stuff
  static void onLivingJump(LivingEntity entity) {
    if (entity == null) {
      return;
    }

    // check if we jumped from a slime block
    BlockPos pos = new BlockPos(entity.getX(), entity.getY(), entity.getZ());
    if (entity.getCommandSenderWorld().isEmptyBlock(pos)) {
      pos = pos.below();
    }
    BlockState state = entity.getCommandSenderWorld().getBlockState(pos);
    Block block = state.getBlock();

    if (TinkerWorld.congealedSlime.contains(block)) {
      bounce(entity, 0.25f);
    } else if (TinkerWorld.slimeDirt.contains(block) || TinkerWorld.vanillaSlimeGrass.contains(block) || TinkerWorld.earthSlimeGrass.contains(block) || TinkerWorld.skySlimeGrass.contains(block) || TinkerWorld.enderSlimeGrass.contains(block) || TinkerWorld.ichorSlimeGrass.contains(block)) {
      bounce(entity, 0.06f);
    }
  }

  private static void bounce(Entity entity, float amount) {
    entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, amount, 0.0D));
    entity.playSound(Sounds.SLIMY_BOUNCE.getSound(), 0.5f + amount, 1f);
  }
}
