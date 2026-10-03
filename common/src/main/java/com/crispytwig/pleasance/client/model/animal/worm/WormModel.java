package com.crispytwig.pleasance.client.model.animal.worm;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;

public class WormModel extends EntityModel<LivingEntityRenderState> {
    private static final AnimationChannel.Interpolation SMOOTH = AnimationChannel.Interpolations.CATMULLROM;

    private static final AnimationDefinition CRAWL = AnimationDefinition.Builder.withLength(0.6667F)
        .looping()
        .addAnimation("root", new AnimationChannel(
            AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.4F), SMOOTH),
            new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.5F), SMOOTH),
            new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.4F), SMOOTH),
            new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.4F), SMOOTH)
        ))
        .addAnimation("root", new AnimationChannel(
            AnimationChannel.Targets.SCALE,
            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.9), SMOOTH),
            new Keyframe(0.2083F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.2), SMOOTH),
            new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), SMOOTH),
            new Keyframe(0.5833F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.9), SMOOTH),
            new Keyframe(0.6667F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.9), SMOOTH)
        ))
        .addAnimation("middle", new AnimationChannel(
            AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.5F), SMOOTH),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.5F), SMOOTH),
            new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.5F), SMOOTH)
        ))
        .addAnimation("middle_back", new AnimationChannel(
            AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), SMOOTH)
        ))
        .addAnimation("middle_back", new AnimationChannel(
            AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 4.0F, -1.75F), SMOOTH),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 4.0F, -1.75F), SMOOTH),
            new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 4.0F, -1.75F), SMOOTH)
        ))
        .addAnimation("tail", new AnimationChannel(
            AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.5833F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), SMOOTH)
        ))
        .addAnimation("tail", new AnimationChannel(
            AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.35355F, 0.0F), SMOOTH),
            new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.27239F, -0.03827F), SMOOTH),
            new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.04102F, -0.00987F), SMOOTH),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.01247F, -0.04294F), SMOOTH),
            new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.17029F, -0.01797F), SMOOTH),
            new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.35355F, 0.0F), SMOOTH),
            new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.35355F, 0.0F), SMOOTH)
        ))
        .addAnimation("tail", new AnimationChannel(
            AnimationChannel.Targets.SCALE,
            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.02, 1.02, 1.02), SMOOTH)
        ))
        .addAnimation("middle_front", new AnimationChannel(
            AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.5833F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), SMOOTH)
        ))
        .addAnimation("middle_front", new AnimationChannel(
            AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.75F, -1.0F), SMOOTH),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), SMOOTH),
            new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 0.75F, -1.0F), SMOOTH),
            new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.75F, -1.0F), SMOOTH)
        ))
        .build();

    private final KeyframeAnimation crawlAnimation;

    public WormModel(ModelPart root) {
        super(root);
        this.crawlAnimation = CRAWL.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot().addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 22.75F, -1.375F));
        PartDefinition headRot = root.addOrReplaceChild("head_rot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.25F, -3.0F));

        PartDefinition head = headRot.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild(
            "head_segment",
            CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -9.0F, -0.75F, 2.0F, 6.0F, 2.0F),
            PartPose.offsetAndRotation(0.0F, -0.25F, 3.0F, 90.0F * Mth.DEG_TO_RAD, 0.0F, -180.0F * Mth.DEG_TO_RAD)
        );

        PartDefinition middle = headRot.addOrReplaceChild("middle", CubeListBuilder.create(), PartPose.offset(0.0F, -0.25F, 3.0F));
        PartDefinition middleBack = middle.addOrReplaceChild("middle_back", CubeListBuilder.create(), PartPose.offset(0.0F, 0.25F, 0.0F));
        middleBack.addOrReplaceChild(
            "middle_back_segment",
            CubeListBuilder.create().texOffs(11, 9).addBox(-1.0F, -6.0F, -0.75F, 2.0F, 6.0F, 2.0F),
            PartPose.offsetAndRotation(0.0F, -0.25F, 0.0F, -90.0F * Mth.DEG_TO_RAD, 0.0F, 0.0F)
        );
        PartDefinition tail = middleBack.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 6.0F));
        tail.addOrReplaceChild(
            "tail_segment",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -12.0F, -0.75F, 2.0F, 6.0F, 2.0F),
            PartPose.offsetAndRotation(0.0F, -0.25F, -6.0F, -90.0F * Mth.DEG_TO_RAD, 0.0F, 0.0F)
        );

        PartDefinition middleFront = middle.addOrReplaceChild("middle_front", CubeListBuilder.create(), PartPose.offset(0.0F, -0.25F, -1.5F));
        middleFront.addOrReplaceChild(
            "middle_front_segment",
            CubeListBuilder.create().texOffs(8, 0).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F),
            PartPose.rotation(-180.0F * Mth.DEG_TO_RAD, 0.0F, 180.0F * Mth.DEG_TO_RAD)
        );
        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);
        float moving = Math.min(state.walkAnimationSpeed * 8.0F, 1.0F);
        this.crawlAnimation.apply((long) (state.ageInTicks * 50.0F), moving * 1.2F);
    }
}
