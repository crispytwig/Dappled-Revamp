package com.crispytwig.pleasance.mixin.client;

import com.crispytwig.pleasance.Pleasance;
import com.crispytwig.pleasance.world.entity.animal.fox.GreyFox;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.entity.state.FoxRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.fox.Fox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoxRenderer.class)
public abstract class FoxRendererMixin {
    @Unique private static final Identifier GREY_FOX = Pleasance.location("textures/entity/fox/grey_fox.png");
    @Unique private static final Identifier GREY_FOX_SLEEP = Pleasance.location("textures/entity/fox/grey_fox_sleep.png");
    @Unique private static final Identifier GREY_FOX_BABY = Pleasance.location("textures/entity/fox/grey_fox_baby.png");
    @Unique private static final Identifier GREY_FOX_SLEEP_BABY = Pleasance.location("textures/entity/fox/grey_fox_sleep_baby.png");

    @Inject(
        method = "extractRenderState(Lnet/minecraft/world/entity/animal/fox/Fox;Lnet/minecraft/client/renderer/entity/state/FoxRenderState;F)V",
        at = @At("TAIL")
    )
    private void pleasance$extractGrey(Fox fox, FoxRenderState state, float partialTicks, CallbackInfo ci) {
        ((GreyFox) state).pleasance$setGrey(((GreyFox) fox).pleasance$isGrey());
    }

    @Inject(
        method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/FoxRenderState;)Lnet/minecraft/resources/Identifier;",
        at = @At("HEAD"),
        cancellable = true
    )
    private void pleasance$greyTexture(FoxRenderState state, CallbackInfoReturnable<Identifier> cir) {
        if (state.variant != Fox.Variant.RED || !((GreyFox) state).pleasance$isGrey()) {
            return;
        }
        if (state.isSleeping) {
            cir.setReturnValue(state.isBaby ? GREY_FOX_SLEEP_BABY : GREY_FOX_SLEEP);
        } else {
            cir.setReturnValue(state.isBaby ? GREY_FOX_BABY : GREY_FOX);
        }
    }
}
