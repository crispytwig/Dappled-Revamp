package com.crispytwig.dappledrevamp.worm.client;

import com.crispytwig.dappledrevamp.worm.Bait;

public final class WormClient {
    private WormClient() {
    }

    public static void init() {
        Bait.startBaitSound = BaitSoundInstance::play;
    }
}
