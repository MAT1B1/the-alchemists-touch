package com.matibi.thealchemiststouch.mixin.effect;

import com.matibi.thealchemiststouch.network.ShootFireballC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class IgnitionMixin {
    @Shadow @Nullable public HitResult crosshairTarget;

    @Inject(method = "doAttack()Z", at = @At("HEAD"))
    private void att$sendFireballPacket(CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient client = (MinecraftClient)(Object)this;
        ClientPlayerEntity player = client.player;
        if (player == null) return;

        if (player.getAttackCooldownProgress(0) < 1.0F) return;

        if (crosshairTarget == null || crosshairTarget.getType() == HitResult.Type.MISS) {
            ClientPlayNetworking.send(new ShootFireballC2SPayload());
        }
    }
}
