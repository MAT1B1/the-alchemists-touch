package com.matibi.thealchemiststouch.screen;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class RitualCircleScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    private int blood = 0;
    private int maxBlood = 1000;

    public RitualCircleScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, playerInventory.player.getEntityWorld().getBlockEntity(pos));
    }

    public RitualCircleScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity) {
        super(ModScreenHandlers.RITUAL_CIRCLE_SCREEN_HANDLER, syncId);

        this.inventory = (Inventory) blockEntity;

        if (blockEntity instanceof com.matibi.thealchemiststouch.block.entity.RitualCircleBlockEntity circle) {
            this.blood = circle.getBlood();
            this.maxBlood = circle.getMaxBlood();
        }

        this.addSlot(new Slot(inventory, 0, 59, 36) {
            @Override public int getMaxItemCount() { return 1; }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    public int getBlood() { return blood; }
    public int getMaxBlood() { return maxBlood; }
    public void syncBlood(int blood, int max) { this.blood = blood; this.maxBlood = max; }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        if (invSlot < 0 || invSlot >= this.slots.size()) return ItemStack.EMPTY;

        Slot slot = this.slots.get(invSlot);
        if (!slot.hasStack()) return ItemStack.EMPTY;

        ItemStack originalStack = slot.getStack();
        ItemStack newStack = originalStack.copy();

        int beSlots = 1;

        if (invSlot < beSlots) {
            if (!this.insertItem(originalStack, beSlots, this.slots.size(), true))
                return ItemStack.EMPTY;
        } else {
            ItemStack oneItem = originalStack.copyWithCount(1);

            if (!this.insertItem(oneItem, 0, beSlots, false))
                return ItemStack.EMPTY;

            originalStack.decrement(1);
        }

        if (originalStack.isEmpty())
            slot.setStack(ItemStack.EMPTY);
        else
            slot.markDirty();

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
