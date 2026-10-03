package com.crispytwig.dappledrevamp.mixin.client;

import com.crispytwig.dappledrevamp.client.renderer.entity.state.BaitingRenderState;
import com.crispytwig.dappledrevamp.world.item.Bait;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidMobRenderer.class)
public abstract class HumanoidMobRendererMixin {
    @Inject(method = "extractHumanoidRenderState", at = @At("TAIL"))
    private static void dappledRevamp$extractBaiting(
        LivingEntity entity, HumanoidRenderState state, float partialTicks, ItemModelResolver itemModelResolver, CallbackInfo ci
    ) {
        ((BaitingRenderState) state).dappledRevamp$setBaiting(Bait.isBaiting(entity));
    }
}
