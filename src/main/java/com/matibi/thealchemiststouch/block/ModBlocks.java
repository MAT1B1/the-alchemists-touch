package com.matibi.thealchemiststouch.block;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block RITUAL_CIRCLE = register("ritual_circle",
            new RitualCircleBlock(AbstractBlock.Settings.create().nonOpaque()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of("ritual_circle")))));

    private static Block register(String id, Block item) {
        return Registry.register(Registries.BLOCK, Identifier.of(TheAlchemistsTouch.MOD_ID, id), item);
    }

    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Registering mod blocks for " + TheAlchemistsTouch.MOD_ID);

    }
}