package com.matibi.thealchemiststouch.item.rune;

import com.matibi.thealchemiststouch.effect.TerrainApplicableEffect;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.PotionItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class RuneItem extends PotionItem {

    public RuneItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient)
            user.sendMessage(Text.translatable("item.the-alchemists-touch.rune.block_only"), true);
        return ActionResult.FAIL;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        ItemStack stack = context.getStack();
        PlayerEntity user = context.getPlayer();

        if (!(world instanceof ServerWorld serverWorld)) return ActionResult.SUCCESS;

        PotionContentsComponent contents = stack.get(DataComponentTypes.POTION_CONTENTS);
        if (contents == null || !contents.hasEffects())
            return ActionResult.FAIL;

        for (StatusEffectInstance effect : contents.getEffects()) {
            StatusEffect effectType = effect.getEffectType().value();
            if (effectType instanceof TerrainApplicableEffect terrainEffect) {
                if (!terrainEffect.isBlockApplicable(serverWorld, pos) && user != null)
                    user.sendMessage(Text.translatable("item.the-alchemists-touch.rune.block_not_good"), true);
                else
                    terrainEffect.applyOnBlock(serverWorld, pos, effect.getDuration(), effect.getAmplifier());
            }
        }
        stack.decrementUnlessCreative(1, context.getPlayer());
        return ActionResult.SUCCESS;
    }
}