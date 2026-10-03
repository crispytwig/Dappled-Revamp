package com.crispytwig.pleasance.world.level.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class RegolithBlock extends Block {
    public static final EnumProperty<Segment> SEGMENT = EnumProperty.create("segment", Segment.class);

    public RegolithBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SEGMENT, Segment.MIDDLE));
    }

    @Override
    protected SoundType getSoundType(BlockState state) {
        return state.getValue(SEGMENT) == Segment.BOTTOM ? SoundType.STONE : super.getSoundType(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SEGMENT);
    }

    public enum Segment implements StringRepresentable {
        TOP("top"),
        MIDDLE("middle"),
        BOTTOM("bottom");

        private final String name;

        Segment(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
