package com.matibi.thealchemiststouch.entity;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final RegistryKey<EntityType<?>> EFFECT_CLOUD_KEY =
            RegistryKey.of(Registries.ENTITY_TYPE.getKey(), Identifier.of(TheAlchemistsTouch.MOD_ID, "effect_cloud"));

    public static final EntityType<EffectCloud> EFFECT_CLOUD = registerEntity("effect_cloud",
            EntityType.Builder.create(EffectCloud::new, SpawnGroup.MISC)
                    .dimensions(0.1f, 0.1f)
                    .maxTrackingRange(8 * 16)
                    .trackingTickInterval(20)
                    .build(EFFECT_CLOUD_KEY)
    );

    private static <T extends Entity> EntityType<T> registerEntity(String id, EntityType<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(TheAlchemistsTouch.MOD_ID, id), type);
    }

    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Registering mod entities for " + TheAlchemistsTouch.MOD_ID);
    }
}
