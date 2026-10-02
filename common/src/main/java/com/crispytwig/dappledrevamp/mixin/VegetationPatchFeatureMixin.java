package com.crispytwig.dappledrevamp.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.VegetationPatchFeature;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;
import java.util.function.Predicate;

@Mixin(VegetationPatchFeature.class)
public abstract class VegetationPatchFeatureMixin {
    @Shadow @Final protected CaveSurface surface;

    @Inject(method = "placeGroundPatch", at = @At("RETURN"))
    private void dappledRevamp$grassUnderMoss(
        WorldGenLevel level, RandomSource random, BlockPos origin, Predicate<BlockState> replaceable, int xRadius, int zRadius,
        CallbackInfoReturnable<Set<BlockPos>> cir
    ) {
        if (this.surface != CaveSurface.FLOOR) {
            return;
        }
        for (BlockPos top : cir.getReturnValue()) {
            if (!level.getBlockState(top).is(Blocks.MOSS_BLOCK)) {
                continue;
            }
            BlockPos below = top.below();
            while (level.getBlockState(below).is(Blocks.MOSS_BLOCK)) {
                below = below.below();
            }
            if (level.getBlockState(below).is(Blocks.DIRT)
                && level.getBlockState(below.below()).is(Blocks.DIRT)
                && dappledRevamp$hasAirBeside(level, below)) {
                level.setBlock(below, Blocks.GRASS_BLOCK.defaultBlockState(), 2);
            }
        }
    }

    @Unique
    private static boolean dappledRevamp$hasAirBeside(WorldGenLevel level, BlockPos pos) {
        for (Direction side : Direction.Plane.HORIZONTAL) {
            if (level.isEmptyBlock(pos.relative(side))) {
                return true;
            }
        }
        return false;
    }
}
