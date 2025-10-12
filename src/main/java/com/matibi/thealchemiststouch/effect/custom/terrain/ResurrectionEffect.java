package com.matibi.thealchemiststouch.effect.custom.terrain;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.effect.ModEffects;
import com.matibi.thealchemiststouch.effect.TerrainApplicableEffect;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static net.minecraft.entity.effect.StatusEffects.*;

public class ResurrectionEffect extends StatusEffect implements TerrainApplicableEffect {
    private static final Map<UUID, Vec3d> ANCHOR_POSITIONS = new HashMap<>();

    public ResurrectionEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xffcc66);

        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, amount) -> {
            if (!(entity instanceof PlayerEntity player)) return true;
            if (!player.hasStatusEffect(ModEffects.RESURRECTION)) return true;

            Vec3d anchor = ANCHOR_POSITIONS.remove(player.getUuid());
            ServerWorld world = (ServerWorld) player.getWorld();
            totemLogic(world, player);

            if (anchor != null) {
                TheAlchemistsTouch.LOGGER.info("Scheduling TP to {}, {}, {}", anchor.x, anchor.y, anchor.z);

                MinecraftServer server = world.getServer();

                server.execute(() -> server.execute(() -> {
                    if (player instanceof ServerPlayerEntity serverPlayer && !serverPlayer.isDead()) {
                        serverPlayer.teleport(world, anchor.x, anchor.y + 1, anchor.z,
                                Set.of(), player.getYaw(), player.getPitch(), false);

                        world.playSound(null, anchor.x, anchor.y, anchor.z,
                                SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, player.getSoundCategory(), 1.0f, 1.0f);

                        world.spawnParticles(
                                ParticleTypes.PORTAL,
                                anchor.x, anchor.y + 1, anchor.z,
                                60, 1.0, 1.0, 1.0, 0.2
                        );
                        totemLogic(world, player);
                    }
                }));
            }
            return false;
        });
    }

    private void totemLogic(ServerWorld world, PlayerEntity player) {
        player.setHealth(1.0f);
        player.clearStatusEffects();
        player.addStatusEffect(new StatusEffectInstance(REGENERATION, 20 * 45, 1));
        player.addStatusEffect(new StatusEffectInstance(ABSORPTION, 20 * 5, 1));
        player.addStatusEffect(new StatusEffectInstance(FIRE_RESISTANCE, 20 * 40, 0));

        // Son et particules de totem
        world.syncWorldEvent(1033, player.getBlockPos(), 0);
        player.playSound(SoundEvents.ITEM_TOTEM_USE, 1.0F, 1.0F);
        player.emitGameEvent(GameEvent.ENTITY_INTERACT);

        if (player instanceof ServerPlayerEntity serverPlayer)
            serverPlayer.networkHandler.sendPacket(new EntityStatusS2CPacket(player, (byte) 35));
    }

    @Override
    public void useOnBlock(ServerWorld world, PlayerEntity player, BlockPos block, int duration, int amplifier) {
        if (world.isClient()) return;

        // Donne effet infini
        player.addStatusEffect(new StatusEffectInstance(ModEffects.RESURRECTION, -1, amplifier));
        ANCHOR_POSITIONS.put(player.getUuid(), block.toCenterPos());

        // Effet visuel de pose d’ancre
        world.playSound(null, block, SoundEvents.ITEM_TRIDENT_RETURN,
                SoundCategory.PLAYERS, 1.0f, 1.2f);

        Vec3d center = block.toCenterPos();

        int particleCount = 80;
        for (int i = 0; i < particleCount; i++) {
            double x = block.getX() + world.getRandom().nextDouble();
            double z = block.getZ() + world.getRandom().nextDouble();

            double y = center.y + 1 + world.getRandom().nextDouble();

            double dy = -0.05 - world.getRandom().nextDouble() * 0.05;

            world.spawnParticles(
                    ParticleTypes.TOTEM_OF_UNDYING,
                    x, y, z,
                    1,
                    0, dy, 0,
                    0.02
            );
        }
    }
}
