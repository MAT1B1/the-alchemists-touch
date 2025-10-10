package com.matibi.thealchemiststouch.network;

import com.matibi.thealchemiststouch.util.BallUtil;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModNetworking {
   public static void init() {
       PayloadTypeRegistry.playC2S().register(
               ShootFireballC2SPayload.ID, ShootFireballC2SPayload.CODEC);
       PayloadTypeRegistry.playC2S().register(
               ShootSnowballC2SPayload.ID, ShootSnowballC2SPayload.CODEC);

       ServerPlayNetworking.registerGlobalReceiver(
                ShootFireballC2SPayload.ID,
                (payload, context) -> context.server().execute(() ->
                        BallUtil.spawnFireball(context.player())
                )
       );
       ServerPlayNetworking.registerGlobalReceiver(
               ShootSnowballC2SPayload.ID,
               (payload, context) -> context.server().execute(() ->
                       BallUtil.spawnSnowball(context.player())
               )
       );
    }
}
