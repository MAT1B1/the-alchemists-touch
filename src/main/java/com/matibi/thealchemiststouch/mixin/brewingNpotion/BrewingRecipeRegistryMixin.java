package com.matibi.thealchemiststouch.mixin.brewingNpotion;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.client.modmenu.config.ModConfig;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.matibi.thealchemiststouch.brewing.BlockRule;

import java.util.List;

@Mixin(BrewingRecipeRegistry.Builder.class)
public abstract class BrewingRecipeRegistryMixin {

    @Unique
    private static final List<BlockRule> BLOCKED_RECIPES = List.of(
            new BlockRule(Potions.AWKWARD, Items.GHAST_TEAR, Potions.REGENERATION)
    );

    @Inject(method = "registerPotionRecipe", at = @At("HEAD"), cancellable = true)
    private void tat$blockVanillaRecipes(RegistryEntry<Potion> input, Item ingredient,
                                         RegistryEntry<Potion> output, CallbackInfo ci) {

        for (BlockRule rule : BLOCKED_RECIPES) {
            boolean matches =
                    rule.input().getKey().map(input::matchesKey).orElse(false) &&
                            ingredient.asItem() == rule.ingredient() &&
                            rule.output().getKey().map(output::matchesKey).orElse(false);

            if (matches) {
                TheAlchemistsTouch.LOGGER.info("Blocked vanilla recipe: {} + {} = {}",
                        input.getIdAsString(), ingredient, output.getIdAsString());
                ci.cancel();
                return;
            }
        }

        if (ModConfig.isPotionDisabled(output.value())) {
            TheAlchemistsTouch.LOGGER.info("Disabled potion detected in brewing: {}",
                    output.getIdAsString());
            ci.cancel();
        }
    }
}
