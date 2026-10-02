package com.crispytwig.dappledrevamp;

import com.crispytwig.dappledrevamp.fox.GreyFoxStorage;
import net.minecraft.resources.Identifier;

public final class DappledRevamp {
    public static final String MOD_ID = "dappledrevamp";

    private static GreyFoxStorage greyFoxStorage;

    private DappledRevamp() {
    }

    public static void init(GreyFoxStorage storage) {
        greyFoxStorage = storage;
    }

    public static GreyFoxStorage greyFoxStorage() {
        return greyFoxStorage;
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
