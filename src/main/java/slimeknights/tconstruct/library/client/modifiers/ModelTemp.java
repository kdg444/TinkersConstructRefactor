package slimeknights.tconstruct.library.client.modifiers;

import com.google.common.collect.ImmutableList;
import com.mojang.math.Transformation;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.util.ItemLayerPixels;

public class ModelTemp {
  public static ImmutableList<BakedQuad> getQuadsForSprite(int color, int tint, TextureAtlasSprite sprite, Transformation transform, int luminosity, @Nullable ItemLayerPixels pixels) {
    return ImmutableList.of();
  }
}
