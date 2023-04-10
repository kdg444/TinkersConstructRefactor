package slimeknights.tconstruct.common.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataGenerator;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierTagProvider;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;

public class ModifierTagProvider extends AbstractModifierTagProvider {
  public ModifierTagProvider(FabricDataOutput output, ExistingFileHelper existingFileHelper) {
    super(output, TConstruct.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags() {
    tag(TinkerTags.Modifiers.GEMS).addOptional(ModifierIds.diamond, ModifierIds.emerald);
    tag(TinkerTags.Modifiers.INVISIBLE_INK_BLACKLIST)
      .add(TinkerModifiers.embellishment.getId(), TinkerModifiers.dyed.getId(), TinkerModifiers.creativeSlot.getId(), TinkerModifiers.statOverride.getId())
      .addOptional(ModifierIds.shiny, TinkerModifiers.golden.getId());
  }

  @Override
  public String getName() {
    return "Tinkers' Construct Modifier Tag Provider";
  }
}
