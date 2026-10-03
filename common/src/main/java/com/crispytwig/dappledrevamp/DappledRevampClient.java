package com.crispytwig.dappledrevamp;

import com.crispytwig.dappledrevamp.client.resources.sounds.BaitSoundInstance;
import com.crispytwig.dappledrevamp.world.item.Bait;

public final class DappledRevampClient {
    private DappledRevampClient() {
    }

    public static void init() {
        Bait.startBaitSound = BaitSoundInstance::play;
    }
}
