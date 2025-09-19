package com.matibi.thealchemiststouch.effect.custom;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

public class NoInteractionEffect extends StatusEffect {
    public NoInteractionEffect() {
        super(StatusEffectCategory.HARMFUL, 0x9f00ff);

        this.addAttributeModifier(
                EntityAttributes.BLOCK_INTERACTION_RANGE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "no_interaction_interaction"),
                -1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.ENTITY_INTERACTION_RANGE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "no_interaction_entity_interaction"),
                -1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.ATTACK_DAMAGE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "no_interaction_knockback"),
                -1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }
}
