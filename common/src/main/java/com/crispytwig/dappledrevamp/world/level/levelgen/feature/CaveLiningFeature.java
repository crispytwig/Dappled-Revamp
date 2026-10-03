package com.crispytwig.dappledrevamp.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

import java.util.List;
import java.util.function.BiPredicate;

public record CaveLiningFeature(HolderSet<Block> replaceable, HolderSet<Block> buriedReplaceable, BlockState state, int depth, int edgeVariation, List<BlockState> transition) implements Feature {
    public static final MapCodec<CaveLiningFeature> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            RegistryCodecs.holderSet(Registries.BLOCK).fieldOf("replaceable").forGetter(CaveLiningFeature::replaceable),
            RegistryCodecs.holderSet(Registries.BLOCK).optionalFieldOf("buried_replaceable", HolderSet.empty()).forGetter(CaveLiningFeature::buriedReplaceable),
            BlockState.CODEC.fieldOf("state").forGetter(CaveLiningFeature::state),
            Codec.intRange(1, 64).fieldOf("depth").forGetter(CaveLiningFeature::depth),
            Codec.intRange(0, 16).optionalFieldOf("edge_variation", 0).forGetter(CaveLiningFeature::edgeVariation),
            BlockState.CODEC.listOf(0, 16).optionalFieldOf("transition", List.of()).forGetter(CaveLiningFeature::transition)
        ).apply(i, CaveLiningFeature::new));

    private static final SimplexNoise EDGE_NOISE = new SimplexNoise(new WorldgenRandom(new LegacyRandomSource(8641L)));
    private static final int SMOOTHING = 3;
    private static final int SIZE = 16 + 2 * SMOOTHING;

    @Override
    public MapCodec<CaveLiningFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos neighbor = new BlockPos.MutableBlockPos();
        ChunkPos chunk = ChunkPos.containing(origin);
        int originX = chunk.getMinBlockX() - SMOOTHING;
        int originZ = chunk.getMinBlockZ() - SMOOTHING;
        int[][] heights = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                heights[i][j] = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, originX + i, originZ + j);
            }
        }
        BiPredicate<BlockPos, BlockState> caveAir = (at, state) -> state.isAir() && at.getY() < heights[at.getX() - originX][at.getZ() - originZ];

        for (int i = SMOOTHING; i < SIZE - SMOOTHING; i++) {
            for (int j = SMOOTHING; j < SIZE - SMOOTHING; j++) {
                int x = originX + i;
                int z = originZ + j;
                int top = heights[i][j] - 1;
                int total = 0;
                for (int oi = -SMOOTHING; oi <= SMOOTHING; oi++) {
                    for (int oj = -SMOOTHING; oj <= SMOOTHING; oj++) {
                        total += heights[i + oi][j + oj];
                    }
                }
                float wave = Mth.clamp(EDGE_NOISE.get(x / 24.0, z / 24.0), -1.0F, 1.0F);
                int bottom = Math.round(total / 49.0F) - this.depth + Math.round(wave * this.edgeVariation);

                for (int y = top; y >= bottom; y--) {
                    BlockState current = level.getBlockState(pos.set(x, y, z));
                    if ((current.is(this.replaceable) || (y < top && current.is(this.buriedReplaceable) && !isSunlit(level, pos)))
                        && anyNeighbor(level, pos, neighbor, caveAir)) {
                        level.setBlock(pos, this.state, Block.UPDATE_CLIENTS);
                    }
                }

                if (!this.transition.isEmpty()) {
                    this.placeTransition(level, pos, neighbor, x, z, top);
                }
            }
        }
        return true;
    }

    private void placeTransition(WorldGenLevel level, BlockPos.MutableBlockPos pos, BlockPos.MutableBlockPos neighbor, int x, int z, int top) {
        Block layer = this.state.getBlock();
        int y = top - 1;
        while (y > top - this.depth && level.getBlockState(pos.set(x, y, z)).is(layer)) {
            y--;
        }
        if (y == top - this.depth || !level.getBlockState(pos).is(this.replaceable)
            || (y == top - 1 && !level.getBlockState(pos.set(x, top, z)).is(layer))) {
            return;
        }
        int size = this.transition.size();
        for (int k = 0; k <= size; k++) {
            BlockState slot = level.getBlockState(pos.set(x, y - k, z));
            boolean valid = slot.is(this.replaceable) || (k > 0 && k < size && slot.is(layer));
            if (!valid || (k < size && anyNeighbor(level, pos, neighbor, (at, state) -> state.isAir() || !state.getFluidState().isEmpty()))) {
                return;
            }
        }
        for (int k = 0; k < size; k++) {
            level.setBlock(pos.set(x, y - k, z), this.transition.get(k), Block.UPDATE_CLIENTS);
        }
    }

    private static boolean isSunlit(WorldGenLevel level, BlockPos pos) {
        if (!level.getBlockState(pos.above()).isAir()) {
            return false;
        }
        for (int dx = -6; dx <= 6; dx++) {
            for (int dz = Math.abs(dx) - 6; dz <= 6 - Math.abs(dx); dz++) {
                if (pos.getY() + 1 >= level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX() + dx, pos.getZ() + dz)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean anyNeighbor(WorldGenLevel level, BlockPos pos, BlockPos.MutableBlockPos neighbor, BiPredicate<BlockPos, BlockState> test) {
        for (Direction direction : Direction.values()) {
            if (test.test(neighbor.setWithOffset(pos, direction), level.getBlockState(neighbor))) {
                return true;
            }
        }
        return false;
    }
}
