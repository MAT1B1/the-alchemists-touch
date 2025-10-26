package com.matibi.thealchemiststouch.ritual;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.ritual.custom.*;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRituals {

    public static final Ritual HEALTH = register("health_ritual", new HealthRitual());
    public static final Ritual STRENGTH = register("strength_ritual", new StrengthRitual());
    public static final Ritual SPEED = register("speed_ritual", new SpeedRitual());
    public static final Ritual INFINITY = register("infinity_ritual", new InfinityRitual());
    public static final Ritual ZOMBIE = register("zombie_ritual", new ZombieRitual());


    private static Ritual register(String id, Ritual ritual) {
        return Registry.register(RitualRegistry.RITUAL, Identifier.of(TheAlchemistsTouch.MOD_ID, id), ritual);
    }

    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Registering rituals for " + TheAlchemistsTouch.MOD_ID);
    }
}
