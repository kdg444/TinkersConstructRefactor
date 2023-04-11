package slimeknights.tconstruct.library.client.data;

import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/** Data generator to create png image files */
@RequiredArgsConstructor
@Log4j2
public abstract class GenericTextureGenerator implements DataProvider {
  private final PackOutput output;
  private final String folder;

  /** Saves the given image to the given location */
  @SuppressWarnings("UnstableApiUsage")
  protected CompletableFuture<?> saveImage(CachedOutput cache, ResourceLocation location, NativeImage image) {
    return CompletableFuture.runAsync(() -> {
      try {
        Path path = this.output.getOutputFolder().resolve(
          Paths.get(PackType.CLIENT_RESOURCES.getDirectory(),
            location.getNamespace(), folder, location.getPath() + ".png"));
        String hash = Hashing.sha1().hashBytes(image.asByteArray()).toString();
        if (/*!Objects.equals(cache.getHash(path), hash) || */!Files.exists(path)) {
          Files.createDirectories(path.getParent());
          image.writeToFile(path);
        }
//      cache.writeIfNeeded(path, hash);
      } catch (IOException e) {
        log.error("Couldn't create data for {}", location, e);
      }
    });
  }
}
