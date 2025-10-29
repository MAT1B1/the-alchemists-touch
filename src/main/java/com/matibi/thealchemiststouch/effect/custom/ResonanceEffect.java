package com.matibi.thealchemiststouch.effect.custom;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;

import java.util.Collection;
import java.util.List;
import java.util.OptionalInt;

public class ResonanceEffect extends StatusEffect {

    private static final int ORBIT_POINTS = 16;
    private static final int PULSE_POINTS = 40;
    private static final float SCALE_ORBIT = 0.45f;
    private static final float SCALE_PULSE = 0.65f;
    private static final int PULSE_PERIOD = 24;
    private static final double ORBIT_SPEED_BASE = 0.08;

    // couleur par défaut si aucun effet à résonner
    private static final int DEFAULT_COLOR = 0xA0E8E0;

    public ResonanceEffect() {
        super(StatusEffectCategory.NEUTRAL, DEFAULT_COLOR);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // particules chaque tick, logique toutes les 40 ticks ci-dessous
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        double maxRadius = 4.0 + amplifier * 2.0;

        Collection<StatusEffectInstance> effects = entity.getStatusEffects().stream()
                .filter(e -> !e.getEffectType().equals(ModEffects.RESONANCE))
                .toList();

        OptionalInt rgb = PotionContentsComponent.mixColors(effects);
        int baseColor = rgb.isEmpty() ? DEFAULT_COLOR : rgb.getAsInt() ;
        int centerColor = lighten(baseColor);

        spawnPulseWave(world, entity, amplifier, maxRadius, centerColor, baseColor);
        spawnOrbitRing(world, entity, amplifier, maxRadius, baseColor);

        StatusEffectInstance self = entity.getStatusEffect(ModEffects.RESONANCE);
        if (self != null && self.getDuration() % 40 == 0 && !effects.isEmpty()) {
            List<LivingEntity> nearby = world.getEntitiesByClass(
                    LivingEntity.class,
                    entity.getBoundingBox().expand(maxRadius),
                    e -> e != entity && e.isAlive()
            );
            for (LivingEntity target : nearby) {
                for (StatusEffectInstance effect : effects) {
                    StatusEffectInstance copy = new StatusEffectInstance(
                            effect.getEffectType(),
                            Math.max(1, effect.getDuration() / 2),
                            effect.getAmplifier()
                    );
                    target.addStatusEffect(copy);
                }
            }
        }
        return true;
    }

    private static void spawnPulseWave(ServerWorld world, LivingEntity entity, int amplifier,
                                       double maxRadius, int centerColor, int edgeColor) {
        int period = Math.max(8, PULSE_PERIOD - amplifier * 2);
        double phase = ((world.getTime() + (entity.getId() & 7)) % period) / (double) period;

        double eased = easeOutSine(phase);
        double r = 0.5 + eased * (maxRadius - 0.5);

        double y = entity.getY() + entity.getHeight() * 0.55;
        double step = (Math.PI * 2.0) / PULSE_POINTS;

        var pulse = new DustColorTransitionParticleEffect(centerColor, edgeColor, SCALE_PULSE);
        for (int i = 0; i < PULSE_POINTS; i++) {
            double angle = i * step;
            double x = entity.getX() + Math.cos(angle) * r;
            double z = entity.getZ() + Math.sin(angle) * r;
            world.spawnParticles(pulse, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    private static void spawnOrbitRing(ServerWorld world, LivingEntity entity, int amplifier,
                                       double radius, int baseColor) {
        double speed = ORBIT_SPEED_BASE + amplifier * 0.02;
        double t = world.getTime() * speed;

        double y = entity.getY() + entity.getHeight() * 0.6;
        double phaseOffset = (entity.getId() & 15) * 0.25;
        double step = (Math.PI * 2.0) / ORBIT_POINTS;
        var orbit = new DustParticleEffect(baseColor, SCALE_ORBIT);

        for (int i = 0; i < ORBIT_POINTS; i++) {
            double angle = t + phaseOffset + i * step;
            double x = entity.getX() + Math.cos(angle) * radius;
            double z = entity.getZ() + Math.sin(angle) * radius;
            world.spawnParticles(orbit, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    private static int lighten(int color) {
        float amount = 0.35f;
        int r = (color >> 16) & 0xFF, g = (color >> 8) & 0xFF, b = color & 0xFF;
        r = clamp255((int) (r + (255 - r) * amount));
        g = clamp255((int) (g + (255 - g) * amount));
        b = clamp255((int) (b + (255 - b) * amount));
        return (r << 16) | (g << 8) | b;
    }

    private static int clamp255(int v) { return Math.max(0, Math.min(255, v)); }

    private static double easeOutSine(double x) {
        return Math.sin((x * Math.PI) / 2.0);
    }
}
