package com.matibi.thealchemiststouch.effect.custom.terrain;

import com.matibi.thealchemiststouch.effect.TerrainApplicableEffect;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class AlchemistEffect extends StatusEffect implements TerrainApplicableEffect {
    public AlchemistEffect() {
            super(StatusEffectCategory.BENEFICIAL, 0xd1c70d);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        applyEffect(world, null, null, entity, amplifier, 1.0D);
        return super.applyUpdateEffect(world, entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {return true;}

    @Override
    public void applyInstantEffect(ServerWorld world, @Nullable Entity effectEntity, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        applyEffect(world, effectEntity, attacker, target, amplifier, proximity);
        super.applyInstantEffect(world, effectEntity, attacker, target, amplifier, proximity);
    }

    private static void applyEffect(ServerWorld world, @Nullable Entity effectEntity, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (!(target instanceof PlayerEntity player)) return;

        ItemStack offhand = player.getOffHandStack();
        int count = offhand.getCount();

        if (offhand.getItem() == Items.COAL || offhand.getItem() == Items.CHARCOAL)
            player.setStackInHand(Hand.OFF_HAND, new ItemStack(Items.GOLD_INGOT, count));
        else if (offhand.getItem() == Items.COAL_BLOCK)
            player.setStackInHand(Hand.OFF_HAND, new ItemStack(Items.GOLD_BLOCK, count));
        else if (offhand.getItem() == Items.COAL_ORE)
            player.setStackInHand(Hand.OFF_HAND, new ItemStack(Items.GOLD_ORE, count));
        else if (offhand.getItem() == Items.DEEPSLATE_COAL_ORE)
            player.setStackInHand(Hand.OFF_HAND, new ItemStack(Items.DEEPSLATE_GOLD_ORE, count));
    }

    @Override
    public void useOnBlock(ServerWorld world, BlockPos block, int duration, int amplifier) {
        var state = world.getBlockState(block);
        var blockType = state.getBlock();

        if (blockType == net.minecraft.block.Blocks.COAL_BLOCK)
            world.setBlockState(block, net.minecraft.block.Blocks.GOLD_BLOCK.getDefaultState());
        else if (blockType == net.minecraft.block.Blocks.COAL_ORE)
            world.setBlockState(block, net.minecraft.block.Blocks.GOLD_ORE.getDefaultState());
        else if (blockType == net.minecraft.block.Blocks.DEEPSLATE_COAL_ORE)
            world.setBlockState(block, net.minecraft.block.Blocks.DEEPSLATE_GOLD_ORE.getDefaultState());
    }

    @Override
    public boolean isBlockNonApplicable(ServerWorld world, BlockPos block) {
        BlockState state = world.getBlockState(block);
        return state.getBlock() != net.minecraft.block.Blocks.COAL_BLOCK
                && state.getBlock() != net.minecraft.block.Blocks.COAL_ORE
                && state.getBlock() != net.minecraft.block.Blocks.DEEPSLATE_COAL_ORE;
    }
}

