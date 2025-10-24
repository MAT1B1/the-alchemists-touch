package com.matibi.thealchemiststouch.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Optional;

public class RitualSettings {
    private final int duration;
    private final int bloodCost;
    private final boolean consumeItem;
    private final int time;
    private final Identifier requiredBiome;
    private final WeatherRequirement weather;
    private final Block requiredBelow;
    private final int minY;
    private final int maxY;
    private final Identifier requiredDimension;
    private final Identifier nearbyEntityId;
    private final int entityRadius;
    private final int minPlayersNearby;

    public static final int MORNING = 0;
    public static final int DAY = 6000;
    public static final int EVENING = 12000;
    public static final int MIDNIGHT = 18000;

    public RitualSettings(
            int duration,
            int bloodCost,
            boolean consumeItem,
            int time,
            Identifier requiredBiome,
            WeatherRequirement weather,
            Block requiredBelow,
            int minY,
            int maxY,
            Identifier requiredDimension,
            Identifier nearbyEntity,
            int entityRadius,
            int minPlayersNearby
    ) {
        this.duration = duration;
        this.bloodCost = bloodCost;
        this.consumeItem = consumeItem;
        this.time = time;
        this.requiredBiome = requiredBiome;
        this.weather = weather;
        this.requiredBelow = requiredBelow;
        this.minY = minY;
        this.maxY = maxY;
        this.requiredDimension = requiredDimension;
        this.nearbyEntityId = nearbyEntity;
        this.entityRadius = entityRadius;
        this.minPlayersNearby = minPlayersNearby;
    }

    // === Getters ===
    public int duration() { return duration; }
    public int bloodCost() { return bloodCost; }
    public boolean consumeItem() { return consumeItem; }
    public int time() { return time; }
    public Identifier requiredBiome() { return requiredBiome; }
    public WeatherRequirement weather() { return weather; }
    public Block requiredBelow() { return requiredBelow; }
    public int minY() { return minY; }
    public int maxY() { return maxY; }
    public Identifier requiredDimension() { return requiredDimension; }
    public Identifier nearbyEntity() { return nearbyEntityId; }
    public int entityRadius() { return entityRadius; }
    public int minPlayersNearby() { return minPlayersNearby; }

    // === Builder ===
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private int duration = 20 * 5;
        private int bloodCost = 1;
        private boolean consumeItem = true;
        private int time = -1;
        private Identifier requiredBiome = null;
        private WeatherRequirement weather = WeatherRequirement.ANY;
        private Block requiredBelow = Blocks.AIR;
        private int minY = -64;
        private int maxY = 320;
        private Identifier requiredDimension = null;
        private Identifier nearbyEntityId = null;
        private int entityRadius = 5;
        private int minPlayersNearby = 0;

        public Builder duration(int ticks) { this.duration = ticks; return this; }
        public Builder bloodCost(int cost) { this.bloodCost = cost; return this; }
        public Builder consumeItem(boolean value) { this.consumeItem = value; return this; }
        public Builder time(int time) { this.time = time; return this; }
        public Builder biome(Identifier biomeId) { this.requiredBiome = biomeId; return this; }
        public Builder weather(WeatherRequirement weather) { this.weather = weather; return this; }
        public Builder blockBelow(Block block) { this.requiredBelow = block; return this; }
        public Builder minY(int y) { this.minY = y; return this; }
        public Builder maxY(int y) { this.maxY = y; return this; }
        public Builder dimension(Identifier dim) { this.requiredDimension = dim; return this; }
        public Builder nearbyEntity(Identifier id, int radius) { this.nearbyEntityId = id; this.entityRadius = radius; return this; }
        public Builder minPlayersNearby(int count) { this.minPlayersNearby = count; return this; }

