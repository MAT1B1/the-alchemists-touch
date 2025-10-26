package com.matibi.thealchemiststouch.block;

import com.matibi.thealchemiststouch.block.entity.ModBlockEntities;
import com.matibi.thealchemiststouch.block.entity.RitualCircleBlockEntity;
import com.matibi.thealchemiststouch.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class RitualCircleBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final VoxelShape SHAPE =
            Block.createCuboidShape(0, 0, 0, 16, 0.1, 16);
    public static final MapCodec<RitualCircleBlock> CODEC = createCodec(RitualCircleBlock::new);

    public RitualCircleBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RitualCircleBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) return ActionResult.SUCCESS;

        if (world.getBlockEntity(pos) instanceof RitualCircleBlockEntity circleEntity) {
            ItemStack circleStack = circleEntity.getStack(0);

            // --- Ouvrir GUI ---
            if (player.isInSneakingPose() || (circleEntity.isEmpty() && stack.isEmpty())) {
                player.openHandledScreen(circleEntity);
                return ActionResult.CONSUME;
            }

            // --- Utilisation du blood bag ---
            if (stack.getItem() == ModItems.BLOOD_BAG
                    && circleEntity.getMaxBlood() > circleEntity.getBlood() + 1) {
                circleEntity.addBlood(1);
                stack.decrement(1);
                syncAndSound(world, player, pos, state, circleEntity);
                return ActionResult.CONSUME;
            }

            // --- Placer un nouvel item ---
            if (circleEntity.isEmpty() && !stack.isEmpty()) {
                circleEntity.setStack(0, stack.copyWithCount(1));
                stack.decrement(1);
                syncAndSound(world, player, pos, state, circleEntity);
                return ActionResult.CONSUME;
            }

            // --- Récupérer l’item s’il n’y a rien dans la main ---
            if (stack.isEmpty() && !circleEntity.isEmpty()) {
                givePlayer(player, circleStack, hand);
                circleEntity.clear();
                syncAndSound(world, player, pos, state, circleEntity);
                return ActionResult.CONSUME;
            }

            // --- Fusionner avec le même item ---
            if (canCombine(stack, circleStack)) {
                int transferable = Math.min(stack.getMaxCount() - stack.getCount(), circleStack.getCount());
                if (transferable > 0) {
                    stack.increment(transferable);
                    circleStack.decrement(transferable);

                    if (circleStack.isEmpty()) {
                        circleEntity.clear();
                    }

                    syncAndSound(world, player, pos, state, circleEntity);
                    return ActionResult.CONSUME;
                }
            }

            // --- Échanger les deux items (comportement d’origine) ---
            if (!circleEntity.isEmpty()) {
                ItemStack oldCircleStack = circleStack.copy();
                circleEntity.setStack(0, stack.copyWithCount(1));
                stack.decrement(1);

                givePlayer(player, oldCircleStack, hand);
                syncAndSound(world, player, pos, state, circleEntity);
                return ActionResult.CONSUME;
            }
        }

        return ActionResult.SUCCESS;
    }

    private static boolean canCombine(ItemStack dest, ItemStack src) {
        return dest.getMaxCount() >= dest.getCount() + src.getCount()
                && dest.getItem() == src.getItem()
                && ItemStack.areItemsAndComponentsEqual(dest, src);
    }

    private static void givePlayer(PlayerEntity player, ItemStack itemStack, Hand hand) {
        if (player.getMainHandStack().isEmpty())
            player.setStackInHand(hand, itemStack);
        else if (!player.getInventory().insertStack(itemStack))
            player.giveOrDropStack(itemStack);
    }

    private static void syncAndSound(World world, @Nullable LivingEntity placer, BlockPos pos, BlockState state, RitualCircleBlockEntity be) {
        if (placer != null)
            world.playSound(placer, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 2f);

        be.markDirty();
        be.syncToClient();
        world.updateListeners(pos, state, state, 3);
        world.emitGameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Emitter.of(state));
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (!this.canPlaceAt(state, world, pos))
            world.breakBlock(pos, true);
        super.neighborUpdate(state, world, pos, sourceBlock, wireOrientation, notify);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos below = pos.down();
        BlockState support = world.getBlockState(below);
        return support.isSideSolidFullSquare(world, below, Direction.UP);
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
        if (state.getBlock() != world.getBlockState(pos).getBlock()) {
            if (world.getBlockEntity(pos) instanceof RitualCircleBlockEntity be) {
                ItemStack stack = be.getStack(0);
                if (!stack.isEmpty())
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), stack);
                world.removeBlockEntity(pos);
            }
        }

        super.onStateReplaced(state, world, pos, moved);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? null :
                validateTicker(type, ModBlockEntities.RITUAL_CIRCLE_BE, RitualCircleBlockEntity::tick);
    }

}
