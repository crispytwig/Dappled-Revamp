package com.crispytwig.pleasance.mixin.client;

import com.crispytwig.pleasance.client.renderer.entity.state.BaitingRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FirstPersonHandsAndItemsRenderer.class)
public abstract class FirstPersonHandsAndItemsRendererMixin {
    @Inject(
        method = "submitArmWithItem",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;III)V"
        )
    )
    private void pleasance$baitingPose(
        PlayerRenderState playerState, FirstPersonHandsAndItemsRenderState state, float partialTicks, float xRot, InteractionHand hand,
        float attack, ItemStack itemStack, float inverseArmHeight, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
        int lightCoords, CallbackInfo ci
    ) {
        AvatarRenderState avatar = playerState.avatarRenderState;
        if (avatar == null || !((BaitingRenderState) avatar).pleasance$isBaiting()) {
            return;
        }
        HumanoidArm arm = hand.asArm(avatar.mainArm);
        int invert = arm == HumanoidArm.RIGHT ? 1 : -1;
        if (hand == avatar.useItemHand) {
            poseStack.translate(invert * -0.4F, 0.1F, -0.3F);
        } else {
            poseStack.translate(invert * -0.5F, Mth.sin(avatar.ageInTicks) * 0.1F, -0.2F);
            poseStack.rotateDegrees(Axis.XP, Mth.cos(avatar.ageInTicks) * 10.0F);
        }
        poseStack.rotateDegrees(Axis.YP, invert * 45.0F);
    }
}
