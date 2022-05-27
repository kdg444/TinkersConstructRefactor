package slimeknights.tconstruct.library.materials.json;

import lombok.Data;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import slimeknights.tconstruct.library.json.JsonRedirect;

import javax.annotation.Nullable;

@Data
public class MaterialJson {
  @Nullable
  private final ConditionJsonProvider condition;
  @Nullable
  private final Boolean craftable;
  @Nullable
  private final Integer tier;
  @Nullable
  private final Integer sortOrder;
  @Nullable
  private final Boolean hidden;
  @Nullable
  private final JsonRedirect[] redirect;
}
