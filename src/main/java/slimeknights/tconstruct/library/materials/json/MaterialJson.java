package slimeknights.tconstruct.library.materials.json;

import lombok.Data;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

@Data
public class MaterialJson {
//  @Nullable
//  private final ConditionJsonProvider condition;
  @Nullable
  private final Boolean craftable;
  @Nullable
  private final Integer tier;
  @Nullable
  private final Integer sortOrder;
  @Nullable
  private final Boolean hidden;
  @Nullable
  private final Redirect[] redirect;

  @Data
  public static class Redirect {
    private final ResourceLocation id;
    @Nullable
    private final ConditionJsonProvider condition;
  }
}
