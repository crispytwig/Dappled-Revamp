package com.crispytwig.dappledrevamp.datagen;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, DappledRevamp.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        tag(blockTag("minecraft:mineable/axe"))
                .add(block(ModBlocks.WORM_BIN.get()));

        tag(blockTag("minecraft:mineable/hoe"))
                .add(block(ModBlocks.RED_POPLAR_LEAF_LAYER.get()))
                .add(block(ModBlocks.ORANGE_POPLAR_LEAF_LAYER.get()))
                .add(block(ModBlocks.YELLOW_POPLAR_LEAF_LAYER.get()));

        tag(blockTag("minecraft:mineable/shovel"))
                .add(block(ModBlocks.PATCHY_GRASS.get()))
                .add(block(ModBlocks.REGOLITH.get()));

        tag(blockTag("minecraft:dirt"))
                .add(block(ModBlocks.REGOLITH.get()));

        for (String tag : new String[]{
                "animals_spawnable_on", "foxes_spawnable_on", "frogs_spawnable_on", "grass_blocks", "parrots_spawnable_on",
                "rabbits_spawnable_on", "sniffer_diggable_block", "supports_big_dripleaf", "turns_into_dirt_path",
                "turns_into_farmland", "valid_spawn", "wolves_spawnable_on"}) {
            tag(blockTag("minecraft:" + tag))
                    .add(block(ModBlocks.PATCHY_GRASS.get()));
        }

        for (String tag : new String[]{
                "cannot_replace_below_tree_trunk", "foxes_spawnable_on", "grass_blocks", "huge_brown_mushroom_can_place_on",
                "huge_red_mushroom_can_place_on", "mineable/shovel", "overrides_mushroom_light_requirement", "sniffer_diggable_block",
                "supports_big_dripleaf", "turns_into_dirt_path", "valid_spawn", "wolves_spawnable_on"}) {
            tag(blockTag("minecraft:" + tag))
                    .add(block(ModBlocks.PATCHY_PODZOL.get()));
        }
    }

    private static ResourceKey<Block> block(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
    }

    private static TagKey<Block> blockTag(String id) {
        return TagKey.create(Registries.BLOCK, Identifier.parse(id));
    }
}
