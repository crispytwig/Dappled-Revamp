package com.crispytwig.dappledrevamp.mixin;

import com.crispytwig.dappledrevamp.poplar.PoplarColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SaplingBlock.class)
public abstract class SaplingBlockMixin {
    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void dappledRevamp$addPoplarColor(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        if (PoplarColor.isPoplarSapling((Block) (Object) this)) {
            builder.add(PoplarColor.PROPERTY);
        }
    }
}
