package slimeknights.tconstruct.tools;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.fabricators_of_create.porting_lib.model.ModelLoaderRegistry;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import slimeknights.mantle.data.ISafeManagerReloadListener;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.ClientEventBase;
import slimeknights.tconstruct.common.network.TinkerNetwork;
import slimeknights.tconstruct.library.TinkerRegistries;
import slimeknights.tconstruct.library.client.materials.MaterialTooltipCache;
import slimeknights.tconstruct.library.client.model.tools.MaterialModel;
import slimeknights.tconstruct.library.client.model.tools.ToolModel;
import slimeknights.tconstruct.library.client.modifiers.BreakableDyedModifierModel;
import slimeknights.tconstruct.library.client.modifiers.BreakableMaterialModifierModel;
import slimeknights.tconstruct.library.client.modifiers.BreakableModifierModel;
import slimeknights.tconstruct.library.client.modifiers.FluidModifierModel;
import slimeknights.tconstruct.library.client.modifiers.ModifierModelManager;
import slimeknights.tconstruct.library.client.modifiers.ModifierModelManager.ModifierModelRegistrationEvent;
import slimeknights.tconstruct.library.client.modifiers.NormalModifierModel;
import slimeknights.tconstruct.library.client.modifiers.TankModifierModel;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.utils.HarvestTiers;
import slimeknights.tconstruct.tools.client.ArmorModelHelper;
import slimeknights.tconstruct.tools.client.OverslimeModifierModel;
import slimeknights.tconstruct.tools.client.PlateArmorModel;
import slimeknights.tconstruct.tools.client.SlimelytraArmorModel;
import slimeknights.tconstruct.tools.client.SlimeskullArmorModel;
import slimeknights.tconstruct.tools.client.ToolContainerScreen;
import slimeknights.tconstruct.tools.client.particles.AxeAttackParticle;
import slimeknights.tconstruct.tools.client.particles.HammerAttackParticle;
import slimeknights.tconstruct.tools.logic.InteractionHandler;
import slimeknights.tconstruct.tools.modifiers.ability.armor.DoubleJumpModifier;
import slimeknights.tconstruct.tools.network.TinkerControlPacket;

import static slimeknights.tconstruct.library.client.model.tools.ToolModel.registerItemColors;

@SuppressWarnings("unused")
public class ToolClientEvents extends ClientEventBase {
  /** Keybinding for interacting using a helmet */
  private static final KeyMapping HELMET_INTERACT = new KeyMapping(TConstruct.makeTranslationKey("key", "helmet_interact"), KeyConflictContext.IN_GAME, InputConstants.getKey("key.keyboard.z"), "key.categories.tconstruct");
  /** Keybinding for interacting using leggings */
  private static final KeyMapping LEGGINGS_INTERACT = new KeyMapping(TConstruct.makeTranslationKey("key", "leggings_interact"), KeyConflictContext.IN_GAME, InputConstants.getKey("key.keyboard.i"), "key.categories.tconstruct");

  /** Listener to clear modifier cache */
  private static final ISafeManagerReloadListener MODIFIER_RELOAD_LISTENER = manager -> {
    for (Modifier modifier : TinkerRegistries.MODIFIERS) {
      modifier.clearCache(PackType.CLIENT_RESOURCES);
    }
  };

  @SubscribeEvent
  static void addResourceListener(RegisterClientReloadListenersEvent manager) {
    ModifierModelManager.init(manager);
    MaterialTooltipCache.init(manager);
    manager.registerReloadListener(MODIFIER_RELOAD_LISTENER);
    manager.registerReloadListener(PlateArmorModel.RELOAD_LISTENER);
    manager.registerReloadListener(SlimeskullArmorModel.RELOAD_LISTENER);
    manager.registerReloadListener(SlimelytraArmorModel.RELOAD_LISTENER);
    manager.registerReloadListener(HarvestTiers.RELOAD_LISTENER);
  }

  @SubscribeEvent
  static void registerModelLoaders(ModelRegistryEvent event) {
    ModelLoaderRegistry.registerLoader(TConstruct.getResource("material"), MaterialModel.LOADER);
    ModelLoaderRegistry.registerLoader(TConstruct.getResource("tool"), ToolModel.LOADER);
  }

  static void registerModifierModels(ModifierModelRegistrationEvent event) {
    event.registerModel(TConstruct.getResource("normal"), NormalModifierModel.UNBAKED_INSTANCE);
    event.registerModel(TConstruct.getResource("breakable"), BreakableModifierModel.UNBAKED_INSTANCE);
    event.registerModel(TConstruct.getResource("overslime"), OverslimeModifierModel.UNBAKED_INSTANCE);
    event.registerModel(TConstruct.getResource("fluid"), FluidModifierModel.UNBAKED_INSTANCE);
    event.registerModel(TConstruct.getResource("tank"), TankModifierModel.UNBAKED_INSTANCE);
    event.registerModel(TConstruct.getResource("breakable_material"), BreakableMaterialModifierModel.UNBAKED_INSTANCE);
    event.registerModel(TConstruct.getResource("breakable_dyed"), BreakableDyedModifierModel.UNBAKED_INSTANCE);
  }

