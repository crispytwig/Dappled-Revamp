package com.crispytwig.pleasance.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpreadingSnowyBlock.class)
public abstract class SpreadingSnowyBlockMixin {
    @Inject(method = "canStayAlive", at = @At("HEAD"), cancellable = true)
    private static void pleasance$surviveUnderMoss(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(Blocks.GRASS_BLOCK) && level.getBlockState(pos.above()).is(Blocks.MOSS_BLOCK)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "canPropagate", at = @At("HEAD"), cancellable = true)
    private static void pleasance$noSpreadUnderMoss(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (level.getBlockState(pos.above()).is(Blocks.MOSS_BLOCK)) {
            cir.setReturnValue(false);
        }
    }
}
