package com.matibi.thealchemiststouch.recipe;

import com.matibi.thealchemiststouch.item.alchemicalStone.ModAlchemicalStone;
import com.matibi.thealchemiststouch.item.alchemicalStone.AlchemicalStone;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class AlchemicalStoneRecipe extends SpecialCraftingRecipe {
    public AlchemicalStoneRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
        if (craftingRecipeInput.getWidth() == 3
                && craftingRecipeInput.getHeight() == 3
                && craftingRecipeInput.getStackCount() == 9) {
            for (int i = 0; i < craftingRecipeInput.getHeight(); i++) {
                for (int j = 0; j < craftingRecipeInput.getWidth(); j++) {
                    ItemStack itemStack = craftingRecipeInput.getStackInSlot(j, i);
                    if (itemStack.isEmpty())
                        return false;

                    if (j == 1 && i == 1) {
                        if (!itemStack.isOf(Items.POTION))
                            return false;
                    } else if (!itemStack.isOf(ModAlchemicalStone.ALCHEMICAL_STONE))
                        return false;
                }
            }
            return true;
        } else
            return false;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return super.getIngredientPlacement();
    }

    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        ItemStack itemStack = craftingRecipeInput.getStackInSlot(1, 1);
        if (!itemStack.isOf(Items.POTION))
            return ItemStack.EMPTY;
        else
            return AlchemicalStone.getItemStack(itemStack.get(DataComponentTypes.POTION_CONTENTS), 8);
    }

    @Override
    public RecipeSerializer<AlchemicalStoneRecipe> getSerializer() {
        return ModRecipeSerializer.RUNE_FROM_POTION;
    }
}
