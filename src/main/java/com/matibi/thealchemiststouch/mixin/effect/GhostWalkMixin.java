package com.matibi.thealchemiststouch.mixin.effect;

import com.matibi.thealchemiststouch.util.SpectatorMemory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public class GhostWalkMixin implements SpectatorMemory {
    @Unique private GameMode tat$prevGamemode;
    @Override public GameMode tat$getPrevGamemode() { return tat$prevGamemode; }
    @Override public void tat$setPrevGamemode(GameMode gm) { tat$prevGamemode = gm; }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    private void tat$write(WriteView view, CallbackInfo ci) {
        if (tat$prevGamemode != null) view.putString("tat_prev_gamemode", tat$prevGamemode.asString());
    }
    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void tat$read(ReadView view, CallbackInfo ci) {
        if (!Objects.equals(view.getString("tat_prev_gamemode", "-1"), "-1"))
            tat$prevGamemode = GameMode.byId(view.getString("tat_prev_gamemode", "survival"),
                    GameMode.SURVIVAL);
    }
}
