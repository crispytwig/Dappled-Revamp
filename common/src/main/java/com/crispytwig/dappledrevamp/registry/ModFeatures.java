package com.crispytwig.dappledrevamp.registry;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.platform.registry.DeferredHolder;
import com.crispytwig.dappledrevamp.platform.registry.DeferredRegister;
import com.crispytwig.dappledrevamp.world.level.levelgen.feature.CaveLiningFeature;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;

public class ModFeatures {
    public static final DeferredRegister<MapCodec<? extends Feature>> FEATURE_TYPES = DeferredRegister.create(BuiltInRegistries.FEATURE_TYPE, DappledRevamp.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends Feature>, MapCodec<CaveLiningFeature>> CAVE_LINING = FEATURE_TYPES.register("cave_lining",
            () -> CaveLiningFeature.CODEC);
}
