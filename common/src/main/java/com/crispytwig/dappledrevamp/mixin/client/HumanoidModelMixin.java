package com.crispytwig.dappledrevamp.mixin.client;

import com.crispytwig.dappledrevamp.client.renderer.entity.state.BaitingRenderState;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin {
    @Shadow @Final public ModelPart rightArm;
    @Shadow @Final public ModelPart leftArm;

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At("TAIL"))
    private void dappledRevamp$baitingPose(HumanoidRenderState state, CallbackInfo ci) {
        if (!((BaitingRenderState) state).dappledRevamp$isBaiting()) {
            return;
        }
        boolean rodInRightHand = state.useItemHand.asArm(state.mainArm) == HumanoidArm.RIGHT;
        ModelPart rodArm = rodInRightHand ? this.rightArm : this.leftArm;
        ModelPart baitArm = rodInRightHand ? this.leftArm : this.rightArm;
        rodArm.yRot = rodInRightHand ? -0.8F : 0.8F;
        rodArm.xRot = -0.97079635F;
        baitArm.xRot = rodArm.xRot + Mth.sin(state.ageInTicks) * 0.2F;
    }
}
