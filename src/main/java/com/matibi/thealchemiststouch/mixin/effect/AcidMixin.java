package com.matibi.thealchemiststouch.mixin.effect;


import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class AcidMixin {

    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    private void preventDropAndClearInventoryIfAcidOrPetrified(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.hasStatusEffect(ModEffects.ACID) && player.isDead()) {
            player.getInventory().clear();
            ci.cancel();
        }
    }
}