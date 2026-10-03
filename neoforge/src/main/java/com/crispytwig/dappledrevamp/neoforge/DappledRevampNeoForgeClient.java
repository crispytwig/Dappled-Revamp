package com.crispytwig.dappledrevamp.neoforge;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.worm.WormContent;
import com.crispytwig.dappledrevamp.worm.client.WormClient;
import com.crispytwig.dappledrevamp.worm.client.WormModel;
import com.crispytwig.dappledrevamp.worm.client.WormRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(value = DappledRevamp.MOD_ID, dist = Dist.CLIENT)
public class DappledRevampNeoForgeClient {
    public DappledRevampNeoForgeClient(IEventBus modEventBus) {
        WormClient.init();
        modEventBus.addListener(DappledRevampNeoForgeClient::registerLayers);
        modEventBus.addListener(DappledRevampNeoForgeClient::registerRenderers);
    }

    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WormRenderer.LAYER, WormModel::createBodyLayer);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(WormContent.WORM.get(), WormRenderer::new);
    }
}
