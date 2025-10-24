package com.matibi.thealchemiststouch.entity.renderer;

import com.matibi.thealchemiststouch.block.entity.RitualCircleBlockEntity;
import com.matibi.thealchemiststouch.item.ModItems;
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
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class RitualCircleBlockEntityRenderer implements
        BlockEntityRenderer<RitualCircleBlockEntity, RitualCircleBlockEntityRenderer.RitualCircleRenderState> {

    private final ItemModelManager itemModelManager;

    private static final Set<Item> ITEM_RENDER = new HashSet<>();
    private static final Set<Item> FLIP_RENDER = new HashSet<>();

    static {
        FLIP_RENDER.addAll(List.of(
                Items.HANGING_ROOTS, Items.PALE_HANGING_MOSS, Items.SPORE_BLOSSOM,
                Items.WEEPING_VINES,

                Items.COPPER_GOLEM_STATUE, Items.EXPOSED_COPPER_GOLEM_STATUE,
                Items.OXIDIZED_COPPER_GOLEM_STATUE, Items.WEATHERED_COPPER_GOLEM_STATUE,
                Items.WAXED_COPPER_GOLEM_STATUE, Items.WAXED_EXPOSED_COPPER_GOLEM_STATUE,
                Items.WAXED_OXIDIZED_COPPER_GOLEM_STATUE, Items.WAXED_WEATHERED_COPPER_GOLEM_STATUE
        ));

        ITEM_RENDER.addAll(List.of(
                Items.REDSTONE, Items.LEVER, Items.TRIPWIRE_HOOK,
                Items.BELL, Items.IRON_CHAIN, Items.FROGSPAWN,
                Items.GLOW_LICHEN, Items.LADDER, Items.STRING,
                Items.SWEET_BERRIES, Items.GLOW_BERRIES, ModItems.BLOOD_BAG,
                Items.RED_MUSHROOM, Items.BROWN_MUSHROOM, Items.LILY_PAD,

                Items.COPPER_GOLEM_STATUE, Items.EXPOSED_COPPER_GOLEM_STATUE,
                Items.OXIDIZED_COPPER_GOLEM_STATUE, Items.WEATHERED_COPPER_GOLEM_STATUE,
                Items.WAXED_COPPER_GOLEM_STATUE, Items.WAXED_EXPOSED_COPPER_GOLEM_STATUE,
                Items.WAXED_OXIDIZED_COPPER_GOLEM_STATUE, Items.WAXED_WEATHERED_COPPER_GOLEM_STATUE,

                Items.RAIL, Items.ACTIVATOR_RAIL,
                Items.DETECTOR_RAIL, Items.POWERED_RAIL,

                Items.OAK_DOOR, Items.SPRUCE_DOOR, Items.BIRCH_DOOR,
                Items.JUNGLE_DOOR, Items.ACACIA_DOOR, Items.DARK_OAK_DOOR,
                Items.MANGROVE_DOOR, Items.CHERRY_DOOR, Items.BAMBOO_DOOR,
                Items.PALE_OAK_DOOR, Items.IRON_DOOR,

                Items.OAK_SIGN, Items.SPRUCE_SIGN, Items.BIRCH_SIGN, Items.JUNGLE_SIGN,
                Items.ACACIA_SIGN, Items.DARK_OAK_SIGN, Items.MANGROVE_SIGN,
                Items.CHERRY_SIGN, Items.BAMBOO_SIGN, Items.PALE_OAK_SIGN,

                Items.OAK_HANGING_SIGN, Items.SPRUCE_HANGING_SIGN, Items.BIRCH_HANGING_SIGN, Items.JUNGLE_HANGING_SIGN,
                Items.ACACIA_HANGING_SIGN, Items.DARK_OAK_HANGING_SIGN, Items.MANGROVE_HANGING_SIGN,
                Items.CHERRY_HANGING_SIGN, Items.BAMBOO_HANGING_SIGN, Items.PALE_OAK_HANGING_SIGN,

                Items.CANDLE, Items.WHITE_CANDLE, Items.ORANGE_CANDLE, Items.MAGENTA_CANDLE,
                Items.LIGHT_BLUE_CANDLE, Items.YELLOW_CANDLE, Items.LIME_CANDLE, Items.PINK_CANDLE,
                Items.GRAY_CANDLE, Items.LIGHT_GRAY_CANDLE, Items.CYAN_CANDLE, Items.PURPLE_CANDLE,
                Items.BLUE_CANDLE, Items.BROWN_CANDLE, Items.GREEN_CANDLE, Items.RED_CANDLE, Items.BLACK_CANDLE,

                Items.DANDELION, Items.POPPY, Items.BLUE_ORCHID,
                Items.ALLIUM, Items.AZURE_BLUET, Items.RED_TULIP,
                Items.ORANGE_TULIP, Items.WHITE_TULIP, Items.PINK_TULIP,
                Items.OXEYE_DAISY, Items.CORNFLOWER, Items.LILY_OF_THE_VALLEY,
                Items.WITHER_ROSE, Items.TORCHFLOWER, Items.PITCHER_PLANT,
                Items.SUNFLOWER, Items.LILAC, Items.ROSE_BUSH, Items.PEONY,

                Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.MELON_SEEDS,
                Items.PUMPKIN_SEEDS, Items.TORCHFLOWER_SEEDS, Items.PITCHER_POD,
                Items.CARROT, Items.POTATO, Items.POISONOUS_POTATO,
                Items.SWEET_BERRIES, Items.GLOW_BERRIES, Items.NETHER_WART,
                Items.COCOA_BEANS, Items.NETHER_WART
        ));
    }


    public RitualCircleBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemModelManager = ctx.itemModelManager();
    }

    public static class RitualCircleRenderState extends BlockEntityRenderState {
        public ItemRenderState itemRenderState = null;
        public ItemStack itemStack = ItemStack.EMPTY;
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

    @Override
    public void render(RitualCircleRenderState state,
                       MatrixStack matrices,
                       OrderedRenderCommandQueue queue,
                       CameraRenderState cameraState) {
        if (state.itemStack.isEmpty()) return;

        matrices.push();

        boolean isBlockItem = state.itemStack.getItem() instanceof BlockItem;
        boolean itemRender = ITEM_RENDER.contains(state.itemStack.getItem());
        boolean flipRender = FLIP_RENDER.contains(state.itemStack.getItem());

        if (!isBlockItem || itemRender) {
            matrices.translate(0.5f, 0.01f, 0.5f);
            if (!flipRender) {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                matrices.translate(0.0f, -0.41f, 0.0f);
            }
            matrices.translate(0.0f, 0.3f, 0.0f);

            if (state.itemRenderState != null) {
                state.itemRenderState.render(
                        matrices,
                        queue,
                        state.lightmapCoordinates,
                        OverlayTexture.DEFAULT_UV,
                        0
                );
            }
        } else {
            matrices.translate(0.3f, 0f, 0.3f);
            matrices.scale(0.4f, 0.4f, 0.4f);
            if (flipRender) {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180f));
                matrices.translate(0f, -1f, -0.955f);
            }


            var blockState = ((BlockItem) state.itemStack.getItem()).getBlock().getDefaultState();
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

        matrices.pop();
    }

    @Override
    public boolean rendersOutsideBoundingBox() {
        return true;
    }
}
