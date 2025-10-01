package com.matibi.thealchemiststouch.client.modmenu.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
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

    public static void init(File configDir) {
        FILE = new File(configDir, "the_alchemists_touch.json");
        load();
    }

    public static boolean isPotionDisabled(Potion potion) {
        var id = Registries.POTION.getId(potion);
        if (id == null) return false;
        var base = basePotionId(id);
        // désactivée si l’id exact OU la base est dans la liste
        return DISABLED_POTIONS.contains(id) || DISABLED_POTIONS.contains(base);
    }

    public static boolean isPotionDisabled(ItemStack stack) {
        // Vérifie si l’item contient une potion
        var contents = stack.get(DataComponentTypes.POTION_CONTENTS);
        if (contents == null) return false;

        // Potion associée à l’ItemStack
        if (contents.potion().isEmpty()) return false;
        Potion potion = contents.potion().get().value();
        Identifier id = Registries.POTION.getId(potion);
        if (id == null) return false;

        // Identifiant de base (sans suffixe long/strong)
        Identifier base = basePotionId(id);

        // Désactivée si l’id exact OU la base est dans la liste
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
            e.printStackTrace();
        }
    }

    public static void save() {
        try (var w = new FileWriter(FILE)) {
            Dto dto = new Dto();
            dto.disabledPotions = DISABLED_POTIONS.stream()
                    .map(Identifier::toString)
                    .toList();
            GSON.toJson(dto, w);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final class Dto {
        java.util.List<String> disabledPotions;
    }
}