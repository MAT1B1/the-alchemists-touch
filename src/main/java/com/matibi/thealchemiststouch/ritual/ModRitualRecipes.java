package com.matibi.thealchemiststouch.ritual;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModRitualRecipes {
    public static final RecipeSerializer<RitualRecipe> RITUAL_SERIALIZER = RitualRecipeSerializer.INSTANCE;
    public static final RecipeType<RitualRecipe> RITUAL_TYPE = RitualRecipeType.INSTANCE;

    private static final Map<Identifier, RitualRecipe> RITUALS = new LinkedHashMap<>();

    public static void register() {
        Registry.register(
                Registries.RECIPE_TYPE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, RitualRecipeType.ID),
                RITUAL_TYPE
        );
        Registry.register(
                Registries.RECIPE_SERIALIZER,
                Identifier.of(TheAlchemistsTouch.MOD_ID, RitualRecipeType.ID),
                RITUAL_SERIALIZER
        );

        TheAlchemistsTouch.LOGGER.info("Registered RitualRecipe type & serializer for " + TheAlchemistsTouch.MOD_ID);
    }

    // ==== Custom runtime management ====
    public static void register(RitualRecipe recipe) {
        RITUALS.put(recipe.getId(), recipe);
    }

    public static RitualRecipe get(Identifier id) {
        return RITUALS.get(id);
    }

    public static Iterable<RitualRecipe> values() {
        return RITUALS.values();
    }

    public static void clear() {
        RITUALS.clear();
    }
}
