package com.crispytwig.pleasance.world.item.crafting;

import com.crispytwig.pleasance.world.level.block.Moist;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;

public class DryRecipe extends SmeltingRecipe {
    public static final RecipeSerializer<DryRecipe> SERIALIZER = new RecipeSerializer<>(
        MapCodec.unit(DryRecipe::new),
        StreamCodec.<RegistryFriendlyByteBuf, DryRecipe>of((buf, recipe) -> {}, buf -> new DryRecipe())
    );

    public DryRecipe() {
        super(
            new Recipe.CommonInfo(false),
            new AbstractCookingRecipe.CookingBookInfo(CookingBookCategory.BLOCKS, ""),
            Ingredient.of(BuiltInRegistries.ITEM.stream()
                .filter(item -> item instanceof BlockItem blockItem && blockItem.getBlock().defaultBlockState().hasProperty(Moist.PROPERTY))),
            new ItemStackTemplate(Items.MOSS_BLOCK),
            0.1F,
            200
        );
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.input().test(input.item()) && Moist.isMoist(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input) {
        return Moist.makeDry(input.item().copyWithCount(1));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeSerializer<SmeltingRecipe> getSerializer() {
        return (RecipeSerializer<SmeltingRecipe>) (RecipeSerializer<?>) SERIALIZER;
    }
}
