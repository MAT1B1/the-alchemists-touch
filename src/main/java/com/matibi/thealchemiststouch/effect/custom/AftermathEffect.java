package com.matibi.thealchemiststouch.effect.custom;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;

public class AftermathEffect extends StatusEffect {
    public AftermathEffect() {
        super(StatusEffectCategory.NEUTRAL, 0x4A1A1A);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (world.isClient()) return false;

        float half = entity.getMaxHealth() * 0.5f;

        if (entity.getHealth() > half)
            entity.removeStatusEffect(ModEffects.AFTERMATH);

        StatusEffectInstance inst = entity.getStatusEffect(ModEffects.AFTERMATH);
        if (inst != null && inst.getDuration() == 1)
            entity.damage(world, world.getDamageSources().magic(), entity.getMaxHealth());

        return super.applyUpdateEffect(world, entity, amplifier);
    }
}
