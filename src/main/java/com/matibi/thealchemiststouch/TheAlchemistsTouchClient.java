package com.matibi.thealchemiststouch;

import com.matibi.thealchemiststouch.client.OreESP;
import com.matibi.thealchemiststouch.datacomponent.ModDataComponents;
import com.matibi.thealchemiststouch.entity.ModEntities;
import com.matibi.thealchemiststouch.particle.CloudEffectParticle;
import com.matibi.thealchemiststouch.particle.ModParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.component.DataComponentTypes;

public class TheAlchemistsTouchClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OreESP.init();
        ComponentTooltipAppenderRegistry.addAfter(
                DataComponentTypes.ENCHANTMENTS,
                ModDataComponents.IMBUED_EFFECT
        );
        EntityRendererRegistry.register(ModEntities.EFFECT_CLOUD, EmptyEntityRenderer::new);
        ParticleFactoryRegistry.getInstance().register(ModParticle.CLOUD_EFFECT, CloudEffectParticle.Factory::new);
    }

}