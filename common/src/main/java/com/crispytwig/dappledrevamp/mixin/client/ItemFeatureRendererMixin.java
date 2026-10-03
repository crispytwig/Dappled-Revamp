package com.crispytwig.dappledrevamp.mixin.client;

import com.crispytwig.dappledrevamp.tint.TintOverlays;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemFeatureRenderer.class)
public abstract class ItemFeatureRendererMixin {
    @ModifyReturnValue(
        method = "getLayerColorSafe([ILnet/minecraft/client/resources/model/geometry/BakedQuad$MaterialInfo;)I",
        at = @At("RETURN")
    )
    private static int dappledRevamp$tintOverlayColor(int color, @Local(argsOnly = true) BakedQuad.MaterialInfo material) {
        return material.tintIndex() == TintOverlays.TINT_INDEX ? TintOverlays.defaultColor() : color;
    }
}
