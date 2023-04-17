package slimeknights.tconstruct.tables.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import slimeknights.mantle.item.RetexturedBlockItem;

import javax.annotation.Nullable;
import java.util.function.BooleanSupplier;

/** Retextured block that conditionally enables show all variants */
public class TableBlockItem extends RetexturedBlockItem {
  private final BooleanSupplier showAllCondition;
  public TableBlockItem(Block block, TagKey<Item> textureTag, Properties builder, BooleanSupplier showAllCondition, @Nullable CreativeModeTab tab) {
    super(block, textureTag, builder, tab);
    this.showAllCondition = showAllCondition;
  }

  @Override
  public void fillItemCategory(FabricItemGroupEntries items) {
    if (showAllCondition.getAsBoolean()) {
      addTagVariants(this.getBlock(), this.textureTag, items, true);
    } else {
      items.accept(new ItemStack(this));
    }
  }
}
