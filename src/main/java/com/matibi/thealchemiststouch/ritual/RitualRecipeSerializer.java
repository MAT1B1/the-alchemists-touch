package com.matibi.thealchemiststouch.ritual;

import com.matibi.thealchemiststouch.ritual.action.RitualAction;
import com.matibi.thealchemiststouch.ritual.action.RitualActionCodec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.RecipeSerializer;

public class RitualRecipeSerializer implements RecipeSerializer<RitualRecipe> {
    public static final RitualRecipeSerializer INSTANCE = new RitualRecipeSerializer();

    // --- CODEC pour JSON/datapack ---
    public static final MapCodec<RitualRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStack.CODEC.fieldOf("ingredient").forGetter(RitualRecipe::getInput),
            RitualActionCodec.CODEC.fieldOf("action").forGetter(RitualRecipe::getAction),
            RitualSettings.CODEC.fieldOf("settings").forGetter(RitualRecipe::getSettings)
    ).apply(instance, (input, action, settings) ->
            new RitualRecipe(null, input, action, settings)
    ));

    // --- PacketCodec pour réseau ---
    public static final PacketCodec<RegistryByteBuf, RitualRecipe> PACKET_CODEC =
            PacketCodec.ofStatic(RitualRecipeSerializer::write, RitualRecipeSerializer::read);

    @Override
    public MapCodec<RitualRecipe> codec() {
        return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, RitualRecipe> packetCodec() {
        return PACKET_CODEC;
    }

    private static RitualRecipe read(RegistryByteBuf buf) {
        ItemStack input = ItemStack.PACKET_CODEC.decode(buf);
        RitualAction action = RitualActionCodec.PACKET_CODEC.decode(buf);
        RitualSettings settings = RitualSettings.PACKET_CODEC.decode(buf);
        return new RitualRecipe(null, input, action, settings);
    }

    private static void write(RegistryByteBuf buf, RitualRecipe recipe) {
        ItemStack.PACKET_CODEC.encode(buf, recipe.getInput());
        RitualActionCodec.PACKET_CODEC.encode(buf, recipe.getAction());
        RitualSettings.PACKET_CODEC.encode(buf, recipe.getSettings());
    }
}