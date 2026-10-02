package com.crispytwig.dappledrevamp.mixin;

import com.crispytwig.dappledrevamp.fox.GreyFoxGroup;
import net.minecraft.world.entity.animal.fox.Fox;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Fox.FoxGroupData.class)
public abstract class FoxGroupDataMixin implements GreyFoxGroup {
    @Unique
    private @Nullable Boolean dappledRevamp$grey;

    @Override
    public @Nullable Boolean dappledRevamp$getGrey() {
        return this.dappledRevamp$grey;
    }

    @Override
    public void dappledRevamp$setGrey(boolean grey) {
        this.dappledRevamp$grey = grey;
    }
}
