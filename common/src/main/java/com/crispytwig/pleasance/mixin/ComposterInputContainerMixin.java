package com.crispytwig.pleasance.mixin;

import com.crispytwig.pleasance.registry.ModBlocks;
import com.crispytwig.pleasance.world.level.block.WormBinBlock;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.level.block.ComposterBlock$InputContainer")
public abstract class ComposterInputContainerMixin {
    @Shadow
    @Final
    private BlockState state;

    @ModifyReturnValue(method = "canPlaceItemThroughFace", at = @At("RETURN"))
    private boolean pleasance$wormBinFoodOnly(boolean original, int slot, ItemStack itemStack) {
        return original && (!this.state.is(ModBlocks.WORM_BIN.get()) || WormBinBlock.canCompost(itemStack));
    }
}
