package com.crispytwig.dappledrevamp.mixin;

import com.crispytwig.dappledrevamp.poplar.PoplarColor;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FlowerPotBlock.class)
public abstract class FlowerPotBlockMixin {
    @ModifyArg(
        method = "useItemOn",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"
        )
    )
    private BlockState dappledRevamp$potPoplarColor(BlockState contents, @Local(argsOnly = true) ItemStack stack) {
        return contents.hasProperty(PoplarColor.PROPERTY) ? contents.setValue(PoplarColor.PROPERTY, PoplarColor.of(stack)) : contents;
    }

    @ModifyExpressionValue(
        method = {"useWithoutItem", "getCloneItemStack"},
        at = @At(value = "NEW", target = "Lnet/minecraft/world/item/ItemStack;")
    )
    private ItemStack dappledRevamp$unpotPoplarColor(ItemStack plant, @Local(argsOnly = true) BlockState state) {
        return state.hasProperty(PoplarColor.PROPERTY) ? state.getValue(PoplarColor.PROPERTY).sapling() : plant;
    }
}
