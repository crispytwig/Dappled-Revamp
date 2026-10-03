package com.crispytwig.dappledrevamp.registry;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.platform.registry.DeferredHolder;
import com.crispytwig.dappledrevamp.platform.registry.DeferredRegister;
import com.crispytwig.dappledrevamp.world.item.BaitedRodItem;
import com.crispytwig.dappledrevamp.world.item.WormBucketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, DappledRevamp.MOD_ID);

    public static final DeferredHolder<Item, Item> WORM = registerItem("worm", new Item.Properties().stacksTo(16));
    public static final DeferredHolder<Item, WormBucketItem> WORM_BUCKET = ITEMS.register("worm_bucket", () -> new WormBucketItem(new Item.Properties().setId(itemKey("worm_bucket"))
            .stacksTo(1)
            .craftRemainder(Items.BUCKET)
            .component(ModDataComponents.WORM_COUNT.get(), 1)));
    public static final DeferredHolder<Item, BaitedRodItem> BAITED_ROD = registerItem("baited_rod", BaitedRodItem::new, new Item.Properties().durability(64).enchantable(1));
    public static final DeferredHolder<Item, SpawnEggItem> WORM_SPAWN_EGG = registerSpawnEgg("worm_spawn_egg", ModEntityTypes.WORM);

    private static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, DappledRevamp.location(name));
    }

    private static DeferredHolder<Item, Item> registerItem(String name, Item.Properties properties) {
        return registerItem(name, Item::new, properties);
    }

    static <T extends Item> DeferredHolder<Item, T> registerItem(String name, Function<Item.Properties, T> factory, Item.Properties properties) {
        return ITEMS.register(name, () -> factory.apply(properties.setId(itemKey(name))));
    }

    private static <T extends Mob> DeferredHolder<Item, SpawnEggItem> registerSpawnEgg(String name, Supplier<EntityType<T>> type) {
        return ITEMS.register(name, () -> new SpawnEggItem(new Item.Properties().setId(itemKey(name)).spawnEgg(type.get())));
    }
}
