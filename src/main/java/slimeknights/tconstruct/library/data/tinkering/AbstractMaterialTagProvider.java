package slimeknights.tconstruct.library.data.tinkering;

import net.minecraft.data.DataGenerator;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import slimeknights.tconstruct.library.data.AbstractTagProvider;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialManager;

/** Tag provider for materials */
public abstract class AbstractMaterialTagProvider extends AbstractTagProvider<IMaterial> {
  protected AbstractMaterialTagProvider(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
    super(generator, modId, MaterialManager.TAG_FOLDER, IMaterial::getIdentifier, id -> true, existingFileHelper);
  }
}
