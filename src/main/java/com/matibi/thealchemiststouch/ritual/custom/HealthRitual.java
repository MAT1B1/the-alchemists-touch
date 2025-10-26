package com.matibi.thealchemiststouch.ritual.custom;

import com.matibi.thealchemiststouch.block.entity.RitualCircleBlockEntity;
import com.matibi.thealchemiststouch.effect.ModEffects;
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

public class HealthRitual implements Ritual {

    @Override
    public boolean checkConditions(ServerWorld world, RitualCircleBlockEntity blockEntity, PlayerEntity player) {
        if (blockEntity.getIngredient() != Items.GOLDEN_APPLE) return false;

        long time = world.getTimeOfDay() % 24000L;
        if (time >= 12000) return false;

        if (world.isRaining() || world.isThundering()) return false;

        return player.hasStatusEffect(ModEffects.RESURRECTION);
    }

    @Override
    public int bloodCost() {
        return 60;
    }

    @Override
    public int xpLvlCost() {
        return 12;
    }

    @Override
    public int duration() {
        return 20 * 6;
    }

    @Override
    public float successChance(ServerWorld world, PlayerEntity player) {
        float healthRatio = player.getHealth() / player.getMaxHealth();
        return 0.8f * healthRatio + 0.1f; // entre 10 % et 90 %
    }

    @Override
    public void completeRitual(ServerWorld world, BlockPos pos, RitualCircleBlockEntity circle, PlayerEntity player) {
        player.removeStatusEffect(ModEffects.RESURRECTION);

        float damage = player.getHealth() / 2f;
        player.damage(world, world.getDamageSources().magic(), damage);

        ItemStack potionStack = new ItemStack(Items.POTION);
        potionStack.set(DataComponentTypes.POTION_CONTENTS,
                new PotionContentsComponent(ModPotions.PERMANENT_HEALTH));

        world.spawnEntity(new ItemEntity(
                world,
                pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5,
                potionStack
        ));

        world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 0.8f, 1.3f);
        world.spawnParticles(ParticleTypes.HEART,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                30, 0.5, 0.5, 0.5, 0.01);
    }

    @Override
    public void onFailure(ServerWorld world, BlockPos pos, RitualCircleBlockEntity circle, @Nullable PlayerEntity player) {
        if (player != null) {
            player.removeStatusEffect(ModEffects.RESURRECTION);
            player.damage(world, world.getDamageSources().magic(), 3 * 2f);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 20 * 6, 1));
        }

         world.playSound(null, pos, SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.BLOCKS, 0.8f, 0.6f);

        world.spawnParticles(
                ParticleTypes.SMOKE,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                40, 0.6, 0.6, 0.6, 0.03
        );

        world.spawnParticles(
                ParticleTypes.FLAME,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                20, 0.3, 0.3, 0.3, 0.02
        );
    }

    @Override
    public void playEffects(ServerWorld world, BlockPos pos, float progress) {
        playRoundParticleEffect(world, pos, progress, 0xf00000);
    }
}
