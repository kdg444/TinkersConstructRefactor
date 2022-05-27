package slimeknights.tconstruct.tools.modifiers.traits.skull;

import io.github.fabricators_of_create.porting_lib.mixin.common.accessor.DamageSourceAccessor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import io.github.fabricators_of_create.porting_lib.event.common.PlayerTickEvents;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.hooks.IArmorInteractModifier;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability.TinkerDataKey;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nullable;
import java.util.UUID;

public class SelfDestructiveModifier extends NoLevelsModifier implements IArmorInteractModifier {
  private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(UUID.fromString("68ee3026-1d50-4eb4-914e-a8b05fbfdb71"), TConstruct.prefix("self_destruct_slowdown"), -0.9f, Operation.MULTIPLY_TOTAL);
  /** Self damage source */
  private static final DamageSource SELF_DESTRUCT = ((DamageSourceAccessor)(DamageSourceAccessor.port_lib$init(TConstruct.prefix("self_destruct")))).port_lib$setDamageBypassesArmor().setExplosion();
  /** Key for the time the fuse finises */
  private static final TinkerDataKey<Integer> FUSE_FINISH = TConstruct.createKey("self_destruct_finish");

  public SelfDestructiveModifier() {
    PlayerTickEvents.START.register(SelfDestructiveModifier::playerTick);
  }

  @Override
  public boolean startArmorInteract(IToolStackView tool, int level, Player player, EquipmentSlot slot) {
    if (player.isShiftKeyDown()) {
      TinkerDataCapability.CAPABILITY.maybeGet(player).ifPresent(data -> data.put(FUSE_FINISH, player.tickCount + 30));
      player.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
      // make the player slow
      AttributeInstance instance = player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
      if (instance != null && !instance.hasModifier(SPEED_MODIFIER)) {
        instance.addTransientModifier(SPEED_MODIFIER);
      }
      return true;
    }
    return false;
  }

  /** Restores speed to full */
  private static void restoreSpeed(LivingEntity livingEntity) {
    AttributeInstance instance = livingEntity.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
    if (instance != null) {
      instance.removeModifier(SPEED_MODIFIER);
    }
  }

  @Override
  public void stopArmorInteract(IToolStackView tool, int level, Player player, EquipmentSlot slot) {
    TinkerDataCapability.CAPABILITY.maybeGet(player).ifPresent(data -> data.remove(FUSE_FINISH));
    restoreSpeed(player);
  }

  @Override
  public void onUnequip(IToolStackView tool, int level, EquipmentChangeContext context) {
    context.getTinkerData().ifPresent(data -> data.remove(FUSE_FINISH));
    restoreSpeed(context.getEntity());
  }

  @Nullable
  @Override
  public <T> T getModule(Class<T> type) {
    return tryModuleMatch(type, IArmorInteractModifier.class, this);
  }

  /** Called on player tick to update the fuse */
  private static void playerTick(Player player) {
    if (!player.level.isClientSide && !player.isSpectator()) {
      TinkerDataCapability.CAPABILITY.maybeGet(player).ifPresent(data -> {
        Integer fuseFinish = data.get(FUSE_FINISH);
        if (fuseFinish != null && fuseFinish <= player.tickCount) {
          player.level.explode(player, player.getX(), player.getY(), player.getZ(), 3, Explosion.BlockInteraction.DESTROY);
          player.hurt(SELF_DESTRUCT, 99999);
          if (player.getHealth() > 0) {
            restoreSpeed(player);
          }
          data.remove(FUSE_FINISH);
        }
      });
    }
  }
}
