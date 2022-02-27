package slimeknights.tconstruct.tools.modifiers.upgrades.general;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import slimeknights.mantle.lib.event.LivingEntityEvents;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.impl.SingleUseModifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Collection;
import java.util.Iterator;

public class SoulboundModifier extends SingleUseModifier {
  public SoulboundModifier() {
    // high priority so we do it before other possibly death-inventory-modifying mods
    LivingEntityEvents.DROPS.register(this::onPlayerDeath);
    ServerPlayerEvents.COPY_FROM.register(this::onPlayerClone);
  }

  /** Called when the player dies to store the item in the original inventory */
  private boolean onPlayerDeath(LivingEntity entity, DamageSource source, Collection<ItemEntity> drops) {
//    if (event.isCanceled()) {
//      return;
//    }
    // only care about real players with keep inventory off
    if (!entity.getCommandSenderWorld().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && entity instanceof Player player /*&& !(entity instanceof FakePlayer)*/) { // TODO: PORT (fake players?)
      Iterator<ItemEntity> iter = drops.iterator();
      Inventory inventory = player.getInventory();
      while (iter.hasNext()) {
        ItemEntity itemEntity = iter.next();
        ItemStack stack = itemEntity.getItem();
        // find tools with soulbound
        if (TinkerTags.Items.MODIFIABLE.contains(stack.getItem())) {
          ToolStack tool = ToolStack.from(stack);
          if (tool.getModifierLevel(this) > 0) {
            inventory.add(stack);
            iter.remove();
          }
        }
      }
    }
    return false;
  }

  /** Called when the new player is created to fetch the soulbound item from the old */
  private void onPlayerClone(ServerPlayer original, ServerPlayer clone, boolean alive) {
    if (alive) {
      return;
    }
    // inventory already copied
    if (clone.getCommandSenderWorld().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || original.isSpectator()) {
      return;
    }
    // find the soulbound items
    Inventory originalInv = original.getInventory();
    Inventory cloneInv = clone.getInventory();
    for(int i = 0; i < originalInv.getContainerSize(); i++) {
      // find tools with soulbound
      ItemStack stack = originalInv.getItem(i);
      if (!stack.isEmpty() && TinkerTags.Items.MODIFIABLE.contains(stack.getItem())) {
        ToolStack tool = ToolStack.from(stack);
        if (tool.getModifierLevel(this) > 0) {
          cloneInv.add(stack);
        }
      }
    }
  }
}
