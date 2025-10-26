package com.matibi.thealchemiststouch.ritual;

import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;
import com.matibi.thealchemiststouch.TheAlchemistsTouch;

public class RitualRegistry {
    public static final RegistryKey<Registry<Ritual>> RITUAL_KEY =
            RegistryKey.ofRegistry(Identifier.of(TheAlchemistsTouch.MOD_ID, "ritual"));

    public static final Registry<Ritual> RITUAL =
            new SimpleRegistry<>(RITUAL_KEY, Lifecycle.stable());
}
