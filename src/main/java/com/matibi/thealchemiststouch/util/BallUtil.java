package com.matibi.thealchemiststouch.util;

import com.matibi.thealchemiststouch.effect.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BallUtil {
    public static void spawnFireball(PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient()
                || !player.hasStatusEffect(ModEffects.IGNITION)
                || player.hasStatusEffect(ModEffects.FROST)) return;

        Vec3d eyePos = player.getEyePos();
        Vec3d look = player.getRotationVec(1.0F).normalize();
        Vec3d velocity = look.multiply(20);

        FireballEntity fireball = new FireballEntity(world, player, velocity, 1);
        fireball.setPosition(eyePos.add(look.multiply(1.5)));

        world.spawnEntity(fireball);
    }

    public static void spawnSnowball(PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient()
                || !player.hasStatusEffect(ModEffects.FROST)
                || player.hasStatusEffect(ModEffects.IGNITION)) return;

        Vec3d eyePos = player.getEyePos();
        Vec3d look = player.getRotationVec(1.0F).normalize();

        SnowballEntity snowball = new SnowballEntity(world, player, new ItemStack(Items.SNOWBALL));
        snowball.setPosition(eyePos.add(look.multiply(1.5)));
        snowball.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 1.5F, 1.0F);

        world.spawnEntity(snowball);
    }

}
