package com.crispytwig.dappledrevamp.fox;

import com.crispytwig.dappledrevamp.DappledRevamp;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.level.biome.Biome;

public interface GreyFox {
    TagKey<Biome> SPAWNS_GREY_FOXES = TagKey.create(Registries.BIOME, DappledRevamp.id("spawns_grey_foxes"));

    boolean dappledRevamp$isGrey();

    void dappledRevamp$setGrey(boolean grey);

    interface Storage {
        boolean isGrey(Fox fox);

        void setGrey(Fox fox, boolean grey);
    }
}
