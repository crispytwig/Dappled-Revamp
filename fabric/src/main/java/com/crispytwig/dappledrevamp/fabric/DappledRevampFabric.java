package com.crispytwig.dappledrevamp.fabric;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.Registrar;
import com.crispytwig.dappledrevamp.fox.GreyFox;
import com.crispytwig.dappledrevamp.moist.DryRecipe;
import com.crispytwig.dappledrevamp.moist.Moist;
import com.crispytwig.dappledrevamp.moist.MoistenRecipe;
import com.crispytwig.dappledrevamp.poplar.PoplarColor;
import com.crispytwig.dappledrevamp.worm.Worm;
import com.crispytwig.dappledrevamp.worm.WormContent;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.List;
import java.util.function.Supplier;

public class DappledRevampFabric implements ModInitializer, GreyFox.Storage, Registrar {
    private static final ResourceKey<CreativeModeTab> NATURAL_BLOCKS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace("natural_blocks"));
    private static final AttachmentType<Boolean> GREY = AttachmentRegistry.create(
        DappledRevamp.id("grey"),
        builder -> builder
            .persistent(Codec.BOOL)
            .syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all())
    );

    @Override
    public void onInitialize() {
        DappledRevamp.init(this);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, DappledRevamp.id("moisten"), MoistenRecipe.SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, DappledRevamp.id("dry"), DryRecipe.SERIALIZER);

        for (String feature : new String[]{"moss_patch", "red_mushroom","patch_pumpkin", "patch_berry_bush", "patch_poplar_bush", "patch_sunflower"}) {
            BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.DAPPLED_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registries.PLACED_FEATURE, DappledRevamp.id(feature))
            );
        }

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DAPPLED_FOREST), MobCategory.CREATURE, EntityTypes.WOLF, 5, 4, 4);

        WormContent.register(this);
        FabricDefaultAttributeRegistry.register(WormContent.WORM.get(), Worm.createAttributes());
        SpawnPlacements.register(WormContent.WORM.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Worm::checkWormSpawnRules);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DAPPLED_FOREST), MobCategory.AMBIENT, WormContent.WORM.get(), 10, 2, 4);
        for (WormContent.TabEntry entry : WormContent.TAB_ENTRIES) {
            CreativeModeTabEvents.modifyOutputEvent(entry.tab()).register(output -> output.insertAfter(entry.after().get(), entry.item().get()));
        }

        CreativeModeTabEvents.modifyOutputEvent(NATURAL_BLOCKS).register(output -> {
            ItemStack poplar = PoplarColor.ORANGE.sapling();
            output.insertBefore(poplar, PoplarColor.RED.sapling());
            output.insertAfter(poplar, PoplarColor.YELLOW.sapling());
        });
        CreativeModeTabEvents.MODIFY_OUTPUT_ALL.register((tab, output) ->
            Moist.insertMoistBefore(List.copyOf(output.getDisplayStacks()), output::insertBefore)
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> factory) {
        T value = Registry.register((Registry<T>) registry, DappledRevamp.id(name), factory.get());
        return () -> value;
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
