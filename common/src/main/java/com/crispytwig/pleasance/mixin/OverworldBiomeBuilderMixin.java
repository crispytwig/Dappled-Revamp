package com.crispytwig.pleasance.mixin;

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
    private static final int pleasance$COLD = 1;

    @Shadow @Final private ResourceKey<Biome>[][] MIDDLE_BIOMES;
    @Shadow @Final private ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void pleasance$expandDappledForest(CallbackInfo ci) {
        this.MIDDLE_BIOMES_VARIANT[pleasance$COLD][0] = null;
        this.MIDDLE_BIOMES[pleasance$COLD][2] = Biomes.DAPPLED_FOREST;
    }
}
