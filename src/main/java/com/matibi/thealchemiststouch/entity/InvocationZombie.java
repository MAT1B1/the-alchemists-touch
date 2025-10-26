package com.matibi.thealchemiststouch.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class InvocationZombie extends ZombieEntity {
    private final PlayerEntity owner;

    public InvocationZombie(EntityType<? extends ZombieEntity> type, World world) {
        super(type, world);
        this.owner = null;
    }

    public InvocationZombie(World world, PlayerEntity owner) {
        super(EntityType.ZOMBIE, world);
        this.owner = owner;

        this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
        this.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
        this.equipStack(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
    }

    @Override
    protected void initCustomGoals() {
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2, true));

        this.targetSelector.add(1, new ActiveTargetGoal<>(
                this,
                LivingEntity.class,
                10,
                true,
                false,
                (target, source) -> target != owner
        ));
    }

    public PlayerEntity getOwner() {
        return owner;
    }
}
