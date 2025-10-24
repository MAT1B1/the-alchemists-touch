package com.matibi.thealchemiststouch.ritual;

import com.matibi.thealchemiststouch.ritual.action.RitualAction;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class RitualManager {
    public static Optional<RitualRecipe> findRitual(ItemStack input) {
        for (RitualRecipe recipe : ModRitualRecipes.values())
            if (ItemStack.areItemsAndComponentsEqual(recipe.getInput(), input))
                return Optional.of(recipe);
        return Optional.empty();
    }

    public static void perform(ServerWorld world, BlockPos pos, RitualRecipe recipe) {
        RitualAction action = recipe.getAction();
        if (action != null)
            action.execute(world, pos);
    }
}
