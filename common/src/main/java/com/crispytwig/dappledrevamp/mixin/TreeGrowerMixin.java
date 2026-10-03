package com.crispytwig.dappledrevamp.mixin;

import com.crispytwig.dappledrevamp.world.level.block.PoplarColor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TreeGrower.class)
public abstract class TreeGrowerMixin {
    @Shadow
    private @Nullable ResourceKey<Feature> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
        throw new AssertionError();
    }

    @Redirect(
        method = "growTree",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/grower/TreeGrower;getConfiguredFeature(Lnet/minecraft/util/RandomSource;Z)Lnet/minecraft/resources/ResourceKey;"
        )
    )
    private @Nullable ResourceKey<Feature> dappledRevamp$growPoplarColor(
        TreeGrower grower, RandomSource random, boolean hasFlowers,
        ServerLevel level, ChunkGenerator generator, BlockPos pos, BlockState state, RandomSource growRandom
    ) {
        if (state.hasProperty(PoplarColor.PROPERTY)) {
            return state.getValue(PoplarColor.PROPERTY).tree();
        }
        return this.getConfiguredFeature(random, hasFlowers);
    }
}
