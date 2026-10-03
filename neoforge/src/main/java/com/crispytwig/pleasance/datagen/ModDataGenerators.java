package com.crispytwig.pleasance.datagen;

import com.crispytwig.pleasance.Pleasance;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

public class ModDataGenerators {
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModLanguageProvider::new);
        event.createProvider(ModSoundDefinitionsProvider::new);

        event.createProvider(ModBlockTagsProvider::new);
        event.createProvider(ModItemTagsProvider::new);
        event.createProvider(ModBiomeTagsProvider::new);

        event.createReloadableRegistryObjects(new RegistrySetBuilder()
                .add(Registries.LOOT_TABLE, ModLootTableProvider.create()::run)
                .add(ModRecipeProvider.create()), Set.of(Pleasance.MOD_ID, "minecraft"));
    }
}
