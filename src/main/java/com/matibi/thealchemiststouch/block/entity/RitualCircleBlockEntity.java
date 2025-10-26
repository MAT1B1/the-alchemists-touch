package com.matibi.thealchemiststouch.block.entity;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import com.matibi.thealchemiststouch.network.RitualCircleSyncS2CPayload;
import com.matibi.thealchemiststouch.ritual.Ritual;
import com.matibi.thealchemiststouch.ritual.RitualRegistry;
import com.matibi.thealchemiststouch.screen.RitualCircleScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

public class RitualCircleBlockEntity extends BlockEntity
        implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int bloodAmount = 1; // initial amount
    private static final int MAX_BLOOD = 1000;

    private Ritual currentRitual = null;
    private int ritualTicks = 0;
    private boolean isPerforming = false;


    public RitualCircleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RITUAL_CIRCLE_BE, pos, state);
    }

    // =============================================
    // ================ RITUAL LOGIC =================
    // =============================================

    public void tryTriggerRitual(ServerWorld world, RitualCircleBlockEntity circle, PlayerEntity player) {
        for (Ritual ritual : RitualRegistry.RITUAL) {
            if (ritual.checkConditions(world, circle, player)) {
                if (circle.getBlood() < ritual.bloodCost() || player.experienceLevel < ritual.xpLvlCost())
                    continue;

                this.bloodAmount -= ritual.bloodCost();
                player.addExperienceLevels(-ritual.xpLvlCost());

                // Démarre le rituel
                this.currentRitual = ritual;
                this.ritualTicks = 0;
                this.isPerforming = true;

                markDirty();
                world.syncWorldEvent(WorldEvents.BREWING_STAND_BREWS, pos, 0);
                TheAlchemistsTouch.LOGGER.info("Rituel commencé : {}", ritual.getClass().getSimpleName());
                return;
            }
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, RitualCircleBlockEntity circle) {
        if (world.isClient() || !circle.isPerforming || circle.currentRitual == null) return;
        if (!(world instanceof ServerWorld serverWorld)) return;
        PlayerEntity ritualPlayer = serverWorld.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 6, false);
        if (ritualPlayer == null) {
            circle.currentRitual.onFailure(serverWorld, pos, null);
            circle.finishRitual();
            return;
        }

        // Vérifie si le rituel est encore valide
        if (!circle.currentRitual.checkConditions(serverWorld, circle, ritualPlayer)) {
            serverWorld.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    2.5f, World.ExplosionSourceType.BLOCK);
            circle.currentRitual.onFailure(serverWorld, pos, ritualPlayer);
            circle.finishRitual();
            return;
        }

        circle.ritualTicks++;

        float progress = (float) circle.ritualTicks / circle.currentRitual.duration();
        progress = Math.min(progress, 1f);

        circle.currentRitual.playEffects(serverWorld, pos, progress);

        if (circle.ritualTicks >= circle.currentRitual.duration()) {
            if (world.random.nextFloat() <= circle.currentRitual.successChance(serverWorld, ritualPlayer))
                circle.currentRitual.completeRitual(serverWorld, pos, ritualPlayer);
            else
                circle.currentRitual.onFailure(serverWorld, pos, ritualPlayer);

            if (circle.currentRitual.consumeItem())
                circle.inventory.getFirst().decrement(1);

            circle.finishRitual();
            serverWorld.syncWorldEvent(WorldEvents.END_PORTAL_FRAME_FILLED, pos, 0);

            if (circle.getBlood() <= 0) {
                serverWorld.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
                serverWorld.removeBlock(pos, false);
                serverWorld.playSound(
                        null,
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        SoundEvents.ENTITY_GENERIC_EXPLODE,
                        SoundCategory.BLOCKS,
                        0.8f, 1.2f
                );
                TheAlchemistsTouch.LOGGER.warn("Le cercle rituel s'est désintégré (plus de sang).");
            }

            TheAlchemistsTouch.LOGGER.info("Rituel terminé !");
        }
    }

    private void finishRitual() {
        this.isPerforming = false;
        this.currentRitual = null;
        this.ritualTicks = 0;

        syncToClient();
        markDirty();
    }


    // =============================================
    // =============== DATA & SYNC =================
    // =============================================

    @Override
    public DefaultedList<ItemStack> getItems() { return inventory; }

    public Item getIngredient() { return inventory.getFirst().getItem();}

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

    public int getMaxBlood() { return MAX_BLOOD; }

    public void syncToClient() {
        if (!(world instanceof ServerWorld serverWorld)) return;
        RitualCircleSyncS2CPayload payload =
                new RitualCircleSyncS2CPayload(pos, inventory.getFirst(), bloodAmount);

        for (ServerPlayerEntity player : PlayerLookup.tracking(serverWorld, pos))
            ServerPlayNetworking.send(player, payload);
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (!this.isPerforming && world instanceof ServerWorld serverWorld && !inventory.getFirst().isEmpty()) {
            PlayerEntity nearest = serverWorld.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 6, false);
            if (nearest != null)
                tryTriggerRitual(serverWorld, this, nearest);
        }
    }


    // =============================================
    // ================ SERIALISATION ===============
    // =============================================

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        Inventories.writeData(view, inventory);
        view.putInt("Blood", bloodAmount);

        // Sauvegarde de l'état du rituel
        view.putBoolean("IsPerforming", isPerforming);
        if (currentRitual != null) {
            Identifier id = RitualRegistry.RITUAL.getId(currentRitual);
            if (id != null)
                view.putString("CurrentRitual", id.toString());
            view.putInt("RitualTicks", ritualTicks);
        }
    }


    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        Inventories.readData(view, inventory);
        this.bloodAmount = view.getInt("Blood", 0);

        this.isPerforming = view.getBoolean("IsPerforming", false);
        if (isPerforming) {
            String id = view.getString("CurrentRitual", "");
            currentRitual = RitualRegistry.RITUAL.get(Identifier.tryParse(id));
            this.ritualTicks = view.getInt("RitualTicks", 0);
        }
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
