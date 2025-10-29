package com.matibi.thealchemiststouch.effect.custom;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class BerserkEffect extends StatusEffect {
    public BerserkEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xba0006);

        this.addAttributeModifier(
                EntityAttributes.JUMP_STRENGTH,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "berserk_jump"),
                0.4D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.MOVEMENT_SPEED,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "berserk_mvt"),
                0.4D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.ATTACK_SPEED,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "berserk_atk_speed"),
                2.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.ATTACK_DAMAGE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "berserk_atk_dmg"),
                2.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.MINING_EFFICIENCY,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "berserk_mining_efficiency"),
                2.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.BLOCK_BREAK_SPEED,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "berserk_mining_speed"),
                2.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

    @Override
    public void forEachAttributeModifier(int amplifier, BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> consumer) {
        int lvl = amplifier + 1;

        consumer.accept(
                EntityAttributes.JUMP_STRENGTH,
                new EntityAttributeModifier(
                        Identifier.of(TheAlchemistsTouch.MOD_ID, "berserk_jump"),
                        0.2D + 0.1D * (lvl -1),
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                )
        );

        consumer.accept(
                EntityAttributes.MOVEMENT_SPEED,
                new EntityAttributeModifier(
                        Identifier.of(TheAlchemistsTouch.MOD_ID, "berserk_mvt"),
                        0.2D + 0.1D * (lvl - 1),
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                )
        );

        consumer.accept(
                EntityAttributes.ATTACK_SPEED,
                new EntityAttributeModifier(
                        Identifier.of(TheAlchemistsTouch.MOD_ID, "berserk_atk_speed"),
                        2.0D * lvl,
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                )
        );

        consumer.accept(
                EntityAttributes.ATTACK_DAMAGE,
                new EntityAttributeModifier(
                        Identifier.of(TheAlchemistsTouch.MOD_ID, "berserk_atk_dmg"),
                        2.0D * lvl,
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                )
        );

        consumer.accept(
                EntityAttributes.MINING_EFFICIENCY,
                new EntityAttributeModifier(
                        Identifier.of(TheAlchemistsTouch.MOD_ID, "berserk_mining_efficiency"),
                        2.0D * lvl,
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                )
        );

        consumer.accept(
                EntityAttributes.BLOCK_BREAK_SPEED,
                new EntityAttributeModifier(
                        Identifier.of(TheAlchemistsTouch.MOD_ID, "berserk_mining_speed"),
                        2.0D * lvl,
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                )
        );
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (!entity.hasStatusEffect(StatusEffects.NIGHT_VISION))
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,
                    -1, 0, false, false, false));

        return super.applyUpdateEffect(world, entity, amplifier);
    }
}
