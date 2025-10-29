package com.matibi.thealchemiststouch.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public record ImbuedEffect(RegistryEntry<StatusEffect> effect, int hitsRemaining, int amplifier)
        implements TooltipAppender {

    public static final Codec<ImbuedEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registries.STATUS_EFFECT.getEntryCodec().fieldOf("effect").forGetter(ImbuedEffect::effect),
            Codec.INT.fieldOf("hits").forGetter(ImbuedEffect::hitsRemaining),
            Codec.INT.optionalFieldOf("amp", 0).forGetter(ImbuedEffect::amplifier)
    ).apply(instance, ImbuedEffect::new));

    public static final PacketCodec<RegistryByteBuf, ImbuedEffect> PACKET_CODEC =
            PacketCodec.tuple(
                    PacketCodecs.registryEntry(RegistryKeys.STATUS_EFFECT), ImbuedEffect::effect,
                    PacketCodecs.VAR_INT, ImbuedEffect::hitsRemaining,
                    PacketCodecs.VAR_INT, ImbuedEffect::amplifier,
                    ImbuedEffect::new
            );

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer,
                              TooltipType type, ComponentsAccess components) {

        Formatting color = switch (this.effect().value().getCategory()) {
            case BENEFICIAL, NEUTRAL -> Formatting.BLUE;
            case HARMFUL    -> Formatting.RED;
        };

        MutableText effectName = Text.translatable(this.effect().value().getTranslationKey());
        if (this.amplifier() > 0) {
            effectName = effectName.append(Text.literal(" "))
                    .append(Text.translatable("potion.potency." + this.amplifier()));
        }
        textConsumer.accept(
                Text.translatable("tooltip.the-alchemists-touch.imbued_line",
                                effectName, this.hitsRemaining())
                        .formatted(color)
        );
    }
}
