package com.matibi.thealchemiststouch.datagen.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModUsLanguageProvider extends FabricLanguageProvider {
    TranslationBuilder t;

    public ModUsLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder t_param) {
        t = t_param;
        // Vanilla effects
        registerVanilla("levitation", "Levitation");
        registerVanilla("glowing", "Glowing");
        registerVanilla("darkness", "Darkness");
        registerVanilla("haste", "Haste");
        registerVanilla("mining_fatigue", "Mining Fatigue");
        t.add("item.minecraft.potion.effect.alcohol", "Alcohol");
        t.add("item.minecraft.splash_potion.effect.alcohol", "Splash Alcohol");
        t.add("item.minecraft.lingering_potion.effect.alcohol", "Lingering Alcohol");
        t.add("item.minecraft.tipped_arrow.effect.alcohol", "Arrow of Alcohol");

        // Custom effects without rune
        register("saturation", "Saturation");
        register("long_leg", "Long legs");
        register("liquid_walker", "Liquid Walker");
        register("ore_sense", "Ore Sense");
        register("resonance", "Resonance");
        register("reactivation", "Reactivation");
        register("purification", "Purification");
        register("teleportation", "Teleportation");
        register("thorns", "Thorns");
        register("brain_washing", "Brain Washing");
        register("frost", "Frost");
        register("death", "Death");
        register("double_health", "Double Health");
        register("resurrection", "Resurrection");
        register("infinity", "Infinity");
        register("long_cooldown", "Long Cooldown");
        register("short_cooldown", "Short Cooldown");
        register("masking", "Hidden Effect");
        registerReverse("unstable", "Unstable");
        register("vampirism", "Vampirism");
        register("stun", "Stun");
        register("no_interaction", "Hands Bound");
        register("aftermath", "Aftermath");
        register("berserk", "Berserk");
        register("ghost_walk", "Ghost Walk");
        register("dwarf", "Dwarfism");
        register("photosynthesis", "Photosynthesis");
        register("oblivion", "Oblivion");
        register("spider_legs", "Spider Legs");
        register("rust", "Rust");

        // Permanent potions
        register("perm_health", "Permanent Health");
        register("perm_strength", "Permanent Strength");
        register("perm_speed", "Permanent Speed");

        // Custom effects with rune
        registerWithRune("giant", "Giant");
        registerWithRune("petrification", "Petrification");
        registerWithRune("acid", "Acidity");
        registerWithRune("ignition", "Ignition");
        registerWithRune("alchemist", "Alchemist");

        // ---- Other non-effect items ----
        t.add("item.the-alchemists-touch.poisonous_carrot", "Poisonous Carrot");
        t.add("item.the-alchemists-touch.poisonous_beetroot", "Poisonous Beetroot");
        t.add("item.the-alchemists-touch.alchemist_core", "Alchemist Core");
        t.add("item.the-alchemists-touch.claw", "Claw");
        t.add("item.the-alchemists-touch.zombie_brain", "Zombie's brain");
        t.add("item.the-alchemists-touch.leaf", "Leaf");
        t.add("item.the-alchemists-touch.witch_finger", "Witches' Finger");
        t.add("item.the-alchemists-touch.blood_bag", "Blood Bag");
        t.add("item.the-alchemists-touch.rune.effect.empty", "Alchemical Stones");
        t.add("item.the-alchemists-touch.rune.effect.mixed", "Multi-effect Alchemical Stones");
        registerVanillaReverse("mixed", "Multi-effect");

        // Seringue
        for (var entry : Registries.STATUS_EFFECT) {
            Identifier id = Registries.STATUS_EFFECT.getId(entry);
            if (id == null || !id.getNamespace().equals("minecraft")) continue;
            String translationKey = "item.the-alchemists-touch.syringe.effect." + id.toTranslationKey();
            String effectName = Text.translatable(entry.getTranslationKey()).getString();
            t.add(translationKey, "Syringe of " + effectName);
        }
        t.add("item.the-alchemists-touch.syringe", "Syringe");
        t.add("item.the-alchemists-touch.syringe.effect.empty", "Syringe");
        t.add("the-alchemists-touch.blood_type.unknown", "Unknown");
        t.add("the-alchemists-touch.blood_type.human", "Human Blood");
        t.add("the-alchemists-touch.blood_type.monster", "Monster Blood");

        // Special messages
        t.add("item.the-alchemists-touch.rune.block_only", "Runes can only be used on blocks");
        t.add("item.the-alchemists-touch.rune.block_not_good", "The block is not compatible");
        t.add("itemGroup.the-alchemists-touch.alchemy", "Alchemy");
        t.add("splash.the-alchemists-touch.magic", "Alchemy is power !!!");
        t.add("splash.the-alchemists-touch.thanks", "Thank you for supporting me LivelyBadGood");
        t.add("tooltip.the-alchemists-touch.imbued_line", "%s (%s hits remaining)");
    }

    private void registerWithRune(String id, String name) {
        register(id, name);
        t.add("item.the-alchemists-touch.rune.effect." + id, "Alchemical Stones of " + name);
    }

    private void registerVanilla(String id, String name) {
        t.add("item.minecraft.potion.effect." + id, "Potion of " + name);
        t.add("item.minecraft.splash_potion.effect." + id, "Splash Potion of " + name);
        t.add("item.minecraft.lingering_potion.effect." + id, "Lingering Potion of " + name);
        t.add("item.minecraft.tipped_arrow.effect." + id, "Arrow of " + name);
    }

    private void registerVanillaReverse(String id, String name) {
        t.add("item.minecraft.potion.effect." + id,  name + " Potion");
        t.add("item.minecraft.splash_potion.effect." + id, name + " Splash Potion");
        t.add("item.minecraft.lingering_potion.effect." + id, name + " Lingering Potion");
        t.add("item.minecraft.tipped_arrow.effect." + id, name + " Arrow");
        t.add("item.the-alchemists-touch.syringe.effect." + id, name + " Syringe");
    }

    private void registerReverse(String id, String name) {
        registerVanillaReverse(id, name);
        t.add("effect.the-alchemists-touch." + id, name);
    }

    private void register(String id, String name) {
        registerVanilla(id, name);
        t.add("effect.the-alchemists-touch." + id, name);
        t.add("item.the-alchemists-touch.syringe.effect.the-alchemists-touch." + id, "Syringe of " + name);
    }
}
