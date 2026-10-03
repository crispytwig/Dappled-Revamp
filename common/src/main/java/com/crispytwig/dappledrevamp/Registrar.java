package com.crispytwig.dappledrevamp;

import net.minecraft.core.Registry;

import java.util.function.Supplier;

public interface Registrar {
    <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> factory);
}
