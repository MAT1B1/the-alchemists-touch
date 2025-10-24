package com.matibi.thealchemiststouch.block.entity;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<RitualCircleBlockEntity> RITUAL_CIRCLE_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(TheAlchemistsTouch.MOD_ID, "ritual_circle_be"),
                    FabricBlockEntityTypeBuilder.create(RitualCircleBlockEntity::new, ModBlocks.RITUAL_CIRCLE).build());

    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Registering block entities for " + TheAlchemistsTouch.MOD_ID);
    }
 }
