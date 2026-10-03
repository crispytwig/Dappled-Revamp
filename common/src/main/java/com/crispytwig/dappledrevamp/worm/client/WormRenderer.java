package com.crispytwig.dappledrevamp.worm.client;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.worm.Worm;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class WormRenderer extends MobRenderer<Worm, LivingEntityRenderState, WormModel> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(DappledRevamp.id("worm"), "main");
    private static final Identifier TEXTURE = DappledRevamp.id("textures/entity/worm.png");

    public WormRenderer(EntityRendererProvider.Context context) {
        super(context, new WormModel(context.bakeLayer(LAYER)), 0.0F);
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
