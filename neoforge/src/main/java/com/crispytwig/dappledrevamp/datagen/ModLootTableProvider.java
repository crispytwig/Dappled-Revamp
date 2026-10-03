package com.crispytwig.dappledrevamp.datagen;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class ModLootTableProvider {
    public static LootTableProvider create() {
        return new LootTableProvider(Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootProvider::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(ModEntityLootProvider::new, LootContextParamSets.ENTITY)
        ));
    }
}
