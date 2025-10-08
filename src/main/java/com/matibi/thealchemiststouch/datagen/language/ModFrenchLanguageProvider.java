package com.matibi.thealchemiststouch.datagen.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModFrenchLanguageProvider extends FabricLanguageProvider {
    TranslationBuilder t;

    public ModFrenchLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "fr_fr", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder t_param) {
        t = t_param;
        // Effets vanilla déjà existants
        registerVanilla("levitation", "de Lévitation");
        registerVanilla("glowing", "de Surbrillance");
        registerVanilla("darkness", "de Obscurité");
        registerVanilla("haste", "de Célérité");
        registerVanilla("mining_fatigue", "de Fatigue de minage");
        t.add("item.minecraft.potion.effect.alcohol", "Alcool");
        t.add("item.minecraft.splash_potion.effect.alcohol", "Alcohol jetable");
        t.add("item.minecraft.lingering_potion.effect.alcohol", "Alcool persistant");
        t.add("item.minecraft.tipped_arrow.effect.alcohol", "Flèche d'Alcool");

        // Effets custom simples (pas de rune)
        register("saturation", "Saturation");
        register("long_leg", "Grandes jambes");
        register("liquid_walker", "Marche sur liquide");
        register("ore_sense", "Détection de minerais");
        register("resonance", "Résonance");
        register("reactivation", "Réactivation");
        register("purification", "Purification");
        register("teleportation", "Téléportation");
        register("thorns", "Épines", "d'Épines");
        register("brain_washing", "Lavage de cerveau");
        register("frost", "Givre");
        register("death", "Mort");
        register("double_health", "Double vie");
        register("resurrection", "Résurrection");
        register("infinity", "Infinité","d'Infinité");
        register("long_cooldown", "Cooldown allongé");
        register("short_cooldown", "Cooldown réduit");
        register("masking", "Voile d'oublie");
        register("unstable", "Instable", "Instable");
        register("vampirism", "Vampirisme");
        register("stun", "Étourdissement", "d'Étourdissement");
        register("no_interaction", "Mains liées");
        register("aftermath", "Contrecoup");
        register("berserk", "Berserk");
        register("ghost_walk", "Marche spectral");
        register("dwarf", "Nanisme");
        register("photosynthesis", "Photosynthèse");
        register("oblivion", "Oubli", "d'Oubli");
        register("spider_legs", "Pattes d’araignée");
        register("rust", "Rouille");

        // Potions permanentes
        register("perm_health", "Vie Permanente");
        register("perm_strength", "Force Permanente");
        register("perm_speed", "Vitesse Permanente");

        // Effets custom avec rune
        registerWithAlchemicalStone("giant", "Géant");
        registerWithAlchemicalStone("petrification", "Pétrification");
        registerWithAlchemicalStone("acid", "Acidité", "d'Acidité");
        registerWithAlchemicalStone("ignition", "Ignition", "d'Ignition");
        registerWithAlchemicalStone("alchemist", "Alchimiste", "de l'Alchimiste");

        // ---- Autres items qui ne sont pas des effets ----
        t.add("item.the-alchemists-touch.poisonous_carrot", "Carotte empoisonnée");
        t.add("item.the-alchemists-touch.poisonous_beetroot", "Betterave empoisonnée");
        t.add("item.the-alchemists-touch.alchemist_core", "Noyau d'Alchimiste");
        t.add("item.the-alchemists-touch.claw", "Griffe");
        t.add("item.the-alchemists-touch.zombie_brain", "Cerveau de zombie");
        t.add("item.the-alchemists-touch.leaf", "Feuille");
        t.add("item.the-alchemists-touch.witch_finger", "Doigt de sorcière");
        t.add("item.the-alchemists-touch.blood_bag", "Poche de sang");
        t.add("item.the-alchemists-touch.alchemical_stone.effect.empty", "Pierre alchimique");
        t.add("item.the-alchemists-touch.alchemical_stone.effect.mixed", "Pierre alchimique multi-effest");
        registerVanilla("mixed", "multi-effets");

        // Seringue
        for (var entry : Registries.STATUS_EFFECT) {
            Identifier id = Registries.STATUS_EFFECT.getId(entry);
            if (id == null || !id.getNamespace().equals("minecraft")) continue;
            String translationKey = "item.the-alchemists-touch.syringe.effect." + id.toTranslationKey();
            String effectName = Text.translatable(entry.getTranslationKey()).getString();
            t.add(translationKey, "Seringue de " + effectName);
        }
        t.add("item.the-alchemists-touch.syringe", "Seringue");
        t.add("item.the-alchemists-touch.syringe.effect.empty", "Seringue");
        t.add("the-alchemists-touch.blood_type.unknown", "Inconnu");
        t.add("the-alchemists-touch.blood_type.human", "Sang humain");
        t.add("the-alchemists-touch.blood_type.monster", "Sang de monstre");

        // Messages spéciaux
        t.add("item.the-alchemists-touch.alchemical_stone.block_only", "Les pierres alchimiques ne peuvent être utilisées que sur des blocs");
        t.add("item.the-alchemists-touch.alchemical_stone.block_not_good", "Le bloc n'est pas compatible");
        t.add("itemGroup.the-alchemists-touch.alchemy", "Alchimie");
        t.add("splash.the-alchemists-touch.magic", "L'Alchimie c'est le pouvoir !!!");
        t.add("splash.the-alchemists-touch.thanks", "Merci de me supporter LivelyBadGood");
        t.add("tooltip.the-alchemists-touch.imbued_line", "%s (%s coups restants)");
    }

    private void registerWithAlchemicalStone(String id, String name) {
        registerWithAlchemicalStone(id, name, "de " + name);
    }

    private void registerWithAlchemicalStone(String id, String effect_name, String name) {
        register(id, effect_name, name);
        t.add("item.the-alchemists-touch.alchemical_stone.effect." + id, "Pierre " + name);
    }

    private void registerVanilla(String id, String name) {
        t.add("item.minecraft.potion.effect." + id, "Potion " + name);
        t.add("item.minecraft.splash_potion.effect." + id, "Potion jetable " + name);
        t.add("item.minecraft.lingering_potion.effect." + id, "Potion persistante " + name);
        t.add("item.minecraft.tipped_arrow.effect." + id, "Flèche " + name);
    }

    private void register(String id, String name) {
        register(id, name, "de " + name);
    }

    private void register(String id, String effect_name, String name) {
        registerVanilla(id, name);
        t.add("effect.the-alchemists-touch." + id, effect_name);
        t.add("item.the-alchemists-touch.syringe.effect.the-alchemists-touch." + id, "Seringue " + name);
    }
}
