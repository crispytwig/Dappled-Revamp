package com.crispytwig.dappledrevamp.mixin.client;

import com.crispytwig.dappledrevamp.worm.client.BaitingRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(HumanoidRenderState.class)
public abstract class HumanoidRenderStateMixin implements BaitingRenderState {
    @Unique
    private boolean dappledRevamp$baiting;

    @Override
    public boolean dappledRevamp$isBaiting() {
        return this.dappledRevamp$baiting;
    }

    @Override
    public void dappledRevamp$setBaiting(boolean baiting) {
        this.dappledRevamp$baiting = baiting;
    }
}
