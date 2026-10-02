package com.crispytwig.dappledrevamp.fabric;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.fox.GreyFoxStorage;
import com.crispytwig.dappledrevamp.poplar.PoplarColor;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class DappledRevampFabric implements ModInitializer, GreyFoxStorage {
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

        for (String feature : new String[]{"moss_patch", "red_mushroom","patch_pumpkin", "patch_berry_bush"}) {
            BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.DAPPLED_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registries.PLACED_FEATURE, DappledRevamp.id(feature))
            );
        }

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DAPPLED_FOREST), MobCategory.CREATURE, EntityTypes.WOLF, 5, 4, 4);

        CreativeModeTabEvents.modifyOutputEvent(NATURAL_BLOCKS).register(output -> {
            ItemStack poplar = PoplarColor.ORANGE.sapling();
            output.insertBefore(poplar, PoplarColor.RED.sapling());
            output.insertAfter(poplar, PoplarColor.YELLOW.sapling());
        });
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
