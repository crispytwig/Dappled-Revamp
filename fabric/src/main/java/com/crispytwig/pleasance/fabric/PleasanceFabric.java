package com.crispytwig.pleasance.fabric;

import com.crispytwig.pleasance.Pleasance;
import com.crispytwig.pleasance.registry.ModCreativeTab;
import com.crispytwig.pleasance.registry.ModEntityTypes;
import com.crispytwig.pleasance.world.entity.animal.fox.GreyFox;
import com.crispytwig.pleasance.world.level.block.Moist;
import com.crispytwig.pleasance.world.level.block.PoplarColor;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

import java.util.List;
import java.util.function.Predicate;

public class PleasanceFabric implements ModInitializer, GreyFox.Storage {
    private static final AttachmentType<Boolean> GREY = AttachmentRegistry.create(
        Pleasance.location("grey"),
        builder -> builder
            .persistent(Codec.BOOL)
            .syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all())
    );

    @Override
    public void onInitialize() {
        Pleasance.init(this);
        Pleasance.bootstrap();

        Pleasance.createAttributes((type, builder) -> FabricDefaultAttributeRegistry.register(type, builder.build()));

        Pleasance.registerSpawnPlacements(SpawnPlacements::register);

        for (ModCreativeTab.Entry entry : ModCreativeTab.ENTRIES) {
            CreativeModeTabEvents.modifyOutputEvent(entry.tab()).register(output -> output.insertAfter(entry.after().get(), entry.item().get()));
        }

        CreativeModeTabEvents.modifyOutputEvent(ModCreativeTab.NATURAL_BLOCKS).register(output -> {
            ItemStack poplar = PoplarColor.ORANGE.sapling();
            output.insertBefore(poplar, PoplarColor.RED.sapling());
            output.insertAfter(poplar, PoplarColor.YELLOW.sapling());
        });
        CreativeModeTabEvents.MODIFY_OUTPUT_ALL.register((tab, output) ->
            Moist.insertMoistBefore(List.copyOf(output.getDisplayStacks()), output::insertBefore)
        );

        registerBiomeSpawns();
        registerBiomeFeatures();
    }

    private static void registerBiomeSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DAPPLED_FOREST), MobCategory.CREATURE, EntityTypes.WOLF, 5, 4, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DAPPLED_FOREST), MobCategory.AMBIENT, ModEntityTypes.WORM.get(), 10, 2, 4);
    }

    private static void registerBiomeFeatures() {
        Predicate<BiomeSelectionContext> dappledForest = BiomeSelectors.includeByKey(Biomes.DAPPLED_FOREST);
        for (String name : List.of("moss_patch", "hanging_roots", "red_mushroom", "patch_pumpkin", "patch_berry_bush", "patch_poplar_bush", "patch_sunflower", "patch_grass_dappled_forest", "flower_dappled_forest", "roof_plants")) {
            BiomeModifications.addFeature(dappledForest, GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registries.PLACED_FEATURE, Pleasance.location(name)));
        }
        BiomeModifications.addFeature(dappledForest, GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);
        BiomeModifications.addFeature(dappledForest, GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER);
        BiomeModifications.addFeature(dappledForest, GenerationStep.Decoration.UNDERGROUND_DECORATION,
            ResourceKey.create(Registries.PLACED_FEATURE, Pleasance.location("dirt_cave_lining")));
        BiomeModifications.addCarver(dappledForest, ResourceKey.create(Registries.CARVER, Pleasance.location("surface_cave")));
    }

    @Override
    public boolean isGrey(Fox fox) {
        return fox.getAttachedOrElse(GREY, false);
    }

    @Override
    public void setGrey(Fox fox, boolean grey) {
        fox.setAttached(GREY, grey ? Boolean.TRUE : null);
    }
}
