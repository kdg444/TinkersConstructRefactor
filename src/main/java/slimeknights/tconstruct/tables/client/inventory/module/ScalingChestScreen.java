package slimeknights.tconstruct.tables.client.inventory.module;

import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;
import slimeknights.mantle.client.screen.MultiModuleScreen;
import slimeknights.mantle.inventory.BaseContainerMenu;
import slimeknights.tconstruct.tables.block.entity.inventory.IScalingContainer;
import slimeknights.tconstruct.transfer.EmptySlottedStorage;

import java.util.Optional;

public class ScalingChestScreen<T extends BlockEntity> extends DynamicContainerScreen {
  private final IScalingContainer scaling;
  public ScalingChestScreen(MultiModuleScreen<?> parent, BaseContainerMenu<T> container, Inventory playerInventory, Component title) {
    super(parent, container, playerInventory, title);
    BlockEntity tile = container.getTile();
    SlottedStorage<ItemVariant> handler = (SlottedStorage<ItemVariant>) Optional.ofNullable(TransferUtil.getItemStorage(tile))
                                   .orElse(EmptySlottedStorage.EMPTY);
    this.scaling = handler instanceof IScalingContainer ? (IScalingContainer) handler : handler::getSlotCount;
    this.slotCount = scaling.getVisualSize();
    this.sliderActive = true;
  }

  @Override
  public void updatePosition(int parentX, int parentY, int parentSizeX, int parentSizeY) {
    this.leftPos = parentX + this.xOffset;
    this.topPos = parentY + this.yOffset;

    // calculate rows and columns from space
    this.columns = (this.imageWidth - this.slider.width) / slot.w;
    this.rows = this.imageHeight / slot.h;

    this.updateSlider();
    this.updateSlots();
  }

  @Override
  protected void updateSlider() {
    this.sliderActive = this.slotCount > this.columns * this.rows;
    super.updateSlider();
    this.slider.setEnabled(this.sliderActive);
    this.slider.show();
  }

  @Override
  public void update(int mouseX, int mouseY) {
    this.slotCount = this.scaling.getVisualSize();
    super.update(mouseX, mouseY);
    this.updateSlider();
    this.updateSlots();
  }

  @Override
  public boolean shouldDrawSlot(Slot slot) {
    if (slot.getSlotIndex() >= this.scaling.getVisualSize()) {
      return false;
    }
    return super.shouldDrawSlot(slot);
  }

  @Override
  protected void renderLabels(GuiGraphics graphics, int x, int y) {}
}
