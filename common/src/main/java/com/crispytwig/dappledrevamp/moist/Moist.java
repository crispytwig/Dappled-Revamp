package com.crispytwig.dappledrevamp.moist;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public final class Moist {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String LIST_PATH = DappledRevamp.MOD_ID + "/moistenable.json";

    public static final BooleanProperty PROPERTY = BooleanProperty.create("moist");
    private static @Nullable Set<Identifier> moistenable;

    private Moist() {
    }

    public static boolean canBeMoist(@Nullable ResourceKey<Block> id) {
        return id != null && moistenable().contains(id.identifier());
    }

    public static boolean isMoist(BlockState state) {
        return state.hasProperty(PROPERTY) && state.getValue(PROPERTY);
    }

    public static boolean canMoisten(BlockState state) {
        return state.hasProperty(PROPERTY) && !state.getValue(PROPERTY);
    }

    public static boolean isMoist(ItemStack stack) {
        return Boolean.TRUE.equals(stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).get(PROPERTY));
    }

    public static boolean canMoisten(ItemStack stack) {
        return stack.getItem() instanceof BlockItem blockItem
            && blockItem.getBlock().defaultBlockState().hasProperty(PROPERTY)
            && !isMoist(stack);
    }

    public static ItemStack makeMoist(ItemStack stack) {
        stack.set(DataComponents.BLOCK_STATE, stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).with(PROPERTY, true));
        return stack;
    }

    private static synchronized Set<Identifier> moistenable() {
        if (moistenable == null) {
            Set<Identifier> blocks = new HashSet<>();
            try {
                Enumeration<URL> lists = Moist.class.getClassLoader().getResources(LIST_PATH);
                while (lists.hasMoreElements()) {
                    URL list = lists.nextElement();
                    try (Reader reader = new InputStreamReader(list.openStream(), StandardCharsets.UTF_8)) {
                        for (JsonElement block : JsonParser.parseReader(reader).getAsJsonObject().getAsJsonArray("blocks")) {
                            blocks.add(Identifier.parse(block.getAsString()));
                        }
                    } catch (IOException | RuntimeException e) {
                        LOGGER.error("Failed to read moistenable blocks from {}", list, e);
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Failed to find moistenable block lists", e);
            }
            moistenable = Set.copyOf(blocks);
        }
        return moistenable;
    }
}
