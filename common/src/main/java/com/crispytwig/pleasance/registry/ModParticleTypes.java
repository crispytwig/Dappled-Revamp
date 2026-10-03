package com.crispytwig.pleasance.registry;

import com.crispytwig.pleasance.Pleasance;
import com.crispytwig.pleasance.platform.registry.DeferredHolder;
import com.crispytwig.pleasance.platform.registry.DeferredRegister;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Pleasance.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RED_POPLAR_LEAF_BURST = register("red_poplar_leaf_burst");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ORANGE_POPLAR_LEAF_BURST = register("orange_poplar_leaf_burst");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YELLOW_POPLAR_LEAF_BURST = register("yellow_poplar_leaf_burst");

    private static DeferredHolder<ParticleType<?>, SimpleParticleType> register(String name) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(false) {});
    }
}
