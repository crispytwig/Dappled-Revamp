package com.crispytwig.dappledrevamp.fabric;

import com.crispytwig.dappledrevamp.DappledRevampClient;
import com.crispytwig.dappledrevamp.client.model.animal.worm.WormModel;
import com.crispytwig.dappledrevamp.client.renderer.entity.WormRenderer;
import com.crispytwig.dappledrevamp.registry.ModBlocks;
import com.crispytwig.dappledrevamp.registry.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.color.block.BlockTintSources;

import java.util.List;

public class DappledRevampFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DappledRevampClient.init();
        ModelLayerRegistry.registerModelLayer(WormRenderer.LAYER, WormModel::createBodyLayer);
        EntityRendererRegistry.register(ModEntityTypes.WORM.get(), WormRenderer::new);
        BlockColorRegistry.register(List.of(BlockTintSources.grassBlock()), ModBlocks.PATCHY_GRASS.get());
    }
}
