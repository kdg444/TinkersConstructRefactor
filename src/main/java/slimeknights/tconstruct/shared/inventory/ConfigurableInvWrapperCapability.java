package slimeknights.tconstruct.shared.inventory;

import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.world.Container;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Iterator;
import java.util.List;

public class ConfigurableInvWrapperCapability implements SlottedStorage<ItemVariant> {

  private final InventoryStorage wrapped;
  private final boolean canInsert;
  private final boolean canExtract;

  public ConfigurableInvWrapperCapability(Container inv, boolean canInsert, boolean canExtract) {
    this.wrapped = InventoryStorage.of(inv, null);
    this.canInsert = canInsert;
    this.canExtract = canExtract;
  }

  @Override
  public @UnmodifiableView List<SingleSlotStorage<ItemVariant>> getSlots() {
    return this.wrapped.getSlots();
  }

  @Override
  public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
    if (!this.canInsert) {
      return 0;
    }
    return this.wrapped.insert(resource, maxAmount, transaction);
  }

  @Override
  public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
    if (!this.canExtract) {
      return 0;
    }
    return this.wrapped.extract(resource, maxAmount, transaction);
  }

  @Override
  public Iterator<StorageView<ItemVariant>> iterator() {
    return this.wrapped.iterator();
  }

  @Override
  public int getSlotCount() {
    return this.wrapped.getSlotCount();
  }

  @Override
  public SingleSlotStorage<ItemVariant> getSlot(int slot) {
    return this.wrapped.getSlot(slot);
  }

  @Override
  public boolean supportsInsertion() {
    return this.canInsert;
  }

  @Override
  public boolean supportsExtraction() {
    return this.canExtract;
  }

  @Override
  public Iterator<StorageView<ItemVariant>> nonEmptyIterator() {
    return this.wrapped.nonEmptyIterator();
  }

  @Override
  public Iterable<StorageView<ItemVariant>> nonEmptyViews() {
    return this.wrapped.nonEmptyViews();
  }
}
