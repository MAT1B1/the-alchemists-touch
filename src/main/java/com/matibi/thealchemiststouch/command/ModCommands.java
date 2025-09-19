package com.matibi.thealchemiststouch.command;

import com.matibi.thealchemiststouch.entity.ModEntities;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.matibi.thealchemiststouch.entity.EffectCloud;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import java.util.List;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public final class ModCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, env) ->
            dispatcher.register(
                literal("cloud")
                            // /cloud (par défaut)
                    .executes(ctx ->
                            spawnCloud(ctx.getSource().getPlayerOrThrow(), 64, -1, ctx.getSource().getWorld()))

                    .then(argument("size", IntegerArgumentType.integer(1))
                        .executes(ctx -> {
                            int size = IntegerArgumentType.getInteger(ctx, "size");
                            return spawnCloud(ctx.getSource().getPlayerOrThrow(),
                                    size, -1,
                                    ctx.getSource().getWorld());
                        })

                        .then(argument("lifetime", IntegerArgumentType.integer(-1))
                            .executes(ctx -> {
                                int size = IntegerArgumentType.getInteger(ctx, "size");
                                int lifetime = IntegerArgumentType.getInteger(ctx, "lifetime");
                                return spawnCloud(ctx.getSource().getPlayerOrThrow(),
                                        size, lifetime,
                                        ctx.getSource().getWorld());
                            })
                        )
                    )
            )
        );
    }

    private static int spawnCloud(ServerPlayerEntity player, int size, int lifetime, ServerWorld sw) {
        try {
            EffectCloud cloud = ModEntities.EFFECT_CLOUD.create(sw, SpawnReason.COMMAND);
            if (cloud == null) {
                player.sendMessage(Text.literal("Échec: factory de l'entité a renvoyé null."), false);
                return 0;
            }

            cloud.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), 0f, 0f);
            cloud.setMAX_BLOCKS(size);
            cloud.setLifetimeTicks(lifetime);

            cloud.setEffects(List.of(
                    new StatusEffectInstance(StatusEffects.POISON, 1, 0)
            ));

            boolean ok = sw.spawnEntity(cloud);
            if (!ok) {
                player.sendMessage(Text.literal("Échec du spawn de l'entité."), false);
                return 0;
            }

            player.sendMessage(Text.literal("Nuage créé (" + cloud.getMAX_BLOCKS() + " blocs, lifetime=" + lifetime + ")"), false);
            return 1;
        } catch (Exception ex) {
            player.sendMessage(Text.literal("Erreur: " + ex.getClass().getSimpleName() + " - " + ex.getMessage()), false);
            return 0;
        }
    }
}
