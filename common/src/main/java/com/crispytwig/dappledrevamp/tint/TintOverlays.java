package com.crispytwig.dappledrevamp.tint;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.GrassColor;
import org.jspecify.annotations.Nullable;

import java.util.List;

public final class TintOverlays {
    public static final String SUFFIX = "_tint_overlay";
    public static final int TINT_INDEX = 99;
    public static final BlockTintSource TINT = BlockTintSources.grass();

    private TintOverlays() {
    }

    public static int defaultColor() {
        return ARGB.opaque(GrassColor.getDefaultColor());
    }

    public static boolean isOverlay(BakedQuad quad) {
        return quad.materialInfo().tintIndex() == TINT_INDEX;
    }

    public static List<BakedQuad> withoutOverlays(List<BakedQuad> quads) {
        return quads.stream().anyMatch(TintOverlays::isOverlay) ? quads.stream().filter(quad -> !isOverlay(quad)).toList() : quads;
    }

    public interface Lookup {
        Material.@Nullable Baked dappledRevamp$tintOverlayFor(Material.Baked base);
    }
}
