package com.matibi.thealchemiststouch.block.entity;

import com.matibi.thealchemiststouch.network.RitualCircleSyncS2CPayload;
import com.matibi.thealchemiststouch.screen.RitualCircleScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class RitualCircleBlockEntity extends BlockEntity
        implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int bloodAmount = 0;
    private static final int MAX_BLOOD = 10;

    public RitualCircleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RITUAL_CIRCLE_BE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public boolean isEmpty() {
        return ImplementedInventory.super.isEmpty() || inventory.getFirst() == ItemStack.EMPTY;
    }

    public int getBlood() {
        return bloodAmount;
    }

    public void setBlood(int amount) {
        this.bloodAmount = Math.min(amount, MAX_BLOOD);
        markDirty();
    }

    public void addBlood(int amount) {
        setBlood(this.bloodAmount + amount);
    }

    public boolean consumeBlood(int amount) {
        if (this.bloodAmount >= amount) {
            this.bloodAmount -= amount;
            markDirty();
            return true;
        }
        return false;
    }

    public int getMaxBlood() {
        return MAX_BLOOD;
    }

    public float getBloodPercent() {
        return (float) bloodAmount / MAX_BLOOD;
    }

    public void syncToClient() {
        if (!(world instanceof ServerWorld serverWorld)) return;
        RitualCircleSyncS2CPayload payload =
                new RitualCircleSyncS2CPayload(pos, inventory.getFirst(), bloodAmount);

        for (ServerPlayerEntity player : PlayerLookup.tracking(serverWorld, pos))
            ServerPlayNetworking.send(player, payload);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        Inventories.writeData(view, inventory);
        view.putInt("Blood", bloodAmount);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        Inventories.readData(view, inventory);
        this.bloodAmount = view.getInt("Blood", 0);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Ritual Circle");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new RitualCircleScreenHandler(syncId, playerInventory, this.pos);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
