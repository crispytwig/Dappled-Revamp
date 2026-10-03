package com.crispytwig.dappledrevamp.neoforge;

import com.crispytwig.dappledrevamp.DappledRevampClient;
import com.crispytwig.dappledrevamp.client.model.animal.worm.WormModel;
import com.crispytwig.dappledrevamp.client.renderer.entity.WormRenderer;
import com.crispytwig.dappledrevamp.registry.ModBlocks;
import com.crispytwig.dappledrevamp.registry.ModEntityTypes;
import net.minecraft.client.color.block.BlockTintSources;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.List;

public class DappledRevampNeoForgeClient {
    public static void init(IEventBus modEventBus, ModContainer modContainer) {
        DappledRevampClient.init();
        modEventBus.addListener(DappledRevampNeoForgeClient::registerRenderers);
        modEventBus.addListener(DappledRevampNeoForgeClient::registerLayerDefinitions);
        modEventBus.addListener(DappledRevampNeoForgeClient::registerBlockColors);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.WORM.get(), WormRenderer::new);
    }

    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WormRenderer.LAYER, WormModel::createBodyLayer);
    }

    private static void registerBlockColors(RegisterColorHandlersEvent.BlockTintSources event) {
        event.register(List.of(BlockTintSources.grassBlock()), ModBlocks.PATCHY_GRASS.get());
    }
}
