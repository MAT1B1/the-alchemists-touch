package com.matibi.thealchemiststouch.network;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record RitualCircleSyncS2CPayload(BlockPos pos, ItemStack stack, int blood)
        implements CustomPayload {

    public static final Id<RitualCircleSyncS2CPayload> ID =
            new Id<>(Identifier.of(TheAlchemistsTouch.MOD_ID, "ritual_circle_sync"));

    public static final PacketCodec<RegistryByteBuf, RitualCircleSyncS2CPayload> CODEC =
            PacketCodec.of(
                    (payload, buf) -> {
                        buf.writeBlockPos(payload.pos);
                        buf.writeBoolean(!payload.stack.isEmpty()); // flag
                        if (!payload.stack.isEmpty()) {
                            ItemStack.PACKET_CODEC.encode(buf, payload.stack);
                        }
                        buf.writeInt(payload.blood);
                    },
                    buf -> {
                        BlockPos pos = buf.readBlockPos();
                        boolean hasStack = buf.readBoolean();
                        ItemStack stack = hasStack ? ItemStack.PACKET_CODEC.decode(buf) : ItemStack.EMPTY;
                        int blood = buf.readInt();
                        return new RitualCircleSyncS2CPayload(pos, stack, blood);
                    }
            );

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}