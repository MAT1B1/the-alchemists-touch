package com.matibi.thealchemiststouch.event;

import com.matibi.thealchemiststouch.item.ModItems;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public final class CopperCleanHandler {
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClient) return ActionResult.PASS;

            ItemStack heldItem = player.getStackInHand(hand);
            if (!(heldItem.getItem() instanceof AxeItem)) return ActionResult.PASS;

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);

            if (state.getBlock() instanceof Oxidizable
                && Oxidizable.getDecreasedOxidationBlock(state.getBlock()).isPresent())
                dropOxidationFragment((ServerWorld) world, pos);

            return ActionResult.PASS;
        });
    }

    private static void dropOxidationFragment(ServerWorld world, BlockPos pos) {
        world.spawnEntity(new net.minecraft.entity.ItemEntity(
                world,
                pos.getX() + 0.5,
                pos.getY() + 1.0,
                pos.getZ() + 0.5,
                new ItemStack(ModItems.OXYDATION)
        ));
    }
}
