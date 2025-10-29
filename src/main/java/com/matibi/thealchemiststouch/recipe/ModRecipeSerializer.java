package com.matibi.thealchemiststouch.recipe;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeSerializer {
    public static final SpecialCraftingRecipe.SpecialRecipeSerializer<AlchemicalStoneRecipe> RUNE_FROM_POTION =
            new SpecialCraftingRecipe.SpecialRecipeSerializer<>(AlchemicalStoneRecipe::new);

    public static final SpecialCraftingRecipe.SpecialRecipeSerializer<CombinationRecipe> COMBINATION =
            new SpecialCraftingRecipe.SpecialRecipeSerializer<>(CombinationRecipe::new);

    public static final SpecialCraftingRecipe.SpecialRecipeSerializer<FoodWithEffectRecipe> FOOD_WITH_EFFECT =
            new SpecialCraftingRecipe.SpecialRecipeSerializer<>(FoodWithEffectRecipe::new);

    public static final SpecialCraftingRecipe.SpecialRecipeSerializer<ImbuedEffectRecipe> IMBUED_EFFECT =
            new SpecialCraftingRecipe.SpecialRecipeSerializer<>(ImbuedEffectRecipe::new);

    public static void registerSerializer(String id, SpecialCraftingRecipe.SpecialRecipeSerializer<?> serializer) {
        Registry.register(Registries.RECIPE_SERIALIZER,
                Identifier.of(TheAlchemistsTouch.MOD_ID, id),
                serializer);
    }

    public static void register() {
        registerSerializer("rune_from_potion", ModRecipeSerializer.RUNE_FROM_POTION);

        registerSerializer("combination", ModRecipeSerializer.COMBINATION);

        registerSerializer("food_with_effect_recipe", ModRecipeSerializer.FOOD_WITH_EFFECT);

        registerSerializer("imbued_effect", ModRecipeSerializer.IMBUED_EFFECT);
    }
}
