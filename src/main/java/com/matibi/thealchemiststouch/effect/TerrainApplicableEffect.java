package com.matibi.thealchemiststouch.effect;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public interface TerrainApplicableEffect{

    default void useOnBlock(ServerWorld world, BlockPos block, int duration, int amplifier) {

    }
    default void useOnBlock(ServerWorld world, PlayerEntity player, BlockPos block, int duration, int amplifier) {
        useOnBlock(world, block, duration, amplifier);
    }
    default boolean isBlockNonApplicable(ServerWorld world, BlockPos block) {
        return false;
    }
}
