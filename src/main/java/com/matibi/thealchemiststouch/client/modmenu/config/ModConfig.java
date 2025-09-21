package com.matibi.thealchemiststouch.client.modmenu.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        return id == null || !DISABLED_POTIONS.contains(id);
    }

    public static void load() {
        try {
            if (!FILE.exists()) {
                save(); // crée fichier par défaut
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
