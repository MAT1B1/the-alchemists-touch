package com.matibi.thealchemiststouch.mixin.effect.petrification;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HeldItemRenderer.class)
public abstract class PetrificationArmMixin {
    @Unique
    private static final Identifier STONE_TEX = Identifier.of(TheAlchemistsTouch.MOD_ID, "textures/entity/petrified.png");

    @Unique
    private static Identifier stoneIfPetrified(Identifier original) {
        var player = MinecraftClient.getInstance().player;
        return (player != null && player.hasStatusEffect(ModEffects.PETRIFICATION)) ? STONE_TEX : original;
    }

    @ModifyArg(
            method = "renderArm",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;renderRightArm(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;Z)V"),
            index = 3
    )
    private Identifier tat$swapRightArm_renderArm(Identifier original) {
        return stoneIfPetrified(original);
    }

    @ModifyArg(
            method = "renderArm",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;renderLeftArm(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;Z)V"),
            index = 3
    )
    private Identifier tat$swapLeftArm_renderArm(Identifier original) {
        return stoneIfPetrified(original);
    }

    @ModifyArg(
            method = "renderArmHoldingItem",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;renderRightArm(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;Z)V"),
            index = 3
    )
    private Identifier tat$swapRightArm_renderArmHoldingItem(Identifier original) {
        return stoneIfPetrified(original);
    }

    @ModifyArg(
            method = "renderArmHoldingItem",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;renderLeftArm(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;Z)V"),
            index = 3
    )
    private Identifier tat$swapLeftArm_renderArmHoldingItem(Identifier original) {
        return stoneIfPetrified(original);
    }
}
