package com.matibi.thealchemiststouch.mixin.imbuedWeapon;

import com.matibi.thealchemiststouch.client.render.state.GreenGlintState;
import com.matibi.thealchemiststouch.datacomponent.ModDataComponents;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