        public RitualSettings build() {
            return new RitualSettings(duration, bloodCost, consumeItem, time, requiredBiome,
                    weather, requiredBelow, minY, maxY, requiredDimension, nearbyEntityId,
                    entityRadius, minPlayersNearby);
        }
    }

    // === Enum météo ===
    public enum WeatherRequirement {
        ANY, CLEAR, RAIN, THUNDER;

        public boolean matches(World world) {
            return switch (this) {
                case ANY -> true;
                case CLEAR -> !world.isRaining() && !world.isThundering();
                case RAIN -> world.isRaining() && !world.isThundering();
                case THUNDER -> world.isThundering();
            };
        }
    }

    // === Vérification environnement ===
    public boolean matchesEnvironment(World world, BlockPos pos) {
        // météo
        if (!weather.matches(world)) return false;

        // biome
        if (requiredBiome != null) {
            Identifier current = world.getBiome(pos).getKey().map(RegistryKey::getValue).orElse(null);
            if (current == null || !current.equals(requiredBiome))
                return false;
        }

        // bloc en dessous
        if (requiredBelow != Blocks.AIR) {
            Block below = world.getBlockState(pos.down()).getBlock();
            if (below != requiredBelow)
                return false;
        }

        // altitude
        int y = pos.getY();
        if (y < minY || y > maxY)
            return false;

        // dimension
        if (requiredDimension != null) {
            Identifier currentDim = world.getRegistryKey().getValue();
            if (!currentDim.equals(requiredDimension))
                return false;
        }

        // entité proche
        if (nearbyEntityId != null) {
            EntityType<?> type = Registries.ENTITY_TYPE.get(nearbyEntityId);
            if (world.getEntitiesByType(type, new Box(pos).expand(entityRadius), e -> true).isEmpty())
                return false;
        }

        // joueurs proches
        if (minPlayersNearby > 0) {
            long players = world.getPlayers().stream()
                    .filter(p -> p.squaredDistanceTo(pos.toCenterPos()) < entityRadius * entityRadius)
                    .count();
            return players >= minPlayersNearby;
        }

        return true;
    }

    // === Sérialisation ===
    public static final MapCodec<RitualSettings> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("duration").forGetter(RitualSettings::duration),
            Codec.INT.fieldOf("blood_cost").forGetter(RitualSettings::bloodCost),
            Codec.BOOL.fieldOf("consume_item").forGetter(RitualSettings::consumeItem),
            Codec.INT.fieldOf("require_time").forGetter(RitualSettings::time),
            Identifier.CODEC.optionalFieldOf("biome")
                    .xmap(opt -> opt.orElse(null), Optional::ofNullable).forGetter(RitualSettings::requiredBiome),
            Codec.STRING.xmap(s -> WeatherRequirement.valueOf(s.toUpperCase()), w -> w.name().toLowerCase())
                    .optionalFieldOf("weather", WeatherRequirement.ANY).forGetter(RitualSettings::weather),
            Registries.BLOCK.getCodec()
                    .optionalFieldOf("required_block")
                    .xmap(opt -> opt.orElse(Blocks.AIR), b -> b == Blocks.AIR ? Optional.empty() : Optional.of(b))
                    .forGetter(RitualSettings::requiredBelow),
            Codec.INT.fieldOf("min_y").orElse(-64).forGetter(RitualSettings::minY),
            Codec.INT.fieldOf("max_y").orElse(320).forGetter(RitualSettings::maxY),
            Identifier.CODEC.optionalFieldOf("dimension")
                    .xmap(opt -> opt.orElse(null), Optional::ofNullable)
                    .forGetter(RitualSettings::requiredDimension),
            Identifier.CODEC.optionalFieldOf("nearby_entity")
                    .xmap(opt -> opt.orElse(null), Optional::ofNullable)
                    .forGetter(RitualSettings::nearbyEntity),
            Codec.INT.fieldOf("entity_radius").orElse(5).forGetter(RitualSettings::entityRadius),
            Codec.INT.fieldOf("min_players_nearby").orElse(0).forGetter(RitualSettings::minPlayersNearby)
    ).apply(instance, RitualSettings::new));

    public static final PacketCodec<RegistryByteBuf, RitualSettings> PACKET_CODEC =
            PacketCodec.ofStatic(RitualSettings::write, RitualSettings::read);

    private static RitualSettings read(RegistryByteBuf buf) {
        int duration = buf.readInt();
        int bloodCost = buf.readInt();
        boolean consumeItem = buf.readBoolean();
        int time = buf.readInt();
        Identifier biome = buf.readOptional(Identifier.PACKET_CODEC).orElse(null);
        WeatherRequirement weather = buf.readEnumConstant(WeatherRequirement.class);
        Identifier blockId = buf.readOptional(Identifier.PACKET_CODEC).orElse(Identifier.of("minecraft:air"));
        Block block = Registries.BLOCK.get(blockId);
        int minY = buf.readInt();
        int maxY = buf.readInt();
        Identifier dim = buf.readOptional(Identifier.PACKET_CODEC).orElse(null);
        Identifier entityId = buf.readOptional(Identifier.PACKET_CODEC).orElse(null);
        int entityRadius = buf.readInt();
        int minPlayersNearby = buf.readInt();

        return new RitualSettings(duration, bloodCost, consumeItem, time, biome, weather, block,
                minY, maxY, dim, entityId, entityRadius, minPlayersNearby);
    }

    private static void write(RegistryByteBuf buf, RitualSettings s) {
        buf.writeInt(s.duration());
        buf.writeInt(s.bloodCost());
        buf.writeBoolean(s.consumeItem());
        buf.writeInt(s.time());
        buf.writeOptional(Optional.ofNullable(s.requiredBiome()), Identifier.PACKET_CODEC);
        buf.writeEnumConstant(s.weather());
        buf.writeOptional(Optional.of(Registries.BLOCK.getId(s.requiredBelow())), Identifier.PACKET_CODEC);
        buf.writeInt(s.minY());
        buf.writeInt(s.maxY());
        buf.writeOptional(Optional.ofNullable(s.requiredDimension()), Identifier.PACKET_CODEC);
        buf.writeOptional(Optional.ofNullable(s.nearbyEntityId), Identifier.PACKET_CODEC);
        buf.writeInt(s.entityRadius());
        buf.writeInt(s.minPlayersNearby());
    }
}