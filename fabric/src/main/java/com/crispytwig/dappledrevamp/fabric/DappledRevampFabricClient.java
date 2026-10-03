package com.crispytwig.dappledrevamp.fabric;

import com.crispytwig.dappledrevamp.worm.WormContent;
import com.crispytwig.dappledrevamp.worm.client.WormClient;
import com.crispytwig.dappledrevamp.worm.client.WormModel;
import com.crispytwig.dappledrevamp.worm.client.WormRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

public class DappledRevampFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WormClient.init();
        ModelLayerRegistry.registerModelLayer(WormRenderer.LAYER, WormModel::createBodyLayer);
        EntityRendererRegistry.register(WormContent.WORM.get(), WormRenderer::new);
    }
}
