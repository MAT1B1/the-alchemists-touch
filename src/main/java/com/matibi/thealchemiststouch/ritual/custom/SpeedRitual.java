package com.matibi.thealchemiststouch.ritual.custom;

import com.matibi.thealchemiststouch.block.entity.RitualCircleBlockEntity;
import com.matibi.thealchemiststouch.potion.ModPotions;
import com.matibi.thealchemiststouch.ritual.Ritual;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class SpeedRitual implements Ritual {

    @Override
    public boolean checkConditions(ServerWorld world, RitualCircleBlockEntity blockEntity, PlayerEntity player) {
        if (blockEntity.getIngredient() != Items.SUGAR) return false;

        long time = world.getTimeOfDay() % 24000L;
        if (time >= 12000) return false;

        return !world.isRaining() && !world.isThundering();
    }

    @Override
    public int bloodCost() {
        return 50;
    }

    @Override
    public int xpLvlCost() {
        return 12;
    }

    @Override
    public float successChance(ServerWorld world, PlayerEntity player) {
        return Math.min(0.75f + player.experienceLevel * 0.003f, 0.95f); // 75% - 95%
    }

    @Override
    public void completeRitual(ServerWorld world, BlockPos pos, PlayerEntity player) {
        ItemStack potionStack = new ItemStack(Items.POTION);
        potionStack.set(DataComponentTypes.POTION_CONTENTS,
                new PotionContentsComponent(ModPotions.PERMANENT_SPEED));

        world.spawnEntity(new ItemEntity(
                world,
                pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5,
                potionStack
        ));

        world.playSound(null, pos, SoundEvents.ENTITY_HORSE_GALLOP, SoundCategory.BLOCKS, 0.9f, 1.5f);
        world.spawnParticles(ParticleTypes.CLOUD,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                20, 0.5, 0.5, 0.5, 0.01);
    }

   @Override
    public void onFailure(ServerWorld world, BlockPos pos, @Nullable PlayerEntity player) {
        if (player != null) {
            player.damage(world, world.getDamageSources().magic(), 2 * 2f);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 10, 2));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 20 * 10, 1));
        }

        world.playSound(null, pos, SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.BLOCKS, 0.8f, 1.2f);
        world.spawnParticles(ParticleTypes.SMOKE,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                30, 0.4, 0.4, 0.4, 0.02);
        world.spawnParticles(ParticleTypes.SPORE_BLOSSOM_AIR,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                15, 0.3, 0.3, 0.3, 0.01);
    }

    @Override
    public void playEffects(ServerWorld world, BlockPos pos, float progress) {
        playRoundParticleEffect(world, pos, progress, 0x07b7e3);
    }
}
