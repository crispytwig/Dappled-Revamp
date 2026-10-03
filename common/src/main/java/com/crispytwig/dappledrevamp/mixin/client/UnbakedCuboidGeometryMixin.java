package com.crispytwig.dappledrevamp.mixin.client;

import com.crispytwig.dappledrevamp.client.renderer.block.TintOverlays;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.renderer.block.dispatch.ModelState;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.cuboid.CuboidFace;
import net.minecraft.client.resources.model.cuboid.CuboidRotation;
import net.minecraft.client.resources.model.cuboid.FaceBakery;
import net.minecraft.client.resources.model.cuboid.UnbakedCuboidGeometry;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(UnbakedCuboidGeometry.class)
public abstract class UnbakedCuboidGeometryMixin {
    @Unique
    private static final String BAKE = "bake(Ljava/util/List;Lnet/minecraft/client/resources/model/sprite/TextureSlots;Lnet/minecraft/client/resources/model/ModelBaker;Lnet/minecraft/client/renderer/block/dispatch/ModelState;Lnet/minecraft/client/resources/model/ModelDebugName;)Lnet/minecraft/client/resources/model/geometry/QuadCollection;";

    @WrapOperation(
        method = BAKE,
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/cuboid/FaceBakery;bakeQuad(Lnet/minecraft/client/resources/model/ModelBaker;Lorg/joml/Vector3fc;Lorg/joml/Vector3fc;Lnet/minecraft/client/resources/model/cuboid/CuboidFace;Lnet/minecraft/client/resources/model/sprite/Material$Baked;Lnet/minecraft/core/Direction;Lnet/minecraft/client/renderer/block/dispatch/ModelState;Lnet/minecraft/client/resources/model/cuboid/CuboidRotation;Lnet/minecraft/core/Direction;I)Lnet/minecraft/client/resources/model/geometry/BakedQuad;"
        )
    )
    private static BakedQuad dappledRevamp$bakeTintOverlay(
        ModelBaker modelBaker, Vector3fc from, Vector3fc to, CuboidFace face, Material.Baked material, Direction facing, ModelState modelState,
        CuboidRotation elementRotation, Direction shadeDirectionOverride, int lightEmission, Operation<BakedQuad> original,
        @Share("tintOverlay") LocalRef<BakedQuad> tintOverlay
    ) {
        Material.Baked overlay = ((TintOverlays.Lookup) modelBaker.materials()).dappledRevamp$tintOverlayFor(material);
        if (overlay != null) {
            CuboidFace overlayFace = new CuboidFace(face.cullForDirection(), TintOverlays.TINT_INDEX, face.texture(), face.uvs(), face.rotation());
            tintOverlay.set(original.call(modelBaker, from, to, overlayFace, overlay, facing, modelState, elementRotation, shadeDirectionOverride, lightEmission));
        }
        return original.call(modelBaker, from, to, face, material, facing, modelState, elementRotation, shadeDirectionOverride, lightEmission);
    }

    @WrapOperation(
        method = BAKE,
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/geometry/QuadCollection$Builder;addUnculledFace(Lnet/minecraft/client/resources/model/geometry/BakedQuad;)Lnet/minecraft/client/resources/model/geometry/QuadCollection$Builder;"
        )
    )
    private static QuadCollection.Builder dappledRevamp$addUnculledTintOverlay(
        QuadCollection.Builder builder, BakedQuad quad, Operation<QuadCollection.Builder> original,
        @Share("tintOverlay") LocalRef<BakedQuad> tintOverlay
    ) {
        original.call(builder, quad);
        BakedQuad overlay = tintOverlay.get();
        if (overlay != null) {
            tintOverlay.set(null);
            original.call(builder, overlay);
        }
        return builder;
    }

    @WrapOperation(
        method = BAKE,
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/geometry/QuadCollection$Builder;addCulledFace(Lnet/minecraft/core/Direction;Lnet/minecraft/client/resources/model/geometry/BakedQuad;)Lnet/minecraft/client/resources/model/geometry/QuadCollection$Builder;"
        )
    )
    private static QuadCollection.Builder dappledRevamp$addCulledTintOverlay(
        QuadCollection.Builder builder, Direction direction, BakedQuad quad, Operation<QuadCollection.Builder> original,
        @Share("tintOverlay") LocalRef<BakedQuad> tintOverlay
    ) {
        original.call(builder, direction, quad);
        BakedQuad overlay = tintOverlay.get();
        if (overlay != null) {
            tintOverlay.set(null);
            original.call(builder, direction, overlay);
        }
        return builder;
    }
}
