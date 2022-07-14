package slimeknights.tconstruct.library.materials.json;

import lombok.Data;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.json.JsonRedirect;

import javax.annotation.Nullable;

@Data
public class MaterialJson {
  @Nullable
  private final ResourceLocation condition;
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
