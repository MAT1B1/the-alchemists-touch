package com.matibi.thealchemiststouch.mixin.brewingNpotion;

import com.matibi.thealchemiststouch.client.modmenu.config.ModConfig;
import com.matibi.thealchemiststouch.potion.ModPotion;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static java.lang.Math.min;

@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandBlockEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private static void injectCustomFuel(World world, BlockPos pos, BlockState state, BrewingStandBlockEntity blockEntity, CallbackInfo ci) {
        BrewingStandAccessor accessor = (BrewingStandAccessor) blockEntity;

        ItemStack fuelStack = accessor.getInventory().get(4);

        if (accessor.getFuel() < 20 && fuelStack.getItem() == Items.LAVA_BUCKET) {
            int new_fuel = min(accessor.getFuel() + 4, 20);
            accessor.setFuel(new_fuel);
            accessor.getInventory().set(4, new ItemStack(Items.BUCKET));
            world.updateListeners(pos, state, state, 3);
        }
    }

    @Inject(method = "craft", at = @At("TAIL"))
    private static void tat$swapDisabledBrew(World world, BlockPos pos, DefaultedList<ItemStack> slots, CallbackInfo ci) {
        for (int i = 0; i < 3; i++) {
            ItemStack stack = slots.get(i);
            if (stack.isEmpty() || !isAnyPotionItem(stack)) continue;

            PotionContentsComponent contents = stack.get(DataComponentTypes.POTION_CONTENTS);
            if (contents == null) continue;

            var optEntry = contents.potion();
            if (optEntry.isEmpty()) continue;

            Potion brewed = optEntry.get().value();

            if (ModConfig.isPotionDisabled(brewed))
                stack.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(ModPotion.UNSTABLE));
        }
    }

    @Unique
    private static boolean isAnyPotionItem(ItemStack stack) {
        return stack.isOf(Items.POTION) || stack.isOf(Items.SPLASH_POTION) || stack.isOf(Items.LINGERING_POTION);
    }
}
