package slimeknights.tconstruct.library.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;

/** Serializer for a forge condition */
public class ConditionSerializer implements JsonDeserializer<JsonCondition> {
  public static final ConditionSerializer INSTANCE = new ConditionSerializer();

  private ConditionSerializer() {}

  @Override
  public JsonCondition deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
    JsonObject jsonObject = GsonHelper.convertToJsonObject(json, ResourceConditions.CONDITION_ID_KEY);
    if (jsonObject.has(ResourceConditions.CONDITION_ID_KEY)) {
      JsonObject jsonCondition = jsonObject.getAsJsonObject(ResourceConditions.CONDITION_ID_KEY);
      ResourceLocation id = new ResourceLocation(jsonCondition.get("type").getAsString());
      return new JsonCondition(id);
    }
    return null;
  }
}
