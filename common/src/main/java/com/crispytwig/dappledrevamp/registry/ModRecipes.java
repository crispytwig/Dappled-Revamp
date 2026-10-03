package com.crispytwig.dappledrevamp.registry;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.platform.registry.DeferredHolder;
import com.crispytwig.dappledrevamp.platform.registry.DeferredRegister;
import com.crispytwig.dappledrevamp.world.item.crafting.DryRecipe;
import com.crispytwig.dappledrevamp.world.item.crafting.MoistenRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, DappledRevamp.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MoistenRecipe>> MOISTEN_SERIALIZER = RECIPE_SERIALIZERS.register("moisten",
            () -> MoistenRecipe.SERIALIZER);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DryRecipe>> DRY_SERIALIZER = RECIPE_SERIALIZERS.register("dry",
            () -> DryRecipe.SERIALIZER);
}
