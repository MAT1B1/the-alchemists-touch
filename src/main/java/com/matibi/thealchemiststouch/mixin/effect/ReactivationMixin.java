package com.matibi.thealchemiststouch.mixin.effect;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class ReactivationMixin {

    @Unique
    private boolean shouldUpdate = true;

    @Inject(method = "onStatusEffectApplied", at = @At("HEAD"))
    private void onStatusEffectApplied(StatusEffectInstance newEffect, @Nullable Entity source, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (newEffect.getEffectType() == ModEffects.REACTIVATION) return;

        if (entity.hasStatusEffect(ModEffects.REACTIVATION)) {
            int newDuration = 30 * 20;
            for (StatusEffectInstance active : entity.getStatusEffects()) {
                if (active.getEffectType().equals(ModEffects.REACTIVATION))
                    newDuration += active.getAmplifier() * 30 * 20;
            }
            for (StatusEffectInstance active : entity.getStatusEffects()) {
                if (!active.getEffectType().equals(newEffect.getEffectType())
                        && !active.getEffectType().equals(ModEffects.REACTIVATION)
                        && shouldUpdate) {
                    shouldUpdate = false;
                    entity.addStatusEffect(new StatusEffectInstance(
                            active.getEffectType(),
                            active.getDuration() + newDuration,
                            active.getAmplifier(),
                            active.isAmbient(),
                            active.shouldShowParticles(),
                            active.shouldShowIcon()
                    ));
                    shouldUpdate = true;
                }
            }
        }
    }
}
