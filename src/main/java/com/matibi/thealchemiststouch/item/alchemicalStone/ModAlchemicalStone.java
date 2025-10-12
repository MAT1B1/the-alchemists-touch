package com.matibi.thealchemiststouch.item.alchemicalStone;

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

public class ModAlchemicalStone {
    public static final RegistryKey<Registry<AlchemicalStone>> ALCHEMICAL_STONE_REGISTRY_KEY =
            RegistryKey.ofRegistry(Identifier.of(TheAlchemistsTouch.MOD_ID, "alchemical_stone"));

    public static final SimpleRegistry<AlchemicalStone> ALCHEMICAL_STONE_REGISTRY = new SimpleRegistry<>(
            ALCHEMICAL_STONE_REGISTRY_KEY,
            Lifecycle.stable()
    );

    public static final Item ALCHEMICAL_STONE = Registry.register(Registries.ITEM,
            Identifier.of(TheAlchemistsTouch.MOD_ID, "alchemical_stone"),
            new AlchemicalStoneItem(new Item.Settings()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID,"alchemical_stone")))
                    .maxCount(16)
                    .component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)
            ));

    public static RegistryEntry<AlchemicalStone> ACID =
            registerAlchemicalStone("acid", ModEffects.ACID, 0);

    public static RegistryEntry<AlchemicalStone> ACID_STRONG =
            registerAlchemicalStone("strong_acid", ModEffects.ACID, 1);

    public static RegistryEntry<AlchemicalStone> PETRIFICATION =
            registerAlchemicalStone("petrification", ModEffects.PETRIFICATION, 0);

    public static RegistryEntry<AlchemicalStone> PETRIFICATION_STRONG =
            registerAlchemicalStone("strong_petrification", ModEffects.PETRIFICATION, 1);

    public static RegistryEntry<AlchemicalStone> ALCHEMIST =
            registerAlchemicalStone("alchemist", ModEffects.ALCHEMIST, 0);

    public static RegistryEntry<AlchemicalStone> IGNITION =
            registerAlchemicalStone("ignition", ModEffects.IGNITION, 0);

    public static RegistryEntry<AlchemicalStone> GIANT =
            registerAlchemicalStone("giant", ModEffects.GIANT, 0);

    public static RegistryEntry<AlchemicalStone> RESURRECTION =
            registerAlchemicalStone("resurrection", ModEffects.RESURRECTION, 0);

    public static RegistryEntry<AlchemicalStone> registerAlchemicalStone(String name, RegistryEntry<StatusEffect> effect, int amplifier) {
        Identifier id = Identifier.of(TheAlchemistsTouch.MOD_ID, name + "_alchemical_stone");
        AlchemicalStone alchemicalStone = new AlchemicalStone(id, effect, amplifier);
        RegistryKey<AlchemicalStone> key = RegistryKey.of(ALCHEMICAL_STONE_REGISTRY_KEY, id);

        RegistryEntryInfo info = RegistryEntryInfo.DEFAULT;
        ALCHEMICAL_STONE_REGISTRY.add(key, alchemicalStone, info);
        return ALCHEMICAL_STONE_REGISTRY.getEntry(alchemicalStone);
    }

    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Registering mod runes for " + TheAlchemistsTouch.MOD_ID);
    }
}
