//package slimeknights.tconstruct.plugin.rei.casting;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import me.shedaniel.math.Point;
//import me.shedaniel.math.Rectangle;
//import me.shedaniel.rei.api.client.gui.widgets.Widget;
//import me.shedaniel.rei.api.common.util.EntryIngredients;
//import me.shedaniel.rei.api.common.util.EntryStacks;
//import mezz.jei.api.recipe.RecipeIngredientRole;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.Font;
//import net.minecraft.client.resources.language.I18n;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import slimeknights.tconstruct.TConstruct;
//import slimeknights.tconstruct.library.recipe.FluidValues;
//import slimeknights.tconstruct.library.recipe.casting.IDisplayableCastingRecipe;
//import slimeknights.tconstruct.plugin.jei.fabric.JEITypes;
//import slimeknights.tconstruct.plugin.rei.BasicCategory;
//
//import java.awt.*;
//import java.util.List;
//
//public abstract class AbstractCastingCategory implements BasicCategory<CastingDisplay> {
//  private static final String KEY_COOLING_TIME = TConstruct.makeTranslationKey("jei", "time");
//  private static final String KEY_CAST_KEPT = TConstruct.makeTranslationKey("jei", "casting.cast_kept");
//  private static final String KEY_CAST_CONSUMED = TConstruct.makeTranslationKey("jei", "casting.cast_consumed");
//  protected static final ResourceLocation BACKGROUND_LOC = TConstruct.getResource("textures/gui/jei/casting.png");
//
//  @Override
//  public void draw(CastingDisplay display, PoseStack matrixStack, double mouseX, double mouseY) {
//    IDisplayableCastingRecipe recipe = display.getRecipe();
//    cachedArrows.getUnchecked(Math.max(1, recipe.getCoolingTime())).draw(matrixStack, 58, 18);
//    block.draw(matrixStack, 38, 35);
//    if (recipe.hasCast()) {
//      (recipe.isConsumed() ? castConsumed : castKept).draw(matrixStack, 63, 39);
//    }
//
//    int coolingTime = recipe.getCoolingTime() / 20;
//    String coolingString = I18n.get(KEY_COOLING_TIME, coolingTime);
//    Font fontRenderer = Minecraft.getInstance().font;
//    int x = 72 - fontRenderer.width(coolingString) / 2;
//    fontRenderer.draw(matrixStack, coolingString, x, 2, Color.GRAY.getRGB());
//  }
//
//
//  @Override
//  public void addWidgets(CastingDisplay display, List<Widget> ingredients, Point origin, Rectangle bounds) {
//    IDisplayableCastingRecipe recipe = display.getRecipe();
//    // items
//    List<ItemStack> casts = recipe.getCastItems();
//    if (!casts.isEmpty()) {
//      slot(38, 19, origin).markInput().entries(EntryIngredients.ofItemStacks(casts));
//    }
//    slot(93, 18, origin).markOutput().entry(EntryStacks.of(recipe.getOutput()));
//
//    // fluids
//    // tank fluids
//    long capacity = FluidValues.METAL_BLOCK;
//    builder.addSlot(RecipeIngredientRole.INPUT, 3, 3)
//      .addTooltipCallback(this)
//      .setFluidRenderer(capacity, false, 32, 32)
//      .setOverlay(tankOverlay, 0, 0)
//      .addIngredients(JEITypes.FLUID_STACK, recipe.getFluids());
//    // pouring fluid
//    int h = 11;
//    if (!recipe.hasCast()) {
//      h += 16;
//    }
//    builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 43, 8)
//      .addTooltipCallback(this)
//      .setFluidRenderer(1L, false, 6, h)
//      .addIngredients(JEITypes.FLUID_STACK, recipe.getFluids());
//  }
//}
