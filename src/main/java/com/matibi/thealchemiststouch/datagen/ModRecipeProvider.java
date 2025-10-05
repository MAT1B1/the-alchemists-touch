package com.matibi.thealchemiststouch.datagen;

import com.matibi.thealchemiststouch.item.ModItems;
import com.matibi.thealchemiststouch.recipe.CombinationRecipe;
import com.matibi.thealchemiststouch.recipe.FoodWithEffectRecipe;
import com.matibi.thealchemiststouch.recipe.ImbuedEffectRecipe;
import com.matibi.thealchemiststouch.recipe.RuneRecipe;
import com.matibi.thealchemiststouch.item.rune.ModRunes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.ComplexRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                createShapeless(RecipeCategory.BREWING, ModItems.ALCHEMIST_CORE, 8)
                        .input(Items.POISONOUS_POTATO)
                        .input(ModItems.POISONOUS_CARROT)
                        .input(ModItems.POISONOUS_BEETROOT)
                        .input(Items.BROWN_MUSHROOM)
                        .input(Items.RED_MUSHROOM)
                        .input(Items.FERMENTED_SPIDER_EYE)
                        .input(Items.BONE_MEAL)
                        .input(Items.ROTTEN_FLESH)
                        .input(ModItems.WITCH_S_FINGER)
                        .criterion(hasItem(ModItems.WITCH_S_FINGER), conditionsFromItem(ModItems.WITCH_S_FINGER))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BREWING, ModRunes.RUNE, 8)
                        .pattern("SSS")
                        .pattern("SAS")
                        .pattern("SSS")
                        .input('A', ModItems.ALCHEMIST_CORE)
                        .input('S', Items.COBBLESTONE)
                        .criterion(hasItem(ModItems.ALCHEMIST_CORE), conditionsFromItem(ModItems.ALCHEMIST_CORE))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.BREWING, ModItems.SYRINGE)
                        .input(Items.GLASS_BOTTLE)
                        .input(Items.IRON_NUGGET)
                        .criterion(hasItem(Items.GLASS_BOTTLE), conditionsFromItem(Items.GLASS_BOTTLE))
                        .offerTo(exporter);

                // Génère la recette spéciale pour la rune à partir d'une potion
                ComplexRecipeJsonBuilder.create(RuneRecipe::new)
                        .offerTo(exporter, "rune_recipe");
                ComplexRecipeJsonBuilder.create(CombinationRecipe::new)
                        .offerTo(exporter, "combination_recipe");
                ComplexRecipeJsonBuilder.create(FoodWithEffectRecipe::new)
                        .offerTo(exporter, "food_with_effect_recipe");
                ComplexRecipeJsonBuilder.create(ImbuedEffectRecipe::new)
                        .offerTo(exporter, "imbued_effect_recipe");
            }
        };
    }
    @Override
    public String getName() {
        return "recipe";
    }
}
