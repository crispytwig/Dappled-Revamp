package com.crispytwig.dappledrevamp.mixin.client;

import com.crispytwig.dappledrevamp.moist.Moist;
import com.crispytwig.dappledrevamp.tint.TintOverlays;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.geometry.ItemQuads;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CuboidItemModelWrapper.class)
public abstract class CuboidItemModelWrapperMixin {
    @Unique
    private static final Object dappledRevamp$MOIST_IDENTITY = new Object();

    @Shadow @Final private ItemQuads itemQuads;

    @Unique
    private volatile @Nullable ItemQuads dappledRevamp$withoutOverlays;

    @Inject(method = "update", at = @At("RETURN"))
    private void dappledRevamp$hideMoistOverlays(
        ItemStackRenderState output, ItemStack item, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable ClientLevel level,
        @Nullable ItemOwner owner, int seed, CallbackInfo ci, @Local ItemStackRenderState.LayerRenderState layer
    ) {
        if (!Moist.isMoist(item)) {
            return;
        }
        ItemQuads quads = this.dappledRevamp$withoutOverlays;
        if (quads == null) {
            quads = ItemQuads.split(TintOverlays.withoutOverlays(this.itemQuads.all()));
            this.dappledRevamp$withoutOverlays = quads;
        }
        layer.setQuads(quads);
        output.appendModelIdentityElement(dappledRevamp$MOIST_IDENTITY);
    }
}
