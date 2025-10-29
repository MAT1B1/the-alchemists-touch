package com.matibi.thealchemiststouch.effect.custom;

import com.matibi.thealchemiststouch.effect.ModEffects;
import com.matibi.thealchemiststouch.util.AttributeUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class InfinityEffect extends StatusEffect {
    private static final Set<StatusEffect> BLACKLISTED_EFFECTS = Set.of(
            ModEffects.REACTIVATION.value(),
            ModEffects.RESONANCE.value()
    );

    public InfinityEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x9f00ff);
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
        for (StatusEffectInstance effect : target.getStatusEffects()) {
            if (BLACKLISTED_EFFECTS.contains(effect.getEffectType().value())) continue;
            if (target instanceof PlayerEntity player)
                AttributeUtils.changeHealthBy(player, -2.0f);
            StatusEffectInstance infinite = new StatusEffectInstance(
                    effect.getEffectType(),
                    -1,
                    effect.getAmplifier(),
                    effect.isAmbient(),
                    effect.shouldShowParticles(),
                    effect.shouldShowIcon()
            );

            target.addStatusEffect(infinite);
        }
    }
}