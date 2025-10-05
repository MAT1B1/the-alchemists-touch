package com.matibi.thealchemiststouch.mixin.effect;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LiquidWalkerMixin {

    @Inject(method = "canWalkOnFluid", at = @At("HEAD"), cancellable = true)
    private void allowWalkingOnFluid(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity)(Object)this;

        if (self.hasStatusEffect(ModEffects.LIQUID_WALKER)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "travel", at = @At("HEAD"))
    private void modifyLiquidSpeed(Vec3d movementInput, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (self.hasStatusEffect(ModEffects.LIQUID_WALKER)) {
            BlockPos below = self.getBlockPos().down();
            BlockState state = self.getWorld().getBlockState(below);

            boolean inLiquid = (state.getFluidState().isOf(Fluids.WATER) || state.getFluidState().isOf(Fluids.LAVA))
                    && !self.isSubmergedInWater();

            if (inLiquid) {
                Vec3d velocity = self.getVelocity();
                double multiplier = 1.15;
                self.setVelocity(velocity.x * multiplier, velocity.y, velocity.z * multiplier);
            }
        }
    }
}

