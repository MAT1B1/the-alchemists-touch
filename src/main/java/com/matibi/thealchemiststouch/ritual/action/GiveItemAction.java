package com.matibi.thealchemiststouch.ritual.action;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class GiveItemAction implements RitualAction {
    private final ItemStack stack;
    public GiveItemAction(ItemStack stack) { this.stack = stack; }
    public ItemStack getStack() { return stack; }

    @Override
    public void execute(ServerWorld world, BlockPos pos) {
        world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, stack.copy()));
    }
}

