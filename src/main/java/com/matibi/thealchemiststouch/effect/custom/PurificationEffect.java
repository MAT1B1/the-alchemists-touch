package com.matibi.thealchemiststouch.effect.custom;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;

public class PurificationEffect extends StatusEffect {

    public PurificationEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFFFFFF);
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (!entity.getEntityWorld().isClient()) {
            for (StatusEffectInstance effect : entity.getStatusEffects()) {
                if (effect.getEffectType().value().getCategory() == StatusEffectCategory.HARMFUL) {
                    entity.removeStatusEffect(effect.getEffectType());
                }
            }
        }

        return super.applyUpdateEffect(world, entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}