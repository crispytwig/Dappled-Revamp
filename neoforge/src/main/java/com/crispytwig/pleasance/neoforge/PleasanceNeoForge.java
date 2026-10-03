package com.crispytwig.pleasance.neoforge;

import com.crispytwig.pleasance.Pleasance;
import com.crispytwig.pleasance.datagen.ModDataGenerators;
import com.crispytwig.pleasance.neoforge.platform.NeoForgeRegistrationProvider;
import com.crispytwig.pleasance.registry.ModCreativeTab;
import com.crispytwig.pleasance.world.entity.animal.fox.GreyFox;
import com.crispytwig.pleasance.world.level.block.Moist;
import com.crispytwig.pleasance.world.level.block.PoplarColor;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

@Mod(Pleasance.MOD_ID)
public class PleasanceNeoForge implements GreyFox.Storage {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Pleasance.MOD_ID);
    private static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> GREY = ATTACHMENT_TYPES.register(
        "grey",
        () -> AttachmentType.builder(() -> false)
            .serialize(Codec.BOOL.fieldOf("grey"), grey -> grey)
            .sync(ByteBufCodecs.BOOL)
            .build()
    );

    public PleasanceNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        NeoForgeRegistrationProvider.EVENT_BUS = modEventBus;
        Pleasance.init(this);
        Pleasance.bootstrap();
        ATTACHMENT_TYPES.register(modEventBus);

        modEventBus.addListener(this::createAttributes);
        modEventBus.addListener(this::registerSpawnPlacements);
        modEventBus.addListener(PleasanceNeoForge::addCreativeTabEntries);
        modEventBus.addListener(ModDataGenerators::gatherData);

        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            PleasanceNeoForgeClient.init(modEventBus, modContainer);
        }
    }

    private void createAttributes(EntityAttributeCreationEvent event) {
        Pleasance.createAttributes((type, builder) -> event.put(type, builder.build()));
    }

    private void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        Pleasance.registerSpawnPlacements(new Pleasance.SpawnPlacementRegistrar() {
            @Override
            public <T extends Mob> void register(EntityType<T> type, SpawnPlacementType placementType, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate) {
                event.register(type, placementType, heightmap, predicate, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            }
        });
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
        for (ModCreativeTab.Entry entry : ModCreativeTab.ENTRIES) {
            if (event.getTabKey().equals(entry.tab())) {
                event.insertAfter(new ItemStack(entry.after().get()), new ItemStack(entry.item().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
        if (!event.getTabKey().equals(ModCreativeTab.NATURAL_BLOCKS)) {
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
