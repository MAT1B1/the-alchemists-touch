package com.matibi.thealchemiststouch.screen;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<RitualCircleScreenHandler> RITUAL_CIRCLE_SCREEN_HANDLER =
            Registry.register(
                    Registries.SCREEN_HANDLER,
                    Identifier.of(TheAlchemistsTouch.MOD_ID, "ritual_circle_screen_handler"),
                    new ExtendedScreenHandlerType<>(
                            RitualCircleScreenHandler::new,
                            PacketCodec.ofStatic(BlockPos.PACKET_CODEC::encode, BlockPos.PACKET_CODEC::decode)
                    )
            );

    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Registering mod screen handlers for " + TheAlchemistsTouch.MOD_ID);
    }
}
