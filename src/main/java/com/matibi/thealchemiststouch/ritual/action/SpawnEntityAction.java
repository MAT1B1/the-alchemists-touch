package com.matibi.thealchemiststouch.ritual.action;

import com.matibi.thealchemiststouch.util.TickUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class SpawnEntityAction implements RitualAction {
    private final EntityType<?> type;
    private final int lifetime; // en ticks (20 ticks = 1 seconde)

    public SpawnEntityAction(EntityType<?> type) {
        this(type, -1); // -1 = pas de durée de vie limitée
    }

    public SpawnEntityAction(EntityType<?> type, int lifetime) {
        this.type = type;
        this.lifetime = lifetime;
    }

    public EntityType<?> getType() { return type; }
    public int getLifetime() { return lifetime; }

    @Override
    public void execute(ServerWorld world, BlockPos pos) {
        Entity entity = type.spawn(world, pos.up(), SpawnReason.MOB_SUMMONED);

        if (entity != null && lifetime > 0) {
            world.getServer().execute(() -> {
                TickUtil.runLater(world, lifetime, () -> {
                    if (entity.isAlive())
                        entity.discard(); // supprime l’entité proprement
                });
            });
        }
    }
}
