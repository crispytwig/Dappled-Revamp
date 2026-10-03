package com.crispytwig.dappledrevamp.datagen;

import com.crispytwig.dappledrevamp.registry.ModBlocks;
import com.crispytwig.dappledrevamp.world.level.block.PoplarColor;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.jspecify.annotations.NonNull;

import java.util.Set;
import java.util.stream.Stream;

public class ModBlockLootProvider extends BlockLootSubProvider {
    private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};

    protected ModBlockLootProvider(LootTableSubProvider.Context output) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), output);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.REGOLITH.get());
        add(ModBlocks.PATCHY_GRASS.get(), block -> createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
        add(ModBlocks.WORM_BIN.get(), block -> LootTable.lootTable()
                .withPool(LootPool.lootPool().add((LootPoolEntryContainer.Builder<?>) applyExplosionDecay(block, LootItem.lootTableItem(block))))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.BONE_MEAL))
                        .when(MatchBlock.blockMatches(this.blocks, block, StatePropertiesPredicate.Builder.properties().hasProperty(ComposterBlock.LEVEL, 8)))));

        add(Blocks.POPLAR_SAPLING, LootTable.lootTable()
                .withPool(applyExplosionCondition(Blocks.POPLAR_SAPLING, LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(coloredSapling(Blocks.POPLAR_SAPLING)))));
        add(Blocks.POTTED_POPLAR_SAPLING, flowerPotTable()
                .withPool(applyExplosionCondition(Blocks.POTTED_POPLAR_SAPLING, LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(coloredSapling(Blocks.POTTED_POPLAR_SAPLING)))));
        add(Blocks.RED_POPLAR_LEAVES, coloredPoplarLeaves(Blocks.RED_POPLAR_LEAVES, PoplarColor.RED));
        add(Blocks.YELLOW_POPLAR_LEAVES, coloredPoplarLeaves(Blocks.YELLOW_POPLAR_LEAVES, PoplarColor.YELLOW));
    }

    private LootTable.Builder flowerPotTable() {
        return LootTable.lootTable()
                .withPool(applyExplosionCondition(Blocks.FLOWER_POT, LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(Blocks.FLOWER_POT))));
    }

    private AlternativesEntry.Builder coloredSapling(Block block) {
        return AlternativesEntry.alternatives(
                LootItem.lootTableItem(Blocks.POPLAR_SAPLING)
                        .when(MatchBlock.blockMatches(this.blocks, block, StatePropertiesPredicate.Builder.properties().hasProperty(PoplarColor.PROPERTY, PoplarColor.ORANGE))),
                LootItem.lootTableItem(Blocks.POPLAR_SAPLING)
                        .apply(CopyBlockState.copyState(block).copy(PoplarColor.PROPERTY))
        );
    }

    private LootTable.Builder coloredPoplarLeaves(Block leaves, PoplarColor color) {
        LootItemCondition.Builder hasShearsOrSilkTouch = new AnyOfCondition.Builder().or(hasShears()).or(hasSilkTouch());
        return createSelfDropDispatchTable(
                leaves,
                Holder.direct(hasShearsOrSilkTouch.build()),
                (LootPoolEntryContainer.Builder<?>) applyExplosionCondition(leaves, LootItem.lootTableItem(Blocks.POPLAR_SAPLING))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(this.enchantments.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_SAPLING_CHANCES))
                        .apply(SetComponentsFunction.setComponent(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(PoplarColor.PROPERTY, color)))
        ).withPool(LootPool.lootPool()
                .setRolls(ContextIntProviders.exactly(1))
                .when(hasShearsOrSilkTouch.invert())
                .add((LootPoolEntryContainer.Builder<?>) applyExplosionDecay(leaves, LootItem.lootTableItem(Items.STICK)
                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(this.enchantments.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_STICK_CHANCES))));
    }

    @Override
    protected @NonNull Iterable<Block> getKnownBlocks() {
        return Stream.concat(
                ModBlocks.BLOCKS.getEntries().stream().map(holder -> (Block) holder.get()),
                Stream.of(Blocks.POPLAR_SAPLING, Blocks.POTTED_POPLAR_SAPLING, Blocks.RED_POPLAR_LEAVES, Blocks.YELLOW_POPLAR_LEAVES)
        ).toList();
    }
}
