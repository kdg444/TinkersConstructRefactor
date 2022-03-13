package slimeknights.tconstruct.tables.client;

import io.github.fabricators_of_create.porting_lib.event.client.TextureStitchCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import slimeknights.mantle.data.ResourceValidator;

import java.util.function.Consumer;

/**
 * Stitches all GUI part textures into the texture sheet
 */
public class PatternGuiTextureLoader extends ResourceValidator {
  /** Initializes the loader */
  public static void init() {
    PatternGuiTextureLoader loader = new PatternGuiTextureLoader();
    TextureStitchCallback.PRE.register(loader::onTextureStitch);
  }

  private PatternGuiTextureLoader() {
    super("textures/gui/tinker_pattern", "textures", ".png");
  }

  /** Called during texture stitch to add the textures in */
  private void onTextureStitch(TextureAtlas atlas, Consumer<ResourceLocation> spriteAdder) {
    if (InventoryMenu.BLOCK_ATLAS.equals(atlas.location())) {
      // manually call reload to ensure it runs at the proper time
      this.onReloadSafe(Minecraft.getInstance().getResourceManager());
      this.resources.forEach(spriteAdder);
      this.clear();
    }
  }
}
