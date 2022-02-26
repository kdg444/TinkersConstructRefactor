package slimeknights.tconstruct.library.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.item.Item;
import slimeknights.mantle.util.JsonHelper;
import slimeknights.tconstruct.TConstruct;

import java.util.Arrays;
import java.util.List;

/** Condition requiring that items exist in the intersection of all required item tags */
@RequiredArgsConstructor
public class TagDifferencePresentCondition implements ConditionJsonProvider {
  public static final ResourceLocation NAME = TConstruct.getResource("tag_difference_present");

  private final ResourceLocation base;
  private final List<ResourceLocation> subtracted;
  public TagDifferencePresentCondition(ResourceLocation base, ResourceLocation... names) {
    this(base, Arrays.asList(names));
  }

  @Override
  public ResourceLocation getConditionId() {
    return NAME;
  }

  public boolean test() {
    TagCollection<Item> itemTags = SerializationTags.getInstance().getOrEmpty(Registry.ITEM_REGISTRY);

    // get the base tag
    Tag<Item> base = itemTags.getTag(this.base);
    if (base == null || base.getValues().isEmpty()) {
      return false;
    }

    // get subtracted tags
    List<Tag<Item>> subtracted = this.subtracted.stream().map(itemTags::getTag).filter(tag -> tag == null || tag.getValues().isEmpty()).toList();
    // none of the subtracted tags had anything? done
    if (subtracted.isEmpty()) {
      return true;
    }
    // all tags have something, so find the first item that is in all tags
    int count = subtracted.size();
    itemLoop:
    for (Item item : base.getValues()) {
      // find the first item contained in no subtracted tags
      for (Tag<Item> tag : subtracted) {
        if (tag.contains(item)) {
          continue itemLoop;
        }
      }
      // no subtracted contains the item? success
      return true;
    }
    // no item not in any subtracted
    return false;
  }

  @Override
  public void writeParameters(JsonObject json) {
    json.addProperty("base", this.base.toString());
    JsonArray names = new JsonArray();
    for (ResourceLocation name : this.subtracted) {
      names.add(name.toString());
    }
    json.add("subtracted", names);
  }
}
