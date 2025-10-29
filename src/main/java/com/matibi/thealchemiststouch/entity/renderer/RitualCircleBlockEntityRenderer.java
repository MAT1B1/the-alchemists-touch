package com.matibi.thealchemiststouch.entity.renderer;

import com.matibi.thealchemiststouch.block.entity.RitualCircleBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.*;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.*;
import org.jetbrains.annotations.Nullable;


@Environment(EnvType.CLIENT)
public class RitualCircleBlockEntityRenderer implements
        BlockEntityRenderer<RitualCircleBlockEntity, RitualCircleBlockEntityRenderer.RitualCircleRenderState> {

    private final ItemModelManager itemModelManager;

    public RitualCircleBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemModelManager = ctx.itemModelManager();
    }

    public static class RitualCircleRenderState extends BlockEntityRenderState {
        public ItemRenderState itemRenderState = null;
        public ItemStack itemStack = ItemStack.EMPTY;
        public Direction facing = Direction.NORTH;
    }

    @Override
    public RitualCircleRenderState createRenderState() {
        return new RitualCircleRenderState();
    }

    @Override
    public void updateRenderState(RitualCircleBlockEntity be,
                                  RitualCircleRenderState state,
                                  float tickProgress,
                                  Vec3d cameraPos,
                                  @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumble) {
        BlockEntityRenderState.updateBlockEntityRenderState(be, state, crumble);

        ItemStack stack = be.getStack(0);
        state.itemStack = stack;
        state.facing = be.getFacing();
        if (!stack.isEmpty()) {
            ItemRenderState itemRenderState = new ItemRenderState();
            itemModelManager.clearAndUpdate(
                    itemRenderState,
                    stack,
                    ItemDisplayContext.GROUND,
                    be.getWorld(),
                    null,
                    be.getPos().hashCode()
            );
            state.itemRenderState = itemRenderState;
        } else
            state.itemRenderState = null;
    }

    private boolean shouldRenderFlipped(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.HANGING_ROOTS
                || item == Items.WEEPING_VINES
                || item == Items.SPORE_BLOSSOM
                || item == Items.POINTED_DRIPSTONE;
    }

    @Override
    public void render(RitualCircleRenderState state,
                       MatrixStack matrices,
                       OrderedRenderCommandQueue queue,
                       CameraRenderState cameraState) {
        if (state.itemStack.isEmpty()) return;

        matrices.push();

        matrices.translate(0.5, 0.0, 0.5);

        Direction facing = state.facing;
        if (!facing.getAxis().isHorizontal())
            facing = Direction.NORTH;
        float yaw = Direction.getHorizontalDegreesOrThrow(facing);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-yaw));

        matrices.translate(-0.5, 0.0, -0.5);


        ItemStack stack = state.itemStack;
        Item item = stack.getItem();

        boolean renderAsBlock = renderAsBlock(state, stack);
        boolean flipRender = shouldRenderFlipped(stack);

        if (item instanceof BlockItem bi && bi.getBlock() instanceof WallMountedBlock) {
            matrices.translate(0.5, -0.2, 0.5);
            matrices.scale(1.2f, 1.2f, 1.2f);
            renderItem(state, matrices, queue);
        } else if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractBannerBlock) {
            matrices.translate(0.5f, 0.02f, 0.5f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
            matrices.translate(0.0f, -0.11f, 0.0f);
            renderItem(state, matrices, queue);
        } else if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof SkullBlock) {
            matrices.translate(0.5, 0.09, 0.5);
            matrices.scale(1.4f, 1.4f, 1.4f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            renderItem(state, matrices, queue);
        } else if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof CandleBlock)
            renderBlock(blockItem.getBlock().getDefaultState(), matrices, state);
        else if (item instanceof BedItem) {
            matrices.translate(0.5, 0.05, 0.5);
            renderItem(state, matrices, queue);
        } else if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof PointedDripstoneBlock) {
            matrices.scale(0.8f, 0.8f, 0.8f);
            matrices.translate(0.13, 0.0, 0.13);
            renderBlock(blockItem.getBlock().getDefaultState(), matrices, state);
        }

        else if (!renderAsBlock) {
            matrices.translate(0.5f, 0.02f, 0.5f);
            if (!flipRender) {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                matrices.translate(0.0f, -0.41f, 0.0f);
            }
            matrices.translate(0.0f, 0.3f, 0.0f);
            renderItem(state, matrices, queue);
        } else {
            matrices.translate(0.3f, 0f, 0.3f);
            matrices.scale(0.4f, 0.4f, 0.4f);
            if (flipRender) {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180f));
                matrices.translate(0f, -1f, -0.955f);
            }

            BlockRenderManager blockRenderer = MinecraftClient.getInstance().getBlockRenderManager();
            BlockState blockState = ((BlockItem) stack.getItem()).getBlock().getDefaultState();

            VertexConsumerProvider.Immediate vertexConsumers = MinecraftClient.getInstance()
                    .getBufferBuilders()
                    .getEntityVertexConsumers();

            blockRenderer.renderBlockAsEntity(
                    blockState,
                    matrices,
                    vertexConsumers,
                    state.lightmapCoordinates,
                    OverlayTexture.DEFAULT_UV
            );
            vertexConsumers.draw();
        }

        matrices.pop();
    }

    private static boolean renderAsBlock(RitualCircleRenderState state, ItemStack stack) {
        boolean renderAsBlock = false;
        if (stack.getItem() instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();

            if (!(block instanceof AbstractBannerBlock) && !(block instanceof SkullBlock)
                && stack.getItem() != Items.POINTED_DRIPSTONE && !(block instanceof BedBlock)
                    && !(block instanceof AbstractCandleBlock))
                if (state.itemRenderState != null && state.itemRenderState.isSideLit())
                    renderAsBlock = true;
        }
        return renderAsBlock;
    }

    private void renderItem(RitualCircleRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue) {
        if (state.itemRenderState != null) {
            state.itemRenderState.render(
                    matrices,
                    queue,
                    state.lightmapCoordinates,
                    OverlayTexture.DEFAULT_UV,
                    0
            );
        }
    }

    private void renderBlock(BlockState blockState, MatrixStack matrices, RitualCircleRenderState state) {
        BlockRenderManager blockRenderer = MinecraftClient.getInstance().getBlockRenderManager();
        VertexConsumerProvider.Immediate vertexConsumers = MinecraftClient.getInstance()
                .getBufferBuilders()
                .getEntityVertexConsumers();

        blockRenderer.renderBlockAsEntity(
                blockState,
                matrices,
                vertexConsumers,
                state.lightmapCoordinates,
                OverlayTexture.DEFAULT_UV
        );
        vertexConsumers.draw();
    }


    @Override
    public boolean rendersOutsideBoundingBox() {
        return true;
    }
}