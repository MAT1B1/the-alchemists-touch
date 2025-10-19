package com.matibi.thealchemiststouch.event;

import com.matibi.thealchemiststouch.item.ModItems;
import com.matibi.thealchemiststouch.util.TickUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.event.GameEvent;

public final class LightningStrikeHandler {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(entity instanceof LightningEntity lightning) || world.isClient()) return;
            TickUtil.runLater(world, 20, () -> {
                BlockPos center = BlockPos.ofFloored(lightning.getX(), lightning.getY(), lightning.getZ());
                BlockPos rodPos = findRodBelow(world, center);
                if (rodPos == null) return;
                dropChargedCopper(world, rodPos);
            });
        });
    }

    private static BlockPos findRodBelow(ServerWorld world, BlockPos around) {
        BlockPos.Mutable m = new BlockPos.Mutable();
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                m.set(around.getX() + dx, around.getY(), around.getZ() + dz);
                for (int i = 0; i < 24 && m.getY() >= world.getBottomY(); i++) {
                    if (world.getBlockState(m).isOf(Blocks.LIGHTNING_ROD)) {
                        return m.toImmutable();
                    }
                    m.move(Direction.DOWN);
                }
            }
        }
        return null;
    }

    private static void dropChargedCopper(ServerWorld world, BlockPos pos) {
        world.spawnEntity(new ItemEntity(
                world,
                pos.getX() + 0.5,
                pos.getY() + 1.0,
                pos.getZ() + 0.5,
                new ItemStack(ModItems.CHARGED_COPPER)
        ));
        world.emitGameEvent(null, GameEvent.ENTITY_PLACE, pos);
    }
}
