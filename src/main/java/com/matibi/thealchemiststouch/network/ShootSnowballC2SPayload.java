package com.matibi.thealchemiststouch.network;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ShootSnowballC2SPayload() implements CustomPayload {
    public static final Id<ShootSnowballC2SPayload> ID =
            new Id<>(Identifier.of(TheAlchemistsTouch.MOD_ID, "shoot_snowball"));

    public static final PacketCodec<RegistryByteBuf, ShootSnowballC2SPayload> CODEC =
            PacketCodec.unit(new ShootSnowballC2SPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
