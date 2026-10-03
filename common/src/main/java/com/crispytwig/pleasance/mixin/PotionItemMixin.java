package com.crispytwig.pleasance.mixin;

import com.crispytwig.pleasance.world.level.block.Moist;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionItem.class)
public abstract class PotionItemMixin {
    @WrapOperation(
        method = "useOn",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z")
    )
    private boolean pleasance$canMoisten(BlockState state, TagKey<Block> tag, Operation<Boolean> original) {
        return original.call(state, tag) || Moist.canMoisten(state);
    }

    @WrapOperation(
        method = "useOn",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"
        )
    )
    private boolean pleasance$moisten(Level level, BlockPos pos, BlockState mud, Operation<Boolean> original, @Local BlockState clicked) {
        return original.call(level, pos, Moist.canMoisten(clicked) ? clicked.setValue(Moist.PROPERTY, true) : mud);
    }
}
