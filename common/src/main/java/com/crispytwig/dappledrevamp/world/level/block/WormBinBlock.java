package com.crispytwig.dappledrevamp.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class WormBinBlock extends ComposterBlock {
    public WormBinBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(LEVEL) < MAX_LEVEL;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(18) != 0) {
            return;
        }
        BlockState newState = state.cycle(LEVEL);
        level.setBlockAndUpdate(pos, newState);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
        level.levelEvent(LevelEvent.COMPOSTER_FILL, pos, 1);
        if (newState.getValue(LEVEL) == MAX_LEVEL) {
            level.scheduleTick(pos, this, 20);
        }
    }
}
