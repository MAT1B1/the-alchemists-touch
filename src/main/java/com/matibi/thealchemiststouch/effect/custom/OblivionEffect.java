package com.matibi.thealchemiststouch.effect.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

public class OblivionEffect extends StatusEffect {
    public OblivionEffect() {
        super(StatusEffectCategory.HARMFUL, 0x383838);
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

        var inventory = player.getInventory();
        var random = world.getRandom();
        int count = inventory.size() / 4;

        for (int i = 0; i < count; i++) {
            int slot = random.nextInt(inventory.size());
            if (!inventory.getStack(slot).isEmpty()) {
                player.dropItem(inventory.getStack(slot).split(1), true);
            }
        }
    }
}
