package com.crispytwig.dappledrevamp.worm;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.Registrar;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public final class WormContent {
    public static Supplier<DataComponentType<Integer>> WORM_COUNT;
    public static Supplier<SoundEvent> BAIT_SOUND;
    public static Supplier<SoundEvent> HOOK_SOUND;
    public static Supplier<EntityType<Worm>> WORM;
    public static Supplier<Block> WORM_BIN;
    public static Supplier<Item> WORM_ITEM;
    public static Supplier<Item> WORM_BUCKET;
    public static Supplier<Item> BAITED_ROD;
    public static Supplier<Item> WORM_SPAWN_EGG;
    public static Supplier<Item> WORM_BIN_ITEM;
    public static List<TabEntry> TAB_ENTRIES;

    private WormContent() {
    }

    public static void register(Registrar registrar) {
        WORM_COUNT = registrar.register(BuiltInRegistries.DATA_COMPONENT_TYPE, "worm_count", () -> DataComponentType.<Integer>builder()
            .persistent(ExtraCodecs.intRange(1, WormBucketItem.MAX_WORMS))
            .networkSynchronized(ByteBufCodecs.VAR_INT)
            .build());

        BAIT_SOUND = sound(registrar, "item.worm.bait");
        HOOK_SOUND = sound(registrar, "item.worm.hook");

        WORM = registrar.register(BuiltInRegistries.ENTITY_TYPE, "worm", () -> {
            ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, DappledRevamp.id("worm"));
            return EntityType.Builder.of(Worm::new, MobCategory.AMBIENT).sized(0.8F, 0.5F).clientTrackingRange(10).build(key);
        });

        WORM_BIN = registrar.register(BuiltInRegistries.BLOCK, "worm_bin", () -> new WormBinBlock(
            BlockBehaviour.Properties.ofFullCopy(Blocks.COMPOSTER).setId(ResourceKey.create(Registries.BLOCK, DappledRevamp.id("worm_bin")))
        ));

        WORM_ITEM = item(registrar, "worm", Item::new, () -> new Item.Properties().stacksTo(16));
        WORM_BUCKET = item(registrar, "worm_bucket", WormBucketItem::new, () -> new Item.Properties()
            .stacksTo(1)
            .craftRemainder(Items.BUCKET)
            .component(WORM_COUNT.get(), 1));
        BAITED_ROD = item(registrar, "baited_rod", BaitedRodItem::new, () -> new Item.Properties().durability(64).enchantable(1));
        WORM_SPAWN_EGG = item(registrar, "worm_spawn_egg", SpawnEggItem::new, () -> new Item.Properties().spawnEgg(WORM.get()));
        WORM_BIN_ITEM = item(registrar, "worm_bin", p -> new BlockItem(WORM_BIN.get(), p), () -> new Item.Properties().useBlockDescriptionPrefix());

        TAB_ENTRIES = List.of(
            new TabEntry(tab("tools_and_utilities"), () -> Items.FISHING_ROD, BAITED_ROD),
            new TabEntry(tab("tools_and_utilities"), BAITED_ROD, WORM_ITEM),
            new TabEntry(tab("tools_and_utilities"), WORM_ITEM, WORM_BUCKET),
            new TabEntry(tab("functional_blocks"), () -> Items.COMPOSTER, WORM_BIN_ITEM),
            new TabEntry(tab("spawn_eggs"), () -> Items.WOLF_SPAWN_EGG, WORM_SPAWN_EGG)
        );
    }

    private static ResourceKey<CreativeModeTab> tab(String name) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace(name));
    }

    public record TabEntry(ResourceKey<CreativeModeTab> tab, Supplier<? extends ItemLike> after, Supplier<? extends ItemLike> item) {
    }

    private static Supplier<SoundEvent> sound(Registrar registrar, String name) {
        return registrar.register(BuiltInRegistries.SOUND_EVENT, name, () -> SoundEvent.createVariableRangeEvent(DappledRevamp.id(name)));
    }

    private static Supplier<Item> item(Registrar registrar, String name, Function<Item.Properties, Item> factory, Supplier<Item.Properties> properties) {
        return registrar.register(BuiltInRegistries.ITEM, name, () -> factory.apply(
            properties.get().setId(ResourceKey.create(Registries.ITEM, DappledRevamp.id(name)))
        ));
    }
}
