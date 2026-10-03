package com.crispytwig.pleasance.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class WormBinBlock extends ComposterBlock {
    public WormBinBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public static boolean canCompost(ItemStack stack) {
        return stack.has(DataComponents.FOOD);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!canCompost(itemStack)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
        return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
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
