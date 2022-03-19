package slimeknights.tconstruct.plugin.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidStack;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.fluid.FluidTooltipHandler;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;
import slimeknights.tconstruct.plugin.TinkersDisplay;
import slimeknights.tconstruct.plugin.jei.melting.MeltingFuelHandler;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.awt.Color;
import java.util.List;

/**
 * Alloy recipe category for JEI display
 */
public class AlloyRecipeCategory extends AbstractTinkersCategory<AlloyRecipe>/*, ITooltipCallback<FluidStack>*/ {
  private static final ResourceLocation BACKGROUND_LOC = TConstruct.getResource("textures/gui/jei/alloy.png");
  private static final Component TITLE = TConstruct.makeTranslation("jei", "alloy.title");
  private static final String KEY_TEMPERATURE = TConstruct.makeTranslationKey("jei", "temperature");

  /** Tooltip for fluid inputs */
  private static final IRecipeTooltipReplacement FLUID_TOOLTIP = (slot, list) ->
    slot.getDisplayedIngredient(VanillaTypes.FLUID).ifPresent(stack -> FluidTooltipHandler.appendMaterial(stack, list));

  /** Tooltip for fuel display */
  public static final IRecipeTooltipReplacement FUEL_TOOLTIP = (slot, tooltip) -> {
    //noinspection SimplifyOptionalCallChains  Not for int streams
    slot.getDisplayedIngredient(VanillaTypes.FLUID)
        .ifPresent(stack -> MeltingFuelHandler.getTemperature(stack.getFluid())
                                              .ifPresent(temperature -> tooltip.add(new TranslatableComponent(KEY_TEMPERATURE, temperature).withStyle(ChatFormatting.GRAY))));
  };

  @Getter
  private final IDrawable background;
  @Getter
  private final Renderer icon;
//  private final IDrawable arrow;
//  private final IDrawable tank;

  public AlloyRecipeCategory() {
//    this.background = helper.createDrawable(BACKGROUND_LOC, 0, 0, 172, 62);
    this.icon = EntryStacks.of(new ItemStack(TinkerSmeltery.smelteryController));
//    this.arrow = helper.drawableBuilder(BACKGROUND_LOC, 172, 0, 24, 17).buildAnimated(200, StartDirection.LEFT, false);
//    this.tank = helper.createDrawable(BACKGROUND_LOC, 172, 17, 16, 16);
  }

  @Override
  public CategoryIdentifier<TinkersDisplay<AlloyRecipe>> getCategoryIdentifier() {
    return TConstructJEIConstants.ALLOY;
  }

  @Override
  public Component getTitle() {
    return TITLE;
  }

  @Override
  public void draw(AlloyRecipe recipe, PoseStack matrices, double mouseX, double mouseY) {
    arrow.draw(matrices, 90, 21);
    // temperature info
    Font fontRenderer = Minecraft.getInstance().font;
    String tempString = I18n.get(KEY_TEMPERATURE, recipe.getTemperature());
    int x = 102 - (fontRenderer.width(tempString) / 2);
    fontRenderer.draw(matrices, tempString, x, 5, Color.GRAY.getRGB());
  }

  /**
   * Draws a variable number of fluids
   * @param builder      Builder
   * @param role         Role of the set of fluids in the recipe
   * @param x            X start
   * @param y            Y start
   * @param totalWidth   Total width
   * @param height       Tank height
   * @param fluids       List of fluids to draw
   * @param minAmount    Minimum tank size
   * @param tooltip      Tooltip callback
   * @return Max amount based on fluids
   */
  public static long drawVariableFluids(IRecipeLayoutBuilder builder, RecipeIngredientRole role, int x, int y, int totalWidth, int height, List<List<FluidStack>> fluids, int minAmount, IRecipeSlotTooltipCallback tooltip) {
    int count = fluids.size();
    long maxAmount = minAmount;
    if (count > 0) {
      // first, find maximum used amount in the recipe so relations are correct
      for(List<FluidStack> list : fluids) {
        for(FluidStack input : list) {
          if (input.getAmount() > maxAmount) {
            maxAmount = input.getAmount();
          }
        }
      }
      // next, draw all fluids but the last
      int w = totalWidth / count;
      int max = count - 1;
      for (int i = 0; i < max; i++) {
        int fluidX = x + i * w;
        builder.addSlot(role, fluidX, y)
               .addTooltipCallback(tooltip)
               .setFluidRenderer(maxAmount, false, w, height)
               .addIngredients(VanillaTypes.FLUID, fluids.get(i));
      }
      // for the last, the width is the full remaining width
      int fluidX = x + max * w;
      builder.addSlot(role, fluidX, y)
             .addTooltipCallback(tooltip)
             .setFluidRenderer(maxAmount, false, totalWidth - (w * max), height)
             .addIngredients(VanillaTypes.FLUID, fluids.get(max));
    }
    return maxAmount;
  }

  @Override
  public void setRecipe(IRecipeLayoutBuilder builder, AlloyRecipe recipe, IFocusGroup focuses) {
    // inputs
    int maxAmount = drawVariableFluids(builder, RecipeIngredientRole.INPUT, 19, 11, 48, 32, recipe.getDisplayInputs(), recipe.getOutput().getAmount(), FLUID_TOOLTIP);

    // output
    builder.addSlot(RecipeIngredientRole.OUTPUT, 137, 11)
           .addTooltipCallback(FLUID_TOOLTIP)
           .setFluidRenderer(maxAmount, false, 16, 32)
           .addIngredient(VanillaTypes.FLUID, recipe.getOutput());

    // fuel
    builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 94, 43)
           .addTooltipCallback(FUEL_TOOLTIP)
           .setFluidRenderer(1, false, 16, 16)
           .setOverlay(tank, 0, 0)
           .addIngredients(VanillaTypes.FLUID, MeltingFuelHandler.getUsableFuels(recipe.getTemperature()));
  }
}
