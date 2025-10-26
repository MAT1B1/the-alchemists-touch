package com.matibi.thealchemiststouch.ritual;

import com.matibi.thealchemiststouch.block.entity.RitualCircleBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

/**
 * Base interface for a ritual, purely in code.
 */
public interface Ritual {

    /** Checks if the ritual can be started */
    boolean checkConditions(ServerWorld world, RitualCircleBlockEntity blockEntity, PlayerEntity player);

    /** Main execution of the ritual */
    void completeRitual(ServerWorld world, BlockPos pos, RitualCircleBlockEntity circle, PlayerEntity player);

    /** Executed if the ritual fails */
    default void onFailure(ServerWorld world, BlockPos pos, RitualCircleBlockEntity circle, @Nullable PlayerEntity player) {
        world.playSound(null, pos, SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.BLOCKS, 0.3f, 2.0f);

        world.spawnParticles(net.minecraft.particle.ParticleTypes.SMOKE,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                10, 0.3, 0.3, 0.3, 0.02);
    }

    /** Determines the chance of success (0.0–1.0). 1.0 = always success */
    default float successChance(ServerWorld world, PlayerEntity player) {
        return 1.0f; // default 100% success
    }

    /** Visual or particle effects played during the ritual */
    default void playEffects(ServerWorld serverWorld, BlockPos pos, float progress) {}

    default void playRoundParticleEffect(ServerWorld world, BlockPos pos, float progress, int color) {
        double centerX = pos.getX() + 0.5;
        double centerY = pos.getY() + 0.8;
        double centerZ = pos.getZ() + 0.5;

        double startRadius = 2.0;
        double endRadius = 0.3;
        double radius = startRadius - (startRadius - endRadius) * progress;

        for (int i = 0; i < 20; i++) {
            double angleOffset = progress * 4 * Math.PI;
            double angle = (i / 20.0) * 2 * Math.PI + angleOffset;

            double startX = centerX + Math.cos(angle) * radius;
            double startY = centerY + world.random.nextDouble() * 0.3 - 0.15;
            double startZ = centerZ + Math.sin(angle) * radius;

            double dx = (centerX - startX) * 0.2;
            double dy = (centerY - startY) * 0.2;
            double dz = (centerZ - startZ) * 0.2;

            DustParticleEffect dust = new DustParticleEffect(color, 1.0f);
            world.spawnParticles(dust, startX, startY, startZ, 1, dx, dy, dz, 0.0);
        }
    }

    /**
     * Blood cost of the ritual
     */
    default int bloodCost() { return 1; }

    /**
     * XP level cost of the ritual
     */
    default int xpLvlCost() { return 1; }

    /**
     * Whether the item should be consumed after execution
     */
    default boolean consumeItem() { return true; }

    /** Ritual duration in ticks */
    default int duration() { return 20 * 5; }
}
