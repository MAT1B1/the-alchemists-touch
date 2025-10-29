package com.matibi.thealchemiststouch.util;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;

import java.util.*;

public final class TickUtil {
    private static final Map<ServerWorld, List<ScheduledTask>> TASKS = new HashMap<>();

    static {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            List<ScheduledTask> list = TASKS.get(world);
            if (list == null) return;

            Iterator<ScheduledTask> it = list.iterator();
            while (it.hasNext()) {
                ScheduledTask task = it.next();
                task.ticks--;
                if (task.ticks <= 0) {
                    try {
                        task.action.run();
                    } catch (Exception e) {
                        TheAlchemistsTouch.LOGGER.info("Error in delayed task {}", String.valueOf(e));
                    }
                    it.remove();
                }
            }
        });
    }

    public static void runLater(ServerWorld world, int ticks, Runnable action) {
        TASKS.computeIfAbsent(world, w -> new ArrayList<>()).add(new ScheduledTask(ticks, action));
    }

    private static final class ScheduledTask {
        int ticks;
        final Runnable action;

        ScheduledTask(int ticks, Runnable action) {
            this.ticks = ticks;
            this.action = action;
        }
    }
}
