package com.matibi.thealchemiststouch.recipe;

import com.matibi.thealchemiststouch.item.alchemicalStone.ModAlchemicalStone;
import com.matibi.thealchemiststouch.util.CombinationUtils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.*;

public class CombinationRecipe extends SpecialCraftingRecipe {
    public CombinationRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    public boolean matches(CraftingRecipeInput input, World world) {
        int validPotionCount = 0;
        Item basePotionItem = null;

        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                ItemStack stack = input.getStackInSlot(x, y);
                if (!stack.isEmpty()) {
                    if (!isPotionItem(stack)) return false;

                    PotionContentsComponent component = stack.get(DataComponentTypes.POTION_CONTENTS);
                    if (component == null || !component.hasEffects()) return false;

                    if (basePotionItem == null)
                        basePotionItem = stack.getItem();
                    else if (stack.getItem() != basePotionItem)
                        return false;

                    validPotionCount++;
                }
            }
        }

        return validPotionCount > 1;
    }

    private boolean isPotionItem(ItemStack stack) {
        return stack.isOf(Items.POTION)
                || stack.isOf(Items.SPLASH_POTION)
                || stack.isOf(Items.LINGERING_POTION)
                || stack.isOf(ModAlchemicalStone.ALCHEMICAL_STONE);
    }

    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        Item firstPotion = null;
        List<StatusEffectInstance> effects = new ArrayList<>();

        for (int i = 0; i < craftingRecipeInput.getHeight(); i++) {
            for (int j = 0; j < craftingRecipeInput.getWidth(); j++) {
                ItemStack itemStack = craftingRecipeInput.getStackInSlot(j, i);
                if (!itemStack.isEmpty() && isPotionItem(itemStack)) {
                    if (firstPotion == null)
                        firstPotion = itemStack.getItem();

                    PotionContentsComponent dataComponent = itemStack.get(DataComponentTypes.POTION_CONTENTS);
                    if (dataComponent != null)
                        dataComponent.getEffects().forEach(effects::add);
                }
            }
        }
        if (firstPotion == null) return ItemStack.EMPTY;

        ItemStack resultStack = new ItemStack(firstPotion);

        PotionContentsComponent combinedComponent = new PotionContentsComponent(
                Optional.empty(),
                Optional.empty(),
                CombinationUtils.combineEffect(effects),
                Optional.of("mixed")
        );

        resultStack.set(DataComponentTypes.POTION_CONTENTS, combinedComponent);

        return resultStack.copyWithCount(craftingRecipeInput.getStackCount());
    }

    @Override
    public RecipeSerializer<CombinationRecipe> getSerializer() {
        return ModRecipeSerializer.COMBINATION;
    }
}
