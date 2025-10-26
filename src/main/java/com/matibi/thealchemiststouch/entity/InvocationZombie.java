package com.matibi.thealchemiststouch.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

public class InvocationZombie extends ZombieEntity {
    private final PlayerEntity owner;

    public InvocationZombie(World world, PlayerEntity owner) {
        super(EntityType.ZOMBIE, world);
        this.owner = owner;

        maybeEquipArmor(EquipmentSlot.HEAD, Items.IRON_HELMET, Items.GOLDEN_HELMET, 0.85f);
        maybeEquipArmor(EquipmentSlot.CHEST, Items.IRON_CHESTPLATE, Items.GOLDEN_CHESTPLATE, 0.6f);
        maybeEquipArmor(EquipmentSlot.LEGS, Items.IRON_LEGGINGS, Items.GOLDEN_LEGGINGS, 0.6f);
        maybeEquipArmor(EquipmentSlot.FEET, Items.IRON_BOOTS, Items.GOLDEN_BOOTS, 0.6f);

        maybeEquip(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD), 0.8f);
    }

    private void maybeEquipArmor(EquipmentSlot slot, Item ironItem, Item goldItem, float chance) {
        ItemStack is = randomChance(0.5) ? new ItemStack(ironItem) : new ItemStack(goldItem);
        maybeEquip(slot, is, chance);
    }

    private void maybeEquip(EquipmentSlot slot, ItemStack stack, float chance) {
        if (randomChance(chance)) {
            setRandomDurability(stack);
            this.equipStack(slot, stack);
        }
    }

    private void setRandomDurability(ItemStack stack) {
        if (!stack.isDamageable()) return;

        int max = stack.getMaxDamage();
        int damage = (int) (max * ThreadLocalRandom.current().nextDouble(0.7, 0.9));
        stack.setDamage(damage);
    }

    private boolean randomChance(double probability) {
        return ThreadLocalRandom.current().nextDouble() < probability;
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
                (target, source) ->
                        target != owner
                                && (!(target instanceof InvocationZombie ally) || ally.getOwner() != owner)
        ));
    }

    public PlayerEntity getOwner() {
        return owner;
    }
}