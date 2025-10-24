package com.matibi.thealchemiststouch.ritual;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.potion.ModPotions;
import com.matibi.thealchemiststouch.ritual.action.GiveItemAction;
import com.matibi.thealchemiststouch.ritual.action.RitualAction;
import com.matibi.thealchemiststouch.ritual.action.SpawnEntityAction;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModRituals {

    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Registering ritual recipes for " + TheAlchemistsTouch.MOD_ID);

        register("summon_zombie",
                Items.ROTTEN_FLESH,
                new SpawnEntityAction(EntityType.ZOMBIE, 20 * 10),
                RitualSettings.builder()
                        .duration(20 * 10)
                        .bloodCost(2)
                        .time(RitualSettings.MIDNIGHT)
                        .build()
        );

        register("perm_health_ritual",
                Items.GOLDEN_APPLE,
                new GiveItemAction(createPotionStack(ModPotions.PERMANENT_HEALTH)),
                RitualSettings.builder()
                        .duration(20 * 20)
                        .bloodCost(8)
                        .consumeItem(true)
                        .time(RitualSettings.MORNING)
                        .weather(RitualSettings.WeatherRequirement.CLEAR)
                        .blockBelow(Blocks.GOLD_BLOCK)
                        .nearbyEntity(EntityType.getId(EntityType.COW), 6)
                        .minPlayersNearby(1)
                        .build()
        );

        register("perm_strength_ritual",
                Items.BLAZE_POWDER,
                new GiveItemAction(createPotionStack(ModPotions.PERMANENT_STRENGTH)),
                RitualSettings.builder()
                        .duration(20 * 25)
                        .bloodCost(10)
                        .consumeItem(true)
                        .time(RitualSettings.EVENING)
                        .weather(RitualSettings.WeatherRequirement.THUNDER)
                        .blockBelow(Blocks.NETHERITE_BLOCK)
                        .nearbyEntity(EntityType.getId(EntityType.ZOMBIE), 8)
                        .dimension(Identifier.of("minecraft:overworld"))
                        .build()
        );

        register("perm_speed_ritual",
                Items.FEATHER,
                new GiveItemAction(createPotionStack(ModPotions.PERMANENT_SPEED)),
                RitualSettings.builder()
                        .duration(20 * 15)
                        .bloodCost(6)
                        .consumeItem(true)
                        .time(RitualSettings.DAY)
                        .weather(RitualSettings.WeatherRequirement.CLEAR)
                        .blockBelow(Blocks.GRASS_BLOCK)
                        .biome(Identifier.of("minecraft:plains"))
                        .build()
        );

        register("infinity_ritual",
                Items.NETHER_STAR,
                new GiveItemAction(createPotionStack(ModPotions.INFINITY)),
                RitualSettings.builder()
                        .duration(20 * 40)
                        .bloodCost(25)
                        .consumeItem(true)
                        .time(RitualSettings.MIDNIGHT)
                        .weather(RitualSettings.WeatherRequirement.THUNDER)
                        .dimension(Identifier.of("minecraft:the_end"))
                        .blockBelow(Blocks.OBSIDIAN)
                        .minPlayersNearby(2)
                        .build()
        );
    }

    private static ItemStack createPotionStack(RegistryEntry<Potion> potion) {
        ItemStack stack = new ItemStack(Items.POTION);
        stack.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(potion));
        return stack;
    }

    public static RitualRecipe register(String id, ItemConvertible inputItem, RitualAction action, RitualSettings settings) {
        Identifier identifier = Identifier.of(TheAlchemistsTouch.MOD_ID, id);
        RitualRecipe recipe = new RitualRecipe(identifier, new ItemStack(inputItem), action, settings);
        ModRitualRecipes.register(recipe);
        return recipe;
    }

    public static RitualRecipe register(String id, ItemStack input, RitualAction action, RitualSettings settings) {
        Identifier identifier = Identifier.of(TheAlchemistsTouch.MOD_ID, id);
        RitualRecipe recipe = new RitualRecipe(identifier, input, action, settings);
        ModRitualRecipes.register(recipe);
        return recipe;
    }
}
