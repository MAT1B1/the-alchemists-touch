package com.matibi.thealchemiststouch.mixin.effect;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantEntity.class)
public class MerchantEntityMixin {

    @Inject(method = "getOffers", at = @At("RETURN"), cancellable = true)
    private void applyBrainwashingDiscount(CallbackInfoReturnable<TradeOfferList> cir) {
        MerchantEntity merchant = (MerchantEntity) (Object) this;
        PlayerEntity customer = merchant.getCustomer();

        if (customer != null && merchant.hasStatusEffect(ModEffects.BRAIN_WASHING)) {
            TradeOfferList originalOffers = cir.getReturnValue();
            TradeOfferList discountedOffers = new TradeOfferList();

            for (TradeOffer offer : originalOffers) {
                TradeOffer discounted = offer.copy();

                ItemStack firstStack = discounted.getFirstBuyItem().itemStack();
                int baseFirst = firstStack.getCount();
                if (baseFirst > 2) {
                    int discount = merchant.getEntityWorld().getRandom().nextInt(baseFirst - 2) + 1;
                    discounted.setSpecialPrice(-discount);
                }
                discountedOffers.add(discounted);
            }
            cir.setReturnValue(discountedOffers);
        }
    }
}
