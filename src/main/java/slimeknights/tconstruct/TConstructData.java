package slimeknights.tconstruct;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerTools;

public class TConstructData implements DataGeneratorEntrypoint {

  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    ExistingFileHelper helper = null;
    TinkerSmeltery.gatherData(fabricDataGenerator);
    TinkerModifiers.gatherData(fabricDataGenerator);

    TinkerTools.gatherData(fabricDataGenerator, helper);
  }
}
