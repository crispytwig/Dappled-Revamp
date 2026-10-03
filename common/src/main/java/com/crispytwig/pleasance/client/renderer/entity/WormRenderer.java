package com.crispytwig.pleasance.client.renderer.entity;

import com.crispytwig.pleasance.Pleasance;
import com.crispytwig.pleasance.client.model.animal.worm.WormModel;
import com.crispytwig.pleasance.world.entity.animal.worm.Worm;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class WormRenderer extends MobRenderer<Worm, LivingEntityRenderState, WormModel> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(Pleasance.location("worm"), "main");
    private static final Identifier TEXTURE = Pleasance.location("textures/entity/worm.png");

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
