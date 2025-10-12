package com.matibi.thealchemiststouch.effect.custom.terrain;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.effect.TerrainApplicableEffect;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEvents;

import java.util.function.BiConsumer;

public class GiantEffect extends StatusEffect implements TerrainApplicableEffect {
    public GiantEffect() {
        super(StatusEffectCategory.NEUTRAL, 0x228B22);

        this.addAttributeModifier(
                EntityAttributes.SCALE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "giant_scale"),
                1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.JUMP_STRENGTH,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "giant_jump"),
                1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.ATTACK_DAMAGE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "giant_dmg"),
                1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.ATTACK_SPEED,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "giant_atk_speed"),
                -0.5D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

    @Override
    public void forEachAttributeModifier(int amplifier, BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> consumer) {
        int lvl = amplifier + 1;

        consumer.accept(EntityAttributes.SCALE,
                new EntityAttributeModifier(
                        Identifier.of(TheAlchemistsTouch.MOD_ID, "giant_atk_speed"),
                        1.0D * lvl,
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        );
        consumer.accept(EntityAttributes.JUMP_STRENGTH,
                new EntityAttributeModifier(
                        Identifier.of(TheAlchemistsTouch.MOD_ID, "giant_scale"),
                        1.0D * lvl,
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        );
        consumer.accept(EntityAttributes.ATTACK_DAMAGE,
                new EntityAttributeModifier(
                        Identifier.of(TheAlchemistsTouch.MOD_ID, "giant_atk_speed"),
                        1.0D * lvl,
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        );
    }

    @Override
    public void useOnBlock(ServerWorld world, BlockPos pos, int duration, int amplifier) {
        var state = world.getBlockState(pos);
        var block = state.getBlock();
        var rand = world.random;

        var ageOpt = state.getProperties().stream()
                .filter(p -> p instanceof IntProperty && p.getName().equals("age"))
                .map(p -> (IntProperty) p)
                .findFirst();

        // --- 1) Si le bloc a une propriété AGE, on le met à son max ---
        if (ageOpt.isPresent()) {
            var ageProp = ageOpt.get();
            int current = state.get(ageProp);
            int max = ageProp.getValues().getLast();
            if (current < max) {
                world.setBlockState(pos, state.with(ageProp, max), 2);
                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 20);
            }
            return;
        }

        // --- 2) Sinon, si le bloc est fertilisable ---
        if (block instanceof Fertilizable fertilizable) {
            int maxTries = 4 + amplifier * 4;
            int tries = 0;
            while (tries < maxTries) {
                state = world.getBlockState(pos);
                if (!fertilizable.isFertilizable(world, pos, state)) break;
                if (!fertilizable.canGrow(world, rand, pos, state)) break;
                fertilizable.grow(world, rand, pos, state);
                tries++;
            }
            if (tries > 0)
                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 20);
        }
    }

}
