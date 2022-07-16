package slimeknights.tconstruct.plugin.rei;

import com.mojang.blaze3d.vertex.PoseStack;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.display.Display;

import java.util.ArrayList;
import java.util.List;

public interface BasicCategory<T extends Display> extends DisplayCategory<T> {

  void draw(T display, PoseStack matrixStack, double mouseX, double mouseY);

  void addWidgets(T display, List<Widget> ingredients, Point origin, Rectangle bounds);

  @Override
  default List<Widget> setupDisplay(T display, Rectangle bounds) {
    List<Widget> widgets = new ArrayList<>();
    widgets.add(Widgets.createRecipeBase(bounds));
    widgets.add(Widgets.createDrawableWidget((helper, poseStack, mouseX, mouseY, partialTick) -> {
      poseStack.pushPose();
      poseStack.translate(bounds.getX(), bounds.getY() + 4, 0);
      draw(display, poseStack, mouseX, mouseY);
      poseStack.popPose();
    }));
    addWidgets(display, widgets, new Point(bounds.getX(), bounds.getY() + 4), bounds);
    return widgets;
  }

  default Slot slot(int x, int y, Point origin) {
    return Widgets.createSlot(new Point(origin.x + x, origin.y + y));
  }
}
