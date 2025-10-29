package com.matibi.thealchemiststouch.effect.custom;

import com.matibi.thealchemiststouch.effect.ModEffects;
import com.matibi.thealchemiststouch.util.SpectatorMemory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class GhostWalkEffect extends StatusEffect{
    public GhostWalkEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xA9E5FF);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (!(entity instanceof ServerPlayerEntity sp)) return false;

        var inst = sp.getStatusEffect(ModEffects.GHOST_WALK);
        if (inst == null) return false;

        var mem = (SpectatorMemory) sp;

        if (mem.tat$getPrevGamemode() == null) {
            mem.tat$setPrevGamemode(sp.interactionManager.getGameMode());
            sp.changeGameMode(net.minecraft.world.GameMode.SPECTATOR);

            sp.getAbilities().setFlySpeed(0.02f);
            sp.sendAbilitiesUpdate();
        }

        if (inst.getDuration() <= 1) {
            if (!sp.isDead()) {
                var prev = mem.tat$getPrevGamemode();
                sp.changeGameMode(prev != null ? prev : net.minecraft.world.GameMode.SURVIVAL);
            }
            mem.tat$setPrevGamemode(null);

            sp.getAbilities().setFlySpeed(0.05f);
            sp.sendAbilitiesUpdate();

        }
        return super.applyUpdateEffect(world, entity, amplifier);
    }
}
