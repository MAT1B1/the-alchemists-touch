package com.matibi.thealchemiststouch.mixin.effect.petrification;

import com.matibi.thealchemiststouch.effect.ModEffects;
import com.matibi.thealchemiststouch.entity.PetrifiedTracker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class PetrifiedTrackedFlagMixin implements PetrifiedTracker {
    @Unique
    private static final TrackedData<Boolean> TAT_PETRIFIED =
            DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void tat$initTracked(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(TAT_PETRIFIED, false);
    }

    @Override
    public boolean tat$isPetrified() {
        return ((LivingEntity)(Object)this).getDataTracker().get(TAT_PETRIFIED);
    }

    @Override
    public void tat$setPetrified(boolean v) {
        ((LivingEntity)(Object)this).getDataTracker().set(TAT_PETRIFIED, v);
    }

    @Inject(method = "tickStatusEffects", at = @At("TAIL"))
    private void tat$syncFromEffects(CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;
        if (!self.getEntityWorld().isClient()) {
            boolean has = self.hasStatusEffect(ModEffects.PETRIFICATION);
            if (has != self.getDataTracker().get(TAT_PETRIFIED)) {
                self.getDataTracker().set(TAT_PETRIFIED, has);
            }
        }
    }
}
