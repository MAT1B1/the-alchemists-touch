// src/main/java/com/matibi/thealchemiststouch/effect/custom/RustEffect.java
package com.matibi.thealchemiststouch.effect.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.item.ItemStack;

public class RustEffect extends StatusEffect {
    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[] {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
    };

    public RustEffect() {
        super(StatusEffectCategory.HARMFUL, 0xB7410E);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayerEntity sp && sp.isCreative())
            return super.applyUpdateEffect(world, entity, amplifier);

        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack stack = entity.getEquippedStack(slot);
            if (stack.isEmpty() || !stack.isDamageable()) continue;

            int max = stack.getMaxDamage();
            if (max <= 0) continue;

            int amount = Math.max(1, max/ 100);

            stack.damage(amount, entity, slot);
        }
        return true;
    }
}
