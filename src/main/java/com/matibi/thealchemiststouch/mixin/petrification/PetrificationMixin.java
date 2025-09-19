package com.matibi.thealchemiststouch.mixin.petrification;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class PetrificationMixin {

    @Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
    private void petrificationInvulnerable(ServerWorld world, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity)(Object)this;
        if (self.hasStatusEffect(ModEffects.PETRIFICATION))
            cir.setReturnValue(true);
    }

    @Inject(method = "tickActiveItemStack", at = @At("HEAD"), cancellable = true)
    private void stopUsingItemIfPetrified(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (entity.hasStatusEffect(ModEffects.PETRIFICATION)) {
            entity.clearActiveItem();
            ci.cancel();
        }
    }

    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    private void preventDropIfPetrified(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (entity.hasStatusEffect(ModEffects.PETRIFICATION))
            ci.cancel();
    }
}

