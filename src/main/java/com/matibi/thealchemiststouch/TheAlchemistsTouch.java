package com.matibi.thealchemiststouch;

import com.matibi.thealchemiststouch.client.modmenu.config.ModConfig;
import com.matibi.thealchemiststouch.command.ModCommands;
import com.matibi.thealchemiststouch.datacomponent.ModDataComponents;
import com.matibi.thealchemiststouch.effect.ModEffects;
import com.matibi.thealchemiststouch.entity.ModEntities;
import com.matibi.thealchemiststouch.event.ModEvent;
import com.matibi.thealchemiststouch.group.ModItemGroups;
import com.matibi.thealchemiststouch.item.ModItems;
import com.matibi.thealchemiststouch.loottable.ModLootTable;
import com.matibi.thealchemiststouch.network.ModNetworking;
import com.matibi.thealchemiststouch.particle.ModParticle;
import com.matibi.thealchemiststouch.potion.ModPotion;
import com.matibi.thealchemiststouch.recipe.ModRecipeSerializer;
import com.matibi.thealchemiststouch.item.alchemicalStone.ModAlchemicalStone;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheAlchemistsTouch implements ModInitializer {
	public static final String MOD_ID = "the-alchemists-touch";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModNetworking.init();
        ModPotion.register();
		ModEffects.register();
		ModItems.register();
		ModAlchemicalStone.register();
		ModRecipeSerializer.register();
        ModItemGroups.register();
        ModDataComponents.register();
        ModParticle.register();
        ModEntities.register();
        ModCommands.register();
        ModLootTable.register();
        ModEvent.register();

		// max stack des potions
		DefaultItemComponentEvents.MODIFY.register(context -> context.modify(item ->
                        item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION,
                (builder, item) -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 16)
        ));

        var cfgDir = FabricLoader.getInstance().getConfigDir().toFile();
        ModConfig.init(cfgDir);
	}
}