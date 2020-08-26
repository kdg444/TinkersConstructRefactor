package slimeknights.tconstruct.library.recipe.tinkerstation.building;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.materials.IMaterial;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationRecipe;
import slimeknights.tconstruct.library.tinkering.IMaterialItem;
import slimeknights.tconstruct.library.tools.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.tables.TinkerTables;
import slimeknights.tconstruct.tables.tileentity.table.tinkerstation.ITinkerStationInventory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This recipe is used for crafting a set of parts into a tool
 */
@AllArgsConstructor
public class ToolBuildingRecipe implements ITinkerStationRecipe {

  @Getter
  protected final ResourceLocation id;
  @Getter
  protected final String group;
  protected final ToolCore output;

  @Override
  public IRecipeSerializer<?> getSerializer() {
    return TinkerTables.toolBuildingRecipeSerializer.get();
  }

  @Override
  public boolean matches(ITinkerStationInventory inv, World worldIn) {
    return ToolBuildHandler.canToolBeBuilt(inv.getAllInputStacks(), this.output);
  }

  @Override
  public ItemStack getCraftingResult(ITinkerStationInventory inv) {
    List<IMaterial> materials = inv.getAllInputStacks().stream()
      .filter(stack -> !stack.isEmpty())
      .map(IMaterialItem::getMaterialFromStack)
      .collect(Collectors.toList());

    return ToolBuildHandler.buildItemFromMaterials(this.output, materials);
  }

  @Override
  public ItemStack getRecipeOutput() {
    return new ItemStack(this.output);
  }
}