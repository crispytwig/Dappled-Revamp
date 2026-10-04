package com.crispytwig.pleasance.world.level.levelgen.feature;

import com.crispytwig.pleasance.world.level.block.LeafLayerBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.Map;

public record LeafPileFeature(Map<Block, Block> layers, IntProvider radius, IntProvider height) implements Feature {
    public static final MapCodec<LeafPileFeature> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.unboundedMap(BuiltInRegistries.BLOCK.byNameCodec(), BuiltInRegistries.BLOCK.byNameCodec()).fieldOf("layers").forGetter(LeafPileFeature::layers),
            IntProviders.codec(1, 6).fieldOf("radius").forGetter(LeafPileFeature::radius),
            IntProviders.codec(1, 8).fieldOf("height").forGetter(LeafPileFeature::height)
        ).apply(i, LeafPileFeature::new));

    @Override
    public MapCodec<LeafPileFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        if (!level.getBlockState(origin.below()).is(BlockTags.SUBSTRATE_OVERWORLD)) {
            return false;
        }
        BlockPos.MutableBlockPos pos = origin.mutable();
        BlockState canopy = level.getBlockState(pos);
        while ((canopy.isAir() || canopy.canBeReplaced()) && pos.getY() < origin.getY() + 24) {
            canopy = level.getBlockState(pos.move(0, 1, 0));
        }
        Block layer = this.layers.get(canopy.getBlock());
        if (layer == null) {
            return false;
        }

        float radius = this.radius.sample(random) + 0.5F;
        int height = this.height.sample(random);
        int reach = Mth.ceil(radius);
        boolean placed = false;
        for (int dx = -reach; dx <= reach; dx++) {
            for (int dz = -reach; dz <= reach; dz++) {
                int layers = Math.round(height * (1.0F - (dx * dx + dz * dz) / (radius * radius)));
                if (layers <= 0) {
                    continue;
                }
                int x = origin.getX() + dx;
                int z = origin.getZ() + dz;
                pos.set(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z), z);
                BlockState current = level.getBlockState(pos);
                if ((current.isAir() || current.canBeReplaced() && current.getFluidState().isEmpty() && !(current.getBlock() instanceof DoublePlantBlock))
                    && level.getBlockState(pos.below()).is(BlockTags.SUBSTRATE_OVERWORLD)) {
                    level.setBlock(pos, layer.defaultBlockState().setValue(LeafLayerBlock.LAYERS, layers), Block.UPDATE_CLIENTS);
                    placed = true;
                }
            }
        }
        return placed;
    }
}