  static void registerRenderers() {
    EntityRendererRegistry.register(TinkerTools.indestructibleItem.get(), ItemEntityRenderer::new);
  }

  public static void clientSetupEvent() {
    PlayerTickEvents.START.register(ToolClientEvents::handleKeyBindings);
    ArmorModelHelper.init();

    // keybinds
    KeyBindingHelper.registerKeyBinding(HELMET_INTERACT);
    KeyBindingHelper.registerKeyBinding(LEGGINGS_INTERACT);

    // screens
    ScreenRegistry.register(TinkerTools.toolContainer.get(), ToolContainerScreen::new);

    registerRenderers();
    registerParticleFactories();
    ColorHandlersCallback.ITEM.register(ToolClientEvents::itemColors);
    ModifierModelRegistrationEvent.EVENT.register(ToolClientEvents::registerModifierModels);
  }

  static void registerParticleFactories() {
    ParticleFactoryRegistry.getInstance().register(TinkerTools.hammerAttackParticle.get(), HammerAttackParticle.Factory::new);
    ParticleFactoryRegistry.getInstance().register(TinkerTools.axeAttackParticle.get(), AxeAttackParticle.Factory::new);
  }

  static void itemColors(ItemColors colors, BlockColors blockColors) {

    // tint tool textures for fallback
    // rock
    registerItemColors(colors, TinkerTools.pickaxe);
    registerItemColors(colors, TinkerTools.sledgeHammer);
    registerItemColors(colors, TinkerTools.veinHammer);
    // dirt
    registerItemColors(colors, TinkerTools.mattock);
    registerItemColors(colors, TinkerTools.pickadze);
    registerItemColors(colors, TinkerTools.excavator);
    // wood
    registerItemColors(colors, TinkerTools.handAxe);
    registerItemColors(colors, TinkerTools.broadAxe);
    // scythe
    registerItemColors(colors, TinkerTools.kama);
    registerItemColors(colors, TinkerTools.scythe);
    // weapon
    registerItemColors(colors, TinkerTools.dagger);
    registerItemColors(colors, TinkerTools.sword);
    registerItemColors(colors, TinkerTools.cleaver);
  }

  // values to check if a key was being pressed last tick, safe as a static value as we only care about a single player client side
  /** If true, we were jumping last tick */
  private static boolean wasJumping = false;
  /** If true, we were interacting with helmet last tick */
  private static boolean wasHelmetInteracting = false;
  /** If true, we were interacting with leggings last tick */
  private static boolean wasLeggingsInteracting = false;

  /** Called on player tick to handle keybinding presses */
  private static void handleKeyBindings(Player player) {
    Minecraft minecraft = Minecraft.getInstance();
    if (minecraft.player != null && minecraft.player == player && player.level.isClientSide() && !minecraft.player.isSpectator()) {

      // jumping in mid air for double jump
      // ensure we pressed the key since the last tick, holding should not use all your jumps at once
      boolean isJumping = minecraft.options.keyJump.isDown();
      if (!wasJumping && isJumping) {
        if (DoubleJumpModifier.extraJump(player)) {
          TinkerNetwork.getInstance().sendToServer(TinkerControlPacket.DOUBLE_JUMP);
        }
      }
      wasJumping = isJumping;

      // helmet interaction
      boolean isHelmetInteracting = HELMET_INTERACT.isDown();
      if (!wasHelmetInteracting && isHelmetInteracting) {
        if (InteractionHandler.startArmorInteract(player, EquipmentSlot.HEAD)) {
          TinkerNetwork.getInstance().sendToServer(TinkerControlPacket.START_HELMET_INTERACT);
        }
      }
      if (wasHelmetInteracting && !isHelmetInteracting) {
        if (InteractionHandler.stopArmorInteract(player, EquipmentSlot.HEAD)) {
          TinkerNetwork.getInstance().sendToServer(TinkerControlPacket.STOP_HELMET_INTERACT);
        }
      }

      // leggings interaction
      boolean isLeggingsInteract = LEGGINGS_INTERACT.isDown();
      if (!wasLeggingsInteracting && isLeggingsInteract) {
        if (InteractionHandler.startArmorInteract(player, EquipmentSlot.LEGS)) {
          TinkerNetwork.getInstance().sendToServer(TinkerControlPacket.START_LEGGINGS_INTERACT);
        }
      }
      if (wasLeggingsInteracting && !isLeggingsInteract) {
        if (InteractionHandler.stopArmorInteract(player, EquipmentSlot.LEGS)) {
          TinkerNetwork.getInstance().sendToServer(TinkerControlPacket.STOP_LEGGINGS_INTERACT);
        }
      }

      wasHelmetInteracting = isHelmetInteracting;
      wasLeggingsInteracting = isLeggingsInteract;
    }
  }
}
