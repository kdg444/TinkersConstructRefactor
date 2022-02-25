package slimeknights.tconstruct.shared;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.item.crafting.RecipeManager;
import slimeknights.mantle.lib.event.RecipesUpdatedCallback;
import slimeknights.tconstruct.common.recipe.RecipeCacheInvalidator;
import slimeknights.tconstruct.fluids.FluidClientEvents;
import slimeknights.tconstruct.gadgets.GadgetClientEvents;
import slimeknights.tconstruct.library.client.book.TinkerBook;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToSpriteTransformer;
import slimeknights.tconstruct.library.client.data.spritetransformer.IColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.ISpriteTransformer;
import slimeknights.tconstruct.library.client.data.spritetransformer.RecolorSpriteTransformer;
import slimeknights.tconstruct.smeltery.SmelteryClientEvents;
import slimeknights.tconstruct.tables.client.PatternGuiTextureLoader;

import java.util.function.Consumer;

/**
 * This class should only be referenced on the client side
 */
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
  }
}
