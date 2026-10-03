package com.crispytwig.pleasance.mixin;

import com.crispytwig.pleasance.world.level.block.Moist;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {
    @Inject(method = "getDrops", at = @At("RETURN"))
    private void pleasance$keepMoist(LootParams.Builder params, CallbackInfoReturnable<List<ItemStack>> cir) {
        BlockState state = (BlockState) (Object) this;
        if (!Moist.isMoist(state)) {
            return;
        }
        for (ItemStack drop : cir.getReturnValue()) {
            if (drop.is(state.getBlock().asItem())) {
                Moist.makeMoist(drop);
            }
        }
    }
}
