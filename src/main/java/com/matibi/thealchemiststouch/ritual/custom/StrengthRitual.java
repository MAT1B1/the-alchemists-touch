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

public class StrengthRitual implements Ritual {

    @Override
    public boolean checkConditions(ServerWorld world, RitualCircleBlockEntity blockEntity, PlayerEntity player) {
        if (blockEntity.getIngredient() != Items.ENCHANTED_GOLDEN_APPLE) return false;

        long time = world.getTimeOfDay() % 24000L;
        if (time < 12000) return false;

        if (world.isRaining() || world.isThundering()) return false;

        return player.getHealth() > 10f;
    }

    @Override
    public int bloodCost() {
        return 75;
    }

    @Override
    public int xpLvlCost() {
        return 15;
    }

    @Override
    public int duration() {
        return 20 * 8;
    }

    @Override
    public float successChance(ServerWorld world, PlayerEntity player) {
        return Math.min(0.65f + player.experienceLevel * 0.004f, 0.9f); // 65% - 90%
    }

    @Override
    public void completeRitual(ServerWorld world, BlockPos pos, PlayerEntity player) {
        player.damage(world, world.getDamageSources().magic(), 4.0f);

        ItemStack potionStack = new ItemStack(Items.POTION);
        potionStack.set(DataComponentTypes.POTION_CONTENTS,
                new PotionContentsComponent(ModPotions.PERMANENT_STRENGTH));

        world.spawnEntity(new ItemEntity(
                world,
                pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5,
                potionStack
        ));

        world.playSound(null, pos, SoundEvents.ENTITY_BLAZE_AMBIENT, SoundCategory.BLOCKS, 0.8f, 0.8f);
        world.spawnParticles(ParticleTypes.ANGRY_VILLAGER,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                25, 0.4, 0.4, 0.4, 0.02);
    }

    @Override
    public void onFailure(ServerWorld world, BlockPos pos, @Nullable PlayerEntity player) {
        if (player != null) {
            player.damage(world, world.getDamageSources().magic(), 6.0f);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20 * 10, 1));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 5, 0));
        }

        world.playSound(null, pos, SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.BLOCKS, 0.7f, 1.0f);
        world.spawnParticles(ParticleTypes.SMOKE,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                30, 0.4, 0.4, 0.4, 0.02);
        world.spawnParticles(ParticleTypes.LAVA,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                15, 0.2, 0.2, 0.2, 0.01);
    }

    @Override
    public void playEffects(ServerWorld world, BlockPos pos, float progress) {
        playRoundParticleEffect(world, pos, progress, 0xffc933);
    }
}
