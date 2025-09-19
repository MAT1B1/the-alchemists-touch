package com.matibi.thealchemiststouch.effect.custom;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

public class StunEffect extends StatusEffect {
    public StunEffect() {
        super(StatusEffectCategory.HARMFUL, 0xede13b);

        this.addAttributeModifier(
                EntityAttributes.MOVEMENT_SPEED,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "stun_movement"),
                -0.95D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.ATTACK_DAMAGE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "stun_knockback"),
                -0.95D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.JUMP_STRENGTH,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "stun_jump"),
                -0.95D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.BLOCK_BREAK_SPEED,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "stun_break_block"),
                -0.95D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.BLOCK_INTERACTION_RANGE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "stun_interaction"),
                -0.95D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.ENTITY_INTERACTION_RANGE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "stun_entity_interaction"),
                -0.95D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.MOVEMENT_EFFICIENCY,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "stun_movement_efficiency"),
                -0.95D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }
}
