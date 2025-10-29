package com.matibi.thealchemiststouch.mixin.effect;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class FrostMixin {

    @Unique
    private static final Identifier POWDER_SNOW_OUTLINE =
            Identifier.ofVanilla("textures/misc/powder_snow_outline.png");

    @Inject(method = "render", at = @At("TAIL"))
    private void att$renderFrostOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity p = mc.player;
        if (p == null || mc.isPaused()) return;

        if (!p.hasStatusEffect(ModEffects.FROST)
                || p.hasStatusEffect(ModEffects.IGNITION)) return;

        int w = context.getScaledWindowWidth();
        int h = context.getScaledWindowHeight();

        int color = ColorHelper.withAlpha(0.70f, Colors.WHITE);

        context.drawTexture(
                RenderPipelines.GUI_TEXTURED,
                POWDER_SNOW_OUTLINE,
                0, 0,
                0, 0,
                w, h,
                w, h,
                w, h,
                color
        );
    }
}
