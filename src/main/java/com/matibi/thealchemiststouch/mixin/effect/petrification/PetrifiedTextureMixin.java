package com.matibi.thealchemiststouch.mixin.effect.petrification;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.client.render.state.PetrifiedFlag;
import com.matibi.thealchemiststouch.entity.PetrifiedTracker;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class PetrifiedTextureMixin {
    @Unique
    private static final Identifier STONE_TEX =
            Identifier.of(TheAlchemistsTouch.MOD_ID, "textures/entity/petrified.png");

    @Inject(
            method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V",
            at = @At("TAIL")
    )
    private void tat$markPetrified(LivingEntity entity,
                                   LivingEntityRenderState state,
                                   float tickDelta,
                                   CallbackInfo ci) {
        if (state instanceof PetrifiedFlag flag) {
            boolean petrified = (entity instanceof PetrifiedTracker t) && t.tat$isPetrified();
            flag.tat$setPetrified(petrified);
        }
    }

    @Redirect(
            method = "getRenderLayer(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;ZZZ)Lnet/minecraft/client/render/RenderLayer;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;getTexture(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;)Lnet/minecraft/util/Identifier;"
            )
    )
    private Identifier tat$swapTexture(LivingEntityRenderer<LivingEntity, LivingEntityRenderState, EntityModel<? super LivingEntityRenderState>> self,
                                       LivingEntityRenderState state) {
        return (state instanceof PetrifiedFlag p && p.tat$isPetrified())
                ? STONE_TEX
                : self.getTexture(state);
    }

}