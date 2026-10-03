package com.crispytwig.pleasance.datagen;

import com.crispytwig.pleasance.Pleasance;
import com.crispytwig.pleasance.registry.ModBlocks;
import com.crispytwig.pleasance.registry.ModDataComponents;
import com.crispytwig.pleasance.registry.ModItems;
import com.crispytwig.pleasance.world.level.block.LeafLayerBlock;
import com.crispytwig.pleasance.world.level.block.PoplarColor;
import com.crispytwig.pleasance.world.level.block.RegolithBlock;
import com.crispytwig.pleasance.world.level.block.ShelvedMushroom;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.conditional.FishingRodCast;
import net.minecraft.client.renderer.item.properties.select.ComponentContents;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplate;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {
    private static final TextureSlot TOP_OVERLAY = TextureSlot.create("top_overlay");
    private static final TextureSlot OVERLAY = TextureSlot.create("overlay");
    private static final TextureSlot PODZOL_TOP_OVERLAY = TextureSlot.create("podzol_top_overlay");
    private static final TextureSlot PODZOL_OVERLAY = TextureSlot.create("podzol_overlay");

    private static final ExtendedModelTemplate OVERLAY_GRASS_BLOCK = ExtendedModelTemplateBuilder.builder()
            .parent(Identifier.withDefaultNamespace("block/block"))
            .requiredTextureSlot(TextureSlot.PARTICLE)
            .requiredTextureSlot(TextureSlot.BOTTOM)
            .requiredTextureSlot(TextureSlot.TOP)
            .requiredTextureSlot(TOP_OVERLAY)
            .requiredTextureSlot(TextureSlot.SIDE)
            .requiredTextureSlot(OVERLAY)
            .element(element -> element
                    .from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.DOWN, face -> face.uvs(0, 0, 16, 16).texture(TextureSlot.BOTTOM).cullface(Direction.DOWN))
                    .face(Direction.UP, face -> face.uvs(0, 0, 16, 16).texture(TextureSlot.TOP).cullface(Direction.UP))
                    .allFacesExcept((direction, face) -> face.uvs(0, 0, 16, 16).texture(TextureSlot.SIDE).cullface(direction), Set.of(Direction.DOWN, Direction.UP)))
            .element(element -> element
                    .from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.UP, face -> face.uvs(0, 0, 16, 16).texture(TOP_OVERLAY).cullface(Direction.UP).tintindex(0))
                    .allFacesExcept((direction, face) -> face.uvs(0, 0, 16, 16).texture(OVERLAY).cullface(direction).tintindex(0), Set.of(Direction.DOWN, Direction.UP)))
            .build();

    private static final ExtendedModelTemplate OVERLAY_PODZOL_GRASS_BLOCK = ExtendedModelTemplateBuilder.builder()
            .parent(Identifier.withDefaultNamespace("block/block"))
            .requiredTextureSlot(TextureSlot.PARTICLE)
            .requiredTextureSlot(TextureSlot.BOTTOM)
            .requiredTextureSlot(TextureSlot.TOP)
            .requiredTextureSlot(TextureSlot.SIDE)
            .requiredTextureSlot(OVERLAY)
            .requiredTextureSlot(PODZOL_TOP_OVERLAY)
            .requiredTextureSlot(PODZOL_OVERLAY)
            .element(element -> element
                    .from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.DOWN, face -> face.uvs(0, 0, 16, 16).texture(TextureSlot.BOTTOM).cullface(Direction.DOWN))
                    .face(Direction.UP, face -> face.uvs(0, 0, 16, 16).texture(TextureSlot.TOP).cullface(Direction.UP).tintindex(0))
                    .allFacesExcept((direction, face) -> face.uvs(0, 0, 16, 16).texture(TextureSlot.SIDE).cullface(direction), Set.of(Direction.DOWN, Direction.UP)))
            .element(element -> element
                    .from(0, 0, 0).to(16, 16, 16)
                    .allFacesExcept((direction, face) -> face.uvs(0, 0, 16, 16).texture(OVERLAY).cullface(direction).tintindex(0), Set.of(Direction.DOWN, Direction.UP)))
            .element(element -> element
                    .from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.UP, face -> face.uvs(0, 0, 16, 16).texture(PODZOL_TOP_OVERLAY).cullface(Direction.UP))
                    .allFacesExcept((direction, face) -> face.uvs(0, 0, 16, 16).texture(PODZOL_OVERLAY).cullface(direction), Set.of(Direction.DOWN, Direction.UP)))
            .build();

    public ModModelProvider(PackOutput output) {
        super(output, Pleasance.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        patchyGrass(blockModels);
        patchyPodzol(blockModels);
        regolith(blockModels);
        wormBin(blockModels);
        leafLayer(blockModels, ModBlocks.RED_POPLAR_LEAF_LAYER.get(), Blocks.RED_POPLAR_LEAVES);
        leafLayer(blockModels, ModBlocks.ORANGE_POPLAR_LEAF_LAYER.get(), Blocks.ORANGE_POPLAR_LEAVES);
        leafLayer(blockModels, ModBlocks.YELLOW_POPLAR_LEAF_LAYER.get(), Blocks.YELLOW_POPLAR_LEAVES);
        poplarSaplings(blockModels);
        shelfMushroom(blockModels);

        simpleItem(itemModels, ModItems.WORM.get());
        spawnEgg(itemModels, ModItems.WORM_SPAWN_EGG.get());
        itemModels.generateBooleanDispatch(ModItems.BAITED_ROD.get(), new FishingRodCast(),
                ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(Items.FISHING_ROD, "_cast")),
                ItemModelUtils.plainModel(itemModels.createFlatItemModel(ModItems.BAITED_ROD.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM)));
        wormBucket(itemModels);
    }

    private void patchyGrass(BlockModelGenerators blockModels) {
        Block block = ModBlocks.PATCHY_GRASS.get();
        TextureMapping textures = new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"))
                .put(TOP_OVERLAY, TextureMapping.getBlockTexture(block, "_top_overlay"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side"))
                .put(OVERLAY, TextureMapping.getBlockTexture(block, "_side_overlay"));
        Identifier model = OVERLAY_GRASS_BLOCK.create(block, textures, blockModels.modelOutput);
        MultiVariant snowy = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(Blocks.GRASS_BLOCK, "_snow"));
        blockModels.createGrassLikeBlock(block, BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(model)), snowy);
        blockModels.registerSimpleTintedItemModel(block, model, new GrassColorSource());
    }

    private void regolith(BlockModelGenerators blockModels) {
        Block block = ModBlocks.REGOLITH.get();
        Identifier middle = ModelTemplates.CUBE_ALL.create(block, TextureMapping.cube(block), blockModels.modelOutput);
        Identifier top = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(block, "_top", new TextureMapping()
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_top"))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block)), blockModels.modelOutput);
        Identifier bottom = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(block, "_bottom", new TextureMapping()
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_bottom"))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.STONE)), blockModels.modelOutput);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(RegolithBlock.SEGMENT)
                .select(RegolithBlock.Segment.TOP, BlockModelGenerators.plainVariant(top))
                .select(RegolithBlock.Segment.MIDDLE, BlockModelGenerators.plainVariant(middle))
                .select(RegolithBlock.Segment.BOTTOM, BlockModelGenerators.plainVariant(bottom))));
        blockModels.registerSimpleItemModel(block, middle);
    }

    private void patchyPodzol(BlockModelGenerators blockModels) {
        Block block = ModBlocks.PATCHY_PODZOL.get();
        TextureMapping textures = new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.GRASS_BLOCK, "_top"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(OVERLAY, TextureMapping.getBlockTexture(Blocks.GRASS_BLOCK, "_side_overlay"))
                .put(PODZOL_TOP_OVERLAY, TextureMapping.getBlockTexture(block, "_top_overlay"))
                .put(PODZOL_OVERLAY, TextureMapping.getBlockTexture(block, "_side_overlay"));
        Identifier model = OVERLAY_PODZOL_GRASS_BLOCK.create(block, textures, blockModels.modelOutput);
        MultiVariant snowy = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(Blocks.GRASS_BLOCK, "_snow"));
        blockModels.createGrassLikeBlock(block, BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(model)), snowy);
        blockModels.registerSimpleTintedItemModel(block, model, new GrassColorSource());
    }

    private void wormBin(BlockModelGenerators blockModels) {
        Block block = ModBlocks.WORM_BIN.get();
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block)
                .with(BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block)));
        for (int level = 1; level <= 8; level++) {
            String suffix = level == 8 ? "_contents_ready" : "_contents" + level;
            generator.with(BlockModelGenerators.condition().term(BlockStateProperties.LEVEL_COMPOSTER, level),
                    BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(Blocks.COMPOSTER, suffix)));
        }
        blockModels.blockStateOutput.accept(generator);
    }

    private void leafLayer(BlockModelGenerators blockModels, Block block, Block leaves) {
        TextureMapping textures = TextureMapping.defaultTexture(leaves).copySlot(TextureSlot.TEXTURE, TextureSlot.PARTICLE);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(LeafLayerBlock.LAYERS)
                .generate(layers -> BlockModelGenerators.plainVariant(layers < 8
                        ? snowHeight(layers * 2).createWithSuffix(block, "_height" + layers * 2, textures, blockModels.modelOutput)
                        : ModelLocationUtils.getModelLocation(leaves)))));
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block, "_height2"));
    }

    private static ExtendedModelTemplate snowHeight(int height) {
        return ExtendedModelTemplateBuilder.builder()
                .parent(Identifier.withDefaultNamespace("block/snow_height" + height))
                .requiredTextureSlot(TextureSlot.TEXTURE)
                .requiredTextureSlot(TextureSlot.PARTICLE)
                .build();
    }

    private void poplarSaplings(BlockModelGenerators blockModels) {
        Map<PoplarColor, ItemModel.Unbaked> itemCases = new LinkedHashMap<>();
        PropertyDispatch.C1<MultiVariant, PoplarColor> sapling = PropertyDispatch.initial(PoplarColor.PROPERTY)
                .select(PoplarColor.ORANGE, BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(Blocks.POPLAR_SAPLING)));
        PropertyDispatch.C1<MultiVariant, PoplarColor> potted = PropertyDispatch.initial(PoplarColor.PROPERTY)
                .select(PoplarColor.ORANGE, BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(Blocks.POTTED_POPLAR_SAPLING)));

        for (PoplarColor color : new PoplarColor[]{PoplarColor.RED, PoplarColor.YELLOW}) {
            String name = color.getSerializedName() + "_poplar_sapling";
            Material texture = new Material(Pleasance.location("block/" + name));
            sapling.select(color, BlockModelGenerators.plainVariant(
                    ModelTemplates.CROSS.create(Pleasance.location("block/" + name), TextureMapping.cross(texture), blockModels.modelOutput)));
            potted.select(color, BlockModelGenerators.plainVariant(
                    ModelTemplates.FLOWER_POT_CROSS.create(Pleasance.location("block/potted_" + name), TextureMapping.plant(texture), blockModels.modelOutput)));
            itemCases.put(color, ItemModelUtils.plainModel(
                    ModelTemplates.FLAT_ITEM.create(Pleasance.location("item/" + name), TextureMapping.layer0(texture), blockModels.modelOutput)));
        }

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(Blocks.POPLAR_SAPLING).with(sapling));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(Blocks.POTTED_POPLAR_SAPLING).with(potted));
        blockModels.itemModelOutput.accept(Items.POPLAR_SAPLING, ItemModelUtils.selectBlockItemProperty(PoplarColor.PROPERTY,
                ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(Items.POPLAR_SAPLING)), itemCases));
    }

    private void shelfMushroom(BlockModelGenerators blockModels) {
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(Blocks.SHELF_MUSHROOM)
                .with(PropertyDispatch.initial(BlockStateProperties.AGE_1, ShelvedMushroom.PROPERTY).generate((age, shelved) ->
                        BlockModelGenerators.plainVariant(shelved
                                ? Pleasance.location("block/shelf_mushroom_stage" + age + "_shelved")
                                : ModelLocationUtils.getModelLocation(Blocks.SHELF_MUSHROOM, "_stage" + age))))
                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    private void simpleItem(ItemModelGenerators itemModels, Item item) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private void spawnEgg(ItemModelGenerators itemModels, Item item) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private void wormBucket(ItemModelGenerators itemModels) {
        Item item = ModItems.WORM_BUCKET.get();
        ItemModel.Unbaked single = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_1", ModelTemplates.FLAT_ITEM));
        itemModels.itemModelOutput.accept(item, ItemModelUtils.select(new ComponentContents<>(ModDataComponents.WORM_COUNT.get()), single,
                ItemModelUtils.when(2, ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_2", ModelTemplates.FLAT_ITEM))),
                ItemModelUtils.when(3, ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_3", ModelTemplates.FLAT_ITEM))),
                ItemModelUtils.when(4, ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_4", ModelTemplates.FLAT_ITEM))),
                ItemModelUtils.when(5, ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_5", ModelTemplates.FLAT_ITEM)))));
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.concat(super.getKnownBlocks(),
                Stream.of(Blocks.POPLAR_SAPLING.builtInRegistryHolder(), Blocks.POTTED_POPLAR_SAPLING.builtInRegistryHolder(),
                        Blocks.SHELF_MUSHROOM.builtInRegistryHolder()));
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.concat(super.getKnownItems(), Stream.of(Items.POPLAR_SAPLING.builtInRegistryHolder()));
    }
}
