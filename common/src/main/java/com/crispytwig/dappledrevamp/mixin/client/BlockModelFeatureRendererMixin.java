package com.crispytwig.dappledrevamp.mixin.client;

import com.crispytwig.dappledrevamp.tint.TintOverlays;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.QuadInstance;
import net.minecraft.client.renderer.feature.BlockModelFeatureRenderer;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BlockModelFeatureRenderer.class)
public abstract class BlockModelFeatureRendererMixin {
    @ModifyArg(method = "putQuad", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/QuadInstance;setColor(I)V"))
    private static int dappledRevamp$tintOverlayColor(int color, @Local(argsOnly = true) BakedQuad quad, @Local(argsOnly = true) int baseTintColor) {
        return TintOverlays.isOverlay(quad) ? ARGB.multiply(baseTintColor, TintOverlays.defaultColor()) : color;
    }
}
