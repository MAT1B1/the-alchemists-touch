package com.matibi.thealchemiststouch.mixin.effect;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(method = "updateMouse", at = @At("HEAD"))
    private void invertXYIfBrainwashed(double timeDelta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;

        if (player != null && player.hasStatusEffect(ModEffects.BRAIN_WASHING)) {
            try {
                var fieldX = Mouse.class.getDeclaredField("cursorDeltaX");
                var fieldY = Mouse.class.getDeclaredField("cursorDeltaY");
                fieldX.setAccessible(true);
                fieldY.setAccessible(true);

                double dx = (double) fieldX.get(this);
                double dy = (double) fieldY.get(this);

                fieldX.set(this, -dx);
                fieldY.set(this, -dy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
