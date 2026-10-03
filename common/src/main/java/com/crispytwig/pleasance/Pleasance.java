package com.crispytwig.pleasance;

import com.crispytwig.pleasance.registry.ModBlocks;
import com.crispytwig.pleasance.registry.ModDataComponents;
import com.crispytwig.pleasance.registry.ModEntityTypes;
import com.crispytwig.pleasance.registry.ModFeatures;
import com.crispytwig.pleasance.registry.ModItems;
import com.crispytwig.pleasance.registry.ModParticleTypes;
import com.crispytwig.pleasance.registry.ModRecipes;
import com.crispytwig.pleasance.registry.ModSoundEvents;
import com.crispytwig.pleasance.world.entity.animal.fox.GreyFox;
import com.crispytwig.pleasance.world.entity.animal.worm.Worm;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;
import org.slf4j.Logger;

public final class Pleasance {
    public static final String MOD_ID = "pleasance";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static GreyFox.Storage greyFoxStorage;

    private Pleasance() {
    }

    public static Identifier location(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void bootstrap() {
        touch(ModDataComponents.DATA_COMPONENT_TYPES);
        touch(ModSoundEvents.SOUND_EVENTS);
        touch(ModParticleTypes.PARTICLE_TYPES);
        touch(ModEntityTypes.ENTITY_TYPES);
        touch(ModItems.ITEMS);
        touch(ModBlocks.BLOCKS);
        touch(ModRecipes.RECIPE_SERIALIZERS);
        touch(ModFeatures.FEATURE_TYPES);
    }

    private static void touch(Object registry) {
    }

    @FunctionalInterface
    public interface AttributeRegistrar {
        void register(EntityType<? extends LivingEntity> type, AttributeSupplier.Builder builder);
    }

    @FunctionalInterface
    public interface SpawnPlacementRegistrar {
        <T extends Mob> void register(EntityType<T> type, SpawnPlacementType placementType, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate);
    }

    public static void createAttributes(AttributeRegistrar r) {
        r.register(ModEntityTypes.WORM.get(), Worm.createAttributes());
    }

    public static void registerSpawnPlacements(SpawnPlacementRegistrar r) {
        r.register(ModEntityTypes.WORM.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Worm::checkWormSpawnRules);
    }

    public static void init(GreyFox.Storage storage) {
        greyFoxStorage = storage;
    }

    public static GreyFox.Storage greyFoxStorage() {
        return greyFoxStorage;
    }
}
