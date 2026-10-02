package com.crispytwig.dappledrevamp.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.treedecorators.ShelfMushroomDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShelfMushroomDecorator.class)
public abstract class ShelfMushroomDecoratorMixin {
    @Unique
    private static final int dappledRevamp$MIN_HEIGHT = 3;
    @Unique
    private static final int dappledRevamp$MAX_HEIGHT = 6;

    @Inject(method = "isWithinDecoratableHeight", at = @At("HEAD"), cancellable = true)
    private static void dappledRevamp$raiseShelfMushrooms(BlockPos pos, int treeBaseY, CallbackInfoReturnable<Boolean> cir) {
        int dy = pos.getY() - treeBaseY;
        cir.setReturnValue(dy >= dappledRevamp$MIN_HEIGHT && dy <= dappledRevamp$MAX_HEIGHT);
    }

    @Inject(method = "isBlockReplaceableWithShelfMushroom", at = @At("RETURN"), cancellable = true)
    private static void dappledRevamp$requireAirAbove(TreeDecorator.Context context, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && !context.isAir(pos.above())) {
            cir.setReturnValue(false);
        }
    }
}
