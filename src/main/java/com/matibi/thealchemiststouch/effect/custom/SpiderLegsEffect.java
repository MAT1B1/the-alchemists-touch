package com.matibi.thealchemiststouch.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

// Climbing Effect by SameDifferent
public class SpiderLegsEffect extends StatusEffect {
    public SpiderLegsEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x3B8B3B);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (entity.isSneaking()) return super.applyUpdateEffect(world, entity, amplifier);

        Vec3d vel = entity.getVelocity();

        if (entity.horizontalCollision) {
            double climbSpeed = 0.2D + amplifier * 0.05D;
            entity.setVelocity(vel.x * 0.90D, climbSpeed * 0.90D, vel.z * 0.90D);
            entity.fallDistance = 0.0F;
            entity.velocityModified = true;
            return true;
        }

        return super.applyUpdateEffect(world, entity, amplifier);
    }
}
