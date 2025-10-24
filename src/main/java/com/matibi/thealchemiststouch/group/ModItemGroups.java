package com.matibi.thealchemiststouch.group;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.block.ModBlocks;
import com.matibi.thealchemiststouch.client.modmenu.config.ModConfig;
import com.matibi.thealchemiststouch.item.ModItems;
import com.matibi.thealchemiststouch.item.alchemicalStone.ModAlchemicalStone;
import com.matibi.thealchemiststouch.item.alchemicalStone.AlchemicalStone;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> ALCHEMY =
            RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(TheAlchemistsTouch.MOD_ID, "alchemy"));

    public static final ItemGroup ALCHEMY_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(TheAlchemistsTouch.MOD_ID, "alchemy"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(Items.POTION))
                    .displayName(Text.translatable("itemGroup.the-alchemists-touch.alchemy"))
                    .entries((displayContext, entries) -> {
                        var SKIP = java.util.Set.of(
                                Potions.WATER.value(), Potions.AWKWARD.value(), Potions.THICK.value(), Potions.MUNDANE.value()
                        );
                        List<RegistryEntry.Reference<Potion>> all = Registries.POTION.streamEntries()
                                .filter(e -> !SKIP.contains(e.value()))
                                .toList();

                        addPotionType(Items.POTION, entries, all);
                        addPotionType(Items.SPLASH_POTION, entries, all);
                        addPotionType(Items.LINGERING_POTION, entries, all);

                        entries.add(ModAlchemicalStone.ALCHEMICAL_STONE);
                        for (RegistryEntry<AlchemicalStone> entry : ModAlchemicalStone.ALCHEMICAL_STONE_REGISTRY.streamEntries().toList()) {
                            ItemStack stack = AlchemicalStone.getItemStack(entry);
                            if (!stack.isEmpty())
                                entries.add(stack);
                        }

                        entries.add(ModItems.SYRINGE);

                        //entries.add(ModBlocks.RITUAL_CIRCLE);
                    })
                    .build()
    );

    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Registering mod groups for " + TheAlchemistsTouch.MOD_ID);
    }

    public static void addPotionType(Item item, ItemGroup.Entries entries, List<RegistryEntry.Reference<Potion>> all) {
        entries.add(PotionContentsComponent.createStack(item, Potions.WATER));
        entries.add(PotionContentsComponent.createStack(item, Potions.AWKWARD));
        entries.add(PotionContentsComponent.createStack(item, Potions.MUNDANE));
        entries.add(PotionContentsComponent.createStack(item, Potions.THICK));
        for (RegistryEntry<Potion> entry : all)
            if (!ModConfig.isPotionDisabled(entry.value()))
                entries.add(PotionContentsComponent.createStack(item, entry));
    }
}
