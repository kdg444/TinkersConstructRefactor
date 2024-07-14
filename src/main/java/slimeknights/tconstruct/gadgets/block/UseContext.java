package slimeknights.tconstruct.gadgets.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class UseContext{
    private final BlockState state;
    private final Level world;
    private final BlockPos pos;
    private final Player player;
    private final InteractionHand handIn;
    private final BlockHitResult hit;

    public UseContext(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        this.state = state;
        this.world = world;
        this.pos = pos;
        this.player = player;
        this.handIn = handIn;
        this.hit = hit;
    }

    // Getters for all the fields
    public BlockState getState() { return state; }
    public Level getWorld() { return world; }
    public BlockPos getPos() { return pos; }
    public Player getPlayer() { return player; }
    public InteractionHand getHandIn() { return handIn; }
    public BlockHitResult getHit() { return hit; }
}