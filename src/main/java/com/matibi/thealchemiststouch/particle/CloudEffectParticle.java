package com.matibi.thealchemiststouch.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

public class CloudEffectParticle extends BillboardParticle {

    private static final float DRAG = 0.98f;          // frottements plus forts → ralentit
    private static final float BUOYANCY = 0.0002f;    // monte très doucement
    private static final float WOBBLE = 0.015f;       // wobble réduit

    private final float baseScale;
    private final float wobblePhaseX;
    private final float wobblePhaseZ;
    private final double windX;
    private final double windZ;

    public CloudEffectParticle(ClientWorld world, double x, double y, double z,
                               double vx, double vy, double vz, int color, SpriteProvider sprites) {
        super(world, x, y, z, vx, vy, vz, sprites.getFirst());

        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;
        this.setColor(r, g, b);

        this.baseScale = 0.03f + random.nextFloat() * 0.05f;
        this.scale = baseScale;
        this.maxAge = 20 * 2 + random.nextInt(60);

        // vitesses initiales très réduites
        float speed = 0.02f;
        this.velocityX = vx * speed;
        this.velocityY = vy * speed;
        this.velocityZ = vz * speed;

        // vent plus doux
        double windMag = 0.0005 + random.nextDouble() * 0.0015; // ~0.0005–0.002
        double windAngle = random.nextDouble() * Math.PI * 2.0;
        this.windX = Math.cos(windAngle) * windMag;
        this.windZ = Math.sin(windAngle) * windMag;

        this.wobblePhaseX = random.nextFloat() * (float) Math.PI * 2f;
        this.wobblePhaseZ = random.nextFloat() * (float) Math.PI * 2f;

        this.setAlpha(0f);
        this.gravityStrength = 0.0f;
    }

    @Override
    public void tick() {
        super.tick();

        this.velocityY += BUOYANCY;

        this.velocityX += windX;
        this.velocityZ += windZ;

        float t = this.age * 0.1f;
        this.velocityX += Math.sin(t + wobblePhaseX) * WOBBLE * 0.003f;
        this.velocityZ += Math.cos(t + wobblePhaseZ) * WOBBLE * 0.003f;

        this.velocityX *= DRAG;
        this.velocityY *= DRAG;
        this.velocityZ *= DRAG;

        float life = (float) this.age / (float) this.maxAge;
        float fadeIn = Math.min(1f, this.age / 20f); // fondu plus long (20 ticks)
        float fadeOut = 1f - Math.max(0f, (life - 0.8f) / 0.2f);
        this.setAlpha(Math.max(0f, Math.min(1f, fadeIn * fadeOut)));

        this.scale = baseScale * (0.95f + 0.05f * (float) Math.sin(t * 0.5f));
    }

    @Override
    protected RenderType getRenderType() {
        return RenderType.PARTICLE_ATLAS_TRANSLUCENT;
    }

    public record Factory(SpriteProvider sprites) implements ParticleFactory<CloudEffectData> {
        @Override
        public @NotNull Particle createParticle(CloudEffectData parameters, ClientWorld world,
                                                double x, double y, double z,
                                                double velocityX, double velocityY, double velocityZ,
                                                Random random) {
            double s = 0.003 + world.random.nextDouble() * 0.003;
            double jx = (world.random.nextDouble() - 0.5) * s;
            double jy = (world.random.nextDouble() - 0.5) * s * 0.3;
            double jz = (world.random.nextDouble() - 0.5) * s;
            return new CloudEffectParticle(world, x, y, z,
                    velocityX + jx, velocityY + jy, velocityZ + jz, parameters.color(), this.sprites);
        }
    }
}
