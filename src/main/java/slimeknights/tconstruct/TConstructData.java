package slimeknights.tconstruct;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerTools;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

public class TConstructData implements DataGeneratorEntrypoint {

  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    var existingData = System.getProperty("slimeknights.tconstruct.existingData").split(";");
    ExistingFileHelper helper = new ExistingFileHelper(Arrays.stream(existingData).map(Paths::get).toList(), Collections.emptySet(),
      true, null, null);
    TConstruct.onInitializeDataGenerator(fabricDataGenerator, helper);
    TinkerSmeltery.gatherData(fabricDataGenerator);
    TinkerModifiers.gatherData(fabricDataGenerator);

    TinkerTools.gatherData(fabricDataGenerator, helper);
    TinkerFluids.gatherData(fabricDataGenerator);
  }
}
