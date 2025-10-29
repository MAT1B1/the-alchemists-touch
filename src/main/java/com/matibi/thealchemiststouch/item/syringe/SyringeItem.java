package com.matibi.thealchemiststouch.item.syringe;

import com.matibi.thealchemiststouch.datacomponent.BloodType;
import com.matibi.thealchemiststouch.datacomponent.ModDataComponents;
import com.matibi.thealchemiststouch.item.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SyringeItem extends Item {

    public SyringeItem(Settings settings) {
        super(settings.maxDamage(20));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!(world instanceof ServerWorld sw)) return ActionResult.FAIL;
        ItemStack is = user.getStackInHand(hand);
        PotionContentsComponent pcc = is.get(DataComponentTypes.POTION_CONTENTS);

        if (pcc == null) return ActionResult.FAIL;

        user.damage(sw, world.getDamageSources().mobAttack(user), 1.0f);
        is.damage(1, user, EquipmentSlot.MAINHAND);

        if (pcc.hasEffects()) {
            pcc.getEffects().forEach(instance -> {
                StatusEffectInstance statusEffect = user.getStatusEffect(instance.getEffectType());
                int dur = statusEffect == null ? 0 : statusEffect.getDuration();
                user.addStatusEffect(new StatusEffectInstance(
                        instance.getEffectType(),
                        instance.getDuration() + dur,
                        instance.getAmplifier(),
                        instance.isAmbient(),
                        instance.shouldShowParticles(),
                        instance.shouldShowIcon()
                ));
            });
            resetSyringeEffect(is);
        } else if (!user.getActiveStatusEffects().isEmpty()) {
            setSyringeWithEffect(is, user);
        } else {
            ItemStack nis = new ItemStack(ModItems.BLOOD_BAG);
            nis.set(ModDataComponents.BLOOD_TYPE, BloodType.HUMAN);
            user.giveOrDropStack(nis);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        PotionContentsComponent pcc = stack.get(DataComponentTypes.POTION_CONTENTS);

        if (pcc == null) return;
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);

        if (pcc.hasEffects()) {
            pcc.getEffects().forEach(target::addStatusEffect);
            resetSyringeEffect(stack);
        } else if (!target.getActiveStatusEffects().isEmpty()) {
            setSyringeWithEffect(stack, target);
        } else if (target instanceof Monster) {
            ItemStack nis = new ItemStack(ModItems.BLOOD_BAG);
            nis.set(ModDataComponents.BLOOD_TYPE, BloodType.MONSTER);
            attacker.giveOrDropStack(nis);
        } else if (target instanceof PlayerEntity || target instanceof VillagerEntity) {
            ItemStack nis = new ItemStack(ModItems.BLOOD_BAG);
            nis.set(ModDataComponents.BLOOD_TYPE, BloodType.HUMAN);
            attacker.giveOrDropStack(nis);
        } else
            attacker.giveOrDropStack(new ItemStack(ModItems.BLOOD_BAG));

        super.postHit(stack, target, attacker);
    }

    private void resetSyringeEffect(ItemStack is) {
        PotionContentsComponent pcc = new PotionContentsComponent(
                Optional.empty(),
                Optional.empty(),
                List.of(),
                Optional.empty()
        );
        is.set(DataComponentTypes.POTION_CONTENTS, pcc);
    }

    private void setSyringeWithEffect(ItemStack is, LivingEntity target) {
        List<StatusEffectInstance> effects = new ArrayList<>();

        var activeEffects = new ArrayList<>(target.getActiveStatusEffects().entrySet());

        for(var entry :activeEffects) {
            var type = entry.getKey();
            var instance = entry.getValue();

            int oldDuration = instance.getDuration();
            int maxTransfer = Math.min(oldDuration, 20 * 10);
            int remaining = Math.max(0, oldDuration - maxTransfer);

            target.removeStatusEffect(type);

            if (remaining > 0) {
                target.addStatusEffect(new StatusEffectInstance(
                        type,
                        remaining,
                        instance.getAmplifier(),
                        instance.isAmbient(),
                        instance.shouldShowParticles(),
                        instance.shouldShowIcon()
                ));
            }

            if (maxTransfer > 0) {
                effects.add(new StatusEffectInstance(
                        type,
                        maxTransfer,
                        instance.getAmplifier(),
                        instance.isAmbient(),
                        instance.shouldShowParticles(),
                        instance.shouldShowIcon()
                ));
            }
        }

        PotionContentsComponent pcc = new PotionContentsComponent(
                Optional.empty(),
                Optional.empty(),
                effects,
                Optional.empty()
        );

        String translationKey = "item.the-alchemists-touch.syringe." +
                effects.getFirst().getEffectType().value().getTranslationKey();
        is.set(DataComponentTypes.CUSTOM_NAME,
                Text.empty().append(Text.translatable(translationKey)).styled(style -> style.withItalic(false)));

        is.set(DataComponentTypes.POTION_CONTENTS, pcc);
    }
}
