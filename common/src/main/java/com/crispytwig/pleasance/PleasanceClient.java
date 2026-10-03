package com.crispytwig.pleasance;

import com.crispytwig.pleasance.client.resources.sounds.BaitSoundInstance;
import com.crispytwig.pleasance.world.item.Bait;

public final class PleasanceClient {
    private PleasanceClient() {
    }

    public static void init() {
        Bait.startBaitSound = BaitSoundInstance::play;
    }
}
