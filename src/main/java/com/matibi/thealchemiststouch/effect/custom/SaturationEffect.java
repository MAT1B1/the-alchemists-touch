package com.matibi.thealchemiststouch.effect.custom;

import com.matibi.thealchemiststouch.mixin.effect.HungerManagerAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class SaturationEffect extends StatusEffect {
    public SaturationEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x5e370a);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (!(entity instanceof PlayerEntity player)) return false;

        HungerManagerAccessor hunger = (HungerManagerAccessor) player.getHungerManager();
        if (player.age % 10 == 0) {
            float exhaustion = hunger.getExhaustion();
            float reduction = 0.005f + 0.005f * amplifier;
            float newExhaustion = Math.max(0f, exhaustion - reduction);
            hunger.setExhaustion(newExhaustion);
        }
        return super.applyUpdateEffect(world, entity, amplifier);
    }
}
