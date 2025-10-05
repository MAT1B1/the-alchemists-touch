package com.matibi.thealchemiststouch.mixin.effect;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(LivingEntity.class)
public abstract class BerserkMixin {
    @ModifyVariable(
            method = "damage(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At("HEAD"),
            argsOnly = true,
            index = 3 // 0=this, 1=world, 2=amount
    )
    private float tat$capDamageWhileBerserk(float amount) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (!self.hasStatusEffect(ModEffects.BERSERK))
            return amount;

        float minHealth = 1.0f;
        float maxDamageAllowed = Math.max(0.0f, self.getHealth() - minHealth);
        return Math.min(amount, maxDamageAllowed);
    }

    @Inject(
            method = "onStatusEffectsRemoved",
            at = @At("HEAD")
    )
    private void tat$onStatusEffectsRemoved(Collection<StatusEffectInstance> effects, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.getWorld().isClient()) return;

        boolean berserkRemoved = false;
        for (StatusEffectInstance inst : effects) {
            if (inst.getEffectType().equals(ModEffects.BERSERK)) {
                berserkRemoved = true;
                break;
            }
        }

        if (berserkRemoved) {
            if (self.hasStatusEffect(StatusEffects.NIGHT_VISION))
                self.removeStatusEffect(StatusEffects.NIGHT_VISION);
            self.addStatusEffect(new StatusEffectInstance(
                    ModEffects.AFTERMATH,
                    20 * 10,
                    0,
                    false,
                    false,
                    true
            ));
        }
    }

}