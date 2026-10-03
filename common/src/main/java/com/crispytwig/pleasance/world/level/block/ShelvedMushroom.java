package com.crispytwig.pleasance.world.level.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public final class ShelvedMushroom {
    public static final BooleanProperty PROPERTY = BooleanProperty.create("shelved");
    public static final double OFFSET = 5.0 / 16.0;

    private ShelvedMushroom() {
    }

    public static boolean supports(BlockState above) {
        return !above.isAir() && !above.canBeReplaced();
    }
}
