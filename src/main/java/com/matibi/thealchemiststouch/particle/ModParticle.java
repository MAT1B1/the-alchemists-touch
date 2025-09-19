package com.matibi.thealchemiststouch.particle;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModParticle {

    public static final ParticleType<CloudEffectData> CLOUD_EFFECT =
            register("cloud_effect",
                    new ParticleType<>(false) {
                        @Override public MapCodec<CloudEffectData> getCodec() { return CloudEffectData.CODEC; }
                        @Override public PacketCodec<? super RegistryByteBuf, CloudEffectData> getPacketCodec() { return CloudEffectData.PACKET_CODEC; }
                    }
            );

    private static ParticleType<CloudEffectData> register(String name, ParticleType<CloudEffectData> type) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(TheAlchemistsTouch.MOD_ID, name), type);
    }

    public static void register()  {
        TheAlchemistsTouch.LOGGER.info("Registering mod particles for " + TheAlchemistsTouch.MOD_ID);
    }
}
