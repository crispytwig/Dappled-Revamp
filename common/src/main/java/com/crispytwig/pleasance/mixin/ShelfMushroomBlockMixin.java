package com.crispytwig.pleasance.mixin;

import com.crispytwig.pleasance.world.level.block.ShelvedMushroom;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.ShelfMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mixin(ShelfMushroomBlock.class)
public abstract class ShelfMushroomBlockMixin extends HorizontalDirectionalBlock {
    @Shadow @Final private static List<Map<Direction, VoxelShape>> SHAPES;

    @Unique
    private static final List<Map<Direction, VoxelShape>> pleasance$SHELVED_SHAPES = SHAPES.stream()
        .map(shapes -> Map.copyOf(shapes.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().move(0.0, ShelvedMushroom.OFFSET, 0.0)))))
        .toList();

    protected ShelfMushroomBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void pleasance$defaultUnshelved(Properties properties, CallbackInfo ci) {
        this.registerDefaultState(this.defaultBlockState().setValue(ShelvedMushroom.PROPERTY, false));
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void pleasance$addShelved(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(ShelvedMushroom.PROPERTY);
    }

    @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
    private void pleasance$placeShelved(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();
        if (state != null) {
            cir.setReturnValue(state.setValue(ShelvedMushroom.PROPERTY,
                ShelvedMushroom.supports(context.getLevel().getBlockState(context.getClickedPos().above()))));
        }
    }

    @Inject(method = "updateShape", at = @At("RETURN"), cancellable = true)
    private void pleasance$updateShelved(
        BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour,
        BlockPos neighbourPos, BlockState neighbourState, RandomSource random, CallbackInfoReturnable<BlockState> cir
    ) {
        BlockState updated = cir.getReturnValue();
        if (directionToNeighbour == Direction.UP && updated.hasProperty(ShelvedMushroom.PROPERTY)) {
            cir.setReturnValue(updated.setValue(ShelvedMushroom.PROPERTY, ShelvedMushroom.supports(neighbourState)));
        }
    }

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void pleasance$shelvedShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (state.getValue(ShelvedMushroom.PROPERTY)) {
            cir.setReturnValue(pleasance$SHELVED_SHAPES.get(state.getValue(ShelfMushroomBlock.AGE)).get(state.getValue(FACING)));
        }
    }
}
