package com.crispytwig.dappledrevamp.world.entity.animal.fox;

import net.minecraft.world.entity.animal.fox.Fox;

public interface GreyFox {
    boolean dappledRevamp$isGrey();

    void dappledRevamp$setGrey(boolean grey);

    interface Storage {
        boolean isGrey(Fox fox);

        void setGrey(Fox fox, boolean grey);
    }
}
