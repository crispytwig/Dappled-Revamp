package com.crispytwig.dappledrevamp.worm;

import net.minecraft.core.BlockPos;
import net.minecraft.data.AtlasIds;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.objects.AtlasSprite;
import com.crispytwig.dappledrevamp.DappledRevamp;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;

import java.util.function.Consumer;

public class WormBucketItem extends Item {
    public static final int MAX_WORMS = 5;
    private static final Component FULL_ICON = Component.object(new AtlasSprite(AtlasIds.GUI, DappledRevamp.id("worm"))).withStyle(Style::withoutShadow);
    private static final Component EMPTY_ICON = Component.object(new AtlasSprite(AtlasIds.GUI, DappledRevamp.id("worm_empty"))).withStyle(Style::withoutShadow);

    public WormBucketItem(Item.Properties properties) {
        super(properties);
    }

    public static int getWormCount(ItemStack stack) {
        return stack.getOrDefault(WormContent.WORM_COUNT.get(), 1);
    }

    public static ItemStack removeWorm(ItemStack stack) {
        int count = getWormCount(stack);
        if (count <= 1) {
            return new ItemStack(Items.BUCKET);
        }
        stack.set(WormContent.WORM_COUNT.get(), count - 1);
        return stack;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        ItemStack bucket = player.getItemInHand(hand).is(this) ? player.getItemInHand(hand) : stack;
        if (!(target instanceof Worm) || getWormCount(bucket) >= MAX_WORMS) {
            return InteractionResult.PASS;
        }
        if (!player.level().isClientSide()) {
            bucket.set(WormContent.WORM_COUNT.get(), getWormCount(bucket) + 1);
            target.discard();
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), WormContent.HOOK_SOUND.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!(context.getLevel() instanceof ServerLevel level)) {
            return InteractionResult.SUCCESS;
        }
        Worm worm = WormContent.WORM.get().create(level, EntitySpawnReason.BUCKET);
        if (worm == null) {
            return InteractionResult.PASS;
        }
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        worm.snapTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, context.getRotation() + 180.0F, 0.0F);
        level.addFreshEntity(worm);
        level.playSound(null, pos, WormContent.HOOK_SOUND.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        Player player = context.getPlayer();
        ItemStack result = removeWorm(context.getItemInHand());
        if (player != null) {
            player.setItemInHand(context.getHand(), result);
        }
        return InteractionResult.SUCCESS_SERVER;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getWormCount(stack) < MAX_WORMS;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(getWormCount(stack) * 13.0F / MAX_WORMS);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        int count = getWormCount(stack);
        MutableComponent row = Component.empty();
        for (int i = 0; i < MAX_WORMS; i++) {
            row.append(i < count ? FULL_ICON : EMPTY_ICON);
        }
        builder.accept(row);
    }
}
