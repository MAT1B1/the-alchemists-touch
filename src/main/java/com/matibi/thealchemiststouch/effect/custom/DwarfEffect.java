package com.matibi.thealchemiststouch.effect.custom;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class DwarfEffect extends StatusEffect {
    public DwarfEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xCC7722);

        this.addAttributeModifier(
                EntityAttributes.SCALE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "dwarf_scale"),
                -0.5D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.JUMP_STRENGTH,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "dwarf_jump"),
                -0.5D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.ATTACK_DAMAGE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "dwarf_dmg"),
                -0.5D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.ATTACK_SPEED,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "dwarf_atk_speed"),
                1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

    @Override
    public void forEachAttributeModifier(int amplifier, BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> consumer) {
        int lvl = amplifier + 1;

        consumer.accept(EntityAttributes.SCALE,
                new EntityAttributeModifier(
                        Identifier.of(TheAlchemistsTouch.MOD_ID, "dwarf_atk_speed"),
                        -0.5D * lvl,
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        );
        consumer.accept(EntityAttributes.ATTACK_SPEED,
                new EntityAttributeModifier(
                        Identifier.of(TheAlchemistsTouch.MOD_ID, "dwarf_atk_speed"),
                        1.0D * lvl,
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        );
    }

}
