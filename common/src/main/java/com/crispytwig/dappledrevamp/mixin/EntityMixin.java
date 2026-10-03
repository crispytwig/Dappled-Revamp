package com.crispytwig.dappledrevamp.mixin;

import com.crispytwig.dappledrevamp.world.level.block.LeafLayerBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @WrapOperation(
        method = "checkFallDamage",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/Block;fallOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;D)V"
        )
    )
    private void dappledRevamp$landInLeafLayer(Block block, Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance, Operation<Void> original) {
        if (!LeafLayerBlock.cushionFall(level, entity, fallDistance)) {
            original.call(block, level, state, pos, entity, fallDistance);
        }
    }
}
