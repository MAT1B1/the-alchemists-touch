package com.matibi.thealchemiststouch.util;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CombinationUtils {
    public static  List<StatusEffectInstance> combineEffect(List<StatusEffectInstance> effects) {
        AtomicInteger countBenef = new AtomicInteger();
        AtomicInteger countHarm = new AtomicInteger();

        Map<RegistryEntry<StatusEffect>, StatusEffectInstance> unique = new HashMap<>();

        for (StatusEffectInstance e : effects) {
            RegistryEntry<StatusEffect> type = e.getEffectType();

            if (unique.containsKey(type)) {
                StatusEffectInstance prev = unique.get(type);

                if (e.getAmplifier() > prev.getAmplifier() ||
                        (e.getAmplifier() == prev.getAmplifier() && e.getDuration() > prev.getDuration()))
                    unique.put(type, e);
            } else
                unique.put(type, e);
        }

        List<StatusEffectInstance> cleaned = new ArrayList<>(unique.values());

        cleaned.forEach(e -> {
            if (e.getEffectType().value().isBeneficial()) countBenef.incrementAndGet();
            else countHarm.incrementAndGet();
        });

        return ((countBenef.get() + countHarm.get() <= 2) || (countBenef.get() <= countHarm.get() + 1 &&
                countHarm.get() <= countBenef.get() + 1))
                ? cleaned
                : List.of(new StatusEffectInstance(ModEffects.UNSTABLE, 1));
    }
}
