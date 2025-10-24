package com.matibi.thealchemiststouch.block.entity;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.network.RitualCircleSyncS2CPayload;
import com.matibi.thealchemiststouch.ritual.RitualManager;
import com.matibi.thealchemiststouch.ritual.RitualRecipe;
import com.matibi.thealchemiststouch.ritual.RitualSettings;
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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RitualCircleBlockEntity extends BlockEntity
        implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int bloodAmount = 0;
    private static final int MAX_BLOOD = 100;

    private int progress = 0;
    private int maxProgress = 0;
    private boolean isRunning = false;
    private RitualRecipe currentRecipe = null;

    public RitualCircleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RITUAL_CIRCLE_BE, pos, state);
    }

    // =============================================
    // ================ TICK LOGIC =================
    // =============================================
    public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, T blockEntity) {
        if (!(blockEntity instanceof RitualCircleBlockEntity be)) return;

        // --- Client-side: show particles only ---
        if (world.isClient()) {
            if (be.isRunning()) {
                double x = pos.getX() + 0.5;
                double y = pos.getY() + 0.1;
                double z = pos.getZ() + 0.5;
                world.addParticleClient(
                        net.minecraft.particle.ParticleTypes.ENCHANT,
                        x + world.getRandom().nextGaussian() * 0.2,
                        y,
                        z + world.getRandom().nextGaussian() * 0.2,
                        0, 0.02, 0
                );
            }
            return;
        }

        // --- Server-side logic ---
        if (be.isRunning()) {
            be.progress++;
            if (be.progress >= be.maxProgress)
                be.finishRitual((ServerWorld) world);
            return;
        }

        if (be.getBlood() > 0 && !be.getStack(0).isEmpty())
            be.tryStartRitual((ServerWorld) world, pos);
    }

    private void tryStartRitual(ServerWorld world, BlockPos pos) {
        ItemStack input = inventory.getFirst();
        if (input.isEmpty()) return;

        Optional<RitualRecipe> opt = RitualManager.findRitual(input);
        if (opt.isEmpty()) return;

        RitualRecipe recipe = opt.get();
        RitualSettings settings = recipe.getSettings();

        if (conditionMet(world, settings, pos)) {
            TheAlchemistsTouch.LOGGER.info("Ritual started: {}", recipe.getId());
            this.isRunning = true;
            this.currentRecipe = recipe;
            this.progress = 0;
            this.maxProgress = settings.duration();

            world.playSound(null, pos,
                    SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE,
                    SoundCategory.BLOCKS, 1f, 1.2f);
        }
    }

    private boolean conditionMet(ServerWorld world, RitualSettings settings, BlockPos pos) {
        if (!consumeBlood(settings.bloodCost()))
            return false;

        if (settings.matchesEnvironment(world, pos))
            return false;

        if (settings.time() != -1) {
            long time = world.getTimeOfDay() % 24000L;
            return Math.abs(time - settings.time()) <= 20 * 30;
        }

        return true;
    }


    private void finishRitual(ServerWorld world) {
        if (currentRecipe == null) return;
        RitualSettings settings = currentRecipe.getSettings();

        RitualManager.perform(world, pos, currentRecipe);

        if (settings.consumeItem())
            inventory.set(0, ItemStack.EMPTY);

        world.playSound(null, pos,
                SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL,
                SoundCategory.BLOCKS, 1f, 1.1f);

        world.spawnParticles(
                net.minecraft.particle.ParticleTypes.EXPLOSION,
                pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                5, 0, 0, 0, 0.1
        );

        if (bloodAmount <= 0) {
            world.playSound(null, pos,
                    SoundEvents.BLOCK_FIRE_EXTINGUISH,
                    SoundCategory.BLOCKS, 1f, 0.8f);

            world.spawnParticles(
                    net.minecraft.particle.ParticleTypes.SMOKE,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    10, 0.3, 0.3, 0.3, 0.02
            );

            world.breakBlock(pos, false);
            return;
        }

        this.isRunning = false;
        this.currentRecipe = null;
        this.progress = 0;
        markDirty();
        syncToClient();
    }

    // =============================================
    // =============== DATA & SYNC =================
    // =============================================

    public boolean isRunning() { return isRunning; }
    public float getProgressPercent() { return maxProgress == 0 ? 0 : (float) progress / maxProgress; }

    @Override
    public DefaultedList<ItemStack> getItems() { return inventory; }

    @Override
    public boolean isEmpty() {
        return ImplementedInventory.super.isEmpty() || inventory.getFirst().isEmpty();
    }

    public int getBlood() { return bloodAmount; }
    public void setBlood(int amount) {
        this.bloodAmount = Math.min(amount, MAX_BLOOD);
        markDirty();
    }

    public void addBlood(int amount) { setBlood(this.bloodAmount + amount); }

    public boolean consumeBlood(int amount) {
        if (this.bloodAmount >= amount) {
            this.bloodAmount -= amount;
            markDirty();
            return true;
        }
        return false;
    }

    public int getMaxBlood() { return MAX_BLOOD; }
    public float getBloodPercent() { return (float) bloodAmount / MAX_BLOOD; }

    public void syncToClient() {
        if (!(world instanceof ServerWorld serverWorld)) return;
        RitualCircleSyncS2CPayload payload =
                new RitualCircleSyncS2CPayload(pos, inventory.getFirst(), bloodAmount);

        for (ServerPlayerEntity player : PlayerLookup.tracking(serverWorld, pos))
            ServerPlayNetworking.send(player, payload);
    }

    // =============================================
    // ================ SERIALISATION ===============
    // =============================================

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

    // =============================================
    // ================ UI / NETWORK ================
    // =============================================

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
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
