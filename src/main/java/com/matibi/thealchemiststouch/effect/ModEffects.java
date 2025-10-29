    package com.matibi.thealchemiststouch.effect;

    import com.matibi.thealchemiststouch.TheAlchemistsTouch;
    import com.matibi.thealchemiststouch.effect.custom.*;
    import com.matibi.thealchemiststouch.effect.custom.permanent.PermanentSpeedEffect;
    import com.matibi.thealchemiststouch.effect.custom.permanent.PermanentStrengthEffect;
    import com.matibi.thealchemiststouch.effect.custom.permanent.PermanentHealthEffect;
    import com.matibi.thealchemiststouch.effect.custom.terrain.*;
    import net.minecraft.entity.effect.StatusEffect;
    import net.minecraft.registry.Registries;
    import net.minecraft.registry.Registry;
    import net.minecraft.registry.entry.RegistryEntry;
    import net.minecraft.util.Identifier;

    public class ModEffects {

        public static final RegistryEntry<StatusEffect> LONG_LEG = registerStatusEffects("long_leg",
                new LongLegEffect());

        public static final RegistryEntry<StatusEffect> LIQUID_WALKER = registerStatusEffects("liquid_walker",
                new LiquidWalkerEffect());

        public static final RegistryEntry<StatusEffect> ORE_SENSE = registerStatusEffects("ore_sense",
                new OreSenseEffect());

        public static final RegistryEntry<StatusEffect> RESONANCE = registerStatusEffects("resonance",
                new ResonanceEffect());

        public static final RegistryEntry<StatusEffect> REACTIVATION = registerStatusEffects("reactivation",
                new ReactivationEffect());

        public static final RegistryEntry<StatusEffect> PURIFICATION = registerStatusEffects("purification",
                new PurificationEffect());

        public static final RegistryEntry<StatusEffect> PETRIFICATION = registerStatusEffects("petrification",
                new PetrificationEffect());

        public static final RegistryEntry<StatusEffect> ACID = registerStatusEffects("acid",
                new AcidEffect());

        public static final RegistryEntry<StatusEffect> IGNITION = registerStatusEffects("ignition",
                new IgnitionEffect());

        public static final RegistryEntry<StatusEffect> TELEPORTATION = registerStatusEffects("teleportation",
                new TeleportationEffect());

        public static final RegistryEntry<StatusEffect> THORNS = registerStatusEffects("thorns",
                new ThornsEffect());

        public static final RegistryEntry<StatusEffect> BRAIN_WASHING = registerStatusEffects("brain_washing",
                new BrainwashingEffect());

        public static final RegistryEntry<StatusEffect> FROST = registerStatusEffects("frost",
                new FrostEffect());

        public static final RegistryEntry<StatusEffect> ALCHEMIST = registerStatusEffects("alchemist",
                new AlchemistEffect());

        public static final RegistryEntry<StatusEffect> DEATH = registerStatusEffects("death",
                new DeathEffect());

        public static final RegistryEntry<StatusEffect> SATURATION = registerStatusEffects("saturation",
                new SaturationEffect());

        public static final RegistryEntry<StatusEffect> DOUBLE_HEALTH = registerStatusEffects("double_health",
                new DoubleHealthEffect());

        public static final RegistryEntry<StatusEffect> RESURRECTION = registerStatusEffects("resurrection",
                new ResurrectionEffect());

        public static final RegistryEntry<StatusEffect> INFINITY = registerStatusEffects("infinity",
                new InfinityEffect());

        public static final RegistryEntry<StatusEffect> LONG_COOLDOWN = registerStatusEffects("long_cooldown",
                new LongCooldownEffect());

        public static final RegistryEntry<StatusEffect> SHORT_COOLDOWN = registerStatusEffects("short_cooldown",
                new ShortCooldownEffect());

        public static final RegistryEntry<StatusEffect> MASKING = registerStatusEffects("masking",
                new MaskingEffect());

        public static final RegistryEntry<StatusEffect> UNSTABLE = registerStatusEffects("unstable",
                new UnstableEffect());

        public static final RegistryEntry<StatusEffect> VAMPIRISM = registerStatusEffects("vampirism",
                new VampirismEffect());

        public static final RegistryEntry<StatusEffect> STUN = registerStatusEffects("stun",
                new StunEffect());

        public static final RegistryEntry<StatusEffect> NO_INTERACTION = registerStatusEffects("no_interaction",
                new NoInteractionEffect());

        public static final RegistryEntry<StatusEffect> AFTERMATH = registerStatusEffects("aftermath",
                new AftermathEffect());

        public static final RegistryEntry<StatusEffect> BERSERK = registerStatusEffects("berserk",
                new BerserkEffect());

        public static final RegistryEntry<StatusEffect> GHOST_WALK = registerStatusEffects("ghost_walk",
                new GhostWalkEffect());

        public static final RegistryEntry<StatusEffect> GIANT = registerStatusEffects("giant",
                new GiantEffect());

        public static final RegistryEntry<StatusEffect> DWARF = registerStatusEffects("dwarf",
                new DwarfEffect());

        public static final RegistryEntry<StatusEffect> PHOTOSYNTHESIS = registerStatusEffects("photosynthesis",
                new PhotosynthesisEffect());

        public static final RegistryEntry<StatusEffect> OBLIVION = registerStatusEffects("oblivion",
                new OblivionEffect());

        public static final RegistryEntry<StatusEffect> ADHESION = registerStatusEffects("adhesion",
                new AdhesionEffect());

        public static final RegistryEntry<StatusEffect> RUST = registerStatusEffects("rust",
                new RustEffect());

        public static final RegistryEntry<StatusEffect> PERMANENT_HEALTH = registerStatusEffects("perm_health",
                new PermanentHealthEffect());

        public static final RegistryEntry<StatusEffect> PERMANENT_STRENGTH = registerStatusEffects("perm_strength",
                new PermanentStrengthEffect());

        public static final RegistryEntry<StatusEffect> PERMANENT_SPEED = registerStatusEffects("perm_speed",
                new PermanentSpeedEffect());

        private static RegistryEntry<StatusEffect> registerStatusEffects(String name, StatusEffect effect) {
            return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(TheAlchemistsTouch.MOD_ID, name), effect);
        }

        public static void register() {
            TheAlchemistsTouch.LOGGER.info("registering mod effects for " + TheAlchemistsTouch.MOD_ID);
        }
    }
