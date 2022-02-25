package slimeknights.tconstruct.library.utils;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Logic to keep track of the side of the block that was last hit
 */
public class BlockSideHitListener {
  private static final Map<UUID,Direction> HIT_FACE = new HashMap<>();
  private static boolean init = false;

  /** Initializies this listener */
  public static void init() {
    if (init) {
      return;
    }
    init = true;
    UseBlockCallback.EVENT.register(BlockSideHitListener::onLeftClickBlock);
    ServerPlayConnectionEvents.DISCONNECT.register(BlockSideHitListener::onLeaveServer);
  }

  /** Called when the player left clicks a block to store the face */
  private static InteractionResult onLeftClickBlock(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
    HIT_FACE.put(player.getUUID(), hitResult.getDirection());
    return InteractionResult.PASS;
  }

  /** Called when a player leaves the server to clear the face */
  private static void onLeaveServer(ServerGamePacketListenerImpl handler, MinecraftServer server) {
    HIT_FACE.remove(handler.getPlayer().getUUID());
  }

  /**
   * Gets the side this player last hit, should return correct values in most modifier hooks related to block breaking
   * @param player  Player
   * @return  Side last hit
   */
  public static Direction getSideHit(Player player) {
    return HIT_FACE.getOrDefault(player.getUUID(), Direction.UP);
  }
}
