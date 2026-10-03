package com.crispytwig.pleasance.datagen;

import com.crispytwig.pleasance.tags.ModBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends TagsProvider<Biome> {
    public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.BIOME, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        tag(ModBiomeTags.SPAWNS_GREY_FOXES)
                .add(Biomes.DAPPLED_FOREST);

        tag(biomeTag("pleasance:has_structure/village_dappled"))
                .add(Biomes.DAPPLED_FOREST);
    }

    private static TagKey<Biome> biomeTag(String id) {
        return TagKey.create(Registries.BIOME, Identifier.parse(id));
    }
}
