package slimeknights.tconstruct.shared;

import io.github.fabricators_of_create.porting_lib.event.client.ModelsBakedCallback;
import io.github.fabricators_of_create.porting_lib.event.common.RecipesUpdatedCallback;
import io.github.fabricators_of_create.porting_lib.util.ArmorTextureItem;
import io.github.fabricators_of_create.porting_lib.util.FluidAttributes;
import me.alphamode.star.client.renderers.UpsideDownFluidRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import slimeknights.tconstruct.common.recipe.RecipeCacheInvalidator;
import slimeknights.tconstruct.fluids.FluidClientEvents;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.gadgets.GadgetClientEvents;
import slimeknights.tconstruct.library.client.book.TinkerBook;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToSpriteTransformer;
import slimeknights.tconstruct.library.client.data.spritetransformer.IColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.ISpriteTransformer;
import slimeknights.tconstruct.library.client.data.spritetransformer.RecolorSpriteTransformer;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.smeltery.SmelteryClientEvents;
import slimeknights.tconstruct.tables.TableClientEvents;
import slimeknights.tconstruct.tables.client.PatternGuiTextureLoader;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.ToolClientEvents;
import slimeknights.tconstruct.tools.client.ModifierClientEvents;
import slimeknights.tconstruct.tools.client.PlateArmorModel;
import slimeknights.tconstruct.tools.client.SlimelytraArmorModel;
import slimeknights.tconstruct.tools.client.SlimeskullArmorModel;
import slimeknights.tconstruct.tools.client.ToolRenderEvents;
import slimeknights.tconstruct.tools.client.TravelersGearModel;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import slimeknights.tconstruct.world.WorldClientEvents;

import java.util.function.Consumer;

import static net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer.ARMOR_LOCATION_CACHE;

/**
 * This class should only be referenced on the client side
 */
@SuppressWarnings("removal")
public class TinkerClient implements ClientModInitializer {
  /**
   * Called by TConstruct to handle any client side logic that needs to run during the constructor
   */
  @Override
  public void onInitializeClient() {
    TinkerBook.initBook();
    // needs to register listeners early enough for minecraft to load
    PatternGuiTextureLoader.init();

    // add the recipe cache invalidator to the client
    Consumer<RecipeManager> recipesUpdated = event -> RecipeCacheInvalidator.reload(true);
    RecipesUpdatedCallback.EVENT.register((recipeManager) -> recipesUpdated.accept(recipeManager));

    // register datagen serializers
    ISpriteTransformer.SERIALIZER.registerDeserializer(RecolorSpriteTransformer.NAME, RecolorSpriteTransformer.DESERIALIZER);
    GreyToSpriteTransformer.init();
    IColorMapping.SERIALIZER.registerDeserializer(GreyToColorMapping.NAME, GreyToColorMapping.DESERIALIZER);
    FluidClientEvents.clientSetup();
    GadgetClientEvents.init();
    CommonsClientEvents.init();
    SmelteryClientEvents.init();
    TableClientEvents.init();
    ModifierClientEvents.init();
    ToolRenderEvents.init();
    ToolClientEvents.clientSetupEvent();
    WorldClientEvents.clientSetup();

    // Armor Models
    for(ModifiableArmorItem armorItem : TinkerTools.plateArmor.values()) {
      ArmorRenderer renderer = ((matrices, vertexConsumers, itemStack, entity, armorSlot, light, _default) -> {
//        if(plateModel == null)
        Model plateModel = PlateArmorModel.getModel(itemStack, armorSlot, _default);
        ArmorRenderer.renderPart(matrices, vertexConsumers, light, itemStack, plateModel, getArmorResource(entity, itemStack, armorSlot, null));
      });
      ArmorRenderer.register(renderer, armorItem);
    }
    for(ModifiableArmorItem armorItem : TinkerTools.travelersGear.values()) {
      ArmorRenderer renderer = ((matrices, vertexConsumers, itemStack, entity, armorSlot, light, _default) -> {
//        if(travelersModel == null)
        Model travelersModel = TravelersGearModel.getModel(itemStack, armorSlot, _default);
        ArmorRenderer.renderPart(matrices, vertexConsumers, light, itemStack, travelersModel, getArmorResource(entity, itemStack, armorSlot, null));
      });
      ArmorRenderer.register(renderer, armorItem);
    }
    ArmorRenderer elytraRenderer = ((matrices, vertexConsumers, itemStack, entityLiving, armorSlot, light, _default) -> {
//      if(elytraModel == null)
      Model elytraModel = SlimelytraArmorModel.getModel(entityLiving, itemStack, _default);
      ArmorRenderer.renderPart(matrices, vertexConsumers, light, itemStack, elytraModel, getArmorResource(entityLiving, itemStack, armorSlot, null));
    });
    ArmorRenderer.register(elytraRenderer, TinkerTools.slimesuit.get(ArmorSlotType.CHESTPLATE));
    ArmorRenderer skullRenderer = ((matrices, vertexConsumers, itemStack, entityLiving, armorSlot, light, _default) -> {
//      if(slimeModel == null)
      Model slimeModel = SlimeskullArmorModel.getModel(itemStack, _default);
      ArmorRenderer.renderPart(matrices, vertexConsumers, light, itemStack, slimeModel, getArmorResource(entityLiving, itemStack, armorSlot, null));
    });
    ArmorRenderer.register(skullRenderer, TinkerTools.slimesuit.get(ArmorSlotType.HELMET));

    ModelsBakedCallback.EVENT.register((manager, models, loader) -> {
      FluidAttributes attributes = TinkerFluids.ichor.getStill().getAttributes();
      FluidRenderHandlerRegistry.INSTANCE.register(TinkerFluids.ichor.getStill(), TinkerFluids.ichor.getFlowing(), new UpsideDownFluidRenderer(attributes::getStillTexture, attributes::getFlowingTexture, attributes::getOverlayTexture, attributes.getColor()));
    });
  }

//  private Model plateModel, travelersModel, elytraModel, slimeModel;

  public ResourceLocation getArmorResource(net.minecraft.world.entity.Entity entity, ItemStack stack, EquipmentSlot slot, @javax.annotation.Nullable String type) {
    ArmorItem item = (ArmorItem)stack.getItem();
    String texture = item.getMaterial().getName();
    String domain = "minecraft";
    int idx = texture.indexOf(':');
    if (idx != -1) {
      domain = texture.substring(0, idx);
      texture = texture.substring(idx + 1);
    }

    String s1;
    if(item instanceof ArmorTextureItem armorTextureItem)
      s1 = armorTextureItem.getArmorTexture(stack, entity, slot, type);
    else
      s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (slot == EquipmentSlot.LEGS ? 2 : 1), type == null ? "" : String.format("_%s", type));
    ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);

    if (resourcelocation == null) {
      resourcelocation = new ResourceLocation(s1);
      ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
    }

    return resourcelocation;
  }
}
