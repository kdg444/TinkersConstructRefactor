package slimeknights.tconstruct.tables.block.entity.inventory;

import io.github.fabricators_of_create.porting_lib.transfer.item.IItemHandlerModifiable;
import slimeknights.mantle.block.entity.MantleBlockEntity;
import io.github.fabricators_of_create.porting_lib.util.NBTSerializable;

/** Interface for tinker chest TEs */
public interface IChestItemHandler extends IItemHandlerModifiable, NBTSerializable, IScalingContainer {
  /** Sets the parent of this block */
  void setParent(MantleBlockEntity parent);
}
