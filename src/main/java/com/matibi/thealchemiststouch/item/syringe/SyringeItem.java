package com.matibi.thealchemiststouch.item.syringe;

import com.matibi.thealchemiststouch.datacomponent.BloodType;
import com.matibi.thealchemiststouch.datacomponent.ModDataComponents;
import com.matibi.thealchemiststouch.item.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
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
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!(world instanceof ServerWorld sw)) return ActionResult.FAIL;
        ItemStack is = user.getStackInHand(hand);
        PotionContentsComponent pcc = is.get(DataComponentTypes.POTION_CONTENTS);

        if (pcc == null) return ActionResult.FAIL;

        user.damage(sw, world.getDamageSources().mobAttack(user), 1.0f);
        is.decrementUnlessCreative(1, user);

        if (pcc.hasEffects())
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

        else if (!user.getActiveStatusEffects().isEmpty()) {
            ItemStack nis = new ItemStack(ModItems.SYRINGE);
            List<StatusEffectInstance> effects = new ArrayList<>();

            var activeEffects = new ArrayList<>(user.getActiveStatusEffects().entrySet());

            for (var entry : activeEffects) {
                var type = entry.getKey();
                var instance = entry.getValue();

                int oldDuration = instance.getDuration();
                int maxTransfer = Math.min(oldDuration, 20 * 10);
                int remaining = Math.max(0, oldDuration - maxTransfer);

                user.removeStatusEffect(type);

                if (remaining > 0) {
                    user.addStatusEffect(new StatusEffectInstance(
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

            pcc = new PotionContentsComponent(
                    Optional.empty(),
                    Optional.empty(),
                    effects,
                    Optional.empty()
            );

            String translationKey = "item.the-alchemists-touch.syringe." +
                    effects.getFirst().getEffectType().value().getTranslationKey();
            nis.set(DataComponentTypes.CUSTOM_NAME,
                    Text.empty().append(Text.translatable(translationKey)).styled(style -> style.withItalic(false)));

            nis.set(DataComponentTypes.POTION_CONTENTS, pcc);
            user.giveOrDropStack(nis);
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
        stack.decrementUnlessCreative(1, attacker);

        if (pcc.hasEffects())
            pcc.getEffects().forEach(target::addStatusEffect);
        else if (target instanceof Monster) {
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
}
