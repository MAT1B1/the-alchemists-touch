package com.matibi.thealchemiststouch.mixin.effect;

import com.matibi.thealchemiststouch.effect.ModEffects;
import com.matibi.thealchemiststouch.util.KeyPermutationManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class BrainWashingMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        if (player == null) return;

        if (player.hasStatusEffect(ModEffects.BRAIN_WASHING)) {
            KeyboardInput input = (KeyboardInput)(Object)this;

            KeyPermutationManager.tick();

            input.playerInput = KeyPermutationManager.permute(input.playerInput);
        }
    }
}
