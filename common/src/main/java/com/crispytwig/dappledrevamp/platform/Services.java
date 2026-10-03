package com.crispytwig.dappledrevamp.platform;

import com.crispytwig.dappledrevamp.DappledRevamp;

import java.util.ServiceLoader;

public final class Services {
    private Services() {}

    public static <T> T load(Class<T> clazz) {
        final T service = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        DappledRevamp.LOGGER.debug("Loaded {} for service {}", service, clazz);
        return service;
    }
}
