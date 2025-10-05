package com.matibi.thealchemiststouch.mixin.effect;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void redirectTargetIfBrainwashed(LivingEntity target, CallbackInfo ci) {
        MobEntity mob = (MobEntity) (Object) this;

        // Vérifie que c’est un mob hostile et sous effet
        if (!(mob instanceof Monster)) return;
        if (!mob.hasStatusEffect(ModEffects.BRAIN_WASHING)) return;
        if (target == null || target instanceof net.minecraft.entity.player.PlayerEntity) {
            // Cherche un autre mob hostile dans le coin
            Box box = mob.getBoundingBox().expand(10);
            List<LivingEntity> allies = mob.getWorld().getEntitiesByClass(
                    LivingEntity.class, box,
                    e -> e instanceof Monster && e != mob && mob.canSee(e)
            );

            if (!allies.isEmpty()) {
                LivingEntity newTarget = allies.get(mob.getWorld().random.nextInt(allies.size()));
                mob.setTarget(newTarget);
                ci.cancel(); // empêche la cible d'origine
            }
        }
    }
}