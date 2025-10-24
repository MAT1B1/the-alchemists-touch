package com.matibi.thealchemiststouch.ritual;

import com.matibi.thealchemiststouch.ritual.action.RitualAction;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class RitualRecipe implements Recipe<RecipeInput> {
    private final Identifier id;
    private final ItemStack input;
    private final RitualAction action;
    private final RitualSettings settings;

    public RitualRecipe(Identifier id, ItemStack input, RitualAction action, RitualSettings settings) {
        this.id = id;
        this.input = input;
        this.action = action;
        this.settings = settings;
    }

    @Override
    public boolean matches(RecipeInput recipeInput, World world) {
        return false;
    }

    @Override
    public ItemStack craft(RecipeInput recipeInput, RegistryWrapper.WrapperLookup registries) {
        return ItemStack.EMPTY;
    }

    public RitualAction getAction() { return action; }
    public ItemStack getInput() { return input; }
    public RitualSettings getSettings() { return settings; }
    public Identifier getId() { return id; }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return RitualRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return RitualRecipeType.INSTANCE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.NONE;
    }

    @Override
    public List<RecipeDisplay> getDisplays() {
        return List.of();
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return null;
    }
}
