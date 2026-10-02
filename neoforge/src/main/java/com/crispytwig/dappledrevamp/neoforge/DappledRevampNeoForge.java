package com.crispytwig.dappledrevamp.neoforge;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.fox.GreyFoxStorage;
import com.crispytwig.dappledrevamp.poplar.PoplarColor;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@Mod(DappledRevamp.MOD_ID)
public class DappledRevampNeoForge implements GreyFoxStorage {
    private static final ResourceKey<CreativeModeTab> NATURAL_BLOCKS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace("natural_blocks"));
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, DappledRevamp.MOD_ID);
    private static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> GREY = ATTACHMENT_TYPES.register(
        "grey",
        () -> AttachmentType.builder(() -> false)
            .serialize(Codec.BOOL.fieldOf("grey"), grey -> grey)
            .sync(ByteBufCodecs.BOOL)
            .build()
    );

    public DappledRevampNeoForge(IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);
        modEventBus.addListener(DappledRevampNeoForge::addCreativeTabEntries);
        DappledRevamp.init(this);
    }

    private static void addCreativeTabEntries(BuildCreativeModeTabContentsEvent event) {
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
