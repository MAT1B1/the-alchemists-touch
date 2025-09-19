package com.matibi.thealchemiststouch.item;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final ConsumableComponent POISON_FOOD_CONSUMABLE_COMPONENT = ConsumableComponent.builder()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.POISON, 6 * 20, 1), 0.6f))
            .build();

    public static final FoodComponent POISON_FOOD_COMPONENT = new FoodComponent.Builder()
            .nutrition(1)
            .saturationModifier(0.2f)
            .alwaysEdible()
            .build();

    public static final Item POISONOUS_CARROT = register("poisonous_carrot", new Item(new Item.Settings()
            .food(POISON_FOOD_COMPONENT, POISON_FOOD_CONSUMABLE_COMPONENT)
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID,"poisonous_carrot")))
    ));

    public static final Item POISONOUS_BEETROOT = register("poisonous_beetroot", new Item(new Item.Settings()
            .food(POISON_FOOD_COMPONENT, POISON_FOOD_CONSUMABLE_COMPONENT)
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID,"poisonous_beetroot")))
    ));

    public static final Item ALCHEMIST_CORE = register("alchemist_core", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID,"alchemist_core")))
            .rarity(Rarity.UNCOMMON)
            .component(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT)
    ) {
        @Override
        public boolean hasGlint(ItemStack stack) {
            return true;
        }
    });

    private static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID, id), item);
    }

    private static void customFood(FabricItemGroupEntries entries) {
        entries.addAfter(Items.GOLDEN_CARROT, POISONOUS_CARROT);
        entries.addAfter(Items.BEETROOT, POISONOUS_BEETROOT);
        entries.getDisplayStacks().removeIf(s ->
                s.isOf(Items.POTION) ||
                s.isOf(Items.SPLASH_POTION) ||
                s.isOf(Items.LINGERING_POTION) ||
                s.isOf(Items.TIPPED_ARROW));

    }

    private static void customItem(FabricItemGroupEntries entries) {
        entries.addAfter(Items.NETHER_WART, ModItems.ALCHEMIST_CORE);
    }

    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Registering mod items for " + TheAlchemistsTouch.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::customFood);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::customItem);
    }
}