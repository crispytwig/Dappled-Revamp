package com.crispytwig.dappledrevamp.tags;

import com.crispytwig.dappledrevamp.DappledRevamp;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

@SuppressWarnings("unused")
public class ModBiomeTags {
    public static final TagKey<Biome> SPAWNS_GREY_FOXES = tag("spawns_grey_foxes");

    private static TagKey<Biome> tag(String name) {
        return TagKey.create(Registries.BIOME, DappledRevamp.location(name));
    }
}
