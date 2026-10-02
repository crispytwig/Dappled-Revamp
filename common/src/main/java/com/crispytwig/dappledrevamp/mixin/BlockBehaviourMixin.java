package com.crispytwig.dappledrevamp.mixin;

import com.crispytwig.dappledrevamp.poplar.PoplarColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin {
    @Inject(method = "getCloneItemStack", at = @At("RETURN"))
    private void dappledRevamp$pickPoplarColor(LevelReader level, BlockPos pos, BlockState state, boolean includeData, CallbackInfoReturnable<ItemStack> cir) {
        if (!state.hasProperty(PoplarColor.PROPERTY)) {
            return;
        }
        PoplarColor color = state.getValue(PoplarColor.PROPERTY);
        if (color != PoplarColor.ORANGE) {
            cir.getReturnValue().set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(PoplarColor.PROPERTY, color));
        }
    }
}
