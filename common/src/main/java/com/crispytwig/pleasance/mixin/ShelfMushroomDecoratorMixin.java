package com.crispytwig.pleasance.mixin;

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
    private static final int pleasance$MIN_HEIGHT = 3;
    @Unique
    private static final int pleasance$MAX_HEIGHT = 6;

    @Inject(method = "isWithinDecoratableHeight", at = @At("HEAD"), cancellable = true)
    private static void pleasance$raiseShelfMushrooms(BlockPos pos, int treeBaseY, CallbackInfoReturnable<Boolean> cir) {
        int dy = pos.getY() - treeBaseY;
        cir.setReturnValue(dy >= pleasance$MIN_HEIGHT && dy <= pleasance$MAX_HEIGHT);
    }

    @Inject(method = "isBlockReplaceableWithShelfMushroom", at = @At("RETURN"), cancellable = true)
    private static void pleasance$requireAirAbove(TreeDecorator.Context context, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && !context.isAir(pos.above())) {
            cir.setReturnValue(false);
        }
    }
}
