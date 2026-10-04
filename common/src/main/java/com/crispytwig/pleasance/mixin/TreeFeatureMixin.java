package com.crispytwig.pleasance.mixin;

import com.crispytwig.pleasance.tags.ModBiomeTags;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.treedecorators.ShelfMushroomDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(TreeFeature.class)
public abstract class TreeFeatureMixin {
    @Unique
    private static final ShelfMushroomDecorator pleasance$SHELF_MUSHROOMS = new ShelfMushroomDecorator(0.25F);

    @ModifyExpressionValue(method = "place", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/feature/TreeFeature;decorators:Ljava/util/List;"))
    private List<TreeDecorator> pleasance$addShelfMushrooms(List<TreeDecorator> decorators, @Local(argsOnly = true) WorldGenLevel level, @Local(argsOnly = true) BlockPos origin) {
        if (!(level instanceof WorldGenRegion)
            || decorators.stream().anyMatch(ShelfMushroomDecorator.class::isInstance)
            || !level.getBiome(origin).is(ModBiomeTags.HAS_SHELF_MUSHROOMS)) {
            return decorators;
        }
        List<TreeDecorator> withShelfMushrooms = new ArrayList<>(decorators);
        withShelfMushrooms.add(pleasance$SHELF_MUSHROOMS);
        return withShelfMushrooms;
    }
}
