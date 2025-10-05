// src/main/java/com/matibi/thealchemiststouch/mixin/PotionContentsAppendTooltipMixin.java
package com.matibi.thealchemiststouch.mixin.effect;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.function.Consumer;

@Mixin(PotionContentsComponent.class)
public class MaskingMixin {

    @Inject(
            method = "appendTooltip(Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;Lnet/minecraft/component/ComponentsAccess;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void tat$hideWhenMultiple(Item.TooltipContext ctx,
                                      Consumer<Text> textConsumer,
                                      TooltipType type,
                                      ComponentsAccess components,
                                      CallbackInfo ci) {
        PotionContentsComponent self = (PotionContentsComponent) (Object) this;

        boolean has_masking = false;
        int count = 0;
        Iterator<StatusEffectInstance> it = self.getEffects().iterator();
        while (it.hasNext() && count < 2) {
            StatusEffectInstance e = it.next();
            if (e.equals(ModEffects.MASKING)) has_masking = true;
            if (e.shouldShowParticles())
                count++;
        }

        if (count <= 1 || !has_masking) return;
        ci.cancel();
    }
}
