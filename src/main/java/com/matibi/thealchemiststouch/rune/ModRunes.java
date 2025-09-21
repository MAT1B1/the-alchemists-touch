package com.matibi.thealchemiststouch.rune;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.effect.ModEffects;
import com.mojang.serialization.Lifecycle;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryInfo;
import net.minecraft.util.Identifier;

public class ModRunes {
    public static final RegistryKey<Registry<Rune>> RUNE_REGISTRY_KEY =
            RegistryKey.ofRegistry(Identifier.of(TheAlchemistsTouch.MOD_ID, "rune"));

    public static final SimpleRegistry<Rune> RUNE_REGISTRY = new SimpleRegistry<>(
            RUNE_REGISTRY_KEY,
            Lifecycle.stable()
    );

    public static final Item RUNE = Registry.register(Registries.ITEM,
            Identifier.of(TheAlchemistsTouch.MOD_ID, "rune"),
            new RuneItem(new Item.Settings()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID,"rune")))
                    .maxCount(16)
                    .component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)
            ));

    public static RegistryEntry<Rune> RUNE_ACID = registerRunes("acid", ModEffects.ACID, 0);

    public static RegistryEntry<Rune> RUNE_ACID_STRONG = registerRunes("strong_acid", ModEffects.ACID, 1);

    public static RegistryEntry<Rune> RUNE_PETRIFICATION = registerRunes("petrification", ModEffects.PETRIFICATION, 0);

    public static RegistryEntry<Rune> RUNE_PETRIFICATION_STRONG = registerRunes("strong_petrification", ModEffects.PETRIFICATION, 1);

    public static RegistryEntry<Rune> RUNE_ALCHEMIST = registerRunes("alchemist", ModEffects.ALCHEMIST, 0);

    public static RegistryEntry<Rune> RUNE_IGNITION = registerRunes("ignition", ModEffects.IGNITION, 0);

    public static RegistryEntry<Rune> RUNE_GIANT = registerRunes("giant", ModEffects.GIANT, 0);

    public static RegistryEntry<Rune> registerRunes(String name, RegistryEntry<StatusEffect> effect, int amplifier) {
        Identifier id = Identifier.of(TheAlchemistsTouch.MOD_ID, name + "_rune");
        Rune rune = new Rune(id, effect, amplifier);
        RegistryKey<Rune> key = RegistryKey.of(RUNE_REGISTRY_KEY, id);

        RegistryEntryInfo info = RegistryEntryInfo.DEFAULT;
        RUNE_REGISTRY.add(key, rune, info);
        return RUNE_REGISTRY.getEntry(rune);
    }

    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Registering mod runes for " + TheAlchemistsTouch.MOD_ID);
    }
}
