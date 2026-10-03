package com.crispytwig.dappledrevamp.registry;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.platform.registry.DeferredHolder;
import com.crispytwig.dappledrevamp.platform.registry.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, DappledRevamp.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> WORM_BAIT = register("item.worm.bait");
    public static final DeferredHolder<SoundEvent, SoundEvent> WORM_HOOK = register("item.worm.hook");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(DappledRevamp.location(name)));
    }
}
