package com.crispytwig.dappledrevamp.mixin.client;

import com.crispytwig.dappledrevamp.tint.TintOverlays;
import net.minecraft.client.renderer.texture.SpriteLoader;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.MaterialBaker;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(MaterialBaker.class)
public abstract class MaterialBakerMixin implements TintOverlays.Lookup {
    @Shadow @Final private SpriteLoader.Preparations blockAtlas;

    @Unique
    private final Map<TextureAtlasSprite, Optional<Material.Baked>> dappledRevamp$tintOverlays = new ConcurrentHashMap<>();

    @Override
    public Material.@Nullable Baked dappledRevamp$tintOverlayFor(Material.Baked base) {
        return this.dappledRevamp$tintOverlays.computeIfAbsent(base.sprite(), this::dappledRevamp$findTintOverlay).orElse(null);
    }

    @Unique
    private Optional<Material.Baked> dappledRevamp$findTintOverlay(TextureAtlasSprite sprite) {
        if (!sprite.atlasLocation().equals(TextureAtlas.LOCATION_BLOCKS)) {
            return Optional.empty();
        }
        Identifier name = sprite.contents().name();
        if (name.getPath().endsWith(TintOverlays.SUFFIX)) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.blockAtlas.getSprite(name.withSuffix(TintOverlays.SUFFIX))).map(overlay -> new Material.Baked(overlay, false));
    }
}
