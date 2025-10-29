package com.matibi.thealchemiststouch.effect.custom.terrain;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.effect.TerrainApplicableEffect;
import net.minecraft.block.Blocks;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class PetrificationEffect extends StatusEffect implements TerrainApplicableEffect {
    public PetrificationEffect() {
        super(StatusEffectCategory.HARMFUL, 0xA8A8A8);

        this.addAttributeModifier(
                EntityAttributes.MOVEMENT_SPEED,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "petrification_movement"),
                -1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.KNOCKBACK_RESISTANCE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "petrification_knockback"),
                1.0D,
                EntityAttributeModifier.Operation.ADD_VALUE
        );
        this.addAttributeModifier(
                EntityAttributes.ATTACK_DAMAGE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "petrification_knockback"),
                -1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.JUMP_STRENGTH,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "petrification_jump"),
                -1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.BLOCK_BREAK_SPEED,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "petrification_break_block"),
                -1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.BLOCK_INTERACTION_RANGE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "petrification_interaction"),
                -1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.ENTITY_INTERACTION_RANGE,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "petrification_entity_interaction"),
                -1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.GRAVITY,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "petrification_gravity"),
                1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                EntityAttributes.MOVEMENT_EFFICIENCY,
                Identifier.of(TheAlchemistsTouch.MOD_ID, "petrification_movement_efficiency"),
                -1.0D,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

    @Override
    public void useOnBlock(ServerWorld world, BlockPos block, int duration, int amplifier) {
        if (amplifier == 0 && !world.getBlockState(block).isOf(Blocks.COBBLESTONE)) {
            if (world.getBlockState(block).isOf(Blocks.SAND) || world.getBlockState(block).isOf(Blocks.RED_SAND))
                world.setBlockState(block, Blocks.SANDSTONE.getDefaultState());
            else
                world.setBlockState(block, Blocks.COBBLESTONE.getDefaultState());
        } else if (amplifier > 0)
            world.setBlockState(block, Blocks.BEDROCK.getDefaultState());

    }

    @Override
    public boolean isBlockNonApplicable(ServerWorld world, BlockPos block) {
        return world.getBlockState(block).isOf(Blocks.BEDROCK);
    }
}
