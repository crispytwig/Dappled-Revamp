package com.crispytwig.dappledrevamp.mixin;

import com.crispytwig.dappledrevamp.registry.ModItems;
import com.crispytwig.dappledrevamp.world.entity.animal.worm.Worm;
import com.crispytwig.dappledrevamp.world.item.Bait;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin {
    @Shadow
    public abstract @Nullable Entity getHookedIn();

    @Shadow
    public abstract @Nullable Player getPlayerOwner();

    @WrapOperation(
        method = "shouldStopFishing",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z"),
        require = 0
    )
    private boolean dappledRevamp$keepFishingWithBaitedRod(ItemStack stack, Object item, Operation<Boolean> original) {
        return original.call(stack, item) || stack.is(ModItems.BAITED_ROD.get());
    }

    @Inject(method = "retrieve", at = @At("RETURN"))
    private void dappledRevamp$baitWithHookedWorm(ItemStack rod, CallbackInfoReturnable<Integer> cir) {
        Player player = this.getPlayerOwner();
        if (!(this.getHookedIn() instanceof Worm worm) || player == null || !rod.is(Items.FISHING_ROD)) {
            return;
        }
        for (InteractionHand hand : InteractionHand.values()) {
            if (player.getItemInHand(hand) == rod) {
                player.setItemInHand(hand, Bait.baitRod(player, rod));
                worm.discard();
                return;
            }
        }
    }
}
