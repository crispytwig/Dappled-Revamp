package com.crispytwig.dappledrevamp.datagen;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.registry.ModBlocks;
import com.crispytwig.dappledrevamp.registry.ModItems;
import com.crispytwig.dappledrevamp.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, DappledRevamp.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        tag(ModItemTags.FISHING_BAIT)
                .add(item(ModItems.WORM.get()))
                .add(item(ModItems.WORM_BUCKET.get()));

        tag(itemTag("minecraft:chicken_food"))
                .add(item(ModItems.WORM.get()));

        tag(itemTag("minecraft:enchantable/durability"))
                .add(item(ModItems.BAITED_ROD.get()));

        tag(itemTag("minecraft:enchantable/fishing"))
                .add(item(ModItems.BAITED_ROD.get()));

        tag(itemTag("minecraft:dirt"))
                .add(item(ModBlocks.REGOLITH.get()));

        tag(itemTag("minecraft:grass_blocks"))
                .add(item(ModBlocks.PATCHY_GRASS.get()))
                .add(item(ModBlocks.PATCHY_PODZOL.get()));
    }

    private static ResourceKey<Item> item(ItemLike item) {
        return BuiltInRegistries.ITEM.getResourceKey(item.asItem()).orElseThrow();
    }

    private static TagKey<Item> itemTag(String id) {
        return TagKey.create(Registries.ITEM, Identifier.parse(id));
    }
}
