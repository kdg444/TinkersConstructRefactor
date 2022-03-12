package slimeknights.tconstruct.library.recipe.modifiers.spilling.effects;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidStack;
import slimeknights.mantle.data.GenericLoaderRegistry.IGenericLoader;
import slimeknights.mantle.util.JsonHelper;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;

import java.util.Objects;

/** Spilling effect to apply a potion effect */
@RequiredArgsConstructor
public class EffectSpillingEffect implements ISpillingEffect {
  public static final Loader LOADER = new Loader();

  private final MobEffect effect;
  /** Potion time in seconds */
  private final int time;
  /** Potion level starting at 1 */
  private final int level;

  @Override
  public void applyEffects(FluidStack fluid, float scale, ToolAttackContext context) {
    LivingEntity target = context.getLivingTarget();
    if (target != null) {
      int time = (int)(this.time * 20 * scale);
      if (time > 0) {
        target.addEffect(new MobEffectInstance(effect, time, level - 1));
      }
    }
  }

  @Override
  public IGenericLoader<?> getLoader() {
    return LOADER;
  }

  private static class Loader implements IGenericLoader<EffectSpillingEffect> {
    @Override
    public EffectSpillingEffect deserialize(JsonObject json) {
      ResourceLocation id = JsonHelper.getResourceLocation(json, "name");
      if (!Registry.MOB_EFFECT.containsKey(id)) {
        throw new JsonSyntaxException("Unknown effect " + id);
      }
      MobEffect effect = Objects.requireNonNull(Registry.MOB_EFFECT.get(id));
      int time = GsonHelper.getAsInt(json, "time");
      int level = GsonHelper.getAsInt(json, "level", 1);
      return new EffectSpillingEffect(effect, time, level);
    }

    @Override
    public EffectSpillingEffect fromNetwork(FriendlyByteBuf buffer) {
      ResourceLocation id = buffer.readResourceLocation();
      MobEffect effect = Registry.MOB_EFFECT.get(id);
      int level = buffer.readVarInt();
      int time = buffer.readVarInt();
      return new EffectSpillingEffect(effect, time, level);
    }

    @Override
    public void serialize(EffectSpillingEffect effect, JsonObject json) {
      json.addProperty("name", Objects.requireNonNull(Registry.MOB_EFFECT.getKey(effect.effect)).toString());
      json.addProperty("time", effect.time);
      json.addProperty("level", effect.level);
    }

    @Override
    public void toNetwork(EffectSpillingEffect effect, FriendlyByteBuf buffer) {
      buffer.writeResourceLocation(Registry.MOB_EFFECT.getKey(effect.effect));
      buffer.writeVarInt(effect.time);
      buffer.writeVarInt(effect.level);
    }
  }
}
