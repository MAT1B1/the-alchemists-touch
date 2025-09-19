package com.matibi.thealchemiststouch.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public record CloudEffectData(int color) implements ParticleEffect {
    public static final MapCodec<CloudEffectData> CODEC = RecordCodecBuilder.mapCodec(i ->
            i.group(Codec.INT.fieldOf("color").forGetter(CloudEffectData::color))
                    .apply(i, CloudEffectData::new)
    );

    public static final PacketCodec<RegistryByteBuf, CloudEffectData> PACKET_CODEC =
            PacketCodec.of(
                    (buf, d) -> d.writeVarInt(buf.color()),
                    buf -> new CloudEffectData(buf.readVarInt())
            );

    @Override
    public ParticleType<?> getType() {
        return ModParticle.CLOUD_EFFECT;
    }
}
