package com.matibi.thealchemiststouch.client.modmenu.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.potion.ModPotions;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

public final class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static File FILE;

    public static final Set<Identifier> DISABLED_POTIONS = new HashSet<>();

    public static final Set<RegistryEntry<Potion>> PROTECTED_POTIONS = Set.of(
            Potions.WATER,
            Potions.AWKWARD,
            Potions.THICK,
            Potions.MUNDANE,
            Potions.LUCK,
            ModPotions.UNSTABLE
    );

    public static void init(File configDir) {
        FILE = new File(configDir, "the_alchemists_touch.json");
        load();
    }

    public static boolean isPotionDisabled(Potion potion) {
        var id = Registries.POTION.getId(potion);
        if (id == null) return false;

        for (var protectedEntry : PROTECTED_POTIONS)
            if (protectedEntry.matchesKey(Registries.POTION.getKey(potion).orElseThrow()))
                return false;

        var base = basePotionId(id);
        return DISABLED_POTIONS.contains(id) || DISABLED_POTIONS.contains(base);
    }

    public static Identifier basePotionId(Identifier id) {
        String ns = id.getNamespace();
        String p = id.getPath();
        boolean changed = true;
        while (changed) {
            changed = false;
            if (p.startsWith("long_"))   { p = p.substring(5); changed = true; }
            if (p.startsWith("strong_")) { p = p.substring(7); changed = true; }
        }
        return Identifier.of(ns, p);
    }

    public static void load() {
        try {
            if (!FILE.exists()) {
                save();
                return;
            }
            try (var r = new FileReader(FILE)) {
                Dto dto = GSON.fromJson(r, Dto.class);
                DISABLED_POTIONS.clear();
                if (dto != null && dto.disabledPotions != null) {
                    for (String s : dto.disabledPotions) {
                        DISABLED_POTIONS.add(Identifier.of(s));
                    }
                }
            }
        } catch (Exception e) {
            TheAlchemistsTouch.LOGGER.error("Failed to load config", e);
        }
    }

    public static void save() {
        try (var w = new FileWriter(FILE)) {
            Dto dto = new Dto();
            dto.disabledPotions = DISABLED_POTIONS.stream()
                    .filter(id -> !isProtectedId(id))
                    .map(Identifier::toString)
                    .toList();
            GSON.toJson(dto, w);
        } catch (Exception e) {
            TheAlchemistsTouch.LOGGER.error("Failed to load config", e);
        }
    }

    private static boolean isProtectedId(Identifier id) {
        for (var entry : PROTECTED_POTIONS) {
            var entryId = Registries.POTION.getId(entry.value());
            if (id.equals(entryId)) return true;
        }
        return false;
    }

    private static final class Dto {
        java.util.List<String> disabledPotions;
    }
}