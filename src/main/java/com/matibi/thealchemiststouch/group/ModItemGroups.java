package com.matibi.thealchemiststouch.group;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.rune.ModRunes;
import com.matibi.thealchemiststouch.rune.Rune;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.type.PotionContentsComponent;
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

                        for (RegistryEntry<Potion> entry : all)
                            entries.add(PotionContentsComponent.createStack(Items.POTION, entry));
                        for (RegistryEntry<Potion> entry : all)
                            entries.add(PotionContentsComponent.createStack(Items.SPLASH_POTION, entry));
                        for (RegistryEntry<Potion> entry : all)
                            entries.add(PotionContentsComponent.createStack(Items.LINGERING_POTION, entry));
                        for (RegistryEntry<Potion> entry : all)
                            entries.add(PotionContentsComponent.createStack(Items.TIPPED_ARROW, entry));

                        entries.add(ModRunes.RUNE);
                        for (RegistryEntry<Rune> entry : ModRunes.RUNE_REGISTRY.streamEntries().toList()) {
                            ItemStack stack = Rune.getItemStack(entry);
                            if (!stack.isEmpty())
                                entries.add(stack);
                        }
                    })
                    .build()
    );

    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Registering mod groups for " + TheAlchemistsTouch.MOD_ID);
    }
}
