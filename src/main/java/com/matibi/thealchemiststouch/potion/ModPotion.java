    package com.matibi.thealchemiststouch.potion;

    import com.matibi.thealchemiststouch.TheAlchemistsTouch;
    import com.matibi.thealchemiststouch.effect.ModEffects;
    import com.matibi.thealchemiststouch.item.ModItems;
    import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
    import net.minecraft.entity.effect.StatusEffect;
    import net.minecraft.entity.effect.StatusEffectInstance;
    import net.minecraft.entity.effect.StatusEffects;
    import net.minecraft.item.Items;
    import net.minecraft.potion.Potion;
    import net.minecraft.potion.Potions;
    import net.minecraft.registry.Registries;
    import net.minecraft.registry.Registry;
    import net.minecraft.registry.entry.RegistryEntry;
    import net.minecraft.util.Identifier;

    public class ModPotion {
        public static RegistryEntry<Potion> LEVITATION = registerPotion("levitation", "levitation",
                StatusEffects.LEVITATION, 20 * 10, 0);
        public static RegistryEntry<Potion> LONG_LEVITATION = registerPotion("levitation", "long_levitation",
                StatusEffects.LEVITATION, 20 * 10 * 3, 0);

        public static RegistryEntry<Potion> GLOWING = registerPotion("glowing", "glowing",
                StatusEffects.GLOWING, 20 * 60, 0);
        public static RegistryEntry<Potion> LONG_GLOWING = registerPotion("glowing", "long_glowing",
                StatusEffects.GLOWING, 20 * 60 * 3, 0);

        public static RegistryEntry<Potion> ALCOHOL = registerPotion("alcohol", "alcohol",
                StatusEffects.NAUSEA, 20 * 60, 0);
        public static RegistryEntry<Potion> LONG_ALCOHOL = registerPotion("alcohol", "long_alcohol",
                StatusEffects.NAUSEA, 20 * 60 * 3, 0);
        public static RegistryEntry<Potion> STRONG_ALCOHOL = registerPotion("alcohol", "strong_alcohol",
                StatusEffects.NAUSEA, 20 * 60, 1);

        public static RegistryEntry<Potion> DARKNESS = registerPotion("darkness", "darkness",
                StatusEffects.DARKNESS, 20 * 45, 0);
        public static RegistryEntry<Potion> LONG_DARKNESS = registerPotion("darkness", "long_darkness",
                StatusEffects.DARKNESS, 20 * 30 * 3, 0);

        public static RegistryEntry<Potion> LONG_LEG = registerPotion("long_leg", "long_leg",
                ModEffects.LONG_LEG, 20 * 60 * 3, 0);
        public static RegistryEntry<Potion> LONG_LONG_LEG = registerPotion("long_leg", "long_long_leg",
                ModEffects.LONG_LEG, 20 * 60 * 8, 0);

        public static RegistryEntry<Potion> LIQUID_WALKER = registerPotion("liquid_walker", "liquid_walker",
                ModEffects.LIQUID_WALKER, 20 * 60 * 3, 0);
        public static RegistryEntry<Potion> LONG_LIQUID_WALKER = registerPotion("liquid_walker", "long_liquid_walker",
                ModEffects.LIQUID_WALKER, 20 * 60 * 8, 0);

        public static RegistryEntry<Potion> ORE_SENSE = registerPotion("ore_sense", "ore_sense",
                ModEffects.ORE_SENSE, 20 * 60 * 3, 0);
        public static RegistryEntry<Potion> LONG_ORE_SENSE = registerPotion("ore_sense", "long_ore_sense",
                ModEffects.ORE_SENSE, 20 * 60 * 8, 0);
        public static RegistryEntry<Potion> STRONG_ORE_SENSE = registerPotion("ore_sense", "strong_ore_sense",
                ModEffects.ORE_SENSE, 20 * 60 * 3, 1);

        public static RegistryEntry<Potion> RESONANCE = registerPotion("resonance", "resonance",
                ModEffects.RESONANCE, 20 * 60 * 3, 0);
        public static RegistryEntry<Potion> LONG_RESONANCE = registerPotion("resonance", "long_resonance",
                ModEffects.RESONANCE, 20 * 60 * 8, 0);
        public static RegistryEntry<Potion> STRONG_RESONANCE = registerPotion("resonance", "strong_resonance",
                ModEffects.RESONANCE, 20 * 60 * 3, 1);

        public static RegistryEntry<Potion> REACTIVATION = registerPotion("reactivation", "reactivation",
                ModEffects.REACTIVATION, 20 * 60 * 3, 0);
        public static RegistryEntry<Potion> LONG_REACTIVATION = registerPotion("reactivation", "long_reactivation",
                ModEffects.REACTIVATION, 20 * 60 * 8, 0);
        public static RegistryEntry<Potion> STRONG_REACTIVATION = registerPotion("reactivation", "strong_reactivation",
                ModEffects.REACTIVATION, 20 * 60 * 3, 1);

        public static RegistryEntry<Potion> PURIFICATION = registerPotion("purification", "purification",
                ModEffects.PURIFICATION, 20 * 60 * 3, 0);
        public static RegistryEntry<Potion> LONG_PURIFICATION = registerPotion("purification", "long_purification",
                ModEffects.PURIFICATION, 20 * 60 * 8, 0);

        public static RegistryEntry<Potion> PETRIFICATION = registerPotion("petrification", "petrification",
                ModEffects.PETRIFICATION, 20 * 30, 0);
        public static RegistryEntry<Potion> LONG_PETRIFICATION = registerPotion("petrification", "long_petrification",
                ModEffects.PETRIFICATION, 20 * 30 * 3, 0);

        public static RegistryEntry<Potion> ACID = registerPotion("acid", "acid",
                ModEffects.ACID, 20 * 45, 0);
        public static RegistryEntry<Potion> LONG_ACID = registerPotion("acid", "long_acid",
                ModEffects.ACID, 20 * 30 * 3, 0);
        public static RegistryEntry<Potion> STRONG_ACID = registerPotion("acid", "strong_acid",
                ModEffects.ACID, 20 * 45, 1);

        public static RegistryEntry<Potion> IGNITION = registerPotion("ignition", "ignition",
                ModEffects.IGNITION, 20 * 45, 0);
        public static RegistryEntry<Potion> LONG_IGNITION = registerPotion("ignition", "long_ignition",
                ModEffects.IGNITION, 20 * 30 * 3, 0);

        public static RegistryEntry<Potion> TELEPORTATION = registerPotion("teleportation", "teleportation",
                ModEffects.TELEPORTAION, 1, 0);

        public static RegistryEntry<Potion> THORNS = registerPotion("thorns", "thorns",
                ModEffects.THORNS, 20 * 60 * 3, 0);
        public static RegistryEntry<Potion> LONG_THORNS = registerPotion("thorns", "long_thorns",
                ModEffects.THORNS, 20 * 60 * 8, 0);
        public static RegistryEntry<Potion> STRONG_THORNS = registerPotion("thorns", "strong_thorns",
                ModEffects.THORNS, 20 * 60 * 3, 1);

        public static RegistryEntry<Potion> BRAIN_WASHING = registerPotion("brain_washing", "brain_washing",
                ModEffects.BRAIN_WASHING, 20 * 60 * 3, 0);
        public static RegistryEntry<Potion> LONG_BRAIN_WASHING = registerPotion("brain_washing", "long_brain_washing",
                ModEffects.BRAIN_WASHING, 20 * 60 * 8, 0);

        public static RegistryEntry<Potion> FROST = registerPotion("frost", "frost",
                ModEffects.FROST, 20 * 45, 0);
        public static RegistryEntry<Potion> LONG_FROST = registerPotion("frost", "long_frost",
                ModEffects.FROST, 20 * 30 * 3, 0);

        public static RegistryEntry<Potion> ALCHEMIST = registerPotion("alchemist", "alchemist",
                ModEffects.ALCHEMIST, 1, 0);

        public static RegistryEntry<Potion> DEATH = registerPotion("death", "death",
                ModEffects.DEATH, 1, 0);

        public static RegistryEntry<Potion> SATURATION = registerPotion("saturation", "saturation",
                ModEffects.SATURATION, 20 * 45, 0);
        public static RegistryEntry<Potion> LONG_SATURATION = registerPotion("saturation", "long_saturation",
                ModEffects.SATURATION, 20 * 30 * 3, 0);
        public static RegistryEntry<Potion> STRONG_SATURATION = registerPotion("saturation", "strong_saturation",
                ModEffects.SATURATION, 20 * 45, 1);

        public static RegistryEntry<Potion> DOUBLE_HEALTH = registerPotion("double_health", "double_health",
                ModEffects.DOUBLE_HEALTH, 1, 0);
        public static RegistryEntry<Potion> STRONG_DOUBLE_HEALTH = registerPotion("double_health", "strong_double_health",
                ModEffects.DOUBLE_HEALTH, 1, 1);

        public static RegistryEntry<Potion> RESURRECTION = registerPotion("resurrection", "resurrection",
                ModEffects.RESURRECTION, -1, 0);

        public static RegistryEntry<Potion> INFINITY = registerPotion("infinity", "infinity",
                ModEffects.INFINITY, 1, 0);

        public static final RegistryEntry<Potion> HASTE = registerPotion("haste", "haste",
                StatusEffects.HASTE, 20 * 60 * 3, 0);
        public static final RegistryEntry<Potion> LONG_HASTE = registerPotion("haste", "long_haste",
                StatusEffects.HASTE, 20 * 60 * 8, 0);

        public static final RegistryEntry<Potion> MINING_FATIGUE = registerPotion("mining_fatigue", "mining_fatigue",
                StatusEffects.MINING_FATIGUE, 20 * 60 * 3, 0);
        public static final RegistryEntry<Potion> STRONG_MINING_FATIGUE = registerPotion("mining_fatigue", "strong_mining_fatigue",
                StatusEffects.MINING_FATIGUE, 20 * 60 * 3, 1);
        public static final RegistryEntry<Potion> LONG_MINING_FATIGUE = registerPotion("mining_fatigue", "long_mining_fatigue",
                StatusEffects.MINING_FATIGUE, 20 * 60 * 8, 0);

        public static final RegistryEntry<Potion> LONG_COOLDOWN = registerPotion("long_cooldown", "long_cooldown",
                ModEffects.LONG_COOLDOWN, 20 * 60 * 3, 0);
        public static final RegistryEntry<Potion> LONG_LONG_COOLDOWN = registerPotion("long_cooldown", "long_long_cooldown",
                ModEffects.LONG_COOLDOWN, 20 * 60 * 8, 0);

        public static final RegistryEntry<Potion> SHORT_COOLDOWN = registerPotion("short_cooldown", "short_cooldown",
                ModEffects.SHORT_COOLDOWN, 20 * 60 * 3, 0);
        public static final RegistryEntry<Potion> LONG_SHORT_COOLDOWN = registerPotion("short_cooldown", "long_short_cooldown",
                ModEffects.SHORT_COOLDOWN, 20 * 60 * 8, 0);

        public static final RegistryEntry<Potion> MASKING = registerPotion("masking", "masking",
                ModEffects.MASKING, 1, 0);

        public static final RegistryEntry<Potion> UNSTABLE = registerPotion("unstable", "unstable",
                ModEffects.UNSTABLE, 1, 0);
        public static final RegistryEntry<Potion> STRONG_UNSTABLE = registerPotion("unstable", "strong_unstable",
                ModEffects.UNSTABLE, 1, 1);

        public static final RegistryEntry<Potion> VAMPIRISM = registerPotion("vampirism", "vampirism",
                ModEffects.VAMPIRISM, 20 * 60 * 3, 0);
        public static final RegistryEntry<Potion> LONG_VAMPIRISM = registerPotion("vampirism", "long_vampirism",
                ModEffects.VAMPIRISM, 20 * 60 * 8, 0);
        public static final RegistryEntry<Potion> STRONG_VAMPIRISM = registerPotion("vampirism", "strong_vampirism",
                ModEffects.VAMPIRISM, 20 * 60 * 3, 1);

        public static final RegistryEntry<Potion> STUN = registerPotion("stun", "stun",
                ModEffects.STUN, 20 *  10, 0);
        public static final RegistryEntry<Potion> LONG_STUN = registerPotion("stun", "long_stun",
                ModEffects.STUN, 20 *  30, 0);

        public static final RegistryEntry<Potion> NO_INTERACTION = registerPotion("no_interaction", "no_interaction",
                ModEffects.NO_INTERACTION, 20 *  60 * 3, 0);
        public static final RegistryEntry<Potion> LONG_NO_INTERACTION = registerPotion("no_interaction", "long_no_interaction",
                ModEffects.NO_INTERACTION, 20 *  60 * 8, 0);

        public static final RegistryEntry<Potion> BERSERK = registerPotion("berserk", "berserk",
                ModEffects.BERSERK, 20 *  45, 0);
        public static final RegistryEntry<Potion> LONG_BERSERK = registerPotion("berserk", "long_berserk",
                ModEffects.BERSERK, 20 *  30 * 3, 0);
        public static final RegistryEntry<Potion> STRONG_BERSERK = registerPotion("berserk", "strong_berserk",
                ModEffects.BERSERK, 20 *  45, 1);

        // Permanent potions -------------------------------------------------------------------------------------------

        public static final RegistryEntry<Potion> PERMANENT_HEALTH = registerPotion("health", "health",
                ModEffects.PERMANENT_HEALTH, 1, 0);

        public static final RegistryEntry<Potion> PERMANENT_STRENGTH = registerPotion("strength", "strength",
                ModEffects.PERMANENT_STRENGTH, 1, 0);

        public static final RegistryEntry<Potion> PERMANENT_SPEED = registerPotion("speed", "speed",
                ModEffects.PERMANENT_SPEED, 1, 0);


        public static void register() {
            TheAlchemistsTouch.LOGGER.info("Registering mod potions for " + TheAlchemistsTouch.MOD_ID);

            FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
                // changement de base, mais moins efficace que de farmer des nether wart
                builder.registerPotionRecipe(Potions.WATER, ModItems.ALCHEMIST_CORE, Potions.AWKWARD);

                // potions accessible early
                builder.registerPotionRecipe(Potions.AWKWARD, Items.MAGMA_BLOCK, Potions.FIRE_RESISTANCE);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.GOLDEN_APPLE, Potions.REGENERATION);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.FIREWORK_STAR, Potions.STRENGTH);

                // potion custom
                builder.registerPotionRecipe(Potions.SLOW_FALLING, Items.FEATHER, ModPotion.LEVITATION);
                builder.registerPotionRecipe(Potions.NIGHT_VISION, Items.GLOW_BERRIES, ModPotion.GLOWING);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.SWEET_BERRIES, ModPotion.ALCOHOL);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.INK_SAC, ModPotion.DARKNESS);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.BAMBOO, ModPotion.LONG_LEG);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.LILY_PAD, ModPotion.LIQUID_WALKER);
                builder.registerPotionRecipe(Potions.NIGHT_VISION, Items.IRON_ORE, ModPotion.ORE_SENSE);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.ECHO_SHARD, ModPotion.RESONANCE);
                builder.registerPotionRecipe(ModPotion.SHORT_COOLDOWN, Items.CLOCK, ModPotion.REACTIVATION);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.MILK_BUCKET, ModPotion.PURIFICATION);
                builder.registerPotionRecipe(Potions.TURTLE_MASTER, Items.OBSIDIAN, ModPotion.PETRIFICATION);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.ROTTEN_FLESH, ModPotion.ACID);
                builder.registerPotionRecipe(Potions.FIRE_RESISTANCE, Items.FERMENTED_SPIDER_EYE, ModPotion.IGNITION);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.ENDER_PEARL, ModPotion.TELEPORTATION);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.CACTUS, ModPotion.THORNS);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.AMETHYST_SHARD, ModPotion.BRAIN_WASHING);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.SNOWBALL, ModPotion.FROST);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.COPPER_INGOT, ModPotion.ALCHEMIST);
                builder.registerPotionRecipe(ModPotion.RESURRECTION, Items.FERMENTED_SPIDER_EYE, ModPotion.DEATH);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.BEETROOT, ModPotion.SATURATION);
                builder.registerPotionRecipe(Potions.STRONG_HEALING, Items.GOLDEN_APPLE, ModPotion.DOUBLE_HEALTH);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.TOTEM_OF_UNDYING, ModPotion.RESURRECTION);
                builder.registerPotionRecipe(ModPotion.RESURRECTION, Items.CLOCK, ModPotion.INFINITY);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.IRON_PICKAXE, ModPotion.HASTE);
                builder.registerPotionRecipe(ModPotion.HASTE, Items.FERMENTED_SPIDER_EYE, ModPotion.MINING_FATIGUE);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.CLOCK, ModPotion.SHORT_COOLDOWN);
                builder.registerPotionRecipe(ModPotion.SHORT_COOLDOWN, Items.FERMENTED_SPIDER_EYE, ModPotion.LONG_COOLDOWN);
                builder.registerPotionRecipe(Potions.INVISIBILITY, Items.FERMENTED_SPIDER_EYE, ModPotion.MASKING);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.IRON_SWORD, ModPotion.VAMPIRISM);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.IRON_AXE, ModPotion.STUN);

                // version longue
                builder.registerPotionRecipe(ModPotion.LEVITATION, Items.REDSTONE, ModPotion.LONG_LEVITATION);
                builder.registerPotionRecipe(ModPotion.GLOWING, Items.REDSTONE, ModPotion.LONG_GLOWING);
                builder.registerPotionRecipe(ModPotion.ALCOHOL, Items.REDSTONE, ModPotion.LONG_ALCOHOL);
                builder.registerPotionRecipe(ModPotion.DARKNESS, Items.REDSTONE, ModPotion.LONG_DARKNESS);
                builder.registerPotionRecipe(ModPotion.LONG_LEG, Items.REDSTONE, ModPotion.LONG_LONG_LEG);
                builder.registerPotionRecipe(ModPotion.LIQUID_WALKER, Items.REDSTONE, ModPotion.LONG_LIQUID_WALKER);
                builder.registerPotionRecipe(ModPotion.ORE_SENSE, Items.REDSTONE, ModPotion.LONG_ORE_SENSE);
                builder.registerPotionRecipe(ModPotion.RESONANCE, Items.REDSTONE, ModPotion.LONG_RESONANCE);
                builder.registerPotionRecipe(ModPotion.REACTIVATION, Items.REDSTONE, ModPotion.LONG_REACTIVATION);
                builder.registerPotionRecipe(ModPotion.PURIFICATION, Items.REDSTONE, ModPotion.LONG_PURIFICATION);
                builder.registerPotionRecipe(ModPotion.PETRIFICATION, Items.REDSTONE, ModPotion.LONG_PETRIFICATION);
                builder.registerPotionRecipe(ModPotion.ACID, Items.REDSTONE, ModPotion.LONG_ACID);
                builder.registerPotionRecipe(ModPotion.IGNITION, Items.REDSTONE, ModPotion.LONG_IGNITION);
                builder.registerPotionRecipe(ModPotion.THORNS, Items.REDSTONE, ModPotion.LONG_THORNS);
                builder.registerPotionRecipe(ModPotion.BRAIN_WASHING, Items.REDSTONE, ModPotion.LONG_BRAIN_WASHING);
                builder.registerPotionRecipe(ModPotion.FROST, Items.REDSTONE, ModPotion.LONG_FROST);
                builder.registerPotionRecipe(ModPotion.SATURATION, Items.REDSTONE, ModPotion.LONG_SATURATION);
                builder.registerPotionRecipe(ModPotion.HASTE, Items.REDSTONE, ModPotion.LONG_HASTE);
                builder.registerPotionRecipe(ModPotion.MINING_FATIGUE, Items.REDSTONE, ModPotion.LONG_MINING_FATIGUE);
                builder.registerPotionRecipe(ModPotion.LONG_HASTE, Items.FERMENTED_SPIDER_EYE, ModPotion.LONG_MINING_FATIGUE);
                builder.registerPotionRecipe(ModPotion.SHORT_COOLDOWN, Items.REDSTONE, ModPotion.LONG_SHORT_COOLDOWN);
                builder.registerPotionRecipe(ModPotion.LONG_COOLDOWN, Items.REDSTONE, ModPotion.LONG_LONG_COOLDOWN);
                builder.registerPotionRecipe(ModPotion.LONG_SHORT_COOLDOWN, Items.FERMENTED_SPIDER_EYE, ModPotion.LONG_LONG_COOLDOWN);
                builder.registerPotionRecipe(ModPotion.VAMPIRISM, Items.REDSTONE, ModPotion.LONG_VAMPIRISM);
                builder.registerPotionRecipe(ModPotion.STUN, Items.REDSTONE, ModPotion.LONG_STUN);
                builder.registerPotionRecipe(ModPotion.NO_INTERACTION, Items.REDSTONE, ModPotion.LONG_NO_INTERACTION);
                builder.registerPotionRecipe(ModPotion.BERSERK, Items.REDSTONE, ModPotion.LONG_BERSERK);

                // version strong
                builder.registerPotionRecipe(ModPotion.ALCOHOL, Items.GLOWSTONE, ModPotion.STRONG_ALCOHOL);
                builder.registerPotionRecipe(ModPotion.ORE_SENSE, Items.GLOWSTONE, ModPotion.STRONG_ORE_SENSE);
                builder.registerPotionRecipe(ModPotion.RESONANCE, Items.GLOWSTONE, ModPotion.STRONG_RESONANCE);
                builder.registerPotionRecipe(ModPotion.REACTIVATION, Items.GLOWSTONE, ModPotion.STRONG_REACTIVATION);
                builder.registerPotionRecipe(ModPotion.ACID, Items.GLOWSTONE, ModPotion.STRONG_ACID);
                builder.registerPotionRecipe(ModPotion.THORNS, Items.GLOWSTONE, ModPotion.STRONG_THORNS);
                builder.registerPotionRecipe(ModPotion.SATURATION, Items.GLOWSTONE, ModPotion.STRONG_SATURATION);
                builder.registerPotionRecipe(ModPotion.DOUBLE_HEALTH, Items.GLOWSTONE, ModPotion.STRONG_DOUBLE_HEALTH);
                builder.registerPotionRecipe(ModPotion.MINING_FATIGUE, Items.GLOWSTONE, ModPotion.STRONG_MINING_FATIGUE);
                builder.registerPotionRecipe(ModPotion.UNSTABLE, Items.GLOWSTONE, ModPotion.STRONG_UNSTABLE);
                builder.registerPotionRecipe(ModPotion.VAMPIRISM, Items.GLOWSTONE, ModPotion.STRONG_VAMPIRISM);
                builder.registerPotionRecipe(ModPotion.BERSERK, Items.GLOWSTONE, ModPotion.STRONG_BERSERK);
            });
        }

        private static RegistryEntry<Potion> registerPotion(String name,
                                                            String id,
                                                            RegistryEntry<StatusEffect> effect,
                                                            int duration,
                                                            int amplifier) {
            return Registry.registerReference(Registries.POTION, Identifier.of(TheAlchemistsTouch.MOD_ID, id),
                    new Potion(name, new StatusEffectInstance(effect, duration, amplifier)));
        }
    }
