package slimeknights.tconstruct.tables.block.entity.inventory;

import slimeknights.mantle.lib.transfer.item.IItemHandlerModifiable;
import slimeknights.mantle.block.entity.MantleBlockEntity;
import slimeknights.mantle.lib.util.NBTSerializable;

/** Interface for tinker chest TEs */
public interface IChestItemHandler extends IItemHandlerModifiable, NBTSerializable, IScalingContainer {
  /** Sets the parent of this block */
  void setParent(MantleBlockEntity parent);
}
