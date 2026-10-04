package com.crispytwig.pleasance.registry;

import com.crispytwig.pleasance.Pleasance;
import com.crispytwig.pleasance.platform.registry.DeferredHolder;
import com.crispytwig.pleasance.platform.registry.DeferredRegister;
import com.crispytwig.pleasance.world.level.levelgen.feature.CaveLiningFeature;
import com.crispytwig.pleasance.world.level.levelgen.feature.LeafPileFeature;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;

public class ModFeatures {
    public static final DeferredRegister<MapCodec<? extends Feature>> FEATURE_TYPES = DeferredRegister.create(BuiltInRegistries.FEATURE_TYPE, Pleasance.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends Feature>, MapCodec<CaveLiningFeature>> CAVE_LINING = FEATURE_TYPES.register("cave_lining",
            () -> CaveLiningFeature.CODEC);
    public static final DeferredHolder<MapCodec<? extends Feature>, MapCodec<LeafPileFeature>> LEAF_PILE = FEATURE_TYPES.register("leaf_pile",
            () -> LeafPileFeature.CODEC);
}
