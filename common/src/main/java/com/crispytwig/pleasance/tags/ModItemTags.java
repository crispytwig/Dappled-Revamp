package com.crispytwig.pleasance.tags;

import com.crispytwig.pleasance.Pleasance;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
public class ModItemTags {
    public static final TagKey<Item> FISHING_BAIT = tag("fishing_bait");

    private static TagKey<Item> tag(String name) {
        return TagKey.create(Registries.ITEM, Pleasance.location(name));
    }
}
