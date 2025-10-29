package com.matibi.thealchemiststouch.datacomponent;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public final class ModDataComponents {
    private ModDataComponents() {}

    public static final ComponentType<ImbuedEffect> IMBUED_EFFECT = register("imbued_effect",
            builder -> builder.codec(ImbuedEffect.CODEC).packetCodec(ImbuedEffect.PACKET_CODEC));

    public static final ComponentType<BloodType> BLOOD_TYPE = register("blood_type",
            builder -> builder.codec(BloodType.CODEC).packetCodec(BloodType.PACKET_CODEC));

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, id),
                builderOperator.apply(ComponentType.builder()).build()
        );
    }

    public static void register() {

    }
}
