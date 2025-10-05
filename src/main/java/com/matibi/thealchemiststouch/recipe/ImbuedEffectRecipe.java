package com.matibi.thealchemiststouch.recipe;

import com.matibi.thealchemiststouch.datacomponent.ImbuedEffect;
import com.matibi.thealchemiststouch.datacomponent.ModDataComponents;
import com.matibi.thealchemiststouch.util.ModTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImbuedEffectRecipe extends SpecialCraftingRecipe {
    private static final int DEFAULT_HITS = 10;

    public ImbuedEffectRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack weapon = ItemStack.EMPTY;
        ItemStack potion = ItemStack.EMPTY;

        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                ItemStack stack = input.getStackInSlot(x, y);
                if (stack.isEmpty()) continue;

                if (isPotionItem(stack)) {
                    if (!potion.isEmpty()) return false;
                    potion = stack;
                } else if (isWeapon(stack)) {
                    if (!weapon.isEmpty()) return false;
                    weapon = stack;
                } else
                    return false;
            }
        }
        if (weapon.isEmpty() || potion.isEmpty()) return false;
        if (weapon.contains(ModDataComponents.IMBUED_EFFECT) ||
                !(Objects.requireNonNull(weapon.get(DataComponentTypes.ENCHANTMENTS)).isEmpty()))
            return false;

        List<StatusEffectInstance> effects = getEffectList(potion);
        return !effects.isEmpty();
    }

    private boolean isPotionItem(ItemStack stack) {
        return stack.isOf(Items.POTION)
                || stack.isOf(Items.SPLASH_POTION)
                || stack.isOf(Items.LINGERING_POTION);
    }

    private boolean isWeapon(ItemStack stack) {
        return stack.isIn(ModTags.Items.IMBUEABLE_WEAPONS);
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack weapon = ItemStack.EMPTY;
        ItemStack potion = ItemStack.EMPTY;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            if (isPotionItem(stack))
                potion = stack;
            else if (isWeapon(stack))
                weapon = stack;
        }

        List<StatusEffectInstance> effects = getEffectList(potion);

        StatusEffectInstance chosen = effects.getFirst();

        ItemStack result = weapon.copy();
        result.setCount(1);

        int hits = DEFAULT_HITS + chosen.getDuration() / (20 * 60) * 2;

        result.set(ModDataComponents.IMBUED_EFFECT,
                new ImbuedEffect(chosen.getEffectType(), hits, chosen.getAmplifier()));
        result.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);

        return result;
    }

    @Override
    public RecipeSerializer<ImbuedEffectRecipe> getSerializer() {
        return ModRecipeSerializer.IMBUED_EFFECT;
    }

    public static List<StatusEffectInstance> getEffectList(ItemStack potion) {
        List<StatusEffectInstance> effects = new ArrayList<>();
        PotionContentsComponent component = potion.get(DataComponentTypes.POTION_CONTENTS);
        if(component != null)
            component.getEffects().forEach(effects::add);
        return effects;
    }
}
