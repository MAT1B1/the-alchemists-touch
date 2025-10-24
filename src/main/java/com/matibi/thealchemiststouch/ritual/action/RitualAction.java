package com.matibi.thealchemiststouch.ritual.action;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public interface RitualAction {
    void execute(ServerWorld world, BlockPos pos);
}
