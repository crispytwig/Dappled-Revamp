package com.crispytwig.pleasance.mixin;

import com.crispytwig.pleasance.world.level.block.Moist;
import com.crispytwig.pleasance.world.level.block.PoplarColor;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @WrapOperation(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/Block;createBlockStateDefinition(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V"
        )
    )
    private void pleasance$addProperties(
        Block block, StateDefinition.Builder<Block, BlockState> builder, Operation<Void> original,
        @Local(argsOnly = true) BlockBehaviour.Properties properties
    ) {
        original.call(block, builder);
        ResourceKey<Block> id = ((BlockPropertiesAccessor) properties).pleasance$getId();
        if (PoplarColor.hasColor(id)) {
            builder.add(PoplarColor.PROPERTY);
        }
        if (Moist.canBeMoist(id)) {
            builder.add(Moist.PROPERTY);
        }
    }

    @ModifyVariable(method = "registerDefaultState", at = @At("HEAD"), argsOnly = true)
    private BlockState pleasance$defaultDry(BlockState state) {
        return state.hasProperty(Moist.PROPERTY) ? state.setValue(Moist.PROPERTY, false) : state;
    }
}
