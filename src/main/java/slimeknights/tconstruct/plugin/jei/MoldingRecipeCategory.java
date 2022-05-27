//package slimeknights.tconstruct.plugin.jei;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import lombok.Getter;
//import me.shedaniel.math.Point;
//import me.shedaniel.math.Rectangle;
//import me.shedaniel.rei.api.client.gui.Renderer;
//import me.shedaniel.rei.api.client.gui.widgets.Slot;
//import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
//import me.shedaniel.rei.api.client.gui.widgets.Widget;
//import me.shedaniel.rei.api.client.gui.widgets.WidgetWithBounds;
//import me.shedaniel.rei.api.client.gui.widgets.Widgets;
//import me.shedaniel.rei.api.common.category.CategoryIdentifier;
//import me.shedaniel.rei.api.common.util.EntryStacks;
//import net.minecraft.client.gui.components.events.GuiEventListener;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TranslatableComponent;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.RecipeType;
//import org.jetbrains.annotations.Nullable;
//import slimeknights.tconstruct.TConstruct;
//import slimeknights.tconstruct.library.client.GuiUtil;
//import slimeknights.tconstruct.library.recipe.TinkerRecipeTypes;
//import slimeknights.tconstruct.library.recipe.molding.MoldingRecipe;
//import slimeknights.tconstruct.plugin.TinkersDisplay;
//import slimeknights.tconstruct.smeltery.TinkerSmeltery;
//
//import java.util.Collections;
//import java.util.List;
//
///** Recipe category for molding casts */
//public class MoldingRecipeCategory extends AbstractTinkersCategory<MoldingRecipe> {
//  private static final ResourceLocation BACKGROUND_LOC = TConstruct.getResource("textures/gui/jei/casting.png");
//  private static final Component TITLE = TConstruct.makeTranslation("jei", "molding.title");
//  private static final Component TOOLTIP_PATTERN_CONSUMED = new TranslatableComponent(TConstruct.makeTranslationKey("jei", "molding.pattern_consumed"));
//
//  @Getter
//  private final WidgetInfo background;
//  @Getter
//  private final Renderer icon;
//  private final WidgetInfo table, basin, downArrow, upArrow;
//  public MoldingRecipeCategory() {
//    this.background =  new WidgetInfo(BACKGROUND_LOC, 0, 55, 80, 67);
//    this.icon =  EntryStacks.of(new ItemStack(TinkerSmeltery.blankSandCast.get()));
//    this.table =  new WidgetInfo(BACKGROUND_LOC, 117, 0, 16, 16);
//    this.basin = new WidgetInfo(BACKGROUND_LOC, 117, 16, 16, 16);
//    this.downArrow =  new WidgetInfo(BACKGROUND_LOC, 70, 55, 6, 6);
//    this.upArrow =  new WidgetInfo(BACKGROUND_LOC, 76, 55, 6, 6);
//  }
//
//  @SuppressWarnings("removal")
//  @Override
//  public CategoryIdentifier<TinkersDisplay<MoldingRecipe>> getCategoryIdentifier() {
//    return TConstructJEIConstants.MOLDING.getUid();
//  }
//
//  @SuppressWarnings("removal")
//  @Override
//  public int getDisplayHeight() {
//    return background.height();
//  }
//
//  @Override
//  public int getDisplayWidth(TinkersDisplay<MoldingRecipe> display) {
//    return background.width();
//  }
//
//  record WidgetInfo(ResourceLocation location, int u, int v, int width, int height) {}
//
//  @Override
//  public Component getTitle() {
//    return TITLE;
//  }
//
//  @Override
//  public void addWidgets(TinkersDisplay<MoldingRecipe> display, List<Widget> ingredients, Point origin, Rectangle bounds) {
//    // main item
//    ingredients.add(basicSlot(3, 24, origin).markInput().entries(display.getInputEntries().get(0)));
//    ingredients.add(basicSlot(51, 24, origin).markOutput().entries(display.getOutputEntries().get(0)));
//
//    // if we have a mold, we are pressing into the table, so draw pressed item on input and output
//    if (!display.getRecipe().getPattern().isEmpty()) {
//      ingredients.add(basicSlot(3, 1, origin).markInput().entries(display.getInputEntries().get(1)));
//      if (!display.getRecipe().isPatternConsumed()) {
//        ingredients.add(basicSlot(51, 8, origin).markInput().entries(display.getInputEntries().get(1)));
//      }
//    } else {
////      guiItemStacks.set(ingredients);
//    }
//
//    // draw the main block
//    WidgetInfo block = display.getRecipe().getType() == TinkerRecipeTypes.MOLDING_BASIN.get() ? basin : table;
//    ingredients.add(Widgets.createTexturedWidget(block.location(), origin.x + 3, origin.y + 40, block.u(), block.v(), block.width(), block.height()));
//
//    // if no mold, we "pickup" the item, so draw no table
//    if (!display.getRecipe().getPattern().isEmpty()) {
//      ingredients.add(Widgets.createTexturedWidget(block.location(), origin.x + 51, origin.y + 40, block.u(), block.v(), block.width(), block.height()));
//      ingredients.add(Widgets.createTexturedWidget(downArrow.location(), origin.x + 8, origin.y + 17, downArrow.u(), downArrow.v(), downArrow.width(), downArrow.height()));
//    } else {
//      ingredients.add(Widgets.createTexturedWidget(upArrow.location(), origin.x + 8, origin.y + 17, upArrow.u(), upArrow.v(), upArrow.width(), upArrow.height()));
//    }
//
//    ingredients.add(new WidgetWithBounds() {
//      @Override
//      public Rectangle getBounds() {
//        return bounds;
//      }
//
//      @Override
//      public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
//        Point mouse = new Point(mouseX, mouseY);
//        if (containsMouse(mouse)) {
//          for (Slot slot : Widgets.<Slot>walk(ingredients, listener -> listener instanceof Slot)) {
//            if (slot.containsMouse(mouse) && slot.isHighlightEnabled()) {
//              if (slot.getCurrentTooltip(mouse) != null) {
//                return;
//              }
//            }
//          }
//
//          Tooltip tooltip = getTooltip(mouse);
//
//          if (tooltip != null) {
//            tooltip.queue();
//          }
//        }
//      }
//
//      @Override
//      public List<? extends GuiEventListener> children() {
//        return Collections.emptyList();
//      }
//
//      @Override
//      @Nullable
//      public Tooltip getTooltip(Point mouse) {
//        List<Component> strings = getTooltipStrings(display.getRecipe(), mouse.x - origin.x, mouse.y - origin.y);
//        if (strings.isEmpty()) {
//          return null;
//        }
//        return Tooltip.create(mouse, strings);
//      }
//    });
//  }
//
//  public List<Component> getTooltipStrings(MoldingRecipe recipe, double mouseX, double mouseY) {
//    if (recipe.isPatternConsumed() && !recipe.getPattern().isEmpty() && GuiUtil.isHovered((int)mouseX, (int)mouseY, 50, 7, 18, 18)) {
//      return Collections.singletonList(TOOLTIP_PATTERN_CONSUMED);
//    }
//    return Collections.emptyList();
//  }
//
////  @Override
////  public void setRecipe(IRecipeLayoutBuilder builder, MoldingRecipe recipe, IFocusGroup focuses) {
////    // basic input output
////    builder.addSlot(RecipeIngredientRole.INPUT, 3, 24).addIngredients(recipe.getMaterial());
////    builder.addSlot(RecipeIngredientRole.OUTPUT, 51, 24).addItemStack(recipe.getResultItem());
////
////    // if we have a mold, we are pressing into the table, so draw pressed item on input and output
////    Ingredient pattern = recipe.getPattern();
////    if (!pattern.isEmpty()) {
////      builder.addSlot(RecipeIngredientRole.INPUT, 3, 1).addIngredients(pattern);
////      if (!recipe.isPatternConsumed()) {
////        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 51, 8).addIngredients(pattern);
////      }
////    }
////  }
//}
