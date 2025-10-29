package com.matibi.thealchemiststouch.mixin.effect.petrification;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerEntityRenderer.class)
public abstract class PetrificationArmMixin {

    @Unique
    private static final Identifier STONE_TEX = Identifier.of(TheAlchemistsTouch.MOD_ID, "textures/entity/petrified.png");

    @ModifyArg(
            method = "renderArm",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/RenderLayer;getEntityTranslucent(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"
            ),
            index = 0
    )
    private Identifier tat$checkCondition(Identifier texture) {
        var mc = MinecraftClient.getInstance();
        var player = mc.player;

        if (player != null && player.hasStatusEffect(ModEffects.PETRIFICATION))
            return STONE_TEX;

        return texture;
    }
}