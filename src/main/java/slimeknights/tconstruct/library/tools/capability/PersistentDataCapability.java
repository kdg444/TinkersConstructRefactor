package slimeknights.tconstruct.library.tools.capability;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Player;
import io.github.fabricators_of_create.porting_lib.event.common.EntityEvents;
import io.github.fabricators_of_create.porting_lib.util.Lazy;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.network.SyncPersistentDataPacket;
import slimeknights.tconstruct.common.network.TinkerNetwork;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

/**
 * Capability to store persistent NBT data on an entity. For players, this is automatically synced to the client on load, but not during gameplay.
 * Persists after death, will reassess if we need some data to not persist death
 */
public class PersistentDataCapability implements EntityComponentInitializer {

  /** Capability ID */
  private static final ResourceLocation ID = TConstruct.getResource("persistent_data");
  /** Capability type */
  public static final ComponentKey<NamespacedNBT> CAPABILITY = ComponentRegistry.getOrCreate(ID, NamespacedNBT.class);

  /** Registers this capability */
  public static void register() {
//    FMLJavaModLoadingContext.get().getModEventBus().addListener(EventPriority.NORMAL, false, RegisterCapabilitiesEvent.class, PersistentDataCapability::register);
//    MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, PersistentDataCapability::attachCapability);
    ServerPlayerEvents.COPY_FROM.register(PersistentDataCapability::playerClone);
    ServerPlayerEvents.AFTER_RESPAWN.register(PersistentDataCapability::playerRespawn);
    ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(PersistentDataCapability::playerChangeDimension);
    ServerPlayConnectionEvents.JOIN.register(PersistentDataCapability::playerLoggedIn);
  }

  /** Event listener to attach the capability */
  @Override
  public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
    register();
    registry.registerForPlayers(CAPABILITY, player -> new NamespacedNBT());
//    EntityEvents.ON_REMOVE.register((entity, reason) -> );
//    if (event.getObject() instanceof Player) {
//      Provider provider = new Provider();
//      event.addCapability(ID, provider);
//      event.addListener(provider);
//    }
  }

  /** Syncs the data to the given player */
  private static void sync(Player player) {
    CAPABILITY.maybeGet(player).ifPresent(data -> TinkerNetwork.getInstance().sendTo(new SyncPersistentDataPacket(data.getCopy()), player));
  }

  /** copy caps when the player respawns/returns from the end */
  private static void playerClone(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
    CAPABILITY.maybeGet(oldPlayer).ifPresent(oldData -> {
      CompoundTag nbt = oldData.getCopy();
      if (!nbt.isEmpty()) {
        CAPABILITY.maybeGet(newPlayer).ifPresent(newData -> newData.copyFrom(nbt));
      }
    });
  }

  /** sync caps when the player respawns/returns from the end */
  private static void playerRespawn(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
    sync(newPlayer);
  }

  /** sync caps when the player changes dimensions */
  private static void playerChangeDimension(ServerPlayer player, ServerLevel origin, ServerLevel destination) {
    sync(player);
  }

  /** sync caps when the player logs in */
  private static void playerLoggedIn(ServerGamePacketListenerImpl handler, PacketSender sender, MinecraftServer server) {
    sync(handler.getPlayer());
  }

//  /** Capability provider instance */
//  private static class Provider extends ComponentKey<NamespacedNBT> implements Runnable {
//    private Lazy<CompoundTag> nbt;
//    private LazyOptional<NamespacedNBT> capability;
//    private Provider() {
//      this.nbt = Lazy.of(CompoundTag::new);
//      this.capability = LazyOptional.of(() -> NamespacedNBT.readFromNBT(nbt.get()));
//    }
//
//    @Override
//    public @Nullable NamespacedNBT getInternal(ComponentContainer container) {
//      return null;
//    }
//
//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
//      return CAPABILITY.orEmpty(cap, capability);
//    }
//
//    @Override
//    public void run() {
//      // called when capabilities invalidate, create a new cap just in case they are revived later
//      capability.invalidate();
//      capability = LazyOptional.of(() -> NamespacedNBT.readFromNBT(nbt.get()));
//    }
//
//    @Override
//    public CompoundTag serializeNBT() {
//      return nbt.get().copy();
//    }
//
//    @Override
//    public void deserializeNBT(CompoundTag nbt) {
//      this.nbt = Lazy.of(() -> nbt);
//      run();
//    }
//  }
}
