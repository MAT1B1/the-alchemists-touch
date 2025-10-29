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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class InfinityRitual implements Ritual {

    @Override
    public boolean checkConditions(ServerWorld world, RitualCircleBlockEntity blockEntity, PlayerEntity player) {
        long time = world.getTimeOfDay() % 24000L;

        if (time < 18000 || time > 20000) return false;

        if (world.isThundering()) return false;

        return player.getHealth() <= player.getMaxHealth() / 2f;
    }

    @Override
    public int bloodCost() {
        return 250;
    }

    @Override
    public int xpLvlCost() {
        return 40;
    }

    @Override
    public int duration() {
        return 20 * 15;
    }

    @Override
    public float successChance(ServerWorld world, PlayerEntity player) {
        // de base 20% + 0.5% par niveau d'xp (max 50%)
        float chance = 0.2f + (player.experienceLevel * 0.005f);
        return Math.min(chance, 0.5f);
    }

    @Override
    public void completeRitual(ServerWorld world, BlockPos pos, RitualCircleBlockEntity circle, PlayerEntity player) {
        float damage = player.getMaxHealth() * 0.75f;
        player.damage(world, world.getDamageSources().magic(), damage);

        ItemStack potionStack = new ItemStack(Items.POTION);
        potionStack.set(DataComponentTypes.POTION_CONTENTS,
                new PotionContentsComponent(ModPotions.INFINITY));

        world.spawnEntity(new ItemEntity(
                world,
                pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5,
                potionStack
        ));

        world.playSound(null, pos, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.BLOCKS, 1.0f, 1.0f);
        world.playSound(null, pos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.0f, 1.5f);

        world.spawnParticles(ParticleTypes.END_ROD,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                40, 0.6, 0.6, 0.6, 0.02);
    }

    @Override
    public Item getIngredient() {
        return Items.NETHER_STAR;
    }

    @Override
    public void onFailure(ServerWorld world, BlockPos pos, RitualCircleBlockEntity circle, @Nullable PlayerEntity player) {
        if (player != null) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 20 * 15, 2));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 10, 1));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 20, 2));
        }

        world.createExplosion(null,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                4.0f, true, ServerWorld.ExplosionSourceType.BLOCK);

        world.playSound(null, pos, SoundEvents.ENTITY_WITHER_DEATH, SoundCategory.BLOCKS, 1.0f, 0.7f);

        world.spawnParticles(ParticleTypes.SMOKE,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                50, 0.5, 0.5, 0.5, 0.03);
    }

    @Override
    public void playEffects(ServerWorld world, BlockPos pos, float progress) {
        playRoundParticleEffect(world, pos, progress, 0xAA00FF);

        playRoundParticleEffect(world, pos, progress + 0.5f, 0xFFD700);
    }
}
