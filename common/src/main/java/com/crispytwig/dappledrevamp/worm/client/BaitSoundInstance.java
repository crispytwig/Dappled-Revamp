package com.crispytwig.dappledrevamp.worm.client;

import com.crispytwig.dappledrevamp.worm.Bait;
import com.crispytwig.dappledrevamp.worm.WormContent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.world.entity.player.Player;

public class BaitSoundInstance extends AbstractTickableSoundInstance {
    private final Player player;

    private BaitSoundInstance(Player player) {
        super(WormContent.BAIT_SOUND.get(), SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
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
