package com.matibi.thealchemiststouch.item;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.effect.ModEffects;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;

public class ModItems {
    public static final ConsumableComponent POISON_FOOD_CONSUMABLE_COMPONENT = statusEffectFood(
            StatusEffects.POISON, 6 * 20, 1, 0.6f);

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

    public static final Item BAT_WING = register("bat_wing", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID, "bat_wing")))
            .food(new FoodComponent(1, 1, false))
    ));

    public static final Item LEAF = register("leaf", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID, "leaf")))
    ));

    public static final Item ZOMBIE_BRAIN = register("zombie_brain", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID, "zombie_brain")))
            .food(new FoodComponent(1, 1, false),
                    statusEffectFood(ModEffects.BRAIN_WASHING, 20 * 10, 0, 0.5f))
    ));

    public static final Item CLAW = register("claw", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID, "claw")))
    ));

    public static final Item WITCH_S_FINGER = register("witch_finger", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID, "witch_finger")))
            .food(new FoodComponent(1, 1, false))
    ));

    /*
    public static final Item STINGER = register("stinger", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID, "stinger")))
    ));

    public static final Item CAMEL_FAT = register("camel_fat", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID, "camel_fat")))
            .food(new FoodComponent(2 * 3, 12.8f, false))
    ));

    public static final Item FOX_TAIL = register("fox_tail", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID, "fox_tail")))
    ));

    public static final Item DOLPHIN_FIN = register("dolphin_fin", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID, "dolphin_fin")))
    ));

    public static final Item MIMIC_SPIRIT = register("mimic_spirit", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID, "mimic_spirit")))
    ));

    public static final Item SOUL_SHARD = register("soul_shard", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsTouch.MOD_ID, "soul_shard")))
    ));*/


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

        List<Item> items = List.of(
                ModItems.BAT_WING,
                ModItems.CLAW,
                ModItems.WITCH_S_FINGER,
                ModItems.ZOMBIE_BRAIN,
                ModItems.LEAF).reversed();

        items.forEach(i -> entries.addAfter(Items.PHANTOM_MEMBRANE, i));
    }

    public static ConsumableComponent statusEffectFood(RegistryEntry<StatusEffect> effect, int duration, int amplifier, float probability) {
        return ConsumableComponent.builder()
                .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(effect, duration, amplifier), probability))
                .build();
    }

    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Registering mod items for " + TheAlchemistsTouch.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::customFood);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::customItem);
    }
}