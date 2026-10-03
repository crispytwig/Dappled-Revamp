package com.crispytwig.dappledrevamp.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.function.Supplier;

public class ModCreativeTab {
    public static final ResourceKey<CreativeModeTab> NATURAL_BLOCKS = vanilla("natural_blocks");
    public static final ResourceKey<CreativeModeTab> FUNCTIONAL_BLOCKS = vanilla("functional_blocks");
    public static final ResourceKey<CreativeModeTab> TOOLS_AND_UTILITIES = vanilla("tools_and_utilities");
    public static final ResourceKey<CreativeModeTab> SPAWN_EGGS = vanilla("spawn_eggs");

    public static final List<Entry> ENTRIES = List.of(
            new Entry(TOOLS_AND_UTILITIES, () -> Items.FISHING_ROD, ModItems.BAITED_ROD),
            new Entry(TOOLS_AND_UTILITIES, ModItems.BAITED_ROD, ModItems.WORM),
            new Entry(TOOLS_AND_UTILITIES, ModItems.WORM, ModItems.WORM_BUCKET),
            new Entry(FUNCTIONAL_BLOCKS, () -> Items.COMPOSTER, ModBlocks.WORM_BIN),
            new Entry(SPAWN_EGGS, () -> Items.WOLF_SPAWN_EGG, ModItems.WORM_SPAWN_EGG),
            new Entry(NATURAL_BLOCKS, () -> Items.GRASS_BLOCK, ModBlocks.PATCHY_GRASS),
            new Entry(NATURAL_BLOCKS, () -> Items.PODZOL, ModBlocks.PATCHY_PODZOL),
            new Entry(NATURAL_BLOCKS, () -> Items.COARSE_DIRT, ModBlocks.REGOLITH)
    );

    private static ResourceKey<CreativeModeTab> vanilla(String name) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace(name));
    }

    public record Entry(ResourceKey<CreativeModeTab> tab, Supplier<? extends ItemLike> after, Supplier<? extends ItemLike> item) {
    }
}
