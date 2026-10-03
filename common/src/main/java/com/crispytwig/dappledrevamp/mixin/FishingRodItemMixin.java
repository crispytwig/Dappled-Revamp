package com.crispytwig.dappledrevamp.mixin;

import com.crispytwig.dappledrevamp.registry.ModItems;
import com.crispytwig.dappledrevamp.registry.ModSoundEvents;
import com.crispytwig.dappledrevamp.world.item.Bait;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingRodItem.class)
public abstract class FishingRodItemMixin extends Item {
    private FishingRodItemMixin(Item.Properties properties) {
        super(properties);
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void dappledRevamp$startBaiting(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (player.fishing != null || !Bait.canBait(player, hand)) {
            return;
        }
        player.startUsingItem(hand);
        if (level.isClientSide()) {
            Bait.startBaitSound.accept(player);
        }
        cir.setReturnValue(InteractionResult.CONSUME);
    }

    @ModifyArg(
        method = "use",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/projectile/FishingHook;<init>(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;II)V"
        ),
        index = 3
    )
    private int dappledRevamp$baitedLure(int lureSpeed, @Local(argsOnly = true) Player player, @Local(argsOnly = true) InteractionHand hand) {
        return player.getItemInHand(hand).is(ModItems.BAITED_ROD.get()) ? lureSpeed + 100 : lureSpeed;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return 25;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if (level.isClientSide() || !(entity instanceof Player player) || !Bait.isBaiting(player)) {
            return itemStack;
        }
        Bait.consumeBait(player, Bait.otherHand(player.getUsedItemHand()));
        level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.WORM_HOOK.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        return Bait.baitRod(player, itemStack);
    }
}
