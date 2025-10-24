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

    public class ModPotions {
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
                ModEffects.TELEPORTATION, 1, 0);
        public static RegistryEntry<Potion> STRONG_TELEPORTATION = registerPotion("teleportation", "strong_teleportation",
                ModEffects.TELEPORTATION, 1, 1);

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

        public static final RegistryEntry<Potion> GHOST_WALK = registerPotion("ghost_walk", "ghost_walk",
                ModEffects.GHOST_WALK, 20 * 5, 0);
        public static final RegistryEntry<Potion> LONG_GHOST_WALK = registerPotion("ghost_walk", "long_ghost_walk",
                ModEffects.GHOST_WALK, 20 * 10, 0);

        public static final RegistryEntry<Potion> GIANT = registerPotion("giant", "giant",
                ModEffects.GIANT, 20 * 60 * 3, 0);
        public static final RegistryEntry<Potion> LONG_GIANT = registerPotion("giant", "long_giant",
                ModEffects.GIANT, 20 * 60 * 8, 0);
        public static final RegistryEntry<Potion> STRONG_GIANT = registerPotion("giant", "strong_giant",
                ModEffects.GIANT, 20 * 60 * 3, 1);

        public static final RegistryEntry<Potion> DWARF = registerPotion("dwarf", "dwarf",
                ModEffects.DWARF, 20 * 60 * 3, 0);
        public static final RegistryEntry<Potion> LONG_DWARF = registerPotion("dwarf", "long_dwarf",
                ModEffects.DWARF, 20 * 60 * 8, 0);
        public static final RegistryEntry<Potion> STRONG_DWARF = registerPotion("dwarf", "strong_dwarf",
                ModEffects.DWARF, 20 * 60 * 3, 1);

        public static final RegistryEntry<Potion> PHOTOSYNTHESIS = registerPotion("photosynthesis", "photosynthesis",
                ModEffects.PHOTOSYNTHESIS, 20 * 60 * 3, 0);
        public static final RegistryEntry<Potion> LONG_PHOTOSYNTHESIS = registerPotion("photosynthesis", "long_photosynthesis",
                ModEffects.PHOTOSYNTHESIS, 20 * 60 * 8, 0);
        public static final RegistryEntry<Potion> STRONG_PHOTOSYNTHESIS = registerPotion("photosynthesis", "strong_photosynthesis",
                ModEffects.PHOTOSYNTHESIS, 20 * 60 * 3, 1);

        public static final RegistryEntry<Potion> OBLIVION = registerPotion("oblivion", "oblivion",
                ModEffects.OBLIVION, 1, 0);

        public static final RegistryEntry<Potion> ADHESION = registerPotion("adhesion", "adhesion",
                ModEffects.ADHESION, 20 * 60 * 3, 0);
        public static final RegistryEntry<Potion> LONG_ADHESION = registerPotion("adhesion", "long_adhesion",
                ModEffects.ADHESION, 20 * 60 * 8, 0);

        public static final RegistryEntry<Potion> RUST = registerPotion("rust", "rust",
                ModEffects.RUST, 20 * 45, 0);
        public static final RegistryEntry<Potion> LONG_RUST = registerPotion("rust", "long_rust",
                ModEffects.RUST, 20 * 30 * 3, 0);

        // Permanent potions -------------------------------------------------------------------------------------------

        public static final RegistryEntry<Potion> PERMANENT_HEALTH = registerPotion("perm_health", "perm_health",
                ModEffects.PERMANENT_HEALTH, 1, 0);

        public static final RegistryEntry<Potion> PERMANENT_STRENGTH = registerPotion("perm_strength", "perm_strength",
                ModEffects.PERMANENT_STRENGTH, 1, 0);

        public static final RegistryEntry<Potion> PERMANENT_SPEED = registerPotion("perm_speed", "perm_speed",
                ModEffects.PERMANENT_SPEED, 1, 0);

        public static RegistryEntry<Potion> INFINITY = registerPotion("infinity", "infinity",
                ModEffects.INFINITY, 1, 0);


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
                builder.registerPotionRecipe(Potions.SLOW_FALLING, Items.FEATHER, ModPotions.LEVITATION);
                builder.registerPotionRecipe(Potions.NIGHT_VISION, Items.GLOW_BERRIES, ModPotions.GLOWING);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.SWEET_BERRIES, ModPotions.ALCOHOL);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.INK_SAC, ModPotions.DARKNESS);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.BAMBOO, ModPotions.LONG_LEG);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.LILY_PAD, ModPotions.LIQUID_WALKER);
                builder.registerPotionRecipe(Potions.NIGHT_VISION, Items.IRON_ORE, ModPotions.ORE_SENSE);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.ECHO_SHARD, ModPotions.RESONANCE);
                builder.registerPotionRecipe(ModPotions.SHORT_COOLDOWN, Items.SUNFLOWER, ModPotions.REACTIVATION);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.MILK_BUCKET, ModPotions.PURIFICATION);
                builder.registerPotionRecipe(Potions.TURTLE_MASTER, Items.OBSIDIAN, ModPotions.PETRIFICATION);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.ROTTEN_FLESH, ModPotions.ACID);
                builder.registerPotionRecipe(Potions.FIRE_RESISTANCE, Items.FERMENTED_SPIDER_EYE, ModPotions.IGNITION);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.ENDER_PEARL, ModPotions.TELEPORTATION);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.CACTUS, ModPotions.THORNS);
                builder.registerPotionRecipe(Potions.AWKWARD, ModItems.ZOMBIE_BRAIN, ModPotions.BRAIN_WASHING);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.SNOWBALL, ModPotions.FROST);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.COPPER_INGOT, ModPotions.ALCHEMIST);
                builder.registerPotionRecipe(ModPotions.RESURRECTION, Items.WITHER_ROSE, ModPotions.DEATH);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.BEETROOT, ModPotions.SATURATION);
                builder.registerPotionRecipe(Potions.STRONG_HEALING, Items.GOLDEN_APPLE, ModPotions.DOUBLE_HEALTH);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.TOTEM_OF_UNDYING, ModPotions.RESURRECTION);
                builder.registerPotionRecipe(Potions.AWKWARD, ModItems.CLAW, ModPotions.HASTE);
                builder.registerPotionRecipe(ModPotions.HASTE, Items.FERMENTED_SPIDER_EYE, ModPotions.MINING_FATIGUE);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.SUNFLOWER, ModPotions.SHORT_COOLDOWN);
                builder.registerPotionRecipe(ModPotions.SHORT_COOLDOWN, Items.FERMENTED_SPIDER_EYE, ModPotions.LONG_COOLDOWN);
                builder.registerPotionRecipe(Potions.INVISIBILITY, Items.FERMENTED_SPIDER_EYE, ModPotions.MASKING);
                builder.registerPotionRecipe(Potions.AWKWARD, ModItems.BLOOD_BAG, ModPotions.VAMPIRISM);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.TORCHFLOWER, ModPotions.BERSERK);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.BONE_MEAL, ModPotions.GIANT);
                builder.registerPotionRecipe(ModPotions.GIANT, Items.FERMENTED_SPIDER_EYE, ModPotions.DWARF);
                builder.registerPotionRecipe(Potions.AWKWARD, ModItems.LEAF, ModPotions.PHOTOSYNTHESIS);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.PITCHER_PLANT, ModPotions.OBLIVION);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.GHAST_TEAR, ModPotions.GHOST_WALK);
                builder.registerPotionRecipe(Potions.AWKWARD, ModItems.CHARGED_COPPER, ModPotions.STUN);
                builder.registerPotionRecipe(Potions.AWKWARD, ModItems.OXYDATION, ModPotions.RUST);
                builder.registerPotionRecipe(Potions.AWKWARD, Items.RESIN_CLUMP, ModPotions.ADHESION);
                builder.registerPotionRecipe(ModPotions.BRAIN_WASHING, Items.RABBIT_FOOT, ModPotions.NO_INTERACTION);

                // version longue
                builder.registerPotionRecipe(ModPotions.LEVITATION, Items.REDSTONE, ModPotions.LONG_LEVITATION);
                builder.registerPotionRecipe(ModPotions.GLOWING, Items.REDSTONE, ModPotions.LONG_GLOWING);
                builder.registerPotionRecipe(ModPotions.ALCOHOL, Items.REDSTONE, ModPotions.LONG_ALCOHOL);
                builder.registerPotionRecipe(ModPotions.DARKNESS, Items.REDSTONE, ModPotions.LONG_DARKNESS);
                builder.registerPotionRecipe(ModPotions.LONG_LEG, Items.REDSTONE, ModPotions.LONG_LONG_LEG);
                builder.registerPotionRecipe(ModPotions.LIQUID_WALKER, Items.REDSTONE, ModPotions.LONG_LIQUID_WALKER);
                builder.registerPotionRecipe(ModPotions.ORE_SENSE, Items.REDSTONE, ModPotions.LONG_ORE_SENSE);
                builder.registerPotionRecipe(ModPotions.RESONANCE, Items.REDSTONE, ModPotions.LONG_RESONANCE);
                builder.registerPotionRecipe(ModPotions.REACTIVATION, Items.REDSTONE, ModPotions.LONG_REACTIVATION);
                builder.registerPotionRecipe(ModPotions.PURIFICATION, Items.REDSTONE, ModPotions.LONG_PURIFICATION);
                builder.registerPotionRecipe(ModPotions.PETRIFICATION, Items.REDSTONE, ModPotions.LONG_PETRIFICATION);
                builder.registerPotionRecipe(ModPotions.ACID, Items.REDSTONE, ModPotions.LONG_ACID);
                builder.registerPotionRecipe(ModPotions.IGNITION, Items.REDSTONE, ModPotions.LONG_IGNITION);
                builder.registerPotionRecipe(ModPotions.THORNS, Items.REDSTONE, ModPotions.LONG_THORNS);
                builder.registerPotionRecipe(ModPotions.BRAIN_WASHING, Items.REDSTONE, ModPotions.LONG_BRAIN_WASHING);
                builder.registerPotionRecipe(ModPotions.FROST, Items.REDSTONE, ModPotions.LONG_FROST);
                builder.registerPotionRecipe(ModPotions.SATURATION, Items.REDSTONE, ModPotions.LONG_SATURATION);
                builder.registerPotionRecipe(ModPotions.HASTE, Items.REDSTONE, ModPotions.LONG_HASTE);
                builder.registerPotionRecipe(ModPotions.MINING_FATIGUE, Items.REDSTONE, ModPotions.LONG_MINING_FATIGUE);
                builder.registerPotionRecipe(ModPotions.LONG_HASTE, Items.FERMENTED_SPIDER_EYE, ModPotions.LONG_MINING_FATIGUE);
                builder.registerPotionRecipe(ModPotions.SHORT_COOLDOWN, Items.REDSTONE, ModPotions.LONG_SHORT_COOLDOWN);
                builder.registerPotionRecipe(ModPotions.LONG_COOLDOWN, Items.REDSTONE, ModPotions.LONG_LONG_COOLDOWN);
                builder.registerPotionRecipe(ModPotions.LONG_SHORT_COOLDOWN, Items.FERMENTED_SPIDER_EYE, ModPotions.LONG_LONG_COOLDOWN);
                builder.registerPotionRecipe(ModPotions.VAMPIRISM, Items.REDSTONE, ModPotions.LONG_VAMPIRISM);
                builder.registerPotionRecipe(ModPotions.STUN, Items.REDSTONE, ModPotions.LONG_STUN);
                builder.registerPotionRecipe(ModPotions.NO_INTERACTION, Items.REDSTONE, ModPotions.LONG_NO_INTERACTION);
                builder.registerPotionRecipe(ModPotions.BERSERK, Items.REDSTONE, ModPotions.LONG_BERSERK);
                builder.registerPotionRecipe(ModPotions.GHOST_WALK, Items.REDSTONE, ModPotions.LONG_GHOST_WALK);
                builder.registerPotionRecipe(ModPotions.GIANT, Items.REDSTONE, ModPotions.LONG_GIANT);
                builder.registerPotionRecipe(ModPotions.DWARF, Items.REDSTONE, ModPotions.LONG_DWARF);
                builder.registerPotionRecipe(ModPotions.PHOTOSYNTHESIS, Items.REDSTONE, ModPotions.LONG_PHOTOSYNTHESIS);
                builder.registerPotionRecipe(ModPotions.ADHESION, Items.REDSTONE, ModPotions.LONG_ADHESION);
                builder.registerPotionRecipe(ModPotions.RUST, Items.REDSTONE, ModPotions.LONG_RUST);

                // version strong
                builder.registerPotionRecipe(ModPotions.ALCOHOL, Items.GLOWSTONE, ModPotions.STRONG_ALCOHOL);
                builder.registerPotionRecipe(ModPotions.ORE_SENSE, Items.GLOWSTONE, ModPotions.STRONG_ORE_SENSE);
                builder.registerPotionRecipe(ModPotions.RESONANCE, Items.GLOWSTONE, ModPotions.STRONG_RESONANCE);
                builder.registerPotionRecipe(ModPotions.REACTIVATION, Items.GLOWSTONE, ModPotions.STRONG_REACTIVATION);
                builder.registerPotionRecipe(ModPotions.ACID, Items.GLOWSTONE, ModPotions.STRONG_ACID);
                builder.registerPotionRecipe(ModPotions.THORNS, Items.GLOWSTONE, ModPotions.STRONG_THORNS);
                builder.registerPotionRecipe(ModPotions.SATURATION, Items.GLOWSTONE, ModPotions.STRONG_SATURATION);
                builder.registerPotionRecipe(ModPotions.DOUBLE_HEALTH, Items.GLOWSTONE, ModPotions.STRONG_DOUBLE_HEALTH);
                builder.registerPotionRecipe(ModPotions.MINING_FATIGUE, Items.GLOWSTONE, ModPotions.STRONG_MINING_FATIGUE);
                builder.registerPotionRecipe(ModPotions.UNSTABLE, Items.GLOWSTONE, ModPotions.STRONG_UNSTABLE);
                builder.registerPotionRecipe(ModPotions.VAMPIRISM, Items.GLOWSTONE, ModPotions.STRONG_VAMPIRISM);
                builder.registerPotionRecipe(ModPotions.BERSERK, Items.GLOWSTONE, ModPotions.STRONG_BERSERK);
                builder.registerPotionRecipe(ModPotions.TELEPORTATION, Items.GLOWSTONE, ModPotions.STRONG_TELEPORTATION);
                builder.registerPotionRecipe(ModPotions.GIANT, Items.GLOWSTONE, ModPotions.STRONG_GIANT);
                builder.registerPotionRecipe(ModPotions.DWARF, Items.GLOWSTONE, ModPotions.STRONG_DWARF);
                builder.registerPotionRecipe(ModPotions.PHOTOSYNTHESIS, Items.GLOWSTONE, ModPotions.STRONG_PHOTOSYNTHESIS);
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
