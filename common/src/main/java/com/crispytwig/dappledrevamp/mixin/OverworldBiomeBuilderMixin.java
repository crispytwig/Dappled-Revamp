package com.crispytwig.dappledrevamp.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OverworldBiomeBuilder.class)
public abstract class OverworldBiomeBuilderMixin {
    @Unique
    private static final int dappledRevamp$COLD = 1;

    @Shadow @Final private ResourceKey<Biome>[][] MIDDLE_BIOMES;
    @Shadow @Final private ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void dappledRevamp$expandDappledForest(CallbackInfo ci) {
        this.MIDDLE_BIOMES_VARIANT[dappledRevamp$COLD][0] = null;
        this.MIDDLE_BIOMES[dappledRevamp$COLD][2] = Biomes.DAPPLED_FOREST;
    }
}
