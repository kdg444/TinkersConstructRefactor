package slimeknights.tconstruct.plugin.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Recipe;
import slimeknights.tconstruct.plugin.TinkersDisplay;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTinkersCategory<R extends Recipe<?>> implements DisplayCategory<TinkersDisplay<R>> {

  @Override
  public Renderer getIcon() {
    return null;
  }

  @Override
  public Component getTitle() {
    return null;
  }

  public void addWidgets(TinkersDisplay<R> display, List<Widget> ingredients, Point origin) {

  }

  public void addWidgets(TinkersDisplay<R> display, List<Widget> ingredients, Point origin, Rectangle bounds) {

  }

  @Override
  public List<Widget> setupDisplay(TinkersDisplay<R> display, Rectangle bounds) {
    List<Widget> widgets = new ArrayList<>();
    widgets.add(Widgets.createRecipeBase(bounds));
    widgets.add(Widgets.createDrawableWidget((helper, poseStack, mouseX, mouseY, partialTick) -> {
      poseStack.pushPose();
      poseStack.translate(bounds.getX(), bounds.getY() + 4, 0);
      draw(display.getRecipe(), poseStack, mouseX, mouseY);
      poseStack.popPose();
    }));
    addWidgets(display, widgets, new Point(bounds.getX(), bounds.getY() + 4));
    addWidgets(display, widgets, new Point(bounds.getX(), bounds.getY() + 4), bounds);
    return widgets;
  }

  public void draw(R recipe, PoseStack matrixStack, double mouseX, double mouseY) {};
}
