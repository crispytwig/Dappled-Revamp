package com.crispytwig.dappledrevamp.registry;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.platform.registry.DeferredHolder;
import com.crispytwig.dappledrevamp.platform.registry.DeferredRegister;
import com.crispytwig.dappledrevamp.world.entity.animal.worm.Worm;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, DappledRevamp.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<Worm>> WORM = register("worm", EntityType.Builder.of(Worm::new, MobCategory.AMBIENT).sized(0.8F, 0.5F).clientTrackingRange(10));

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String id, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, DappledRevamp.location(id));
        return ENTITY_TYPES.register(id, () -> builder.build(key));
    }
}
