package com.crispytwig.pleasance.mixin.client;

import com.crispytwig.pleasance.world.item.WormBucketItem;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin {
    @Unique
    private static final Identifier pleasance$WORM_BAR = Identifier.withDefaultNamespace("worm_durability");

    @Inject(method = "itemBar", at = @At("HEAD"), cancellable = true)
    private void pleasance$wormBar(ItemStack stack, int x, int y, CallbackInfo ci) {
        if (!(stack.getItem() instanceof WormBucketItem) || !stack.isBarVisible()) {
            return;
        }
        GuiGraphicsExtractor graphics = (GuiGraphicsExtractor) (Object) this;
        int left = x + 2;
        int top = y + 13;
        graphics.fill(RenderPipelines.GUI, left, top, left + 13, top + 2, 0xFF000000);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, pleasance$WORM_BAR, 13, 1, 0, 0, left, top, stack.getBarWidth(), 1);
        ci.cancel();
    }
}
