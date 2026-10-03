package com.crispytwig.pleasance.world.level.block;

import com.crispytwig.pleasance.mixin.LivingEntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class LeafLayerBlock extends SnowLayerBlock {
    private final Supplier<SimpleParticleType> burstParticle;

    public LeafLayerBlock(Supplier<SimpleParticleType> burstParticle, BlockBehaviour.Properties properties) {
        super(properties);
        this.burstParticle = burstParticle;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getEntityInsideCollisionShape(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return this.getShape(state, level, pos, CollisionContext.empty());
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return type == PathComputationType.LAND;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (entity instanceof LivingEntity && pos.equals(entity.blockPosition())) {
            double factor = 1.0 - state.getValue(LAYERS) * 0.05;
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(factor, 1.0, factor));
        }
    }

    public static boolean cushionFall(Level level, Entity entity, double fallDistance) {
        BlockPos bottom = entity.blockPosition();
        if (!(entity instanceof LivingEntity) || !(level.getBlockState(bottom).getBlock() instanceof LeafLayerBlock)) {
            return false;
        }
        LivingEntityInvoker invoker = (LivingEntityInvoker) entity;
        int fullDamage = invoker.pleasance$calculateFallDamage(fallDistance, 1.0F);
        if (fullDamage <= 0) {
            return false;
        }

        int available = 0;
        int height = 0;
        for (BlockState state; (state = level.getBlockState(bottom.above(height))).getBlock() instanceof LeafLayerBlock; height++) {
            available += state.getValue(LAYERS);
        }
        int cushionedDamage = invoker.pleasance$calculateFallDamage(fallDistance, damageModifier(available));
        int consumed = 1;
        while (consumed < available && invoker.pleasance$calculateFallDamage(fallDistance, damageModifier(consumed)) > cushionedDamage) {
            consumed++;
        }
        entity.causeFallDamage(fallDistance, damageModifier(consumed), entity.damageSources().fall());

        if (level instanceof ServerLevel serverLevel) {
            int particles = Math.min(8 + fullDamage * 4, 64);
            double speed = Math.min(0.08 + fullDamage * 0.02, 0.4);
            int remaining = consumed;
            for (int i = height - 1; i >= 0 && remaining > 0; i--) {
                BlockPos pos = bottom.above(i);
                BlockState state = level.getBlockState(pos);
                int removed = Math.min(remaining, state.getValue(LAYERS));
                remaining -= removed;
                ((LeafLayerBlock) state.getBlock()).burst(serverLevel, pos, state, entity, removed, Mth.ceil(particles * removed / (float) consumed), speed);
            }
        }
        return true;
    }

    private static float damageModifier(int layers) {
        return 1.0F - Math.min(layers * 0.1F, 0.8F);
    }

    private void burst(ServerLevel level, BlockPos pos, BlockState state, Entity entity, int removed, int particles, double speed) {
        int layers = state.getValue(LAYERS);
        if (removed == layers) {
            level.destroyBlock(pos, false, entity);
        } else {
            BlockState newState = state.setValue(LAYERS, layers - removed);
            level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_DESTROY_BLOCK, pos, Block.getId(state));
            level.setBlockAndUpdate(pos, newState);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, newState));
        }
        level.sendParticles(this.burstParticle.get(), pos.getX() + 0.5, pos.getY() + layers / 8.0, pos.getZ() + 0.5,
            particles, 0.25, 0.05, 0.25, speed, speed * 0.8, speed);
    }
}
