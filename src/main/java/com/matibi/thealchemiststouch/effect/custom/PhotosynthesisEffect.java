package com.matibi.thealchemiststouch.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PhotosynthesisEffect extends StatusEffect {
    public PhotosynthesisEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x55FF55);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int interval = (20 * 10) >> amplifier;
        if (interval == 0) interval = 1;
        return duration % interval == 0;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            if (!world.getRegistryKey().equals(World.OVERWORLD))
                return super.applyUpdateEffect(world, entity, amplifier);

            if (!world.isDay()) return super.applyUpdateEffect(world, entity, amplifier);

            BlockPos pos = player.getBlockPos();
            if (!world.isSkyVisible(pos.up())) return super.applyUpdateEffect(world, entity, amplifier);

            var hunger = player.getHungerManager();
            hunger.add(1, 0.2f + 0.1f * amplifier);

            double x = player.getX();
            double y = player.getBodyY(0.5);
            double z = player.getZ();

            world.spawnParticles(
                    ParticleTypes.HAPPY_VILLAGER,
                    x, y, z,
                    6 + amplifier * 2,
                    0.4, 0.6, 0.4,
                    0.01
            );

        }
        return super.applyUpdateEffect(world, entity, amplifier);
    }
}

