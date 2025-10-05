package com.matibi.thealchemiststouch.mixin.effect.petrification;

import com.matibi.thealchemiststouch.client.render.state.PetrifiedFlag;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public abstract class PetrifiedStateDuck implements PetrifiedFlag {
    @Unique private boolean tat$petrified;
    @Override public void tat$setPetrified(boolean v) { tat$petrified = v; }
    @Override public boolean tat$isPetrified() { return tat$petrified; }
}

