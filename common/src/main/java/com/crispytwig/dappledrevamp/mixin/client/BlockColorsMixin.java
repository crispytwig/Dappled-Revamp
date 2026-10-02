package com.crispytwig.dappledrevamp.mixin.client;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BlockColors.class)
public abstract class BlockColorsMixin {
    @Inject(method = "createDefault", at = @At("RETURN"))
    private static void dappledRevamp$tintMoss(CallbackInfoReturnable<BlockColors> cir) {
        cir.getReturnValue().register(List.of(BlockTintSources.grass()), Blocks.MOSS_BLOCK);
    }
}
