package com.crispytwig.pleasance.world.entity.animal.fox;

import net.minecraft.world.entity.animal.fox.Fox;

public interface GreyFox {
    boolean pleasance$isGrey();

    void pleasance$setGrey(boolean grey);

    interface Storage {
        boolean isGrey(Fox fox);

        void setGrey(Fox fox, boolean grey);
    }
}
