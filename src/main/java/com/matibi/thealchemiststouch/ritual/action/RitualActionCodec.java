package com.matibi.thealchemiststouch.ritual.action;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class RitualActionCodec {

    private static final Codec<GiveItemAction> GIVE_ITEM_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ItemStack.CODEC.fieldOf("item").forGetter(GiveItemAction::getStack)
            ).apply(instance, GiveItemAction::new)
    );

    private static final Codec<SpawnEntityAction> SPAWN_ENTITY_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Identifier.CODEC.fieldOf("entity").forGetter(a ->
                            Registries.ENTITY_TYPE.getId(a.getType()))
            ).apply(instance, id -> new SpawnEntityAction(Registries.ENTITY_TYPE.get(id)))
    );

    private static final Map<String, Codec<? extends RitualAction>> CODECS = new HashMap<>();
    static {
        CODECS.put("give_item", GIVE_ITEM_CODEC);
        CODECS.put("spawn_entity", SPAWN_ENTITY_CODEC);
    }

    public static final Codec<RitualAction> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("type").forGetter(RitualActionCodec::getTypeName)
            ).apply(instance, type -> {
                Codec<? extends RitualAction> codec = CODECS.get(type);
                if (codec == null) throw new IllegalStateException("Unknown ritual action type: " + type);
                // On retourne un objet vide par défaut (sera rechargé par le JSON complet)
                return switch (type) {
                    case "give_item" -> new GiveItemAction(ItemStack.EMPTY);
                    case "spawn_entity" -> new SpawnEntityAction(EntityType.PIG);
                    default -> throw new IllegalStateException("Unknown ritual action type: " + type);
                };
            })
    );

    private static String getTypeName(RitualAction action) {
        if (action instanceof GiveItemAction) return "give_item";
        if (action instanceof SpawnEntityAction) return "spawn_entity";
        return "unknown";
    }

    public static final PacketCodec<RegistryByteBuf, RitualAction> PACKET_CODEC =
            PacketCodec.ofStatic((buf, action) -> {
                if (action instanceof GiveItemAction gi) {
                    buf.writeString("give_item");
                    ItemStack.PACKET_CODEC.encode(buf, gi.getStack());
                } else if (action instanceof SpawnEntityAction se) {
                    buf.writeString("spawn_entity");
                    buf.writeIdentifier(Registries.ENTITY_TYPE.getId(se.getType()));
                }
            }, buf -> {
                String type = buf.readString();
                return switch (type) {
                    case "give_item" -> new GiveItemAction(ItemStack.PACKET_CODEC.decode(buf));
                    case "spawn_entity" -> new SpawnEntityAction(
                            Registries.ENTITY_TYPE.get(buf.readIdentifier()));
                    default -> throw new IllegalStateException("Unknown ritual action: " + type);
                };
            });
}
