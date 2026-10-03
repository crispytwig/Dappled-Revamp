package com.crispytwig.pleasance.mixin.client;

import com.crispytwig.pleasance.world.entity.animal.fox.GreyFox;
import net.minecraft.client.renderer.entity.state.FoxRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FoxRenderState.class)
public abstract class FoxRenderStateMixin implements GreyFox {
    @Unique
    private boolean pleasance$grey;

    @Override
    public boolean pleasance$isGrey() {
        return this.pleasance$grey;
    }

    @Override
    public void pleasance$setGrey(boolean grey) {
        this.pleasance$grey = grey;
    }
}
