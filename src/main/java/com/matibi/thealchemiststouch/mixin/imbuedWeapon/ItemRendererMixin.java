package com.matibi.thealchemiststouch.mixin.imbuedWeapon;

import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    /*@Inject(
            method = "renderItem",
            at = @At("HEAD")
    )
    private void att$startGreenGlint(ItemDisplayContext displayContext,
                                     MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                     int light, int overlay, int[] tints, List<BakedQuad> quads,
                                     RenderLayer layer, ItemRenderState.Glint glint, CallbackInfo ci) {
        if (stack.contains(ModDataComponents.IMBUED_EFFECT))
            GreenGlintState.enable();
    }

    @Inject(
            method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V",
            at = @At("RETURN")
    )
    private void att$endGreenGlint(LivingEntity entity, ItemStack stack,
                                   ItemDisplayContext displayContext, MatrixStack matrices,
                                   VertexConsumerProvider vertexConsumers, World world,
                                   int light, int overlay, int seed, CallbackInfo ci) {
        if (GreenGlintState.isEnabled())
            GreenGlintState.disable();
    }*/
}
