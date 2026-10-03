package com.crispytwig.pleasance.client.resources.sounds;

import com.crispytwig.pleasance.registry.ModSoundEvents;
import com.crispytwig.pleasance.world.item.Bait;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class BaitSoundInstance extends AbstractTickableSoundInstance {
    private final Player player;

    private BaitSoundInstance(Player player) {
        super(ModSoundEvents.WORM_BAIT.get(), SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.player = player;
        this.updatePosition();
    }

    public static void play(Player player) {
        Minecraft.getInstance().getSoundManager().play(new BaitSoundInstance(player));
    }

    @Override
    public void tick() {
        if (this.player.isRemoved() || !Bait.isBaiting(this.player)) {
            this.stop();
            return;
        }
        this.updatePosition();
    }

    private void updatePosition() {
        this.x = this.player.getX();
        this.y = this.player.getY();
        this.z = this.player.getZ();
    }
}
