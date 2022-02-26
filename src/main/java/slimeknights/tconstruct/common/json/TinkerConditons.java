package slimeknights.tconstruct.common.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import slimeknights.mantle.util.JsonHelper;
import slimeknights.tconstruct.library.json.TagDifferencePresentCondition;
import slimeknights.tconstruct.library.json.TagIntersectionPresentCondition;

import java.util.Locale;
import java.util.function.BooleanSupplier;

import static slimeknights.tconstruct.common.json.ConfigEnabledCondition.PROPS;

public class TinkerConditons {
  public static ConfigEnabledCondition configEnabled(String configName, BooleanSupplier supplier) {
    return new ConfigEnabledCondition(configName, supplier);
  }

  public static boolean isConfigEnabledPredicate(JsonObject json) {
    String prop = GsonHelper.getAsString(json, "prop");
    ConfigEnabledCondition config = PROPS.get(prop.toLowerCase(Locale.ROOT));
    if (config == null) {
      throw new JsonSyntaxException("Invalid property name '" + prop + "'");
    }
    return config.test(null);
  }

  public static TagDifferencePresentCondition tagDifferencePresent(ResourceLocation base, ResourceLocation... names) {
    return new TagDifferencePresentCondition(base, names);
  }

  public static boolean tagDifferencePresentPredicate(JsonObject json) {
    return new TagDifferencePresentCondition(
      JsonHelper.getResourceLocation(json, "base"),
      JsonHelper.parseList(json, "subtracted", JsonHelper::convertToResourceLocation)).test();
  }

  public static TagIntersectionPresentCondition tagIntersectionPresent(ResourceLocation... names) {
    return new TagIntersectionPresentCondition(names);
  }

  public static boolean tagIntersectionPresentPredicate(JsonObject json) {
    return new TagIntersectionPresentCondition(JsonHelper.parseList(json, "tags", JsonHelper::convertToResourceLocation)).test();
  }
}
