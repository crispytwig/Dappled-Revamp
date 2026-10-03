package com.crispytwig.dappledrevamp.neoforge;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.fox.GreyFox;
import com.crispytwig.dappledrevamp.moist.DryRecipe;
import com.crispytwig.dappledrevamp.moist.Moist;
import com.crispytwig.dappledrevamp.moist.MoistenRecipe;
import com.crispytwig.dappledrevamp.poplar.PoplarColor;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

@Mod(DappledRevamp.MOD_ID)
public class DappledRevampNeoForge implements GreyFox.Storage {
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

    public DappledRevampNeoForge(IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        modEventBus.addListener(DappledRevampNeoForge::addCreativeTabEntries);
        DappledRevamp.init(this);
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
