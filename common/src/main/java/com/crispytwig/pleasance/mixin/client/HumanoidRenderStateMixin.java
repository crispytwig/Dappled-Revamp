package com.crispytwig.pleasance.mixin.client;

import com.crispytwig.pleasance.client.renderer.entity.state.BaitingRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(HumanoidRenderState.class)
public abstract class HumanoidRenderStateMixin implements BaitingRenderState {
    @Unique
    private boolean pleasance$baiting;

    @Override
    public boolean pleasance$isBaiting() {
        return this.pleasance$baiting;
    }

    @Override
    public void pleasance$setBaiting(boolean baiting) {
        this.pleasance$baiting = baiting;
    }
}
