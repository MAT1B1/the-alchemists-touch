package com.matibi.thealchemiststouch.entity;

import com.matibi.thealchemiststouch.particle.CloudEffectData;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

import java.util.*;
import java.util.random.RandomGenerator;

public class EffectCloud extends Entity {
    private static final TrackedData<Integer> MAX_BLOCKS = DataTracker.registerData(EffectCloud.class,
            TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Integer> LIFETIME_TICKS = DataTracker.registerData(
            EffectCloud.class, TrackedDataHandlerRegistry.INTEGER
    );

    private final Set<BlockPos> occupied = new HashSet<>();

    private List<StatusEffectInstance> effects = new ArrayList<>();

    public EffectCloud(EntityType<? extends EffectCloud> type, World world) {
        super(type, world);
        this.noClip = true;
        this.setInvisible(true);
    }

    /* ==================== API publique (facultatif) ==================== */

    public void setLifetimeTicks(int ticks) {
        int v = (ticks == -1) ? -1 : Math.max(0, ticks);
        this.getDataTracker().set(LIFETIME_TICKS, v);
    }
    public void setLifetimeSeconds(int seconds) { setLifetimeTicks(seconds < 0 ? -1 : seconds * 20); }
    public int  getLifetimeTicks() { return this.getDataTracker().get(LIFETIME_TICKS); }
    public boolean isLifetimeInfinite() { return getLifetimeTicks() == -1; }

    public void setMAX_BLOCKS(int size) {
        size = Math.max(1, size);
        this.getDataTracker().set(MAX_BLOCKS, size);
    }
    public int getMAX_BLOCKS() { return this.getDataTracker().get(MAX_BLOCKS); }

    public void setEffects(Collection<StatusEffectInstance> fx) { this.effects = new ArrayList<>(fx); }
    public List<StatusEffectInstance> getEffects() { return this.effects; }

    /* ==================== DataTracker / init ==================== */
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(MAX_BLOCKS, 64);
        builder.add(LIFETIME_TICKS, -1);
    }

    /* ==================== Cycle de vie ==================== */
    @Override
    public void tick() {
        super.tick();

        if (!getWorld().isClient) {
            int life = getLifetimeTicks();
            if (life > 0) {
                setLifetimeTicks(life - 1);
            } else if (life == 0) {
                this.discard();
                return;
            }
        }

        recomputeOccupiedVoxels();
        spawnParticlesClient();
        applyEffectsToEntities();
    }

    /* ==================== Comportements ==================== */
    private void recomputeOccupiedVoxels() {
        occupied.clear();

        BlockPos origin = this.getBlockPos();
        if (isSolid(origin)) return; // on ne démarre pas dans un bloc solide

        ArrayDeque<BlockPos> q = new ArrayDeque<>();
        q.add(origin);
        occupied.add(origin);

        while (!q.isEmpty() && occupied.size() < getMAX_BLOCKS()) {
            BlockPos p = q.poll();
            if (p == null) return;
            for (Direction d : Direction.values()) {
                BlockPos n = p.offset(d);
                if (occupied.contains(n)) continue;
                if (isSolid(n)) continue; // on traverse seulement l’air / non-solide
                if (Math.abs(n.getY() - origin.getY()) > 32) continue;

                occupied.add(n);
                if (occupied.size() >= getMAX_BLOCKS()) break;
                q.add(n);
            }
        }
    }

    // true si le bloc est solide (a une collision)
    private boolean isSolid(BlockPos pos) {
        BlockState state = getWorld().getBlockState(pos);
        VoxelShape shape = state.getCollisionShape(getWorld(), pos);
        return !shape.isEmpty();
    }

    private void applyEffectsToEntities() {
        if (occupied.isEmpty() || effects.isEmpty()) return;

        Box bounds = computeBounds(occupied).expand(1.0);
        List<LivingEntity> list = getWorld().getEntitiesByClass(
                LivingEntity.class, bounds, e -> e.isAlive() && !e.isSpectator()
        );

        for (LivingEntity le : list) {
            BlockPos feet = le.getBlockPos();
            boolean inside = occupied.contains(feet) || occupied.contains(feet.up());

            for (StatusEffectInstance template : effects) {
                var type = template.getEffectType();
                int amp = template.getAmplifier();

                StatusEffectInstance active = le.getStatusEffect(type);

                if (inside) {
                    boolean needApply =
                            active == null || active.getAmplifier() != amp;

                    if (needApply)
                        le.addStatusEffect(new StatusEffectInstance(
                                type,
                                -1,
                                amp,
                                true,
                                false,
                                true
                        ));
                } else
                    if (active != null && active.getDuration() == -1)
                        le.removeStatusEffect(type);
            }
        }
    }

    private Box computeBounds(Set<BlockPos> vox) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE, maxZ = Integer.MIN_VALUE;
        for (BlockPos p : vox) {
            if (p.getX() < minX) minX = p.getX();
            if (p.getY() < minY) minY = p.getY();
            if (p.getZ() < minZ) minZ = p.getZ();
            if (p.getX() > maxX) maxX = p.getX();
            if (p.getY() > maxY) maxY = p.getY();
            if (p.getZ() > maxZ) maxZ = p.getZ();
        }
        return new Box(minX, minY, minZ, maxX + 1, maxY + 1, maxZ + 1);
    }

    private void spawnParticlesClient() {
        if (!getWorld().isClient || occupied.isEmpty()) return;
        ClientWorld world = (ClientWorld) getWorld();

        var r = world.random;
        int rgb = PotionContentsComponent.mixColors(this.effects).orElse(0xECEFB1);
        Random random = Random.from(RandomGenerator.getDefault());

        occupied.forEach(p -> {
            if (random.nextFloat() < 0.05f) {
                double x = p.getX() + 0.5 + (r.nextDouble() - 0.5); // petit décalage aléatoire
                double y = p.getY() + 0.5 + r.nextDouble() * 0.5;   // flotte un peu
                double z = p.getZ() + 0.5 + (r.nextDouble() - 0.5);

                double vx = (r.nextDouble() - 0.5) * 0.02; // vitesse très faible
                double vy = 0.005 + r.nextDouble() * 0.01; // tendance à monter doucement
                double vz = (r.nextDouble() - 0.5) * 0.02;

                world.addParticleClient(new CloudEffectData(rgb), x, y, z, vx, vy, vz);
            }
        });
    }

    /* ==================== Invulnérabilité / interactions ==================== */
    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) { return false; }
    @Override
    public boolean isAttackable() { return false; }

    /* ==================== Persistance ==================== */
    @Override
    protected void readCustomData(ReadView view) {
        this.setMAX_BLOCKS(view.getInt("MaxBlocks", this.getMAX_BLOCKS()));
        this.effects = view.read("Effects", StatusEffectInstance.CODEC.listOf())
                .orElseGet(java.util.ArrayList::new);
        this.setLifetimeTicks(view.getInt("Lifetime", -1));
    }

    @Override
    protected void writeCustomData(WriteView view) {
        view.putInt("MaxBlocks", this.getMAX_BLOCKS());
        view.put("Effects", StatusEffectInstance.CODEC.listOf(), this.effects);
        view.putInt("Lifetime", this.getLifetimeTicks());
    }
}
