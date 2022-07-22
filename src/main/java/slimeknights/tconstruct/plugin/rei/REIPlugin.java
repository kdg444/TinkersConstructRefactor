package slimeknights.tconstruct.plugin.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeManager;
import slimeknights.mantle.recipe.helper.RecipeHelper;
import slimeknights.tconstruct.library.recipe.TinkerRecipeTypes;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipe;
import slimeknights.tconstruct.library.recipe.casting.IDisplayableCastingRecipe;
import slimeknights.tconstruct.library.recipe.fuel.MeltingFuel;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;
import slimeknights.tconstruct.plugin.jei.melting.MeltingFuelHandler;
import slimeknights.tconstruct.plugin.rei.casting.CastingBasinCategory;
import slimeknights.tconstruct.plugin.rei.casting.CastingDisplay;
import slimeknights.tconstruct.plugin.rei.casting.CastingTableCategory;
import slimeknights.tconstruct.plugin.rei.melting.FoundryCategory;
import slimeknights.tconstruct.plugin.rei.melting.MeltingCategory;
import slimeknights.tconstruct.plugin.rei.melting.MeltingDisplay;

import java.util.List;

public class REIPlugin implements REIClientPlugin {

  @Override
  public void registerCategories(CategoryRegistry registry) {
    // casting
    registry.add(new CastingBasinCategory());
    registry.add(new CastingTableCategory());
    // melting and casting
    registry.add(new MeltingCategory());
    registry.add(new AlloyRecipeCategory());
//    registry.add(new EntityMeltingRecipeCategory());
    registry.add(new FoundryCategory());
  }

  @Override
  public void registerEntries(EntryRegistry registry) {
    registry.addEntries();
  }

  @Override
  public void registerDisplays(DisplayRegistry registry) {
    assert Minecraft.getInstance().level != null;
    RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
    // casting
    List<IDisplayableCastingRecipe> castingBasinRecipes = RecipeHelper.getJEIRecipes(manager, TinkerRecipeTypes.CASTING_BASIN.get(), IDisplayableCastingRecipe.class);
    castingBasinRecipes.forEach(castingBasinRecipe -> registry.add(new CastingDisplay(TConstructREIConstants.CASTING_BASIN, castingBasinRecipe)));
    List<IDisplayableCastingRecipe> castingTableRecipes = RecipeHelper.getJEIRecipes(manager, TinkerRecipeTypes.CASTING_TABLE.get(), IDisplayableCastingRecipe.class);
    castingTableRecipes.forEach(castingTableRecipe -> registry.add(new CastingDisplay(TConstructREIConstants.CASTING_TABLE, castingTableRecipe)));
    // melting
    List<MeltingRecipe> meltingRecipes = RecipeHelper.getJEIRecipes(manager, TinkerRecipeTypes.MELTING.get(), MeltingRecipe.class);
    meltingRecipes.forEach(meltingRecipe -> registry.add(new MeltingDisplay(meltingRecipe, TConstructREIConstants.MELTING), meltingRecipes));
    meltingRecipes.forEach(meltingRecipe -> registry.add(new MeltingDisplay(meltingRecipe, TConstructREIConstants.FOUNDRY), meltingRecipes));
    MeltingFuelHandler.setMeltngFuels(RecipeHelper.getRecipes(manager, TinkerRecipeTypes.FUEL.get(), MeltingFuel.class));

    // alloying
    List<AlloyRecipe> alloyRecipes = RecipeHelper.getJEIRecipes(manager, TinkerRecipeTypes.ALLOYING.get(), AlloyRecipe.class);
    alloyRecipes.forEach(alloyRecipe -> registry.add(new AlloyDisplay(alloyRecipe)));
  }
}
