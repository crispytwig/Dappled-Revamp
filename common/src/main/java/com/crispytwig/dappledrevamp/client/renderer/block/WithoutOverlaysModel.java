package com.crispytwig.dappledrevamp.client.renderer.block;

import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class WithoutOverlaysModel implements BlockStateModel {
    private final BlockStateModel wrapped;
    private final Map<BlockStateModelPart, BlockStateModelPart> parts = new ConcurrentHashMap<>();

    public WithoutOverlaysModel(BlockStateModel wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void collectParts(RandomSource random, List<BlockStateModelPart> output) {
        int start = output.size();
        this.wrapped.collectParts(random, output);
        for (int i = start; i < output.size(); i++) {
            output.set(i, this.parts.computeIfAbsent(output.get(i), Part::new));
        }
    }

    @Override
    public Material.Baked particleMaterial() {
        return this.wrapped.particleMaterial();
    }

    @Override
    public @BakedQuad.MaterialFlags int materialFlags() {
        return this.wrapped.materialFlags();
    }

    private static final class Part implements BlockStateModelPart {
        private final BlockStateModelPart wrapped;
        private final List<BakedQuad> unculled;
        private final Map<Direction, List<BakedQuad>> culled = new EnumMap<>(Direction.class);

        private Part(BlockStateModelPart wrapped) {
            this.wrapped = wrapped;
            this.unculled = TintOverlays.withoutOverlays(wrapped.getQuads(null));
            for (Direction direction : Direction.values()) {
                this.culled.put(direction, TintOverlays.withoutOverlays(wrapped.getQuads(direction)));
            }
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable Direction direction) {
            return direction == null ? this.unculled : this.culled.get(direction);
        }

        @Override
        public boolean useAmbientOcclusion() {
            return this.wrapped.useAmbientOcclusion();
        }

        @Override
        public Material.Baked particleMaterial() {
            return this.wrapped.particleMaterial();
        }

        @Override
        public @BakedQuad.MaterialFlags int materialFlags() {
            return this.wrapped.materialFlags();
        }
    }
}
