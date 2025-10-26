package com.matibi.thealchemiststouch.ritual.custom;

import com.matibi.thealchemiststouch.block.entity.RitualCircleBlockEntity;
import com.matibi.thealchemiststouch.entity.InvocationZombie;
import com.matibi.thealchemiststouch.ritual.Ritual;
import com.matibi.thealchemiststouch.util.TickUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class ZombieRitual implements Ritual {

    @Override
    public boolean checkConditions(ServerWorld world, RitualCircleBlockEntity blockEntity, PlayerEntity player) {
        return blockEntity.getIngredient() == Items.ZOMBIE_HEAD;
    }

    @Override
    public float successChance(ServerWorld world, PlayerEntity player) {
        float healthRatio = player.getHealth() / player.getMaxHealth();
        return 0.5f + 0.5f * (1.0f - healthRatio);
    }

    @Override
    public void completeRitual(ServerWorld world, BlockPos pos, RitualCircleBlockEntity circle, PlayerEntity player) {
        Vec3d spawnPos = new Vec3d(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);

        InvocationZombie zombie = new InvocationZombie(world, player);
        zombie.refreshPositionAndAngles(spawnPos.x, spawnPos.y, spawnPos.z, world.random.nextFloat() * 360f, 0);
        zombie.setPersistent();
        zombie.setCustomName(Text.literal("Invocation"));
        zombie.setCustomNameVisible(true);

        world.spawnEntity(zombie);

        world.playSound(null, pos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.0f, 1.5f);
        world.spawnParticles(ParticleTypes.ENCHANT,
                spawnPos.x, spawnPos.y + 0.5, spawnPos.z,
                40, 0.6, 0.6, 0.6, 0.1);

        TickUtil.runLater(world, 20 * 60, () -> {
            if (zombie.isAlive()) {
                world.playSound(null, zombie.getBlockPos(), SoundEvents.ENTITY_ZOMBIE_DEATH, SoundCategory.BLOCKS, 0.8f, 0.6f);
                zombie.discard();
                world.spawnParticles(ParticleTypes.SMOKE,
                        zombie.getX(), zombie.getY() + 0.5, zombie.getZ(),
                        30, 0.4, 0.4, 0.4, 0.02);
            }
        });
    }

    @Override
    public void onFailure(ServerWorld world, BlockPos pos, RitualCircleBlockEntity circle, @Nullable PlayerEntity player) {
        Vec3d spawnPos = new Vec3d(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);

        ZombieEntity failed = new ZombieEntity(world);
        failed.refreshPositionAndAngles(spawnPos.x, spawnPos.y, spawnPos.z, world.random.nextFloat() * 360f, 0);
        failed.setCustomName(Text.literal("Failed Invocation"));
        failed.setCustomNameVisible(true);

        failed.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        failed.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
        failed.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
        failed.equipStack(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
        failed.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        failed.setTarget(player);

        world.spawnEntity(failed);

        world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_INFECT, SoundCategory.BLOCKS, 1.0f, 0.8f);
        world.spawnParticles(ParticleTypes.SMOKE,
                spawnPos.x, spawnPos.y, spawnPos.z,
                40, 0.5, 0.5, 0.5, 0.02);
        world.spawnParticles(ParticleTypes.FLAME,
                spawnPos.x, spawnPos.y + 0.5, spawnPos.z,
                20, 0.3, 0.3, 0.3, 0.02);
    }

    @Override
    public void playEffects(ServerWorld world, BlockPos pos, float progress) {
        playRoundParticleEffect(world, pos, progress, 0xc7c7c7);
    }
}