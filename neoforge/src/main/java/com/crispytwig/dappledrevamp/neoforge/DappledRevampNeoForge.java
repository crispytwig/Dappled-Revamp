package com.crispytwig.dappledrevamp.neoforge;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.Registrar;
import com.crispytwig.dappledrevamp.fox.GreyFox;
import com.crispytwig.dappledrevamp.moist.DryRecipe;
import com.crispytwig.dappledrevamp.moist.Moist;
import com.crispytwig.dappledrevamp.moist.MoistenRecipe;
import com.crispytwig.dappledrevamp.poplar.PoplarColor;
import com.crispytwig.dappledrevamp.worm.Worm;
import com.crispytwig.dappledrevamp.worm.WormContent;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod(DappledRevamp.MOD_ID)
public class DappledRevampNeoForge implements GreyFox.Storage, Registrar {
    private static final ResourceKey<CreativeModeTab> NATURAL_BLOCKS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace("natural_blocks"));
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, DappledRevamp.MOD_ID);
    private static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> GREY = ATTACHMENT_TYPES.register(
        "grey",
        () -> AttachmentType.builder(() -> false)
            .serialize(Codec.BOOL.fieldOf("grey"), grey -> grey)
            .sync(ByteBufCodecs.BOOL)
            .build()
    );
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, DappledRevamp.MOD_ID);

    static {
        RECIPE_SERIALIZERS.register("moisten", () -> MoistenRecipe.SERIALIZER);
        RECIPE_SERIALIZERS.register("dry", () -> DryRecipe.SERIALIZER);
    }

    private final IEventBus modEventBus;
    private final Map<ResourceKey<?>, DeferredRegister<?>> registers = new HashMap<>();

    public DappledRevampNeoForge(IEventBus modEventBus) {
        this.modEventBus = modEventBus;
        ATTACHMENT_TYPES.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        WormContent.register(this);
        modEventBus.addListener(DappledRevampNeoForge::addCreativeTabEntries);
        modEventBus.addListener(DappledRevampNeoForge::createAttributes);
        modEventBus.addListener(DappledRevampNeoForge::registerSpawnPlacements);
        DappledRevamp.init(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> factory) {
        DeferredRegister<Object> register = (DeferredRegister<Object>) this.registers.computeIfAbsent(registry.key(), key -> {
            DeferredRegister<?> created = DeferredRegister.create(registry, DappledRevamp.MOD_ID);
            created.register(this.modEventBus);
            return created;
        });
        return register.register(name, factory);
    }

    private static void createAttributes(EntityAttributeCreationEvent event) {
        event.put(WormContent.WORM.get(), Worm.createAttributes().build());
    }

    private static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
            WormContent.WORM.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Worm::checkWormSpawnRules,
            RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }

    private static void addCreativeTabEntries(BuildCreativeModeTabContentsEvent event) {
        Moist.insertMoistBefore(List.copyOf(event.getParentEntries()), (dry, moist) -> {
            boolean parent = !event.getParentEntries().contains(moist);
            boolean search = !event.getSearchEntries().contains(moist) && event.getSearchEntries().contains(dry);
            if (parent || search) {
                event.insertBefore(dry, moist, parent && search ? CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                    : parent ? CreativeModeTab.TabVisibility.PARENT_TAB_ONLY : CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
            }
        });
        for (WormContent.TabEntry entry : WormContent.TAB_ENTRIES) {
            if (event.getTabKey().equals(entry.tab())) {
                event.insertAfter(new ItemStack(entry.after().get()), new ItemStack(entry.item().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
        if (!event.getTabKey().equals(NATURAL_BLOCKS)) {
            return;
        }
        ItemStack poplar = PoplarColor.ORANGE.sapling();
        event.insertBefore(poplar, PoplarColor.RED.sapling(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        event.insertAfter(poplar, PoplarColor.YELLOW.sapling(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    @Override
    public boolean isGrey(Fox fox) {
        return fox.getData(GREY);
    }

    @Override
    public void setGrey(Fox fox, boolean grey) {
        fox.setData(GREY, grey);
    }
}
