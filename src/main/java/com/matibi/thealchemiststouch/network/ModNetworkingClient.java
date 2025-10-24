package com.matibi.thealchemiststouch.network;

import com.matibi.thealchemiststouch.block.entity.RitualCircleBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class ModNetworkingClient {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(
                RitualCircleSyncS2CPayload.ID,
                (payload, context) -> {
                    MinecraftClient client = MinecraftClient.getInstance();
                    client.execute(() -> {
                        if (client.world == null) return;
                        var be = client.world.getBlockEntity(payload.pos());
                        if (be instanceof RitualCircleBlockEntity circle) {
                            circle.setStack(0, payload.stack());
                            circle.setBlood(payload.blood());
                        }
                    });
                }
        );
    }
}
