package com.crispytwig.pleasance.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FallingLeavesParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class LeafBurstParticle extends FallingLeavesParticle {
    protected LeafBurstParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite, double xd, double yd, double zd) {
        super(level, x, y, z, sprite, 0.07F, 10.0F, true, false, 2.0F, 0.0F);
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed) {
            this.xd *= 0.88;
            this.zd *= 0.88;
            this.yd = -0.03 + (this.yd + 0.03) * 0.88;
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random) {
            return new LeafBurstParticle(level, x, y, z, this.sprites.get(random), xAux, Math.abs(yAux) + 0.04, zAux);
        }
    }
}
