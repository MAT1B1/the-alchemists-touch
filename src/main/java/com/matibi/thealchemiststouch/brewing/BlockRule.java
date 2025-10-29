package com.matibi.thealchemiststouch.brewing;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;

public record BlockRule(RegistryEntry<Potion> input, Item ingredient, RegistryEntry<Potion> output) {}
