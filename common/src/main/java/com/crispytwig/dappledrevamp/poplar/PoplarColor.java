package com.crispytwig.dappledrevamp.poplar;

import net.minecraft.core.component.DataComponents;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.levelgen.feature.Feature;

public enum PoplarColor implements StringRepresentable {
    ORANGE("orange", TreeFeatures.ORANGE_POPLAR),
    RED("red", TreeFeatures.RED_POPLAR),
    YELLOW("yellow", TreeFeatures.YELLOW_POPLAR);

    public static final EnumProperty<PoplarColor> PROPERTY = EnumProperty.create("color", PoplarColor.class);

    private final String name;
    private final ResourceKey<Feature> tree;

    PoplarColor(String name, ResourceKey<Feature> tree) {
        this.name = name;
        this.tree = tree;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public ResourceKey<Feature> tree() {
        return this.tree;
    }

    public String tooltipKey() {
        return "tooltip.dappledrevamp.poplar_sapling." + this.name;
    }

    public ItemStack sapling() {
        ItemStack stack = new ItemStack(Items.POPLAR_SAPLING);
        if (this != ORANGE) {
            stack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(PROPERTY, this));
        }
        return stack;
    }

    public static PoplarColor of(ItemStack stack) {
        PoplarColor color = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).get(PROPERTY);
        return color == null ? ORANGE : color;
    }

    public static boolean isPoplarSapling(Block block) {
        return "block.minecraft.poplar_sapling".equals(block.getDescriptionId());
    }
}
