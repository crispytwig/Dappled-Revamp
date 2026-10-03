package com.crispytwig.dappledrevamp.worm;

import com.crispytwig.dappledrevamp.DappledRevamp;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public final class Bait {
    public static final TagKey<Item> BAIT = TagKey.create(Registries.ITEM, DappledRevamp.id("fishing_bait"));

    public static Consumer<Player> startBaitSound = player -> {
    };

    private Bait() {
    }

    public static boolean isBait(ItemStack stack) {
        return stack.is(BAIT);
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
        ItemStack baited = rod.transmuteCopy(WormContent.BAITED_ROD.get());
        player.getCooldowns().addCooldown(baited, 20);
        return baited;
    }

    public static void consumeBait(Player player, InteractionHand baitHand) {
        ItemStack bait = player.getItemInHand(baitHand);
        if (bait.is(WormContent.WORM_BUCKET.get())) {
            player.setItemInHand(baitHand, WormBucketItem.removeWorm(bait));
        } else {
            bait.consume(1, player);
        }
    }
}
