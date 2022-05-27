package slimeknights.tconstruct.library.json;

import com.google.gson.JsonObject;
import lombok.Data;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.resources.ResourceLocation;
import slimeknights.mantle.util.JsonHelper;

import javax.annotation.Nullable;

/** Represents a redirect in a material or modifier JSON */
@SuppressWarnings("ClassCanBeRecord") // GSON does not support records
@Data
public class JsonRedirect {
  private final ResourceLocation id;
  @Nullable
  private final ConditionJsonProvider condition;

  /** Serializes this to JSON */
  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    json.addProperty("id", id.toString());
    if (condition != null) {
      json.addProperty(ResourceConditions.CONDITION_ID_KEY, condition.getConditionId().toString());
      condition.writeParameters(json);
    }
    return json;
  }

  /** Deserializes this to JSON */
  public static JsonRedirect fromJson(JsonObject json) {
    ResourceLocation id = JsonHelper.getResourceLocation(json, "id");
    ConditionJsonProvider condition = null;
    if (json.has("condition")) {
//      condition = CraftingHelper.getCondition(json); TODO: PORT
    }
    return new JsonRedirect(id, condition);
  }
}
