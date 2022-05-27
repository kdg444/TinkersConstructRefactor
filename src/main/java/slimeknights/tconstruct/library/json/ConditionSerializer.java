//package slimeknights.tconstruct.library.json;
//
//import com.google.gson.JsonDeserializationContext;
//import com.google.gson.JsonDeserializer;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParseException;
//import com.google.gson.JsonSerializationContext;
//import com.google.gson.JsonSerializer;
//import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
//import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
//import net.minecraft.util.GsonHelper;
//import io.github.fabricators_of_create.porting_lib.crafting.CraftingHelper;
//
//import java.lang.reflect.Type;
//
///** Serializer for a forge condition */
//public class ConditionSerializer implements JsonDeserializer<ConditionJsonProvider>, JsonSerializer<ConditionJsonProvider> {
//  public static final ConditionSerializer INSTANCE = new ConditionSerializer();
//
//  private ConditionSerializer() {}
//
//  @Override
//  public ConditionJsonProvider deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
//    return CraftingHelper.getCondition(GsonHelper.convertToJsonObject(json, ResourceConditions.CONDITION_ID_KEY));
//  }
//
//  @Override
//  public JsonElement serialize(ConditionJsonProvider condition, Type type, JsonSerializationContext context) {
//    return CraftingHelper.serialize(condition);
//  }
//}
