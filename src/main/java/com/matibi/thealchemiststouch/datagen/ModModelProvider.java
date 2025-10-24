package com.matibi.thealchemiststouch.datagen;

import com.matibi.thealchemiststouch.item.ModItems;
import com.matibi.thealchemiststouch.item.alchemicalStone.ModAlchemicalStone;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.tint.PotionTintSource;

import java.util.List;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
    }



    @Override
    public void generateItemModels(net.minecraft.client.data.ItemModelGenerator itemModelGenerator) {
        List<net.minecraft.item.Item> items = List.of(
                ModItems.CLAW,
                ModItems.WITCH_S_FINGER,
                ModItems.ZOMBIE_BRAIN,
                ModItems.LEAF,
                ModItems.POISONOUS_BEETROOT,
                ModItems.POISONOUS_CARROT,
                ModItems.ALCHEMIST_CORE,
                ModItems.CHARGED_COPPER,
                ModItems.OXYDATION,
                ModItems.BLOOD_BAG
        );

        items.forEach(item -> itemModelGenerator.register(item, Models.GENERATED));

        itemModelGenerator.registerWithTintedOverlay(ModAlchemicalStone.ALCHEMICAL_STONE, new PotionTintSource(0x8a8a8a));
        itemModelGenerator.registerWithTintedOverlay(ModItems.SYRINGE, new PotionTintSource(0xc7dcff));
    }
}