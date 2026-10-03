package com.crispytwig.dappledrevamp.mixin;

import com.crispytwig.dappledrevamp.registry.ModBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.component.Compostable;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ComposterBlock.class)
public abstract class ComposterBlockMixin {
    @WrapOperation(
        method = "addLayer",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/storage/loot/providers/number/ints/ResolvableInt;get(Lnet/minecraft/world/level/storage/loot/LootContext;I)I"
        )
    )
    private static int dappledRevamp$wormBinLayers(
        ResolvableInt layers, LootContext context, int defaultValue, Operation<Integer> original,
        @Nullable Entity sourceEntity, BlockState state, ServerLevel level, BlockPos pos, Compostable compostable
    ) {
        if (!state.is(ModBlocks.WORM_BIN.get())) {
            return original.call(layers, context, defaultValue);
        }
        if (state.getValue(ComposterBlock.LEVEL) == 0) {
            return 1;
        }
        return Math.max(original.call(layers, context, defaultValue), original.call(layers, context, defaultValue));
    }
}
