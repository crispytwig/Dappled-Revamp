package com.crispytwig.dappledrevamp.world.item;

import com.crispytwig.dappledrevamp.mixin.FishingHookAccessor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BaitedRodItem extends FishingRodItem {
    public BaitedRodItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        FishingHook hook = player.fishing;
        boolean caughtFish = hook != null && hook.getHookedIn() == null && ((FishingHookAccessor) hook).dappledRevamp$getNibble() > 0;
        InteractionResult result = super.use(level, player, hand);
        ItemStack rod = player.getItemInHand(hand);
        if (caughtFish && !level.isClientSide() && rod.is(this)) {
            player.setItemInHand(hand, rod.transmuteCopy(Items.FISHING_ROD));
        }
        return result;
    }
}
