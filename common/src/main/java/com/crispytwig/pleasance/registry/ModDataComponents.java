package com.crispytwig.pleasance.registry;

import com.crispytwig.pleasance.Pleasance;
import com.crispytwig.pleasance.platform.registry.DeferredHolder;
import com.crispytwig.pleasance.platform.registry.DeferredRegister;
import com.crispytwig.pleasance.world.item.WormBucketItem;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Pleasance.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> WORM_COUNT = DATA_COMPONENT_TYPES.register("worm_count",
            () -> DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(1, WormBucketItem.MAX_WORMS))
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build());
}
