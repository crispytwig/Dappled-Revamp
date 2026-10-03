package com.crispytwig.dappledrevamp;

import com.crispytwig.dappledrevamp.fox.GreyFox;
import net.minecraft.resources.Identifier;

public final class DappledRevamp {
    public static final String MOD_ID = "dappledrevamp";

    private static GreyFox.Storage greyFoxStorage;

    private DappledRevamp() {
    }

    public static void init(GreyFox.Storage storage) {
        greyFoxStorage = storage;
    }

    public static GreyFox.Storage greyFoxStorage() {
        return greyFoxStorage;
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
