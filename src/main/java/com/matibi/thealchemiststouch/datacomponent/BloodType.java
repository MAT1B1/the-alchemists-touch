package com.matibi.thealchemiststouch.datacomponent;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public record BloodType(String bloodType) implements TooltipAppender {

    public static final BloodType UNKNOWN = new BloodType("unknown");
    public static final BloodType HUMAN = new BloodType("human");
    public static final BloodType MONSTER = new BloodType("monster");

    public static final Codec<BloodType> CODEC =
            Codec.STRING.xmap(BloodType::new, BloodType::bloodType);

    public static final PacketCodec<RegistryByteBuf, BloodType> PACKET_CODEC =
            PacketCodecs.STRING.xmap(BloodType::new, BloodType::bloodType)
                    .cast();

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        textConsumer.accept(Text.translatable("the-alchemists-touch.blood_type." + bloodType)
                .formatted(Formatting.DARK_RED));
    }

}
