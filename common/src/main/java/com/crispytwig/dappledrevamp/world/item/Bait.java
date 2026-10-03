package com.crispytwig.dappledrevamp.world.item;

import com.crispytwig.dappledrevamp.registry.ModItems;
import com.crispytwig.dappledrevamp.tags.ModItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public final class Bait {
    public static Consumer<Player> startBaitSound = player -> {
    };

    private Bait() {
    }

    public static boolean isBait(ItemStack stack) {
        return stack.is(ModItemTags.FISHING_BAIT);
    }

    public static InteractionHand otherHand(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
    }

    public static boolean canBait(LivingEntity entity, InteractionHand rodHand) {
        return entity.getItemInHand(rodHand).is(Items.FISHING_ROD) && isBait(entity.getItemInHand(otherHand(rodHand)));
    }

    public static boolean isBaiting(LivingEntity entity) {
        return entity.isUsingItem() && canBait(entity, entity.getUsedItemHand());
    }

    public static ItemStack baitRod(Player player, ItemStack rod) {
        ItemStack baited = rod.transmuteCopy(ModItems.BAITED_ROD.get());
        player.getCooldowns().addCooldown(baited, 20);
        return baited;
    }

    public static void consumeBait(Player player, InteractionHand baitHand) {
        ItemStack bait = player.getItemInHand(baitHand);
        if (bait.is(ModItems.WORM_BUCKET.get())) {
            player.setItemInHand(baitHand, WormBucketItem.removeWorm(bait));
        } else {
            bait.consume(1, player);
        }
    }
}
