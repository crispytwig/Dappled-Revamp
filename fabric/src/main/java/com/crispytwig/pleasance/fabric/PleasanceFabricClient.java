package com.crispytwig.pleasance.fabric;

import com.crispytwig.pleasance.PleasanceClient;
import com.crispytwig.pleasance.client.model.animal.worm.WormModel;
import com.crispytwig.pleasance.client.particle.LeafBurstParticle;
import com.crispytwig.pleasance.client.renderer.entity.WormRenderer;
import com.crispytwig.pleasance.registry.ModBlocks;
import com.crispytwig.pleasance.registry.ModEntityTypes;
import com.crispytwig.pleasance.registry.ModParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.color.block.BlockTintSources;

import java.util.List;

public class PleasanceFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PleasanceClient.init();
        ModelLayerRegistry.registerModelLayer(WormRenderer.LAYER, WormModel::createBodyLayer);
        EntityRendererRegistry.register(ModEntityTypes.WORM.get(), WormRenderer::new);
        BlockColorRegistry.register(List.of(BlockTintSources.grassBlock()), ModBlocks.PATCHY_GRASS.get(), ModBlocks.PATCHY_PODZOL.get());
        ParticleProviderRegistry.getInstance().register(ModParticleTypes.RED_POPLAR_LEAF_BURST.get(), LeafBurstParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(ModParticleTypes.ORANGE_POPLAR_LEAF_BURST.get(), LeafBurstParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(ModParticleTypes.YELLOW_POPLAR_LEAF_BURST.get(), LeafBurstParticle.Provider::new);
    }
}
