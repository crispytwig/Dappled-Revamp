package com.crispytwig.pleasance.world.item.crafting;

import com.crispytwig.pleasance.world.level.block.Moist;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class MoistenRecipe extends CustomRecipe {
    public static final MoistenRecipe INSTANCE = new MoistenRecipe();
    public static final RecipeSerializer<MoistenRecipe> SERIALIZER = new RecipeSerializer<>(
        MapCodec.unit(INSTANCE),
        StreamCodec.<RegistryFriendlyByteBuf, MoistenRecipe>unit(INSTANCE)
    );
    private static final int BLOCKS = 8;

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return !this.findBlock(input).isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack block = this.findBlock(input);
        return block.isEmpty() ? ItemStack.EMPTY : Moist.makeMoist(block.copyWithCount(BLOCKS));
    }

    private ItemStack findBlock(CraftingInput input) {
        if (input.ingredientCount() != BLOCKS + 1) {
            return ItemStack.EMPTY;
        }
        boolean hasWater = false;
        ItemStack block = ItemStack.EMPTY;
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (stack.isEmpty()) {
                continue;
            }
            if (stack.is(Items.WATER_BUCKET)) {
                if (hasWater) {
                    return ItemStack.EMPTY;
                }
                hasWater = true;
            } else if (!Moist.canMoisten(stack) || !block.isEmpty() && !ItemStack.isSameItemSameComponents(block, stack)) {
                return ItemStack.EMPTY;
            } else {
                block = stack;
            }
        }
        return hasWater ? block : ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<MoistenRecipe> getSerializer() {
        return SERIALIZER;
    }
}
