package com.matibi.thealchemiststouch.mixin.imbuedWeapon;

import com.matibi.thealchemiststouch.datacomponent.ImbuedEffect;
import com.matibi.thealchemiststouch.datacomponent.ModDataComponents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemPostHitMixin {

    @Inject(method = "postHit", at = @At("TAIL"))
    private void att$applyImbueOnHit(ItemStack stack, LivingEntity target,
                                     LivingEntity attacker, CallbackInfo ci) {
        if (attacker.getWorld().isClient()) return;

        ImbuedEffect imbued = stack.get(ModDataComponents.IMBUED_EFFECT);
        if (imbued == null) return;

        int duration = (imbued.effect().value().isInstant()) ? 1 : 20 * 10;

        target.addStatusEffect(new StatusEffectInstance(
                imbued.effect(), duration, imbued.amplifier()));

        int remaining = imbued.hitsRemaining() - 1;
        if (remaining > 0)
            stack.set(ModDataComponents.IMBUED_EFFECT,
                    new ImbuedEffect(imbued.effect(), remaining, imbued.amplifier()));
        else {
            stack.remove(ModDataComponents.IMBUED_EFFECT);
            stack.remove(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE);
        }
    }
}
