package com.crispytwig.dappledrevamp.neoforge;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.fox.GreyFoxStorage;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.animal.fox.Fox;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@Mod(DappledRevamp.MOD_ID)
public class DappledRevampNeoForge implements GreyFoxStorage {
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
        DappledRevamp.init(this);
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
