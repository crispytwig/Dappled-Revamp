package com.crispytwig.dappledrevamp.mixin;

import com.crispytwig.dappledrevamp.poplar.PoplarColor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "addDetailsToTooltip", at = @At("HEAD"))
    private void dappledRevamp$poplarColorTooltip(
        Item.TooltipContext context, TooltipDisplay display, @Nullable Player player, TooltipFlag tooltipFlag, Consumer<Component> builder,
        CallbackInfo ci
    ) {
        ItemStack stack = (ItemStack) (Object) this;
        if (stack.is(Items.POPLAR_SAPLING)) {
            builder.accept(Component.translatable(PoplarColor.of(stack).tooltipKey()).withStyle(ChatFormatting.GRAY));
        }
    }
}
