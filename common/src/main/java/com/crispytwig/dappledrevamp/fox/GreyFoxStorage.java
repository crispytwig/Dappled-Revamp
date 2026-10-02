package com.crispytwig.dappledrevamp.fox;

import net.minecraft.world.entity.animal.fox.Fox;

public interface GreyFoxStorage {
    boolean isGrey(Fox fox);

    void setGrey(Fox fox, boolean grey);
}
