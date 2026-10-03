package com.crispytwig.pleasance.registry;

import com.crispytwig.pleasance.Pleasance;
import com.crispytwig.pleasance.platform.registry.DeferredHolder;
import com.crispytwig.pleasance.platform.registry.DeferredRegister;
import com.crispytwig.pleasance.world.item.crafting.DryRecipe;
import com.crispytwig.pleasance.world.item.crafting.MoistenRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Pleasance.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MoistenRecipe>> MOISTEN_SERIALIZER = RECIPE_SERIALIZERS.register("moisten",
            () -> MoistenRecipe.SERIALIZER);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DryRecipe>> DRY_SERIALIZER = RECIPE_SERIALIZERS.register("dry",
            () -> DryRecipe.SERIALIZER);
}
