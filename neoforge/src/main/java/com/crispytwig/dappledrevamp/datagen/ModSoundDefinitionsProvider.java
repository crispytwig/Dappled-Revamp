package com.crispytwig.dappledrevamp.datagen;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.registry.ModSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public ModSoundDefinitionsProvider(PackOutput output) {
        super(output, DappledRevamp.MOD_ID);
    }

    @Override
    public void registerSounds() {
        add(ModSoundEvents.WORM_BAIT.get(), "item/worm/rod", 4);
        add(ModSoundEvents.WORM_HOOK.get(), "item/worm/hook", 5);
    }

    private void add(SoundEvent event, String path, int variants) {
        SoundDefinition definition = SoundDefinition.definition()
                .subtitle("subtitles." + DappledRevamp.MOD_ID + "." + event.location().getPath());
        for (int i = 1; i <= variants; i++) {
            definition.with(SoundDefinition.Sound.sound(DappledRevamp.location(path + i), SoundDefinition.SoundType.SOUND));
        }
        add(event, definition);
    }
}
