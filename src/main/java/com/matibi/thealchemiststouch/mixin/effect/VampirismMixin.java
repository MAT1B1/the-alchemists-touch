package com.matibi.thealchemiststouch.mixin.effect;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class VampirismMixin {

    @Inject(method = "damage", at = @At("TAIL"))
    private void tat$vampirismAfterDamage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (self.getEntityWorld().isClient() || !cir.getReturnValueZ()) return;

        Entity attacker = source.getAttacker();
        if (!(attacker instanceof LivingEntity living)) return;

        if (living.hasStatusEffect(ModEffects.VAMPIRISM)) {
            int amp = Objects.requireNonNull(living.getStatusEffect(ModEffects.VAMPIRISM)).getAmplifier();

            float ratio = 0.10f + 0.10f * (amp + 1);

            if (!self.isInvulnerableTo(world, source))
                living.heal(amount * ratio);
        }
    }
}
