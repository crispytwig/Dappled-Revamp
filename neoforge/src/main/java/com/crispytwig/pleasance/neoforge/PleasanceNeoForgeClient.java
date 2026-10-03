package com.crispytwig.pleasance.neoforge;

import com.crispytwig.pleasance.PleasanceClient;
import com.crispytwig.pleasance.client.model.animal.worm.WormModel;
import com.crispytwig.pleasance.client.particle.LeafBurstParticle;
import com.crispytwig.pleasance.client.renderer.entity.WormRenderer;
import com.crispytwig.pleasance.registry.ModBlocks;
import com.crispytwig.pleasance.registry.ModEntityTypes;
import com.crispytwig.pleasance.registry.ModParticleTypes;
import net.minecraft.client.color.block.BlockTintSources;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import java.util.List;

public class PleasanceNeoForgeClient {
    public static void init(IEventBus modEventBus, ModContainer modContainer) {
        PleasanceClient.init();
        modEventBus.addListener(PleasanceNeoForgeClient::registerRenderers);
        modEventBus.addListener(PleasanceNeoForgeClient::registerLayerDefinitions);
        modEventBus.addListener(PleasanceNeoForgeClient::registerBlockColors);
        modEventBus.addListener(PleasanceNeoForgeClient::registerParticleProviders);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.WORM.get(), WormRenderer::new);
    }

    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WormRenderer.LAYER, WormModel::createBodyLayer);
    }

    private static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticleTypes.RED_POPLAR_LEAF_BURST.get(), LeafBurstParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.ORANGE_POPLAR_LEAF_BURST.get(), LeafBurstParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.YELLOW_POPLAR_LEAF_BURST.get(), LeafBurstParticle.Provider::new);
    }

    private static void registerBlockColors(RegisterColorHandlersEvent.BlockTintSources event) {
        event.register(List.of(BlockTintSources.grassBlock()), ModBlocks.PATCHY_GRASS.get(), ModBlocks.PATCHY_PODZOL.get());
    }
}
