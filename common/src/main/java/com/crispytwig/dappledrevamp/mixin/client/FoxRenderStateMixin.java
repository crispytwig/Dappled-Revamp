package com.crispytwig.dappledrevamp.mixin.client;

import com.crispytwig.dappledrevamp.fox.GreyFoxRenderState;
import net.minecraft.client.renderer.entity.state.FoxRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FoxRenderState.class)
public abstract class FoxRenderStateMixin implements GreyFoxRenderState {
    @Unique
    private boolean dappledRevamp$grey;

    @Override
    public boolean dappledRevamp$isGrey() {
        return this.dappledRevamp$grey;
    }

    @Override
    public void dappledRevamp$setGrey(boolean grey) {
        this.dappledRevamp$grey = grey;
    }
}
