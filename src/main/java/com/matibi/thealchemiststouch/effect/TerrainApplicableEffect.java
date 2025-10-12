package com.matibi.thealchemiststouch.effect;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
