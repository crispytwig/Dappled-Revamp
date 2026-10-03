package com.crispytwig.dappledrevamp.mixin.client;

import com.crispytwig.dappledrevamp.tint.TintOverlays;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModelBlockRenderer.class)
public abstract class ModelBlockRendererMixin {
    @ModifyReturnValue(method = "computeTintColor", at = @At("RETURN"))
    private int dappledRevamp$tintOverlayColor(
        int color,
        @Local(argsOnly = true) BlockAndTintGetter level,
        @Local(argsOnly = true) BlockState state,
        @Local(argsOnly = true) BlockPos pos,
        @Local(argsOnly = true) int tintIndex
    ) {
        return tintIndex == TintOverlays.TINT_INDEX ? TintOverlays.TINT.colorInWorld(state, level, pos) : color;
    }
}
