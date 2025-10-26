package com.matibi.thealchemiststouch;

import com.matibi.thealchemiststouch.block.ModBlocks;
import com.matibi.thealchemiststouch.block.entity.ModBlockEntities;
import com.matibi.thealchemiststouch.client.OreESP;
import com.matibi.thealchemiststouch.datacomponent.ModDataComponents;
import com.matibi.thealchemiststouch.entity.renderer.RitualCircleBlockEntityRenderer;
import com.matibi.thealchemiststouch.network.ModNetworkingClient;
import com.matibi.thealchemiststouch.screen.ModScreenHandlers;
import com.matibi.thealchemiststouch.screen.RitualCircleScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.component.DataComponentTypes;

public class TheAlchemistsTouchClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OreESP.init();
        ModNetworkingClient.init();

        ComponentTooltipAppenderRegistry.addAfter(
                DataComponentTypes.ENCHANTMENTS,
                ModDataComponents.IMBUED_EFFECT
        );

        ComponentTooltipAppenderRegistry.addFirst(ModDataComponents.BLOOD_TYPE);
        BlockRenderLayerMap.putBlock(ModBlocks.RITUAL_CIRCLE, BlockRenderLayer.CUTOUT);
        BlockEntityRendererFactories.register(ModBlockEntities.RITUAL_CIRCLE_BE, RitualCircleBlockEntityRenderer::new);

        HandledScreens.register(ModScreenHandlers.RITUAL_CIRCLE_SCREEN_HANDLER, RitualCircleScreen::new);
    }

}