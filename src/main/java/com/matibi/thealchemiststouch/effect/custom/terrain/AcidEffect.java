package com.matibi.thealchemiststouch.effect.custom.terrain;

import com.matibi.thealchemiststouch.effect.TerrainApplicableEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class AcidEffect extends StatusEffect implements TerrainApplicableEffect {
    private static final ParticleEffect ACID_DUST = new DustParticleEffect(0x7FFF00, 1.0f);

    public AcidEffect() {
        super(StatusEffectCategory.HARMFUL, 0x7FFF00);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        entity.damage(world, entity.getDamageSources().magic(),1.0F + amplifier);
        return super.applyUpdateEffect(world, entity, amplifier);
    }

    @Override
    public void useOnBlock(ServerWorld world, BlockPos center, int duration, int amplifier) {
        int radius = 1 + amplifier;
        int depth = 1 + amplifier;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = 0; dy > -depth; dy--) {
                    BlockPos target = center.add(dx, dy, dz);
                    float hardness = world.getBlockState(target).getHardness(world, target);
                    if (!world.getBlockState(target).isAir()
                            && hardness >= 0.0F
                            && (hardness < 50.0f || amplifier > 0)) {
                        world.breakBlock(target, false);
                        spawnAcidParticles(world, target, amplifier);
                    }
                }
            }
        }
    }

    private static void spawnAcidParticles(ServerWorld world, BlockPos pos, int amplifier) {
        Random r = world.getRandom();

        double x = pos.getX() + 0.5;
        double y = pos.getY() + 1.01;
        double z = pos.getZ() + 0.5;

        int dustCount = 4 + amplifier * 2;
        world.spawnParticles(ACID_DUST, x, y, z, dustCount, 0.35, 0.15, 0.35, 0.008);

        if (r.nextFloat() < 0.35f)
            world.spawnParticles(ParticleTypes.SMOKE, x, y + 0.05, z, 1 + amplifier, 0.25, 0.05, 0.25, 0.01);

        if (r.nextFloat() < 0.15f)
            world.playSound(
                    null,
                    x, y, z,
                    SoundEvents.BLOCK_LAVA_EXTINGUISH,
                    SoundCategory.BLOCKS,
                    0.35f + 0.1f * amplifier,
                    0.8f + r.nextFloat() * 0.4f
            );
    }
}
