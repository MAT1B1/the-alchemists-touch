package com.matibi.thealchemiststouch.loottable;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

public class ModLootTable {
    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Registering loot table for " + TheAlchemistsTouch.MOD_ID);

        LootTableEvents.MODIFY.register(
                (key, tableBuilder, source, wrapperLookup) -> {
                    addDrop(key, tableBuilder, EntityType.ZOMBIE,
                            ModItems.ZOMBIE_BRAIN, 1, 0.5f, wrapperLookup);
                    addDrop(key, tableBuilder, EntityType.WITCH,
                            ModItems.WITCH_S_FINGER, 1, 5, 0.7f, wrapperLookup);
                    addDrop(key, tableBuilder, EntityType.CAT,
                            ModItems.CLAW, 1, 5, 0.5f, wrapperLookup);
                    addDrop(key, tableBuilder, EntityType.OCELOT,
                            ModItems.CLAW, 1, 5, 1.0f, wrapperLookup);
                    addDrop(key, tableBuilder, EntityType.WOLF,
                            ModItems.CLAW, 1, 5, 0.3f, wrapperLookup);
            });
    }

    private static void addDrop(RegistryKey<LootTable> key, LootTable.Builder tableBuilder,
                                EntityType<?> entityType, Item item, int number, float chance,
                                RegistryWrapper.WrapperLookup wrapperLookup
    ) {
        addDrop(key, tableBuilder, entityType, item, number, number, chance, wrapperLookup);
    }

    private static void addDrop(RegistryKey<LootTable> key, LootTable.Builder tableBuilder,
                                EntityType<?> entityType, Item item, int min, int max, float chance,
                                RegistryWrapper.WrapperLookup wrapperLookup) {
        entityType.getLootTableKey().ifPresent(entityLootKey -> {
            if (entityLootKey.equals(key)) {
                LootPool.Builder baseDrop = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(chance))
                        .with(ItemEntry.builder(item)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)))
                                .apply(EnchantedCountIncreaseLootFunction.builder(wrapperLookup,
                                                ConstantLootNumberProvider.create(1)))
                        );
                tableBuilder.pool(baseDrop);
            }
        });
    }
}
