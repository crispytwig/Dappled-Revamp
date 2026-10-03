package com.crispytwig.pleasance.datagen;

import com.crispytwig.pleasance.Pleasance;
import com.crispytwig.pleasance.registry.ModBlocks;
import com.crispytwig.pleasance.registry.ModItems;
import com.crispytwig.pleasance.world.item.crafting.DryRecipe;
import com.crispytwig.pleasance.world.item.crafting.MoistenRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ModRecipeProvider extends RecipeProvider {
    private final RecipeOutput recipes;

    protected ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
        this.recipes = new RecipeOutput() {
            @Override
            public void accept(ResourceKey<Recipe<?>> id, Recipe<?> recipe, @Nullable AdvancementHolder advancement,
                               net.neoforged.neoforge.common.conditions.ICondition... conditions) {
                ModRecipeProvider.this.output.accept(id, recipe, null, conditions);
            }

            @Override
            public Advancement.Builder advancement() {
                return ModRecipeProvider.this.output.advancement();
            }

            @Override
            public <T> HolderGetter<T> lookup(ResourceKey<? extends Registry<? extends T>> key) {
                return ModRecipeProvider.this.output.lookup(key);
            }

            @Override
            public <T> Stream<Holder.Reference<T>> listContextElements(ResourceKey<? extends Registry<? extends T>> key) {
                return ModRecipeProvider.this.output.listContextElements(key);
            }
        };
    }

    private static ResourceKey<Recipe<?>> id(String path) {
        return ResourceKey.create(Registries.RECIPE, Pleasance.location(path));
    }

    @Override
    protected void buildRecipes() {
        misc();
        leafLayers();
        moist();
    }

    private void misc() {
        shapeless(RecipeCategory.MISC, ModBlocks.WORM_BIN.get())
                .requires(Items.COMPOSTER)
                .requires(ModItems.WORM.get())
                .unlockedBy("has_ingredient", has(ModItems.WORM.get()))
                .save(this.recipes, id("worm_bin"));

        ItemStackTemplate leapingStew = new ItemStackTemplate(Items.SUSPICIOUS_STEW, DataComponentPatch.builder()
                .set(DataComponents.SUSPICIOUS_STEW_EFFECTS, new SuspiciousStewEffects(List.of(new SuspiciousStewEffects.Entry(MobEffects.JUMP_BOOST, 160))))
                .build());
        shapeless(RecipeCategory.FOOD, leapingStew)
                .requires(Items.BOWL)
                .requires(Items.BROWN_MUSHROOM)
                .requires(Items.RED_MUSHROOM)
                .requires(Items.SHELF_MUSHROOM)
                .group("suspicious_stew")
                .unlockedBy("has_ingredient", has(Items.SHELF_MUSHROOM))
                .save(this.recipes, id("suspicious_stew_from_shelf_mushroom"));
    }

    private void leafLayers() {
        leafLayer(ModBlocks.RED_POPLAR_LEAF_LAYER.get(), Items.RED_POPLAR_LEAVES);
        leafLayer(ModBlocks.ORANGE_POPLAR_LEAF_LAYER.get(), Items.ORANGE_POPLAR_LEAVES);
        leafLayer(ModBlocks.YELLOW_POPLAR_LEAF_LAYER.get(), Items.YELLOW_POPLAR_LEAVES);
    }

    private void leafLayer(ItemLike layer, ItemLike leaves) {
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, layer, Ingredient.of(leaves))
                .unlockedBy(getHasName(leaves), has(leaves))
                .save(this.recipes, id(getItemName(layer)));
    }

    private void moist() {
        this.recipes.accept(id("moisten"), MoistenRecipe.INSTANCE, null);
        this.recipes.accept(id("dry"), new DryRecipe(), null);
    }

    public static MultiRegistryBootstrap create() {
        return new MultiRegistryBootstrap() {
            @Override
            public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
                return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
            }

            @Override
            public void run(MultiRegistryBootstrap.BootstrapGetter registries) {
                new ModRecipeProvider(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT)).buildRecipes();
            }
        };
    }
}
