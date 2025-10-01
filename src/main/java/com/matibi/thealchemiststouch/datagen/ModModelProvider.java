package com.matibi.thealchemiststouch.datagen;

import com.matibi.thealchemiststouch.item.ModItems;
import com.matibi.thealchemiststouch.rune.ModRunes;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.tint.PotionTintSource;
import net.minecraft.item.Item;

import java.util.List;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        List<Item> items = List.of(
                ModItems.BAT_WING,
                ModItems.CLAW,
                ModItems.WITCH_S_FINGER,
                ModItems.ZOMBIE_BRAIN,
                ModItems.LEAF,
                ModItems.POISONOUS_BEETROOT,
                ModItems.POISONOUS_CARROT,
                ModItems.ALCHEMIST_CORE);

        items.forEach(item -> itemModelGenerator.register(item, Models.GENERATED));

        itemModelGenerator.registerWithTintedOverlay(ModRunes.RUNE, new PotionTintSource(0x8a8a8a));
    }
}
