package com.crispytwig.dappledrevamp.registry;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.platform.registry.DeferredHolder;
import com.crispytwig.dappledrevamp.platform.registry.DeferredRegister;
import com.crispytwig.dappledrevamp.world.level.block.LeafLayerBlock;
import com.crispytwig.dappledrevamp.world.level.block.RegolithBlock;
import com.crispytwig.dappledrevamp.world.level.block.WormBinBlock;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SnowyBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, DappledRevamp.MOD_ID);

    public static final DeferredHolder<Block, WormBinBlock> WORM_BIN = registerBlock("worm_bin", WormBinBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.COMPOSTER));
    public static final DeferredHolder<Block, GrassBlock> PATCHY_GRASS = registerBlock("patchy_grass", GrassBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK));
    public static final DeferredHolder<Block, SnowyBlock> PATCHY_PODZOL = registerBlock("patchy_podzol", properties -> new SnowyBlock(properties) {}, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.PODZOL));
    public static final DeferredHolder<Block, RegolithBlock> REGOLITH = registerBlock("regolith", RegolithBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT));
    public static final DeferredHolder<Block, LeafLayerBlock> RED_POPLAR_LEAF_LAYER = registerLeafLayer("red_poplar_leaf_layer", ModParticleTypes.RED_POPLAR_LEAF_BURST, MapColor.COLOR_RED);
    public static final DeferredHolder<Block, LeafLayerBlock> ORANGE_POPLAR_LEAF_LAYER = registerLeafLayer("orange_poplar_leaf_layer", ModParticleTypes.ORANGE_POPLAR_LEAF_BURST, MapColor.COLOR_ORANGE);
    public static final DeferredHolder<Block, LeafLayerBlock> YELLOW_POPLAR_LEAF_LAYER = registerLeafLayer("yellow_poplar_leaf_layer", ModParticleTypes.YELLOW_POPLAR_LEAF_BURST, MapColor.COLOR_YELLOW);

    private static ResourceKey<Block> blockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, DappledRevamp.location(name));
    }

    private static DeferredHolder<Block, LeafLayerBlock> registerLeafLayer(String name, Supplier<SimpleParticleType> burstParticle, MapColor mapColor) {
        return registerBlock(name, properties -> new LeafLayerBlock(burstParticle, properties), () -> BlockBehaviour.Properties.of()
                .mapColor(mapColor)
                .replaceable()
                .forceSolidOff()
                .strength(0.1F)
                .sound(SoundType.POPLAR_LEAVES)
                .noOcclusion()
                .isSuffocating((state, level, pos) -> false)
                .isRedstoneConductor((state, level, pos) -> false)
                .ignitedByLava()
                .pushReaction(PushReaction.POPPED));
    }

    private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name, Function<BlockBehaviour.Properties, T> factory, Supplier<BlockBehaviour.Properties> properties) {
        DeferredHolder<Block, T> holder = registerBlockOnly(name, factory, properties);
        ModItems.registerItem(name, props -> new BlockItem(holder.get(), props), new Item.Properties().useBlockDescriptionPrefix());
        return holder;
    }

    private static <T extends Block> DeferredHolder<Block, T> registerBlockOnly(String name, Function<BlockBehaviour.Properties, T> factory, Supplier<BlockBehaviour.Properties> properties) {
        return BLOCKS.register(name, () -> factory.apply(properties.get().setId(blockKey(name))));
    }
}
